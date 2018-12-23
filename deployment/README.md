# Deployment

This folder contains Ansible scripts to deploy the service
to a AWS AutoScalingGroup.

Prerequisite: AWS account

I've implemented two different ways to to this. See the two roles
`betrcode.aws-asg` and `betrcode.aws-cf-asg`.

## Not using CloudFormation (betrcode.aws-asg)

In this first implementation, I just used Ansible modules to create all the needed AWS resources,
and used naming conventions and tags to indicate which resources belong to the same "stack".

This was possible, and nice to not need to use the (awful?) CloudFormation format. But it has downsides.
The main downside is probably the cumbersome cleanup (deletion) of resources by name or tag. It would
be easy to screw up and delete the wrong thing.

This led me to try the same thing, but using CloudFormation instead. See *Using CloudFormation* below.


### Overview of how it works

Every time the playbook is run it will:

* Create a new load balancer
* Create a new launch configuration
* Create a new autoscaling group
* Wait for the new load balancer to report n healthy instances
* Create/update the DNS alias to point to the new load balancer
* Delete (cleanup) any old resources (previously created by this playbook)

The main benefit of this is that *all* infrastructure is replaced on every deployment.
 

### How to deploy

Required parameters:

* `vpc` - the AWS VPC identifier (your vpc)
* `region` - the AWS region to deploy to (example: eu-west-1)
* `subnets` - a list of subnets to deploy to. Must be at least 2. (needs to exist already)
* `aws_key` - the instance key which can be used to log in to the created instances (needs to exist already)
* `route53_zone` - the Route 53 zone where you want to create your DNS entry. (needs to exist already)
* `instance_profile` - the name of the instance profile (or IAM Role) that the created instances will get. (needs to exist already)

Required environment variables:
* `AWS_ACCESS_KEY_ID`
* `AWS_SECRET_ACCESS_KEY`

(or other way of authenticating)

Example command: `ansible-playbook deploy.yml -e "vpc=vpc-8eb15888 aws_key=betrcode-amazon region=eu-central-1 subnets=subnet-171bf888 route53_zone=bettercode.se"`


#### To delete all created resources

To delete all resources created by this playbook, 
run the playbook with the `cleanup` tag. This will not create
any new resources and will delete all old resources. 

Example: `ansible-playbook deploy.yml -e "vpc=vpc-8eb15888 aws_key=betrcode-amazon region=eu-central-1 subnets=subnet-171bf888 route53_zone=bettercode.se" --tags cleanup`


## Using CloudFormation (betrcode.aws-cf-asg)

In this second implementation, I still use Ansible, but most of the resources are created by 
CloudFormation. I kept the Route 53 DNS entry outside CloudFormation because it acts as a pointer 
between different ELBs (inside stacks) and can not be modified this way if it was inside a stack.

The main upside of using CloudFormation is that cleanup of old resources is much easier.
However, the Ansible module for querying which stacks exists is not very nice. It returns a hard-to-use
data structure (for my use-case) and offers very little querying options. 

### Overview of how it works

Every time the playbook is run it will:

* Create a new CloudFormation stack containing:
** A new SecurityGroup for the instances
** A new SecurityGroup for the ElasticLoadBalancer
** A new LaunchConfiguration for the AutoScalingGroup
** A new TargetGroup for the AutoScalingGroup
** A new AutoScalingGroup
** A new ElasticLoadBalancer
** A new Listener for the ElasticLoadBalancer
* Create/update the DNS alias to point to the new load balancer
* Delete (cleanup) any old stacks created by this role

The main benefit of this is that *all* infrastructure is replaced on every deployment.


### How to deploy

Required parameters:

* `vpc` - the AWS VPC identifier (your vpc)
* `region` - the AWS region to deploy to (example: eu-west-1)
* `subnets` - a list of subnets to deploy to. Must be at least 2. (needs to exist already)
* `aws_key` - the instance key which can be used to log in to the created instances (needs to exist already)
* `route53_zone` - the Route 53 zone where you want to create your DNS entry. (needs to exist already)
* `instance_profile` - the name of the instance profile (or IAM Role) that the created instances will get. (needs to exist already)

Required environment variables:
* `AWS_ACCESS_KEY_ID`
* `AWS_SECRET_ACCESS_KEY`

(or other way of authenticating)

Example command: `ansible-playbook deploy-with-cloudformation.yml -e "vpc=vpc-8eb15888 aws_key=betrcode-amazon region=eu-central-1 subnets=subnet-171bf888 route53_zone=bettercode.se"`

#### To delete all created resources

To delete all resources created by this playbook, 
run the playbook with the `cleanup` tag. This will not create
any new resources and will delete all old resources. 

Example: `ansible-playbook deploy-with-cloudformation.yml -e "vpc=vpc-8eb15888 aws_key=betrcode-amazon region=eu-central-1 subnets=subnet-171bf888 route53_zone=bettercode.se" --tags cleanup`

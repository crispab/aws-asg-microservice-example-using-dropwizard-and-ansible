# Deployment

This folder contains a Ansible playbook to deploy the service
to a AWS AutoScalingGroup.

Prerequisite: AWS account

The Ansible roles used in the playbook can be found in the following repositories:
* https://github.com/betrcode/ansible_role_aws_ecr_docker_push
* https://github.com/betrcode/ansible-role-aws-cloudformation-asg

...and published to Ansible Galaxy:
* https://galaxy.ansible.com/betrcode/aws_ecr_docker_push
* https://galaxy.ansible.com/betrcode/aws_cloudformation_asg


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
    * A new SecurityGroup for the instances
    * A new SecurityGroup for the ElasticLoadBalancer
    * A new LaunchConfiguration for the AutoScalingGroup
    * A new TargetGroup for the AutoScalingGroup
    * A new AutoScalingGroup
    * A new ElasticLoadBalancer
    * A new Listener for the ElasticLoadBalancer
* Create/update the DNS alias to point to the new load balancer
* Delete (cleanup) any old stacks created by this role

The main benefit of this is that *all* infrastructure is replaced on every deployment.


### How to deploy

Before using the playbook for the first time, you need to install the required roles.
Run: `./install-requirements.sh`

Required parameters:

* `vpc` - the AWS VPC identifier (your vpc)
* `region` - the AWS region to deploy to (example: eu-west-1)
* `subnets` - a list of subnets to deploy to. Must be at least 2. (needs to exist already)
* `aws_key` - the instance key which can be used to log in to the created instances (needs to exist already)
* `route53_zone` - the Route 53 zone where you want to create your DNS entry. (needs to exist already)
* `instance_profile` - the name of the instance profile (or IAM Role) that the created instances will get. (needs to exist already)

You can define these in a file like: `vars/betrcode-extra-vars.yml`


Required environment variables:
* `AWS_ACCESS_KEY_ID`
* `AWS_SECRET_ACCESS_KEY`

(or other way of authenticating)

Example command: `ansible-playbook deploy.yml -e "vpc=vpc-8eb15888 aws_key=betrcode-amazon region=eu-central-1 subnets=[subnet-171bf888,subnet-171bf889] route53_zone=bettercode.se"`

Or, if you (like me) put your variables in a file:
`ansible-playbook deploy.yml --extra-vars @vars/betrcode-extra-vars.yml`


#### To delete all created resources

To delete all resources created by this playbook, 
run the playbook with the `cleanup` tag. This will not create
any new resources and will delete all old resources. 

Example: `ansible-playbook deploy.yml -e "vpc=vpc-8eb15888 aws_key=betrcode-amazon region=eu-central-1 subnets=[subnet-171bf888,subnet-171bf889] route53_zone=bettercode.se" --tags cleanup`

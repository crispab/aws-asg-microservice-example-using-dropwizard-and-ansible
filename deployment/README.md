# Deployment

This folder contains Ansible scripts to deploy the service
to a AWS AutoScalingGroup.

Prerequisite: AWS account

## Overview of how it works

Every time the playbook is run it will:

* Create a new load balancer
* Create a new launch configuration
* Create a new autoscaling group
* Wait for the new load balancer to report n healthy instances
* Create/update the DNS alias to point to the new load balancer
* Delete (cleanup) any old resources (previously created by this playbook)

The main benefit of this is that *all* infrastructure is replaced on every deployment.
 

## How to deploy

Required parameters:

* `vpc` - the AWS VPC identifier (your vpc)
* `region` - the AWS region to deploy to (example: eu-west-1)
* `subnets` - the subnets to deploy to (needs to exist already)
* `aws_key` - the instance key which can be used to log in to the created instances (needs to exist already)
* `route53_zone` - the Route 53 zone where you want to create your DNS entry. (needs to exist already)

Required environment variables:
* `AWS_ACCESS_KEY_ID`
* `AWS_SECRET_ACCESS_KEY`

(or other way of authenticating)

Example command: `ansible-playbook deploy.yml -e "vpc=vpc-8eb15888 aws_key=betrcode-amazon region=eu-central-1 subnets=subnet-171bf888 route53_zone=bettercode.se"`

### To delete all created resources

To delete all resources created by this playbook, 
run the playbook with the `cleanup` tag. This will not create
any new resources and will delete all old resources. 

Example: `ansible-playbook deploy.yml -e "vpc=vpc-8eb15888 aws_key=betrcode-amazon region=eu-central-1 subnets=subnet-171bf888 route53_zone=bettercode.se" --tags cleanup`

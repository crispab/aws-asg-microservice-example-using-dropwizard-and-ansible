# Deployment

This folder contains a Ansible playbook to deploy the service
to a AWS AutoScalingGroup.

Prerequisite: AWS account


## How it works

The playbook uses two roles. How those roles work is described in the role repositories: 

* https://github.com/crispab/ansible_role_aws_ecr_docker_push
* https://github.com/crispab/ansible-role-aws-cloudformation-asg

## How to deploy

Before using the playbook for the first time, you need to install the required roles.
Run: `./install-requirements.sh`

Required parameters:

* `vpc` - the AWS VPC identifier (your vpc)
* `region` - the AWS region to deploy to (example: eu-west-1)
* `subnets` - a list of subnets to deploy to. Must be at least 2. (needs to exist already)
* `aws_key` - the instance key which can be used to log in to the created instances (needs to exist already)
* `route53_zone` - the Route 53 zone where you want to create your DNS entry. (needs to exist already)
* `instance_profile` - the name of the instance profile (or IAM Role) that the created instances will get. (needs to exist already)

You can define these in a file like: `vars/crisp-extra-vars.yml`


Required environment variables:
* `AWS_ACCESS_KEY_ID`
* `AWS_SECRET_ACCESS_KEY`

(or other way of authenticating)

Example command: `ansible-playbook deploy.yml -e "vpc=vpc-8eb15888 aws_key=crisp-amazon region=eu-central-1 subnets=[subnet-171bf888,subnet-171bf889] route53_zone=crisp.se"`

Or, if you (like me) put your variables in a file:
`ansible-playbook deploy.yml --extra-vars @vars/crisp-extra-vars.yml`


### To delete all created resources

To delete all resources created by this playbook, 
run the playbook with the `cleanup` tag. This will not create
any new resources and will delete all old resources. 

Example: `ansible-playbook deploy.yml -e "vpc=vpc-8eb15888 aws_key=crisp-amazon region=eu-central-1 subnets=[subnet-171bf888,subnet-171bf889] route53_zone=crisp.se" --tags cleanup`

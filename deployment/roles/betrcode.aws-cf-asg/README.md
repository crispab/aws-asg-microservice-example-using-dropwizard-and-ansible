# AWS CloudFormation AutoScalingGroup

This role creates a AWS AutoScalingGroup and a 
ElasticLoadBalancer. The ASG will start a Docker image of 
your choice.

See `defaults/main.yml` for variables that you should override.
See `vars/main.yml` for variables that you can, but probably
should not override.

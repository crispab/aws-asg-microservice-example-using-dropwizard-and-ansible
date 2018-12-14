#!/bin/bash -ex

# This script will be run on instances on startup.
# Output is logged to /var/log/cloud-init-output.log on the instances.

# Install awscli
yum -y install awscli

# Login to ECR
$(aws ecr get-login --no-include-email --region {{ region }})

# Pull and run the docker image
docker run --name "{{ aws_asg_input.app_name }}" -p 8080:8080 -p 8081:8081 -d "{{ docker_image }}"

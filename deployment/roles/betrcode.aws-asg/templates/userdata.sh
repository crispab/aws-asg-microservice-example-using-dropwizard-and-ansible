#!/bin/bash -ex

# This script will be run on instances on startup.
# Output is logged to /var/log/cloud-init-output.log on the instances.

# TODO: docker pull & docker run our own image, not nginx
# But nginx will serve for now.
docker run --name my-nginix -p 8080:80 -d nginx

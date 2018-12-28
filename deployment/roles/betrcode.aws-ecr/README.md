# betrcode AWS ECR

This role creates a Container Registry and uploads Docker image
to the registry.

See `defaults/main.yml` for variables.


Requirements
------------

AWS credentials. 
Permissions to create a Elastic Container Registry.
Permissions to publish to a Elastic Container Registry.

Packages:

* boto3


Role Variables
--------------

`ecr_name` - The name of the Elastic Container Reqistry to create/use.
Example value: *betrcode/goodtimes*


`source_image_tag` The full image name including tag name to push.
Example value: *betrcode/goodtimes:latest*


Dependencies
------------

Not dependent on any other role.


Example Playbook
----------------

---

    - hosts: localhost
      connection: local
      roles:
        - role: betrcode.aws-erc
          ecr_name: "betrcode/goodtimes"
          source_image_tag: "betrcode/goodtimes:latest"


License
-------

MIT


Author Information
------------------

Max Wenzin, partner at Crisp

https://www.crisp.se/konsulter/max-wenzin


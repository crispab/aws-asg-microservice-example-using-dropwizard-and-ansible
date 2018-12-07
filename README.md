# AWS ASG example application

The main purpose of this repository is to demonstrate a way to
deploy a application to a AWS AutoScalingGroup (ASG).

I've included a simple application called *goodtimes* to show 
how it all fits together in a single repository.

I am using some of my current favourite languages, tools and frameworks:

* Gradle for building etc
* Java for the actual application
* DropWizard as a simple microservice REST application framework
* Docker for packaging the application 
* Google JIB for building a Docker image
* Ansible for setting up AWS infrastructure and deploying

## How to run app using Gradle
`./gradlew run`

Stop app with *Ctrl + C*.

## How to build Docker image and run it
Prerequisite: Docker is installed

* `./gradlew jibDockerBuild`
* `docker run docker run goodtimes:latest`

Stop app with *Ctrl + C*.

## How to deploy app to AWS
See: [deployment/README.md](deployment/README.md)

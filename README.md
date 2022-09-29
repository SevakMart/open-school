# Open school

Java 11 Sprint Boot Gradle application.

Build Job https://github.com/SevakMart/open-school/actions/workflows/upload_artifact.yml
## Running on local environment
Steps before running Open school backend on Windows:
* Install docker
  * Follow steps in the article to install docker https://docs.docker.com/desktop/install/windows-install/
  * In root folder of the application run the following command `docker-compose up -d` this will create a container with MySQL instance. 
* Set property ```-Dspring.profiles.active=local``` in Intellij Idea Run/Debug Configuration VM Options\
  If you run via Gradle-based Run Configuration, add `SPRING_PROFILES_ACTIVE=local` to Intellij Idea Run/Debug Configuration Environment Variables instead.

## Deployment
To deploy to dev environment
* use the following job: https://github.com/SevakMart/open-school/actions/workflows/deploy.yml
* Specify the version that needs to be deployed
## HTTPS

### Self-signed certificate

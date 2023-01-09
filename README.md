# Open school

Java 11 Sprint Boot Gradle application.

Build Job https://github.com/SevakMart/open-school/actions/workflows/upload_artifact.yml
## Running on local environment
Steps before running Open school backend on Windows:
* Install docker
  * Follow steps in the article to install docker https://docs.docker.com/desktop/install/windows-install/
  * In root folder of the application run the following command `docker-compose up -d` this will create a container with MySQL instance. 
* Set property ```-Dspring.profiles.active=local``` in Intellij Idea Run/Debug Configuration VM Options\
  If you run via Gradle-based Run Configuration, add `SPRING_PROFILES_ACTIVE=local` to Intellij Idea Run/Debug Configuration Environment Variables instead.\

### If you use terminal to run:
* after `install Docker` step
  * In root folder of the application run the following command ```gradlew clean bootRun --args='--spring.profiles.active=local'```.
  * Enter the command ```CTRL + C``` to stop the application ( ``Terminate batch job (Y/N)?`` ->
      enter ```Y``` ).

## Running locally without Docker (using H2 database)

Steps before running Open school project on Windows:

* Clone the project (if the project already exists on the computer, skip this step)
    * Open the terminal (start -> cmd -> enter or WIN+R -> cmd -> enter).
    * Enter the command ```git clone https://github.com/SevakMart/open-school.git OPEN-SCHOOL-PROJECT```.
    * To check, in terminal enter ```dir``` command to browse folders in current directory, you should
      see ``OPEN-SCHOOL-PROJECT`` folder.
    * Go to project root folder ( enter the command ```cd OPEN-SCHOOL-PROJECT``` ) .

* To run project
    * In the project root folder in terminal run the following command ``gradlew clean bootRun --args='--spring.profiles.active=test'``.
    * Enter the command ```CTRL + C``` to stop ``OPEN-SCHOOL-PROJECT`` ( ``Terminate batch job (Y/N)?`` ->
      enter ```Y``` ).

> Information from the H2 database will be deleted when OPEN-SCHOOL_PROJECT is stopped .


## Deployment
To deploy to dev environment
* use the following job: https://github.com/SevakMart/open-school/actions/workflows/deploy.yml
* Specify the version that needs to be deployed
## HTTPS

### Self-signed certificate

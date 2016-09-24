Setup
----
1. Install git (https://git-scm.com/book/en/v2/Getting-Started-Installing-Git)
2. In a terminal, run `git clone https://github.com/dandoman/team9sofine.git`
3. Then `$cd service-in-a-box`

#### Back End
What you need:

* Java 8 - http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
* Maven - https://maven.apache.org/download.cgi
* Postgres - http://www.postgresql.org/download/

Getting set up:

* Start your postgres database 
* In src/main/resources, use the two scripts to create the database, user and tables. Must be run manually at the moment.

Running during normal development: 

*All of the following are to be run from the project root directory*

* 'mvn spring-boot:run' - will run the back end on port 8080
* 'mvn package' - will build the fat jar with the embedded tomcat server
* Once the fat jar is built, can run using 'java -jar ${jarName}'

While the service is running, you can see some basic back end swagger API documentation at 
* http://localhost:8080/v2/api-docs

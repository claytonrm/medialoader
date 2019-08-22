# Media Loader
Media loader for Facebook Users<br>
*An application to load all of your Facebook photos*

### Get Started

* Clone this repository using either SSH or HTTP on https://github.com/claytonrm/medialoader
* Install requirements

### Requirements
- [Docker](https://www.docker.com)
- [Java 8](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html?printOnly=1)
- [Lombok](https://projectlombok.org/download)
- [Maven](https://maven.apache.org/download.cgi)
- [Facebook User Access Token](https://developers.facebook.com/docs/facebook-login/access-tokens/)

### Setup
Set your docker up following the steps below to create a postgres instance:

##### Download postgres
 ```shell 
 docker pull postgres
 ```
##### Create a network
 ```shell 
 docker network create --driver bridge medialoader-network
 ```
##### Setup postgres
 ```shell 
docker run --name medialoader-postgres --network=medialoader-network -e "POSTGRES_PASSWORD=Medialoader2019" -p 5432:5432 -d postgres
 ```
##### Create a database called *medialoaderdb*
 ```shell 
docker exec medialoader-postgres psql -U postgres -c "CREATE DATABASE medialoaderdb" postgres
 ```

#### Installing pgAdmin 4
* Just in case you want to query your data


```shell
docker run --name pgadmin --network=medialoader-network -p 15432:80 -e "PGADMIN_DEFAULT_EMAIL=typeyour@email.com" -e "PGADMIN_DEFAULT_PASSWORD=createapassword" -d dpage/pgadmin4
```

 After that you are able to access `http://localhost:15432`

### Running App

* Open the project on your IDE and run MedialoaderApplication main class or in your bash (project root) `mvn spring-boot:run`
* Check the URL `http://localhost:8080/swagger-ui.html`

#### Available operations
* You can try it out each of operations available in above URL or via [Postman](https://www.getpostman.com/)

### Running Tests
```shell
mvn test
```


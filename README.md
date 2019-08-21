# Media Loader
Media loader for Facebook Users

## Requirements
- Docker
- Java 8
- Facebook Account

## Setup

```shell
docker pull postgres

# creating a network
docker network create --driver bridge medialoader-network

docker run --name medialoader-postgres --network=medialoader-network -e "POSTGRES_PASSWORD=Medialoader2019" -p 5432:5432 -d postgres

#create a database called "medialoaderdb"
docker exec medialoader-postgres psql -U postgres -c "CREATE DATABASE medialoaderdb" postgres

# Installing pgadmin4
docker run --name pgadmin --network=medialoader-network -p 15432:80 -e "PGADMIN_DEFAULT_EMAIL=typeyour@email.com" -e "PGADMIN_DEFAULT_PASSWORD=createapassword" -d dpage/pgadmin4

```

 - After that you are able to access `http://localhost:15432`


## Running

```
	mvn spring-boot:run

```




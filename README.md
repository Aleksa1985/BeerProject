
#Project

This project is a simple test project and can be used as template for
future projects that will be written in SPRING BOOT technology.
In this project you are able to find most of the things that one rest service needs as a template for production purpose.
In this sample we have covered creating of REST api service based on spring boot, with hibernate ORM and H2 as database.
Because it's a Hibernate used thru spring data, it is very simple change to be attached to any other database.

Basically this project has one purpose:
To fetch data (beers) from external api provider, store data to database, 
and then to manipulate with these data using few api calls.

#Technologies used
- java 13 (but only java 8 language features)
- Spring Boot
- H2
- DOCKER

#Execution
using Docker container:
```
docker build -t project . && docker run -p 9999:9999 -it project
```
using only java (before executing this command you must have installed java and maven):
```
mvn clean install;cd target; java -jar project-1.0-SNAPSHOT.jar
```

#Check the puls
This is a simple custom health check to show what is the status of the app.
For dependencies this application only have database and the Punkapi beer api, but I didn't found hc 
,and it is not implemented.

```
curl -X GET http://localhost:9999/health/healthcheck
```

#Simple CURLS for triggering api calls

- FIND_ALL_BEERS: (return all beers from database)
```
curl -X GET http://localhost:9999/v1/beers
```
- FIND_ONE_BEER_BY_ID: (return only one beer from database)
```
curl -X GET http://localhost:9999/v1/beers/{id}
```
- SAVE_BEERS: (trigger the mechanism to repopulate database with beers)
```
curl -X POST http://localhost:9999/v1/beers
```
- DELETE_BEERS: (delete all beers from the database)
```
curl -X DELETE http://localhost:9999/v1/beers
```
- DELETE_ONE_BEER_BY_ID: (delete one beer from the database)
```
curl -X DELETE http://localhost:9999/v1/beers/{id}
```

#Technical features
- Implemented logging
- Implemented health check
- basic tests (unit and integration)
- UAT test in form of one simple test (where is covered how will end user target this application)

#Improvements
- it will be good that application is runned with docker-compose with database and some external caching like redis
- in v2 because beer population is async and random ,it can be created mechanism to populate some cache periodically from the provider and when POST /beers call is received that simple difference of beers can be moved from cache to DB.
  This will reduce waiting between POST beers and GET beers calls.
- Spring security and user service to be added


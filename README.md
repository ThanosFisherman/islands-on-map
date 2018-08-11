Description
------------
This is a home task for a remote backend position that was assigned to me a while ago.

My Solution was rejected with the following reasoning:

_"The company demands a very high skill set for its remote team therefore we had to choose other applicants who did better."_

So I decided to post my solution to GitHub and kindly seek for contributions in order to improve my Code Quality, but
I will most likely continue improving / optimizing this little project myself anyways.

So The next step is probably to further lower the coupling of components, then write some tests and later on make the project reactive using WebFlux (Reactor) or RxJava.

**NOTE: My future commits are expected to deviate from the current requirements of this task therefore my code is not to be consider a solution to this specific exercise. Rather an individual project that will keep evolving through time.**

Tech Stack
-----------
The following technologies were used for this project.

* MongoDB (Running locally on a database called _pirates_)
* Spring Boot
* Kotlin
* Gradle
* Docker
* Git (Duh)

Instructions
-------------
Following are the instructions for this task:
 
#### Task
* Implement an API-only Spring Boot application.
* You can use https://start.spring.io/ to create the project from zero, please select Gradle, Kotlin and any Spring Boot version you would like to use.
* You can use any database of your choice.
* You can use any libs you like.
* Use a local git repository and send us a tarball archive with your solution by email.
* Don't put your solution on a publicly accessible repository.

Your App should have 3 API endpoints 
- POST "api/maps"
- GET "api/islands" 
- GET "api/islands/:id" 
 
When a POST call to "api/maps" is made the app should make a GET call to https://private-2e8649-advapi.apiary-mock.com/map
and create in the database entries for: 
- The given "Map" and the "Tiles" that belong to the map 
- The islands that can be detected on the map with related tiles 
- An island should belong to a map

**In my future commits calling the `POST` endpoint above won't be necessary because my service will initialize its database by fetching https://private-2e8649-advapi.apiary-mock.com/map at startup.**
 
An island is formed by tiles of type "land" which are surrounded by "water" tiles. Two tiles belong to the same island if they touch.
Tiles only count as touching if they are directly vertically or horizontally next to each other. If they touch with their corners they do not count as touching.  
 
When making a GET call to "api/islands" you should retrieve all islands with the tiles that belong to them. 
 
When making a GET call to "api/islands/:id" you should retrieve just one island with the matching id. Also including related tiles. 

#### Bonus:
- Provide a Dockerfile which allows to run your app  
- Add an API endpoint that returns the map in ASCII style. Output could be something like: 

```
#---x-- 
#--xxx- 
#----x-
```

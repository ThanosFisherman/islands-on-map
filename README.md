Description
------------
This is a home task for a remote backend position that was assigned to me by a Swiss company named [Advanon](https://www.advanon.com/en).

My Solution was rejected with the following reasoning:

_"The company demands a very high skill set for its remote team therefore we had to choose other applicants who did better."_

So I decided to post my solution to GitHub and kindly ask for **Contributions** in order to improve my Code Quality, but
I will probably continue improving / optimizing this little project myself anyways even if I don't get any.

So The next step is probably to make the project reactive using WebFlux (Reactor) or RxJava. Then write some tests and see how it goes from there.

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

### Islands on a map
 
Advanon decided to work with the marine to fight pirates and we need to index maps and islands on those maps.
Given is an API endpoint providing you with tiles for a map. https://advapi.docs.apiary.io/

#### Task
* Implement an API-only Spring Boot application.
* You can use https://start.spring.io/ to create the project from zero, please select Gradle, Kotlin and any Spring Boot version you would like to use.
* You can use any database of your choice.
* You can use any libs you like.
* Use a local git repository and send us a tarball archive with your solution by email.
* Don't put your solution on a publicly accessible repository. (#SorryNotSoSorry :P)
 
Your App should have 3 API endpoints 
- POST "api/maps" 
- GET "api/islands" 
- GET "api/islands/:id" 
 
When a POST call to "api/maps" is made the app should make a GET call to https://private-2e8649-advapi.apiary-mock.com/map
and create in the database entries for: 
- The given "Map" and the "Tiles" that belong to the map 
- The islands that can be detected on the map with related tiles 
- An island should belong to a map 
 
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

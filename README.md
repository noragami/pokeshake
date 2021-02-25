# :whale: Pokeshake REST API
> Pokemons described by Shakespeare. **Did get to catcheth those folk all!**

My response to a software engineering challenge. 

To me, it was quite fun doing this challenge, I hope you'll have a good time looking at it!

### :pencil2: The Task

Develop a REST API that, given a Pokemon name, returns its Shakespearean description.

### :coffee: Technology stack

* Main 
    * [Kotlin](https://kotlinlang.org/)
    * [Spring Boot](https://spring.io/projects/spring-boot)
* Unit tests
    * [JUnit 5](https://junit.org/junit5/)
    * [Mockito(Kotlin)](https://github.com/mockito/mockito-kotlin)
* Integration/E2E tests
    * [WireMock](http://wiremock.org/)
    * [RestAssured](https://rest-assured.io/)
* Patterns/practices
    * [Microservice](https://en.wikipedia.org/wiki/Microservices)
    * [Hexagonal architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software))
    * [TDD](https://en.wikipedia.org/wiki/Test-driven_development)
* Integrations
    * [PokeAPI](https://pokeapi.co/)
    * [Shakespeare Translator](https://funtranslations.com/api/shakespeare)

### :toolbox: Prerequisites

You'll need [Docker](https://www.docker.com/), [Java 8](https://www.java.com/en/download/) and [Maven 3.5+](https://maven.apache.org/download.cgi) installed on your favourite OS.

### :link: Clone source code

```
git clone https://github.com/noragami/pokeshake.git
cd pokeshake
```

### :alembic: Run tests

```
mvn clean verify
```

### :video_game: Run application
You can build/run the application packaged as JAR file via Maven and Java...

```
mvn clean package
java -jar target/pokeshake.jar
```

... or with Docker (it might take a while to download images the first time):

```
docker build -t pokeshake/0.0.1 .
docker run -p 8080:8080 pokeshake/0.0.1
```

### :hammer: Test application
```
curl http://localhost:8080/pokemon/pikachu

{"name":"pikachu","description":"At which hour several of these pokémon gather,  their electricity couldst buildeth and cause lightning storms."}
```

## :memo: Additional notes

##### :bug: Error handling
Shakespeare translator API has a quota limit on the free tier (60 API calls/day, 5 calls/hour).
If you reach the quota limit, the API will send an http response code `429 - Too Many Requests`.
For this reason, I made the decision to forward the same http response code to the client,
instead of failing badly with a `500` response code. So we can keep the information and provide the right feedback to the client.

I also implemented a `404` response code if the client requests a Pokemon that doesn't exist.


## :bulb: Improvement areas for production ready product

- [ ] Document API using OpenAPI/Swagger (using Swagger UI)
- [ ] Add a cache layer (all the requests are deterministic, same Pokemon name -> same Pokemon description, same description -> same Shakespeare description)
- [ ] We'd need a *premium/ultra*  Shakespeare translator API subscription
- [ ] Add Spring Retry policy to automatically re-invoke a failed operation (on the integration side)
- [ ] Add a circuit breaker to fail fast if all else fails
- [ ] This is a single microservice, but in production you'll want a whole microservices ecosystem to scale the right way such as an api gateway/reverse proxy, a service locator, a config server...
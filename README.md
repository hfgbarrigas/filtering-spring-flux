Project Structure
=======
Service implemented using spring flux that exposes a REST api and uses mongodb database
Web app implemented using angularjs 1.6 and its served using NodeJS

Build
=======
mvn clean install

During build time a mongo container is created and deleted in order to run integration tests.

Run
=======
Needs docker installed.

Run - docker-compose build && docker-compose up (or start)

Stop - docker-compose stop

Api
======
curl localhost:8080/persons - H 'Accept: application/json'
```
@Nullable @RequestParam(name = "hasPhoto", required = false)
@Nullable @RequestParam(name = "inContact", required = false)
@Nullable @RequestParam(name = "favourite", required = false)
@Nullable @DecimalMin("0") @DecimalMax("1") @RequestParam(name = "minCompatibilityScore", required = false)
@Nullable @DecimalMin("0") @DecimalMax("1") @RequestParam(name = "maxCompatibilityScore", required = false)
@Nullable @Min(18) @Max(95) @RequestParam(name = "minAge", required = false)
@Nullable @Min(18) @Max(95) @RequestParam(name = "maxAge", required = false)
@Nullable @Min(135) @Max(210) @RequestParam(name = "minHeight", required = false)
@Nullable @Min(135) @Max(210) @RequestParam(name = "maxHeight", required = false)
@Nullable @Min(0) @RequestParam(name = "distance", required = false)
@Nullable @Pattern(regexp = (km|mi)) @RequestParam(name = "distanceUnit", required = false)
@Nullable @RequestParam(name = "lat", required = false)
@Nullable @RequestParam(name = "lon", required = false)`
```

WebApp
======
localhost:8081/filtering

TODO
======
- remove dangling containers when build fails or it's interrupted
(docker ps -> docker kill/stop)
- Webapp unit tests
- Some improvements, move some values over to properties and leverage configuration server

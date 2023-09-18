#  Todo Kotlin Springboot API
## Design decisions

I have chosen to use Kotlin with Springboot to create a basic API with Gradle as my Dependency manager.

I used an OpenAPI generator to convert my api-spec.yml into classes that my controller can then inherit from. This makes it easier to stay within the API spec contract and ensures that the project is aligned with whoever is consuming the API spec.

Copying the api-spec.yml file in resources into https://editor.swagger.io/ will give an overview of the endpoints being exposed, request and response examples with Response codes, and also the possible exceptions that each endpoint can produce.

A Postman collection is included that contains sample requests for this API, as I've found Swagger.io to be a bit problematic with some requests.

I've followed an MVC architecture design with a controller top level that only receives the request and passes it on to the Service level beneath it, which handles the data transformation and mapping between the incoming request and embedded Database that is on the entity level. I've foregone the component level for this project as the complexity for this project was rather low.

I've decided to use an Exception Handler class to manage exceptions and present them in a human-friendly manner.

The API is exposed on localhost port 8080.



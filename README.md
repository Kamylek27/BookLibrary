# Book Library Application

 The application searches for books using the Google Books API "https://www.googleapis.com/books/v1/volumes?q=java&maxResults=40" and display the results in a list.
 The app calls the API, gets the JSON response, parses it, and returns the data as object book or an list of custom book objects.
 Method  in the app:

 * getBookDetailsByIsbn: returns Book object by isbn or return a 404 if the book does not exist in the data set. (localhost:9090/search/isbn/{isbn})
 * getBookDetailsByCategory: returns JSON document with a list of books, empty list if no book belong to specified category.(localhost:9090/search/category/{category})
 * getBestBooksAuthor: returns JSON document with a list of all authors and their rating in descending order of the average rating of their books. (localhost:9090/search/bestauthorsbooks/)
 * getBookWhichNumberOfPagesIsGreaterThanPageAmount: returns JSON document first book which number of pages is greater than specified value.(localhost:9090/search/amountofpages/{amountOfPages})
 * getMostRatedBooksToRead: returns a JSON document with a list of the best rated books which can be read in a month. (localhost:9090/search/best/{pagePerHour}/{hoursSpendsDuringDay})
 * getBooksLastViewed: returns JSON document with a list of 5 books recently viewed. (localhost:9090/search/lastviewed)

## Technology stack & other Open-source libraries

### Server - Backend
 * JDK - Java™ Platform, Standard Edition Development Kit
 * Spring Boot - Framework to ease the bootstrapping and development of new Spring Applications
 * Maven - Dependency Management
 * Spring REST client - using the Spring RestTemplate to consume the APIs.
 
### Libraries and Plugins
 * Ehcache, a widely used, open-source Java-based cache. It features memory and disk stores, listeners, cache loaders, RESTful and SOAP APIs and other very useful features.
 
### External Tools & Services
 * Postman - API Development Environment (Testing Docmentation)
 
### Testing Frameworks 
 * JUnit 5 - The goal is to create an up-to-date foundation for developer-side testing on the JVM. This includes focusing on Java 8 and above, as well as enabling many different styles of testing.
 
 

## Instructions on how to run application.

1) Unpack the rar file BookLibraryApplication
  
2) Inside Eclipse or IntelliJ IDE
    ```
    File -> Import -> Maven -> Existing Maven project
    ```

    Then either build on the command line `./mvnw generate-resources` or using the Eclipse launcher (right click on project and `Run As -> Maven install`) to generate the css. Run the application main method by right clicking on it and choosing `Run As -> Java Application`.

3) Inside IntelliJ IDEA
    In the main menu, choose `File -> Open` and select the Petclinic [pom.xml](pom.xml). Click on the `Open` button.

    A run configuration named `BookLibraryApplication` should have been created for you if you're using a recent Ultimate version. Otherwise, run the application by right clicking on the `BookLibraryApplication` main class and choosing `Run 'BookLibraryApplication'`.

4) Navigate to Petclinic

    Visit [http://localhost:9090](http://localhost:9090) in your browser.



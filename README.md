# simplesearch
Simple implementation of full text search. Application inludes both client and server parts.

## Build application
In the root directory run:

``mvnw clean package``

###Run server
 
From the root folder execute command:

``java -jar simple-server\target\simple-server-0.0.1-SNAPSHOT.jar``

To run server on the custom use different command:

``java -jar simple-server\target\simple-server-0.0.1-SNAPSHOT.jar --server.port=<custom_port>``

###Run client 

From the root folder:

``java -jar simple-client\target\simple-client-0.0.1-SNAPSHOT.jar``


Note, if you run server on the different port use next link to provide correct api_url:

``java -jar simple-client\target\simple-client-0.0.1-SNAPSHOT.jar --api.search=<search_api_url>``


##Spend time
4h
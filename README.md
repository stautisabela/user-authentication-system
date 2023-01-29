
# User authentication system

Source code for a sample implementation of [Spring Security](https://spring.io/projects/spring-security) with [JSON Web Tokens](https://jwt.io/). User information and permissions are persited in a MySQL database and passwords are encrypted with PBKDF2 key.

The login is done via POST request passing the username and password. Once authenticated, the server will generate an access token and a refresh token based on the secret key defined in the `application.properties`.

The access token must be sent as Authorization header for all eventual requests (unimplemented on this project) and expires in 1 hour. After that, a new one can be generated via PUT request passing the username and refresh token.
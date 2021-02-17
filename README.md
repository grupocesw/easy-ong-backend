Project Name: Easy Ong
Java Version 11
Spring Boot Version 2.4.2

Dependencies Installed
- Postgresql
- Spring DEV Tools
- Spring Web
- Spring Data JPA
- Validator
- H2 Database

Dependencies to Install
- Swagger 2
- Redis
- Kafka

Access in browser H2 Database Terminal

http://localhost:8080/h2-console/login.jsp

JDBC URL: jdbc:h2:mem:easyong
User: sa
Password:

Build
Run As -> 5. Maven build..
Goal: clean package
Profiles: pom.xml
Offline: true
Skip Tests: true
Run

java -jar easy-ong-0.0.1-SNAPSHOT.jar

Documentation
http://localhost:8080/swagger-ui.html

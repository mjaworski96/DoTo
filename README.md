# DoTo
DoTo is a ToDo application

# Deploying application
1. Create own postgreSQL database using scripts from db/.
Using other database is possible but required changes in backend/pom.xml and in backend/src/main/resources/application.yml
2. Create your own certificates using commands from certificate/generate.txt and config from certificate/conf.txt. You can change some details e.g. CN, alternative DNS names, passoword. Using exisiting certificates is also posible. Finally you can add root.crt as trusted certificate (It will make doto.crt certificate trusted too).
3. Update backend/src/main/resources/application.yml with your db name and credentials, certificate password and email details. You can also change token secret, lifetime and refresh time.
4. If you changed backend port (server.port property) in previous step, update frontend/proxy.conf.json *
5. Run backend app: mvn spring-boot:run or mvn package and then java -jar target/backend-0.0.1-SNAPSHOT.jar
6. Run frontend app: npm start for english version or npm run start:pl for polish version. Other option is build application using ng build --prod and then deploy it on http server like nginx. Remeber about configuring proxy (like in step 4.)
# Other information
*<!-- --> this step can be ommited if you are deploying app on http server.<br/>
<br/>
Deploying project require installed Java 8, node.js (for angular 8), and postgreSQL (or other database).

This project is to demonstrate how to build a web application without using Spring Boot auto-configuration.

Using Spring Boot saves lot of time. But it doesn't tell how it works internally.

See 05-Spring-MVC-WebApplication.adoc

Deploy the application to Tomcat::
. run maven package to build a WAR file. Name it webapp.war
. drop the WAR file to tomcat folder /webapps
. access the app: localhost:8080/webapp

Enable Tomcat Manager App::
Modify the conf/tomcat-users.xml as below:

....
<?xml version="1.0" encoding="UTF-8"?>
<tomcat-users xmlns="http://tomcat.apache.org/xml"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://tomcat.apache.org/xml tomcat-users.xsd"
              version="1.0">
    <role rolename="manager-gui"/>
    <role rolename="tomcat"/>
    <role rolename="manager-script"/>
    <user username="admin" password="password" roles="manager-gui,manager-script,tomcat" />
</tomcat-users>
....

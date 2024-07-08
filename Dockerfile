FROM tomcat:10.1

EXPOSE 8080
COPY target/ROOT.war /usr/local/tomcat/webapps/
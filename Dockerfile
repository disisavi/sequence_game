FROM  tomcat:9.0.27-jdk8-adoptopenjdk-openj9
LABEL maintainer="Abhijeet Mishra"
ADD target/sq.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]

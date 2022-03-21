FROM openjdk:11
COPY . /app
WORKDIR /app
ENTRYPOINT ["java", "-jar", "/app/target/best-shop.war"]
EXPOSE 8080

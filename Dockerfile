FROM eclipse-temurin:17-jre-jammy
EXPOSE 8080
COPY --from=build /app/target/tech-challenge-customer*.jar /tech-challenge-customer.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/tech-challenge-customer.jar"]
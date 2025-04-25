FROM openjdk:21-jdk-slim

# Establecemos el directorio de trabajo dentro del contenedor
WORKDIR /app

COPY . .

# Copiamos el JAR generado al contenedor
COPY target/proyectoIE-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto en el que corre Spring Boot (por defecto es 8080)
EXPOSE 8080

# Comando para ejecutar la aplicación cuando el contenedor inicie
ENTRYPOINT ["java", "-jar", "app.jar"]

# Usando uma imagem base do OpenJDK (Java 17)
FROM openjdk:17-jdk-slim

# Definindo o diretório de trabalho dentro do container
WORKDIR /MoedaEstudantil/Backend

# Copiar o arquivo JAR gerado pelo Maven para dentro do container
COPY target/moeda-estudantil-backend-0.0.1-SNAPSHOT.jar app.jar

# Expor a porta 8080 (padrão do Spring Boot)
EXPOSE 8080

# Comando para executar o JAR no container
ENTRYPOINT ["java", "-jar", "app.jar"]

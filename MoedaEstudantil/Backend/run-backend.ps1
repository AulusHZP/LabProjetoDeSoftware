#!/usr/bin/env bash

echo "Starting Moeda Estudantil Backend..."
echo

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed or not in PATH"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed or not in PATH"
    echo "Please install Maven"
    exit 1
fi

echo "Starting Spring Boot application..."
echo "Backend will be available at: http://localhost:8080"
echo "API documentation: http://localhost:8080/swagger-ui.html"
echo

mvn spring-boot:run

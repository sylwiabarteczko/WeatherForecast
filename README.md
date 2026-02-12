# 🚀 Ready Project Setup Guide

This project is a full-stack application that fetches real-time and forecast weather data from an external Weather API and exposes REST endpoints for a frontend application.

Built with:
- **Backend**: Java + Spring Boot + PostgreSQL 
- **Frontend**: https://github.com/sylwiabarteczko/WeatherForecast-Frontend

## 1. Clone the Repository:
Open your terminal and clone the project:

```bash
git clone https://github.com/sylwiabarteczko/WeatherForecast
```
Then open the folder in IntelliJ IDEA.

## 2. Build the JAR file:
Before running the backend in Docker, you must build the JAR file.

From the project root (where pom.xml is located), run:

```bash
mvn clean package
```
This will generate an executable JAR file in:

```bash
target/WeatherForecast-1.0.0.jar
```
## 3. Run Backend Locally:
You can test the backend locally before using Docker:
```bash
java -jar target/WeatherForecast-1.0.0.jar
```
## 4. Start the database (PostgreSQL in Docker):
1. Make sure Docker is installed and running.
2. Go to the project folder where `docker-compose.yml` is located.
3. Run Postgres in Docker

4. Alternatively run this command in the terminal:

```bash
docker compose up -d
```
💡 You can check if PostgreSQL is running by visiting
Docker Desktop → Containers → postgres → running indicator should be in action.



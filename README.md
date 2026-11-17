# ♻️ EcoPoints — Recycling Tracker

EcoPoints is a recycling-tracking application that encourages households to recycle by turning the weight of collected materials into reward points. It also provides community-level statistics and administrative tools.

The project combines a **Spring Boot GraphQL backend**, **PostgreSQL** persistence, and an **Angular** frontend.

## ✨ Features

### Household accounts
- Register households with contact and address information.
- Authenticate with a username and password.
- Use Spring Security and JWT for protected operations.
- Associate recycling activity with the household that submitted it.

### Recycling activity
- Record recycling events by material type and weight.
- Supported materials: **PLASTIC**, **GLASS**, **METAL**, and **PAPER**.
- Calculate EcoPoints from the submitted weight.
- Retrieve a household's recycling history and total recycled weight.
- Delete recycling events.

### Community and administration
- Calculate the total weight recycled across the community.
- Identify the household with the highest total points.
- Export household and recycling-event information to a server-side log file (admin operation).

## 🧮 EcoPoints calculation

The current backend awards **10 EcoPoints per kilogram**:

```text
EcoPoints = weight (kg) × 10
```

For example, recycling 2.5 kg earns 25 EcoPoints.

## 🛠️ Tech stack

| Layer | Technologies |
|---|---|
| Backend | Java 17, Spring Boot 4.1.0 |
| API | Spring for GraphQL, GraphQL Java |
| Security | Spring Security, JWT (JJWT) |
| Persistence | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Frontend | Angular, TypeScript |
| Build | Maven Wrapper |

## 📁 Project structure

```text
Recycling-Tracker/
├── src/
│   └── main/
│       ├── java/org/example/ecopoints_recycling_tracker/
│       │   ├── Config/          # Security and JWT configuration
│       │   ├── Controllers/     # GraphQL controllers
│       │   ├── Dto/             # Data transfer objects
│       │   ├── Entity/          # JPA entities and enums
│       │   ├── Exceptions/      # Application exceptions
│       │   ├── Mappers/         # DTO/entity mapping
│       │   ├── Repository/      # Spring Data repositories
│       │   └── Services/        # Business logic
│       └── resources/
│           ├── graphql/         # GraphQL schema
│           └── application.properties
├── ecopoints-frontend/
│   └── src/                     # Angular application source
├── mvnw
├── mvnw.cmd
└── pom.xml
```

## 🚀 Getting started

### Prerequisites
- Java 17
- PostgreSQL
- Git

### 1. Clone the repository

```bash
git clone https://github.com/raidenshogen/Recycling-Tracker.git
cd Recycling-Tracker
```

### 2. Create the PostgreSQL database

```sql
CREATE DATABASE recycleapp;
```

### 3. Configure the database and JWT secret

Open **src/main/resources/application.properties** and configure your local connection:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/recycleapp
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.graphql.graphiql.enabled=true

# Use a securely generated secret of at least 256 bits in production.
jwt.secret=REPLACE_WITH_A_SECURE_RANDOM_SECRET
jwt.expiration-ms=86400000
```

> **Security note:** Never commit database passwords, real JWT secrets, or other credentials. Keep production secrets outside source control, for example in environment variables or a secrets manager.

### 4. Run the backend

macOS/Linux:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The GraphQL endpoint is:

```text
http://localhost:8080/graphql
```

GraphiQL is enabled in the current application configuration. If the GraphiQL interface is available in your running setup, open:

```text
http://localhost:8080/graphiql
```

### 5. Frontend

The Angular source code is located in **ecopoints-frontend/**. The repository currently contains the frontend source directory; check that an Angular package manifest and workspace configuration are present before installing dependencies or starting the development server.

## 🔎 GraphQL examples

The schema is defined in **src/main/resources/graphql/schema.graphqls**.

### Register a household

```graphql
mutation {
  CreateHousehold(
    household: {
      username: "eco_user"
      password: "change_me"
      email: "eco@example.com"
      fullName: "Green Household"
      country: "Morocco"
      address: "Casablanca"
    }
  ) {
    id
    fullName
    email
  }
}
```

### Log a recycling event

```graphql
mutation {
  addRecyclingEvent(
    dto: {
      matType: PLASTIC
      WeightKG: 2.5
      householdId: 1
    }
  ) {
    id
    matType
    WeightKG
    EcoPoints
    recyclingDate
  }
}
```

### Get a household's recycling history

```graphql
query {
  findAllRecyclingEventsByHouseholdId(id: 1) {
    id
    matType
    WeightKG
    EcoPoints
    recyclingDate
  }
}
```

### Find the community leader

```graphql
query {
  HouseholdHaveHighestTotalPoints {
    id
    fullName
    TotalPoints
  }
}
```

> These examples reflect the current GraphQL schema. Operations protected by the backend require a valid JWT; send it as a bearer token in the Authorization header.

## 🔐 Authentication

The backend uses JWT-based authentication. After a successful login, the API returns a payload containing a token, the user's role, and (where applicable) the household ID.

For authenticated GraphQL requests, include:

```http
Authorization: Bearer YOUR_JWT_TOKEN
```

## 🧪 Tests

Run the backend test suite with:

```bash
./mvnw test
```

## 🌱 Project goal

EcoPoints aims to make recycling measurable and rewarding. By recording household recycling activity and displaying points and community statistics, the application helps users follow their contribution and encourages more sustainable habits.

## 📌 Implementation notes

- The backend currently calculates points using a fixed rate of 10 points per kilogram.
- Material type is recorded for each event; the current points calculation is weight-based.
- Hibernate schema updates are configured with **ddl-auto=update**.
- Review authorization rules and configure secure credentials before deploying publicly.
- Frontend source is present, but frontend build/run instructions should be added once its package and Angular workspace configuration are included in the repository.

---

Made with ♻️ to encourage everyday recycling.

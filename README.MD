# Process Management

This README file provides an overview, technical details, and instructions for running the microservices-based system, as well as the tech stack used for implementation.


### Endpoints:
- User Registration
- User Login
- List available products
- Add Card for User
- List Added Cards of User
- Purchase product
- Retrieve transaction history

## Technical Details and Implementation

### Technologies Used
- Docker Compose for orchestration
- JWT-based user authentication
- RDBMS database for data storage
- Spring Cloud components:
    - spring-cloud-starter-gateway
    - spring-cloud-starter-netflix-eureka-client
    - spring-cloud-starter-netflix-hystrix

### Implementation Details
- **User Service**:
    - Provides authentication (registration and login) using JWT.
    - Manages user card details (addition and retrieval), and transaction history.
- **Card Service**:
    - Stores and manages card details along with encryption for security.
- **Payment Service**:
    - Stores and tracks payment history.
- **Product Service**:
    - Stores product information such as name, stock, and price.

### Docker Compose Configuration
- Utilized Docker Compose to define and run the multi-container Docker applications to ensure seamless orchestration of the microservices.

## Running the Application

### Prerequisites
- Docker installed on the system
- Git installed for cloning the project repository

### Instructions
1. Clone the project repository from the provided Git link.
2. Navigate to the project directory.
3. Run the Docker Compose configuration file to deploy the microservices.

## Design :

- Utilized Docker Compose for simplified orchestration of the microservices.
- Implemented JWT-based user authentication for enhanced security.
- Secured card details with encryption to protect sensitive information.
- Utilized RDBMS database for efficient data storage and retrieval.

# Postman Collection
## Choose an environment for better experience : 

https://lunar-crescent-824031.postman.co/workspace/Team-Workspace~b081a444-79d2-4118-8cda-341bbd94e1dc/collection/9718819-ab231e7d-0ce3-4a15-b44b-10bc4fa82f81?action=share&source=copy-link&creator=31273698
---


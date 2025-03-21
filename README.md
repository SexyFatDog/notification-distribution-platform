# 📣 Notification Distribution Platform
A scalable and modular backend platform for managing and distributing notifications through multiple channels such as Email, SMS, and more. Designed with extensibility, concurrency, and flexibility in mind.

# 🚀 Features
- ✅ Support for multiple notification channels (e.g., Email, SMS, etc.)
- ✅ Asynchronous job processing
- ✅ Decoupled architecture for easy extensibility
- ✅ RESTful APIs for sending and managing notifications
- ✅ Retry and failure handling mechanism
- ✅ Logging and monitoring-ready structure

# Tech Stack
- Programming Language: Java​
- Frameworks and Libraries:
- Spring Cloud
- RabbitMQ
- Redis
- Mybatis
- Build Tool: Maven

# Project Structure
- ndp-common: Contains common utilities and shared resources.​
- ndp-config: Manages centralized configuration using Spring Cloud Config.​
- ndp-eureka: Implements service discovery with Eureka.​
- ndp-handler: Processes incoming notification requests.​
- ndp-messageQueue: Handles message queuing with RabbitMQ.​
- ndp-repository: Manages data persistence.​
- ndp-sendService: Executes the actual sending of notifications through various channels.​
- ndp-web: Provides RESTful APIs for interaction with the platform.

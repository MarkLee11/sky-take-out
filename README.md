# Sky Takeout - Restaurant Ordering & Management System

## Overview

Sky Takeout (苍穹外卖) is a comprehensive restaurant ordering and management system built with Spring Boot. It provides both customer-facing ordering functionality and administrative management tools for restaurant operations. The system supports WeChat Mini Program integration, real-time order tracking, payment processing, and comprehensive business analytics.

## System Architecture

This is a multi-module Maven project with a clean separation of concerns:

### Module Structure
- **sky-common**: Common utilities, constants, exceptions, and shared components
- **sky-pojo**: Data Transfer Objects (DTOs), Entity classes, and Value Objects (VOs)  
- **sky-server**: Main application module containing business logic, controllers, and services

### Technology Stack
- **Backend Framework**: Spring Boot 3.3.5
- **Database**: MySQL with MyBatis ORM
- **Cache**: Redis for session management and caching
- **Authentication**: JWT (JSON Web Tokens)
- **API Documentation**: OpenAPI 3.0 (Swagger/Knife4j)
- **File Storage**: Alibaba Cloud OSS
- **Payment**: WeChat Pay integration
- **Real-time Communication**: WebSocket
- **Build Tool**: Maven
- **Additional Libraries**: 
  - Lombok for code generation
  - FastJSON for JSON processing
  - Druid for database connection pooling
  - PageHelper for pagination
  - Apache POI for Excel export

## Key Features

### Customer Features (User App)
- **User Authentication**: WeChat OAuth login integration
- **Browse Menu**: Category-based dish browsing with detailed descriptions and images
- **Shopping Cart**: Add/remove items, modify quantities
- **Order Management**: 
  - Place orders with delivery address selection
  - Real-time order tracking (pending → confirmed → in delivery → completed)
  - Order history and reordering functionality
  - Order cancellation within allowed timeframe
- **Payment**: WeChat Pay integration for secure transactions
- **Address Management**: Save and manage multiple delivery addresses
- **Order Reminders**: Send reminders to restaurant for pending orders

### Administrative Features (Admin Panel)
- **Employee Management**: Staff account creation, role management, login/logout
- **Menu Management**:
  - Category management (dishes and setmeals)
  - Dish management with flavors and pricing
  - Setmeal (combo) configuration
  - Bulk enable/disable items
- **Order Management**:
  - Real-time order monitoring
  - Order acceptance/rejection
  - Order status updates (confirm → prepare → deliver → complete)
  - Order search and filtering
- **Business Analytics & Reporting**:
  - Revenue statistics and trends
  - User growth analytics  
  - Order volume statistics
  - Top 10 best-selling items
  - Excel report export functionality
- **Shop Status Control**: Open/close shop operations
- **File Upload**: Image management for dishes and categories

### Real-time Features
- **WebSocket Integration**: Real-time order notifications to admin panel
- **Scheduled Tasks**: 
  - Automatic cancellation of unpaid orders after 15 minutes
  - Auto-completion of long-running delivery orders
- **Order Reminders**: Customer can send reminders for delayed orders

## Database Schema

The system uses 11 core entities:

- **User**: Customer information with WeChat integration
- **Employee**: Admin staff accounts
- **Category**: Food categories (dishes/setmeals)
- **Dish**: Individual menu items with pricing and descriptions
- **DishFlavor**: Dish customization options (spice level, size, etc.)
- **Setmeal**: Combo meals containing multiple dishes
- **SetmealDish**: Junction table for setmeal compositions
- **Orders**: Order records with status tracking
- **OrderDetail**: Individual items within orders
- **ShoppingCart**: Temporary cart storage
- **AddressBook**: Customer delivery addresses

## API Architecture

### RESTful API Design
The system exposes two main API groups:

#### Admin APIs (`/admin/**`)
- `/admin/employee/**` - Employee management
- `/admin/category/**` - Category management  
- `/admin/dish/**` - Dish management
- `/admin/setmeal/**` - Setmeal management
- `/admin/order/**` - Order management
- `/admin/report/**` - Analytics and reporting
- `/admin/shop/**` - Shop status control
- `/admin/common/**` - File upload utilities

#### User APIs (`/user/**`)
- `/user/user/**` - User authentication
- `/user/category/**` - Browse categories
- `/user/dish/**` - Browse dishes
- `/user/setmeal/**` - Browse setmeals
- `/user/shoppingCart/**` - Cart management
- `/user/order/**` - Order operations
- `/user/addressBook/**` - Address management
- `/user/shop/**` - Shop status check

### Payment Integration (`/notify/**`)
- `/notify/paySuccess` - WeChat Pay callback handling

## Security & Authentication

### Dual Authentication System
- **Admin Authentication**: JWT tokens for restaurant staff
- **User Authentication**: WeChat OAuth + JWT for customers

### Security Features
- JWT token validation interceptors
- Role-based access control (admin vs user endpoints)
- Secure payment callback verification
- Input validation and sanitization
- Global exception handling

## Configuration

### Application Properties
```yaml
server:
  port: 8080

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/sky_take_out
  data:
    redis:
      host: localhost
      port: 6379
      database: 10

sky:
  jwt:
    admin-secret-key: itcast
    admin-ttl: 7200000
    user-secret-key: itheima  
    user-ttl: 7200000
  alioss:
    endpoint: https://oss-cn-hangzhou.aliyuncs.com
    bucket-name: your-bucket
  wechat:
    appid: your-wechat-appid
    secret: your-wechat-secret
```

## Installation & Setup

### Prerequisites
- Java 17 or later
- Maven 3.6+
- MySQL 5.7+
- Redis 6.0+
- WeChat developer account (for Mini Program)
- Alibaba Cloud OSS account (for file storage)

### Database Setup
1. Create a MySQL database named `sky_take_out`
2. Run the SQL scripts to create tables and initial data
3. Update database connection details in `application.yml`

### Application Startup
1. Clone the repository
2. Configure `application.yml` with your database and service credentials
3. Run the following commands:
```bash
mvn clean compile
mvn spring-boot:run -pl sky-server
```

The application will start on `http://localhost:8080`

### API Documentation
Once running, access the API documentation at:
- Swagger UI: `http://localhost:8080/doc.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

## Development Features

### Code Quality & Productivity
- **Lombok Integration**: Reduces boilerplate code with annotations
- **Auto-fill Aspect**: Automatic population of create/update timestamps and user info
- **Global Exception Handling**: Centralized error handling and response formatting
- **Pagination Support**: Built-in pagination for list endpoints
- **Validation**: Comprehensive input validation using Bean Validation

### Testing & Documentation
- **API Documentation**: Complete OpenAPI 3.0 specification
- **Environment Profiles**: Separate configurations for development and production
- **Logging**: Structured logging with SLF4J and Logback

## Business Logic Highlights

### Order Workflow
1. **Customer Places Order**: Select items, add to cart, checkout with address
2. **Payment Processing**: WeChat Pay integration with callback verification  
3. **Restaurant Receives Order**: Real-time WebSocket notification to admin
4. **Order Acceptance**: Admin can accept/reject with reasons
5. **Preparation & Delivery**: Status updates with estimated delivery times
6. **Completion**: Order marked complete, customer can reorder

### Inventory Management
- Real-time dish availability status
- Category-based organization
- Seasonal menu support with enable/disable functionality

### Analytics & Reporting
- Daily/weekly/monthly revenue reports
- Customer growth analytics
- Popular items analysis
- Excel export for external analysis

## WeChat Integration

### Mini Program Features
- **OAuth Login**: Seamless login using WeChat credentials
- **Payment**: Native WeChat Pay integration
- **User Profile**: Access WeChat user information (name, avatar)
- **Notifications**: Order status updates through WeChat

### Payment Flow
1. Customer initiates payment in Mini Program
2. System generates WeChat Pay order
3. Customer completes payment in WeChat
4. WeChat sends callback to `/notify/paySuccess`
5. System updates order status and notifies restaurant

## Performance & Scalability

### Caching Strategy
- Redis for session storage
- Shop status caching
- Database connection pooling with Druid

### Asynchronous Processing
- WebSocket for real-time notifications
- Scheduled tasks for order management
- Non-blocking payment processing

## File Management

### Image Upload & Storage
- Alibaba Cloud OSS integration for scalable file storage
- Support for dish images, category images, and avatars
- Automatic image optimization and CDN delivery

## Monitoring & Maintenance

### Scheduled Tasks
- **Order Timeout**: Automatically cancel unpaid orders after 15 minutes
- **Delivery Completion**: Auto-complete orders in delivery status after 1 hour
- **Daily Reports**: Generate business analytics reports

### Logging & Debugging
- Comprehensive request/response logging
- Database query logging in development mode
- Error tracking and exception reporting

## Contributing

This appears to be a learning/demonstration project showcasing enterprise-level Java development practices. The codebase demonstrates:

- Clean architecture principles
- Modern Spring Boot features
- Integration with popular Chinese services (WeChat, Alibaba Cloud)
- Real-world business logic implementation
- Comprehensive API design
- Security best practices

## License

This project appears to be for educational purposes and demonstrates a complete restaurant management system implementation.

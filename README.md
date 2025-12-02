# Uzima Borehole System

A comprehensive JavaFX-based borehole management system with MySQL database backend.

## Features

- **Client Management**: Full CRUD operations for client records
- **Automatic Cost Calculation**: Intelligent fee calculation based on service selections
- **Authentication**: Separate login portals for clients and staff
- **Modern UI**: Clean, responsive JavaFX interface with dark mode toggle
- **Revenue Analytics**: Complete financial reporting and analytics
- **MVC Architecture**: Well-organized code structure following best practices

## Project Structure

```
src/
├── Main.java                          # Application entry point
├── database/
│   ├── DBConnection.java              # MySQL connection manager
│   └── DatabaseInitializer.java      # Database schema initialization
├── models/
│   ├── Client.java                    # Client entity
│   ├── Staff.java                     # Staff entity
│   ├── SurveyFee.java                # Survey fee lookup
│   ├── DrillingService.java          # Drilling service lookup
│   ├── PumpInstallation.java         # Pump installation lookup
│   └── DepthCharge.java              # Depth charge lookup
├── services/
│   ├── ClientService.java            # Client CRUD operations
│   ├── StaffService.java             # Staff operations
│   ├── CalculationService.java       # Fee calculation logic
│   └── AuthService.java              # Authentication logic
├── utils/
│   ├── IDGenerator.java              # Client ID generation (UZ-001, UZ-002...)
│   └── StyleUtils.java               # UI styling utilities
└── ui/
    ├── login/
    │   └── LoginUI.java              # Login page with tabs
    ├── client/
    │   └── ClientDashboard.java      # Client portal
    └── admin/
        ├── AdminDashboard.java       # Admin dashboard with sidebar
        ├── AddClientForm.java        # Add/Edit client form
        └── ClientTableView.java      # Client table with search
```

## Database Schema

### Tables Created Automatically:
- **clients**: Stores all client information and service details
- **staff**: Admin user credentials
- **survey_fees**: Category-based survey fees
- **drilling_services**: Drilling type fees
- **pump_installation**: Pump installation costs
- **depth_charges**: Depth-based charging rates

## Prerequisites

1. **Java JDK 17 or higher**
2. **JavaFX SDK 17 or higher**
3. **MySQL Server 8.0 or higher**
4. **MySQL Connector/J (JDBC Driver)**

## Setup Instructions

### 1. Database Setup

1. Install MySQL Server
2. Create a database (the system will auto-create `uzima_db`)
3. Update database credentials in `DBConnection.java`:
   ```java
   private static final String USER = "root";
   private static final String PASSWORD = "your_password";
   ```

### 2. JavaFX Configuration

#### Option A: Using IntelliJ IDEA
1. Download JavaFX SDK from https://openjfx.io/
2. File → Project Structure → Libraries → Add → Java
3. Select JavaFX SDK lib folder
4. Run → Edit Configurations → Add VM options:
   ```
   --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.fxml
   ```

#### Option B: Using Maven (Recommended)
Create `pom.xml` in project root:
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.uzima</groupId>
    <artifactId>borehole-system</artifactId>
    <version>1.0</version>
    
    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
    </properties>
    
    <dependencies>
        <!-- JavaFX -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>17.0.2</version>
        </dependency>
        
        <!-- MySQL Connector -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.33</version>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>Main</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 3. MySQL JDBC Driver

Download MySQL Connector/J from:
https://dev.mysql.com/downloads/connector/j/

Add to project libraries or place in `lib/` folder.

## Running the Application

### Using IntelliJ IDEA:
1. Right-click on `Main.java`
2. Select "Run 'Main.main()'"

### Using Maven:
```bash
mvn clean javafx:run
```

### Using Command Line:
```bash
javac --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls -d out src/**/*.java
java --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls -cp "out;lib/mysql-connector-java.jar" Main
```

## Default Login Credentials

### Staff Login:
- **Username**: admin
- **Password**: admin123

### Client Login:
- Register as new client to get Client ID (format: UZ-001)
- Or use existing Client ID from database

## Cost Calculation Logic

The system automatically calculates:

1. **Survey Fee**: Based on client category (Domestic/Commercial/Industrial)
2. **Local Authority Fee**: Based on client category
3. **Drilling Fee**: Based on drilling type (Symmetric/Core/Geo-Technical)
4. **Pump Installation Fee**: Based on pump type
5. **Depth Charge**: depth × cost_per_meter (varies by depth range)
6. **Plumbing Fee**: Calculated from pipe type, diameter, length, and outlets
7. **Subtotal**: Sum of all above fees
8. **Tax (16%)**: Subtotal × 0.16
9. **Total Cost**: Subtotal + Tax

## Key Features

### Client Portal
- View personal information
- See complete service details
- View detailed cost breakdown
- Check payment status

### Admin Dashboard
- Dashboard with statistics cards
- Add new clients with automatic cost calculation
- View/Search all clients in table
- Edit client information
- Delete clients
- Revenue analytics and reporting
- Modern sidebar navigation
- Dark mode toggle (UI ready)

## Validation Rules

- All required fields must be filled
- Phone number required
- Numeric fields validated
- Client ID auto-generated in sequence
- Prepared statements prevent SQL injection

## Technologies Used

- **Java 17+**
- **JavaFX 17+** - Modern UI framework
- **MySQL 8.0+** - Database
- **JDBC** - Database connectivity
- **MVC Pattern** - Clean architecture

## Color Scheme

- Primary: #2C3E50 (Dark Blue)
- Secondary: #3498DB (Blue)
- Success: #27AE60 (Green)
- Warning: #F39C12 (Orange)
- Danger: #E74C3C (Red)
- Background: #ECF0F1 (Light Gray)

## Troubleshooting

### Database Connection Issues
- Verify MySQL is running
- Check credentials in `DBConnection.java`
- Ensure MySQL port 3306 is not blocked

### JavaFX Not Found
- Verify JavaFX is in classpath
- Check VM options include correct module path
- Ensure JavaFX version matches JDK version

### JDBC Driver Issues
- Download correct MySQL Connector/J version
- Add to project libraries
- Include in classpath when running

## Future Enhancements

- PDF invoice generation
- Email notifications
- Payment processing integration
- Advanced reporting with charts
- Role-based access control
- Backup and restore functionality

## License

This project is for educational purposes.

## Author

Uzima Borehole System Team

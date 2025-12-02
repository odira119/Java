# Uzima Borehole System - Database Configuration Guide

## MySQL Database Setup

### Step 1: Install MySQL
1. Download MySQL Community Server from https://dev.mysql.com/downloads/mysql/
2. Install with default settings
3. Remember the root password you set during installation

### Step 2: Configure Database Connection
Edit `src/database/DBConnection.java`:

```java
private static final String USER = "root";
private static final String PASSWORD = "your_mysql_password"; // Change this!
```

### Step 3: Database Auto-Creation
The application automatically:
- Creates database `uzima_db` if it doesn't exist
- Creates all required tables
- Inserts default lookup data
- Creates default admin account

### Default Data Inserted

#### Staff Account
- Username: `admin`
- Password: `admin123`

#### Survey Fees
| Category    | Survey Fee | Local Authority Fee |
|-------------|-----------|---------------------|
| Industrial  | 20,000    | 50,000             |
| Commercial  | 15,000    | 30,000             |
| Domestic    | 7,000     | 10,000             |

#### Drilling Services
| Drilling Type  | Down Payment |
|---------------|--------------|
| Symmetric     | 130,000      |
| Core          | 225,000      |
| Geo-Technical | 335,000      |

#### Pump Installation
| Pump Type              | Cost   |
|------------------------|--------|
| Submersible electric   | 90,000 |
| Solar pump             | 65,000 |
| Hand pump              | 30,000 |

#### Depth Charges
| Range (meters) | Cost per Meter |
|---------------|----------------|
| 1 - 100       | 1,000          |
| 101 - 200     | 1,500          |
| 201 - 300     | 2,000          |
| 301+          | 2,500          |

### Manual Database Creation (Optional)

If you prefer to create the database manually:

```sql
CREATE DATABASE uzima_db;
USE uzima_db;

-- Run the application and it will create tables automatically
```

### Verify Database Setup

1. Open MySQL Workbench or command line
2. Connect to MySQL server
3. Run: `SHOW DATABASES;`
4. Verify `uzima_db` exists
5. Run: `USE uzima_db; SHOW TABLES;`
6. Verify all tables are created

### Troubleshooting

#### Connection Refused
- Ensure MySQL service is running
- Check if port 3306 is available
- Verify firewall settings

#### Access Denied
- Check username and password in DBConnection.java
- Ensure user has CREATE DATABASE privilege
- Try using root account

#### SSL Warning
The application uses `useSSL=false` for local development.
For production, enable SSL and configure certificates.

### Database Backup

To backup your data:
```bash
mysqldump -u root -p uzima_db > uzima_backup.sql
```

To restore:
```bash
mysql -u root -p uzima_db < uzima_backup.sql
```

## Production Deployment

For production deployment:

1. Create dedicated MySQL user:
```sql
CREATE USER 'uzima_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON uzima_db.* TO 'uzima_user'@'localhost';
FLUSH PRIVILEGES;
```

2. Update connection settings with production credentials

3. Enable SSL connection

4. Use connection pooling for better performance

5. Regular database backups

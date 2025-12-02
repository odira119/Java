# Quick Start Guide - Uzima Borehole System

## 🚀 Get Up and Running in 5 Minutes

### Prerequisites Checklist
- [ ] Java JDK 17+ installed
- [ ] MySQL Server 8.0+ installed and running
- [ ] Maven installed (or use IDE)

---

## Step 1: Database Setup (2 minutes)

### Update Database Password
Edit `src/database/DBConnection.java`:

```java
private static final String PASSWORD = "your_mysql_root_password";
```

**That's it!** The app will auto-create everything else.

---

## Step 2: Run the Application (1 minute)

### Option A: Using the Run Script (Easiest)
```bash
run.bat
```

### Option B: Using Maven
```bash
mvn clean javafx:run
```

### Option C: Using IntelliJ IDEA
1. Open project in IntelliJ
2. Right-click `Main.java`
3. Select "Run 'Main.main()'"

---

## Step 3: Login (30 seconds)

### Admin Login
1. Click **"Staff Login"** tab
2. Username: `admin`
3. Password: `admin123`
4. Click **Login**

### Client Registration
1. Click **"Client Login"** tab
2. Click **"New Client? Register Here"**
3. Enter name and phone
4. Save your Client ID (e.g., UZ-001)

---

## Step 4: Add Your First Client (2 minutes)

1. In Admin Dashboard, click **"Add Client"**
2. Fill in the form:
   - **Personal Info**: Name, Phone, Address, Email
   - **Service Details**: 
     - Category (Domestic/Commercial/Industrial)
     - Location, Depth, Drilling Type, Pump Type
   - **Plumbing**: Pipe specs and outlets
3. Click **"Save Client"**
4. ✅ Done! Costs calculated automatically

---

## Common First-Time Issues

### MySQL Connection Failed
```
❌ Problem: Cannot connect to MySQL
✅ Solution: 
   - Ensure MySQL is running
   - Check password in DBConnection.java
   - Verify MySQL is on port 3306
```

### JavaFX Not Found
```
❌ Problem: JavaFX modules not found
✅ Solution:
   - Use Maven: mvn clean javafx:run
   - Or add VM options in IDE:
     --module-path "path/to/javafx-sdk/lib" 
     --add-modules javafx.controls
```

### Database Already Exists Warning
```
❌ Problem: uzima_db already exists
✅ Solution: 
   - This is normal, app will use existing database
   - If you want fresh start: DROP DATABASE uzima_db;
```

---

## Quick Feature Test

### Test 1: View Dashboard Statistics
- Login as admin
- See Total Clients, Revenue, Pending Payments

### Test 2: Add a Test Client
- Click "Add Client"
- Use sample data:
  - Name: John Doe
  - Phone: 0712345678
  - Category: Domestic
  - Depth: 50 meters
  - Drilling: Symmetric
  - Pump: Hand pump
- Watch automatic cost calculation!

### Test 3: Search Clients
- Click "View Clients"
- Use search bar to find client
- Try editing and deleting

---

## Default System Data

### Pre-loaded Price Lists

**Survey Fees:**
- Domestic: KSh 7,000 + 10,000
- Commercial: KSh 15,000 + 30,000
- Industrial: KSh 20,000 + 50,000

**Drilling Services:**
- Symmetric: KSh 130,000
- Core: KSh 225,000
- Geo-Technical: KSh 335,000

**Pump Installation:**
- Submersible: KSh 90,000
- Solar: KSh 65,000
- Hand pump: KSh 30,000

**Depth Charges (per meter):**
- 1-100m: KSh 1,000
- 101-200m: KSh 1,500
- 201-300m: KSh 2,000
- 301+m: KSh 2,500

---

## File Structure at a Glance

```
Java/
├── src/
│   ├── Main.java              ← Start here
│   ├── database/              ← Database connection
│   ├── models/                ← Data models
│   ├── services/              ← Business logic
│   ├── utils/                 ← Helpers
│   └── ui/                    ← User interface
├── pom.xml                    ← Maven config
├── run.bat                    ← Quick start script
├── README.md                  ← Full documentation
└── DATABASE_SETUP.md          ← Database guide
```

---

## Next Steps

✅ **You're ready to go!**

### For Learning:
1. Read `USAGE_GUIDE.md` for detailed features
2. Read `README.md` for architecture details
3. Explore the code in `src/`

### For Customization:
1. Modify prices in `DatabaseInitializer.java`
2. Change colors in `StyleUtils.java`
3. Add features in respective packages

### For Production:
1. Change admin password
2. Create dedicated MySQL user
3. Enable MySQL SSL
4. Set up database backups

---

## Need Help?

📖 **Full Documentation:**
- README.md - Complete project documentation
- USAGE_GUIDE.md - Detailed user manual
- DATABASE_SETUP.md - Database configuration

🔧 **Troubleshooting:**
- Check MySQL is running: `sc query MySQL80`
- Test database connection manually
- Verify Java version: `java -version`
- Check Maven: `mvn -version`

---

## Success Checklist

- [ ] Database connected successfully
- [ ] Application launches
- [ ] Can login as admin
- [ ] Can add a client
- [ ] Can view clients in table
- [ ] Can see cost calculations
- [ ] Can view analytics

**All checked? Congratulations! 🎉**

You're now running the Uzima Borehole System!

---

**Happy managing! 💧**

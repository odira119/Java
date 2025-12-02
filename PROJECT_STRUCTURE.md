# 📁 Uzima Borehole System - Visual Project Structure

```
📦 UzimaBoreholeSystem
│
├── 📂 src/                                    # Source code directory
│   │
│   ├── 📄 Main.java                           # 🚀 Application entry point
│   │                                          # - Initializes database
│   │                                          # - Launches JavaFX app
│   │                                          # - Shows login screen
│   │
│   ├── 📂 database/                           # 💾 Database layer
│   │   ├── 📄 DBConnection.java               # MySQL connection manager
│   │   │                                      # - Singleton pattern
│   │   │                                      # - Auto-reconnect
│   │   └── 📄 DatabaseInitializer.java        # Schema initialization
│   │                                          # - Creates database
│   │                                          # - Creates tables
│   │                                          # - Inserts default data
│   │
│   ├── 📂 models/                             # 📋 Data models (Entities)
│   │   ├── 📄 Client.java                     # Client entity (24 fields)
│   │   ├── 📄 Staff.java                      # Staff entity
│   │   ├── 📄 SurveyFee.java                  # Survey fee lookup
│   │   ├── 📄 DrillingService.java            # Drilling service lookup
│   │   ├── 📄 PumpInstallation.java           # Pump installation lookup
│   │   └── 📄 DepthCharge.java                # Depth charge lookup
│   │
│   ├── 📂 services/                           # ⚙️ Business logic layer
│   │   ├── 📄 ClientService.java              # Client CRUD operations
│   │   │                                      # - Add, update, delete
│   │   │                                      # - Get by ID, get all
│   │   │                                      # - Search, analytics
│   │   ├── 📄 StaffService.java               # Staff operations
│   │   │                                      # - Authentication
│   │   ├── 📄 CalculationService.java         # 💰 Fee calculations
│   │   │                                      # - Survey fees
│   │   │                                      # - Drilling fees
│   │   │                                      # - Pump fees
│   │   │                                      # - Depth charges
│   │   │                                      # - Plumbing fees
│   │   │                                      # - Tax & totals
│   │   └── 📄 AuthService.java                # 🔐 Authentication
│   │                                          # - Client registration
│   │                                          # - Client login
│   │
│   ├── 📂 utils/                              # 🛠️ Utility classes
│   │   ├── 📄 IDGenerator.java                # Client ID generation
│   │   │                                      # - Format: UZ-001, UZ-002...
│   │   │                                      # - Auto-increment
│   │   └── 📄 StyleUtils.java                 # 🎨 UI styling utilities
│   │                                          # - Modern button styles
│   │                                          # - Text field styling
│   │                                          # - Card creation
│   │                                          # - Color scheme
│   │                                          # - Alerts
│   │
│   └── 📂 ui/                                 # 🖥️ User interface layer
│       │
│       ├── 📂 login/                          # Login module
│       │   └── 📄 LoginUI.java                # 🔑 Login page
│       │                                      # - Client login tab
│       │                                      # - Staff login tab
│       │                                      # - Registration dialog
│       │
│       ├── 📂 client/                         # Client portal
│       │   └── 📄 ClientDashboard.java        # 👤 Client dashboard
│       │                                      # - Personal info
│       │                                      # - Service details
│       │                                      # - Cost breakdown
│       │                                      # - Invoice view
│       │
│       └── 📂 admin/                          # Admin portal
│           ├── 📄 AdminDashboard.java         # 📊 Admin dashboard
│           │                                  # - Sidebar navigation
│           │                                  # - Statistics cards
│           │                                  # - Quick actions
│           │                                  # - Analytics view
│           ├── 📄 AddClientForm.java          # ➕ Add/Edit client
│           │                                  # - Personal info form
│           │                                  # - Service details form
│           │                                  # - Plumbing specs form
│           │                                  # - Auto-calculation
│           │                                  # - Validation
│           └── 📄 ClientTableView.java        # 📋 Client management
│                                              # - Searchable table
│                                              # - Edit functionality
│                                              # - Delete functionality
│
├── 📂 lib/                                    # External libraries (optional)
│   └── mysql-connector-java-8.0.33.jar        # MySQL JDBC driver
│
├── 📄 pom.xml                                 # Maven build configuration
│                                              # - Dependencies
│                                              # - Build plugins
│                                              # - JavaFX plugin
│
├── 📄 run.bat                                 # 🚀 Windows quick start script
│
├── 📄 .gitignore                              # Git ignore rules
│
├── 📄 README.md                               # 📖 Complete documentation
├── 📄 QUICK_START.md                          # ⚡ 5-minute setup guide
├── 📄 USAGE_GUIDE.md                          # 📚 Detailed user manual
├── 📄 DATABASE_SETUP.md                       # 💾 Database configuration
├── 📄 PROJECT_SUMMARY.md                      # 📊 Project overview
├── 📄 FINAL_CHECKLIST.md                      # ✅ Delivery checklist
└── 📄 PROJECT_STRUCTURE.md                    # 📁 This file

```

---

## 🎯 Architecture Flow Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         USER INTERACTION                         │
└─────────────────────────────────────────────────────────────────┘
                                 │
                    ┌────────────┴────────────┐
                    │                         │
          ┌─────────▼─────────┐    ┌─────────▼─────────┐
          │  Client Portal    │    │   Admin Portal    │
          │  (Client UI)      │    │   (Admin UI)      │
          └─────────┬─────────┘    └─────────┬─────────┘
                    │                         │
                    └────────────┬────────────┘
                                 │
                    ┌────────────▼────────────┐
                    │     UI LAYER (JavaFX)   │
                    │  - LoginUI              │
                    │  - ClientDashboard      │
                    │  - AdminDashboard       │
                    │  - AddClientForm        │
                    │  - ClientTableView      │
                    └────────────┬────────────┘
                                 │
                    ┌────────────▼────────────┐
                    │   SERVICE LAYER         │
                    │  - ClientService        │
                    │  - StaffService         │
                    │  - CalculationService   │
                    │  - AuthService          │
                    └────────────┬────────────┘
                                 │
                    ┌────────────▼────────────┐
                    │   MODEL LAYER           │
                    │  - Client               │
                    │  - Staff                │
                    │  - SurveyFee            │
                    │  - DrillingService      │
                    │  - PumpInstallation     │
                    │  - DepthCharge          │
                    └────────────┬────────────┘
                                 │
                    ┌────────────▼────────────┐
                    │   DATABASE LAYER        │
                    │  - DBConnection         │
                    │  - DatabaseInitializer  │
                    └────────────┬────────────┘
                                 │
                    ┌────────────▼────────────┐
                    │    MySQL Database       │
                    │      (uzima_db)         │
                    │  - clients              │
                    │  - staff                │
                    │  - survey_fees          │
                    │  - drilling_services    │
                    │  - pump_installation    │
                    │  - depth_charges        │
                    └─────────────────────────┘
```

---

## 📊 Data Flow Diagram

### Adding a New Client

```
Admin clicks "Add Client"
         │
         ▼
AddClientForm.createForm()
         │
         ▼
Admin fills in all fields
         │
         ▼
Click "Save Client"
         │
         ▼
handleSaveClient()
  │
  ├──► validateForm() ──────► ❌ Show error if invalid
  │
  ├──► Create Client object
  │
  ├──► IDGenerator.generateClientId() ──► "UZ-001"
  │
  ├──► Set all client properties
  │
  ├──► CalculationService.calculateAllFees()
  │     │
  │     ├──► calculateSurveyFees() ──► Query survey_fees table
  │     ├──► calculateDrillingFee() ──► Query drilling_services table
  │     ├──► calculatePumpInstallationFee() ──► Query pump_installation table
  │     ├──► calculateDepthCharge() ──► Query depth_charges table
  │     ├──► calculatePlumbingFee() ──► Formula-based calculation
  │     └──► calculateTotals() ──► Subtotal + Tax + Total
  │
  ├──► ClientService.addClient()
  │     │
  │     └──► PreparedStatement ──► INSERT INTO clients
  │
  └──► ✅ Success! Show confirmation with total cost
```

### Client Login Flow

```
Client enters Client ID
         │
         ▼
Click "Login"
         │
         ▼
AuthService.authenticateClient(clientId)
         │
         ▼
ClientService.getClientById(clientId)
         │
         ▼
SELECT * FROM clients WHERE client_id = ?
         │
    ┌────┴────┐
    │         │
    ▼         ▼
  Found    Not Found
    │         │
    │         └──► ❌ Show error
    │
    ▼
Create ClientDashboard
    │
    ▼
Display client information
  - Personal details
  - Service details
  - Complete cost breakdown
```

---

## 🎨 UI Component Hierarchy

```
Application Window
│
├── LoginUI (Initial Screen)
│   ├── Tab: Client Login
│   │   ├── TextField: Client ID
│   │   ├── Button: Login
│   │   └── Hyperlink: Register
│   │       └── Dialog: Registration Form
│   │
│   └── Tab: Staff Login
│       ├── TextField: Username
│       ├── PasswordField: Password
│       └── Button: Login
│
├── ClientDashboard (After client login)
│   ├── TopBar
│   │   ├── Label: Company Name
│   │   └── Button: Logout
│   │
│   └── Content (Scrollable)
│       ├── WelcomeSection
│       │   └── Label: Client Name & ID
│       ├── DetailsSection (Card)
│       │   └── GridPane: Client Information
│       ├── ServiceDetailsSection (Card)
│       │   └── GridPane: Service Information
│       └── CostBreakdownSection (Card)
│           └── GridPane: All Fees & Totals
│
└── AdminDashboard (After staff login)
    ├── Sidebar (Left)
    │   ├── Logo
    │   ├── Button: Dashboard
    │   ├── Button: Add Client
    │   ├── Button: View Clients
    │   ├── Button: Analytics
    │   └── Button: Logout
    │
    ├── TopBar
    │   ├── Label: Page Title
    │   └── ToggleButton: Dark Mode
    │
    └── Content Area (Right, Dynamic)
        │
        ├── Dashboard View
        │   ├── Statistics Cards Row
        │   │   ├── Card: Total Clients
        │   │   ├── Card: Total Revenue
        │   │   ├── Card: Pending Payments
        │   │   └── Card: Total Tax
        │   └── Quick Actions Panel
        │
        ├── Add Client View
        │   └── AddClientForm
        │       ├── Personal Info Section
        │       ├── Service Details Section
        │       ├── Plumbing Details Section
        │       └── Buttons: Save, Clear, Cancel
        │
        ├── View Clients View
        │   └── ClientTableView
        │       ├── Search Bar
        │       ├── TableView (8 columns)
        │       └── Action Buttons per Row
        │
        └── Analytics View
            └── Revenue Breakdown
                └── GridPane: All Totals
```

---

## 💾 Database Schema Relationships

```
┌─────────────────────────────────────────────────────────────┐
│                    CLIENTS TABLE                             │
│  (Main transaction table - stores all client data)           │
│                                                               │
│  client_id [PK] ────► Auto-generated: UZ-001, UZ-002...     │
│  name, address, phone, email                                 │
│  client_category ───┐                                        │
│  drilling_type ─────┼──► Links to lookup tables             │
│  pump_type ─────────┘                                        │
│  ... (service details)                                       │
│  ... (calculated fees)                                       │
│  created_at                                                  │
└─────────────────────────────────────────────────────────────┘
                    │
                    │ References (Lookup)
        ┌───────────┼───────────┬───────────┐
        │           │           │           │
        ▼           ▼           ▼           ▼
┌──────────┐  ┌──────────┐ ┌──────────┐ ┌──────────┐
│ SURVEY   │  │ DRILLING │ │   PUMP   │ │  DEPTH   │
│  FEES    │  │ SERVICES │ │INSTALLTN │ │ CHARGES  │
│          │  │          │ │          │ │          │
│ category │  │ drilling │ │   pump   │ │  range   │
│ [PK]     │  │ type [PK]│ │ type [PK]│ │ [PK]     │
│          │  │          │ │          │ │          │
│ survey_  │  │   down   │ │   cost   │ │   cost   │
│ fee      │  │ payment  │ │          │ │ per_meter│
│          │  │          │ │          │ │          │
│ local_   │  │          │ │          │ │          │
│ auth_fee │  │          │ │          │ │          │
└──────────┘  └──────────┘ └──────────┘ └──────────┘

┌─────────────────────────────────────────────────────────────┐
│                     STAFF TABLE                              │
│  (Admin users - separate from clients)                       │
│                                                               │
│  staff_id [PK, AUTO_INCREMENT]                               │
│  username [UNIQUE]                                           │
│  password                                                    │
└─────────────────────────────────────────────────────────────┘
```

---

## 🔄 Cost Calculation Logic Flow

```
Input: Client with service details
   │
   ▼
┌────────────────────────────────────────┐
│  CalculationService.calculateAllFees() │
└────────────────────────────────────────┘
   │
   ├──► 1. calculateSurveyFees()
   │       │
   │       └──► SELECT FROM survey_fees WHERE category = ?
   │            └──► Set: survey_fee, local_authority_fee
   │
   ├──► 2. calculateDrillingFee()
   │       │
   │       └──► SELECT FROM drilling_services WHERE drilling_type = ?
   │            └──► Set: drilling_fee
   │
   ├──► 3. calculatePumpInstallationFee()
   │       │
   │       └──► SELECT FROM pump_installation WHERE pump_type = ?
   │            └──► Set: pump_installation_fee
   │
   ├──► 4. calculateDepthCharge()
   │       │
   │       └──► SELECT FROM depth_charges WHERE depth BETWEEN range_from AND range_to
   │            └──► depth_charge = depth × cost_per_meter
   │
   ├──► 5. calculatePlumbingFee()
   │       │
   │       └──► Formula:
   │            base_cost = 5000
   │            diameter_factor = diameter × 100
   │            length_factor = length × 50
   │            outlet_factor = outlets × 2000
   │            type_multiplier = based on pipe type (PVC=1.2, Steel=1.5, etc.)
   │            plumbing_fee = (base + diameter + length + outlet) × multiplier
   │
   └──► 6. calculateTotals()
          │
          └──► subtotal = sum of all fees
               tax_paid = subtotal × 0.16
               total_cost = subtotal + tax_paid

Output: Client with all fees calculated
```

---

## 📱 User Journey Maps

### Journey 1: New Client Registration & Service

```
START
  │
  └──► Open Application ──► See Login Screen
         │
         └──► Click "New Client? Register Here"
                │
                └──► Fill Registration Form
                       │
                       └──► Submit ──► Get Client ID (UZ-001)
                              │
                              └──► Save Client ID
                                     │
                                     └──► Contact Admin for Service Details
                                            │
                                            └──► Admin Adds Full Details
                                                   │
                                                   └──► System Calculates Costs
                                                          │
                                                          └──► Client Login to View Invoice
                                                                 │
                                                                 └──► See Complete Breakdown
END (Client can now view their information anytime)
```

### Journey 2: Admin Managing Clients

```
START
  │
  └──► Login as Admin ──► Dashboard View
         │
         ├──► View Statistics
         │      │
         │      └──► Total Clients, Revenue, Pending, Tax
         │
         ├──► Add New Client
         │      │
         │      ├──► Enter Personal Info
         │      ├──► Enter Service Details
         │      ├──► Enter Plumbing Specs
         │      └──► Save ──► Auto-calculate ──► Success!
         │
         ├──► View All Clients
         │      │
         │      ├──► Search for Client
         │      ├──► Edit Client Info
         │      └──► Delete Client (with confirmation)
         │
         └──► View Analytics
                │
                └──► See Revenue Breakdown by Service Type
END (Admin can manage all aspects)
```

---

## 🎯 Key Files Quick Reference

| File | Purpose | Lines | Key Methods |
|------|---------|-------|-------------|
| Main.java | Entry point | ~50 | start(), main() |
| DBConnection.java | DB connection | ~60 | getConnection(), closeConnection() |
| DatabaseInitializer.java | DB setup | ~300 | initialize(), createTables(), insertDefaultData() |
| Client.java | Model | ~300 | Getters/Setters for 24 fields |
| ClientService.java | CRUD | ~350 | add(), update(), delete(), getAll(), search() |
| CalculationService.java | Calculations | ~200 | calculateAllFees(), calculate*() methods |
| LoginUI.java | Login screen | ~250 | createLoginScene(), createClientLoginPane() |
| AdminDashboard.java | Admin UI | ~400 | createScene(), showDashboardView(), showAnalyticsView() |
| AddClientForm.java | Add client | ~500 | createForm(), handleSaveClient(), validateForm() |
| ClientTableView.java | Client table | ~350 | createTableView(), handleEdit(), handleDelete() |
| ClientDashboard.java | Client view | ~350 | createScene(), createCostBreakdownSection() |
| StyleUtils.java | UI styling | ~250 | styleButton(), styleTextField(), createCard() |

---

**This visual structure shows the complete organization of the Uzima Borehole System!** 🎯

*All 29 files working together to create a modern, functional borehole management system.*

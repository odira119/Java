# Uzima Borehole System - User Guide

## Table of Contents
1. [Getting Started](#getting-started)
2. [Client Portal](#client-portal)
3. [Staff/Admin Portal](#staffadmin-portal)
4. [Features Guide](#features-guide)

---

## Getting Started

### First Time Setup

1. **Start the Application**
   - Run `Main.java` or use `mvn javafx:run`
   - The login screen will appear

2. **Database Initialization**
   - On first run, the system automatically:
     - Creates MySQL database `uzima_db`
     - Creates all tables
     - Inserts default data
     - Creates admin account

3. **Default Admin Login**
   - Username: `admin`
   - Password: `admin123`

---

## Client Portal

### Client Registration

1. Click on **"Client Login"** tab
2. Click **"New Client? Register Here"**
3. Fill in the registration form:
   - Full Name (required)
   - Phone Number (required)
   - Email Address (optional)
4. Click **OK**
5. **Important**: Save your Client ID (e.g., UZ-001)

### Client Login

1. Select **"Client Login"** tab
2. Enter your **Client ID** (e.g., UZ-001)
3. Click **Login**

### Client Dashboard Features

After logging in, you can:

#### View Personal Information
- Name
- Phone number
- Email address
- Client ID
- Payment status

#### View Service Details
- Client category (Domestic/Commercial/Industrial)
- Borehole location
- Depth/Height in meters
- Drilling type
- Pump type
- Pipe specifications (type, diameter, length)
- Number of outlets

#### View Cost Breakdown
- Survey Fee
- Local Authority Fee
- Drilling Fee
- Pump Installation Fee
- Plumbing Fee
- Depth Charge
- **Subtotal**
- Tax (16%)
- **Total Cost**

All costs are displayed in Kenyan Shillings (KSh).

---

## Staff/Admin Portal

### Staff Login

1. Click on **"Staff Login"** tab
2. Enter credentials:
   - Username: `admin`
   - Password: `admin123`
3. Click **Login**

### Admin Dashboard Overview

The dashboard shows:
- **Total Clients**: Number of registered clients
- **Total Revenue**: Sum of all client costs
- **Pending Payments**: Count of unpaid invoices
- **Total Tax**: Total tax collected (16% of all invoices)

### Navigation Menu

The sidebar provides access to:
- 📊 **Dashboard**: Overview and statistics
- ➕ **Add Client**: Register new clients
- 👥 **View Clients**: Manage existing clients
- 📈 **Analytics**: Revenue reports
- 🚪 **Logout**: Exit admin portal

---

## Features Guide

### 1. Adding a New Client

**Steps:**
1. Click **"Add Client"** in sidebar
2. Fill in **Personal Information**:
   - Name (required)
   - Address
   - Phone (required)
   - Email

3. Fill in **Service Details**:
   - Client Category: Domestic/Commercial/Industrial
   - Borehole Location
   - Depth/Height in meters
   - Drilling Type: Symmetric/Core/Geo-Technical
   - Pump Type: Submersible/Solar/Hand pump

4. Fill in **Plumbing Details**:
   - Pipe Type: PVC/Steel/HDPE/Copper
   - Pipe Diameter (inches)
   - Pipe Length (meters)
   - Number of Outlets

5. Select **Payment Status**: Pending/Paid/Partial

6. Click **"Save Client"**

**Result:**
- Client ID auto-generated (UZ-XXX)
- All costs calculated automatically
- Client saved to database
- Confirmation shown with total cost

### 2. Viewing All Clients

**Steps:**
1. Click **"View Clients"** in sidebar
2. The client table displays:
   - Client ID
   - Name
   - Phone
   - Category
   - Location
   - Total Cost
   - Payment Status
   - Action buttons (Edit/Delete)

**Features:**
- **Search**: Type in search bar to filter by ID or name
- **Refresh**: Click refresh button to reload data
- **Row Highlighting**: Hover over rows for highlight effect

### 3. Editing a Client

**Steps:**
1. Navigate to "View Clients"
2. Find the client in the table
3. Click **"Edit"** button
4. Update information:
   - Name
   - Address
   - Phone
   - Email
   - Payment Status
5. Click **OK** to save changes

**Note:** Service details cannot be edited directly. Create a new client record if services need to change.

### 4. Deleting a Client

**Steps:**
1. Navigate to "View Clients"
2. Find the client in the table
3. Click **"Delete"** button
4. Confirm deletion in popup
5. Client removed from database

**Warning:** This action cannot be undone!

### 5. Viewing Analytics

**Steps:**
1. Click **"Analytics"** in sidebar
2. View revenue breakdown:
   - Total Survey Fees
   - Total Local Authority Fees
   - Total Drilling Fees
   - Total Pump Installation
   - Total Plumbing Fees
   - Total Depth Charges
   - Total Tax Collected
   - **Total Revenue Generated**

All values displayed in KSh with proper formatting.

---

## Cost Calculation Examples

### Example 1: Domestic Client

**Input:**
- Category: Domestic
- Depth: 80 meters
- Drilling Type: Symmetric
- Pump Type: Hand pump
- Pipe Type: PVC
- Pipe Diameter: 2 inches
- Pipe Length: 100 meters
- Outlets: 2

**Automatic Calculation:**
- Survey Fee: KSh 7,000 (from Domestic category)
- Local Authority Fee: KSh 10,000 (from Domestic category)
- Drilling Fee: KSh 130,000 (Symmetric type)
- Pump Installation: KSh 30,000 (Hand pump)
- Depth Charge: 80m × KSh 1,000 = KSh 80,000
- Plumbing Fee: Calculated from pipe specs
- **Subtotal**: Sum of all fees
- **Tax (16%)**: Subtotal × 0.16
- **Total Cost**: Subtotal + Tax

### Example 2: Industrial Client

**Input:**
- Category: Industrial
- Depth: 250 meters
- Drilling Type: Core
- Pump Type: Submersible electric
- Pipe Type: Steel
- Pipe Diameter: 4 inches
- Pipe Length: 300 meters
- Outlets: 5

**Automatic Calculation:**
- Survey Fee: KSh 20,000 (Industrial)
- Local Authority Fee: KSh 50,000 (Industrial)
- Drilling Fee: KSh 225,000 (Core)
- Pump Installation: KSh 90,000 (Submersible)
- Depth Charge: 250m × KSh 2,000 = KSh 500,000 (201-300m range)
- Plumbing Fee: Higher due to Steel pipes and more outlets
- **Subtotal**: Sum of all fees
- **Tax (16%)**: Subtotal × 0.16
- **Total Cost**: Subtotal + Tax

---

## Tips and Best Practices

### For Clients
1. ✅ Save your Client ID immediately after registration
2. ✅ Contact admin to update service details
3. ✅ Check cost breakdown before proceeding
4. ✅ Note payment status on dashboard

### For Admin Staff
1. ✅ Verify all information before saving
2. ✅ Double-check depth and pipe measurements
3. ✅ Update payment status when received
4. ✅ Use search function for quick lookup
5. ✅ Review analytics regularly
6. ✅ Backup database periodically

### Data Entry Guidelines
1. **Phone Numbers**: Include country code if international
2. **Depth**: Enter in meters only
3. **Pipe Diameter**: Enter in inches
4. **Pipe Length**: Enter in meters
5. **Email**: Optional but recommended for communication

---

## Payment Status Guide

### Status Types
- **Pending**: No payment received (default)
- **Paid**: Full payment completed
- **Partial**: Partial payment received

### Updating Payment Status
1. Navigate to "View Clients"
2. Click "Edit" on client
3. Change payment status
4. Click OK to save

---

## Search and Filter

### Search Functionality
- Type in search box to filter clients
- Searches both Client ID and Name
- Real-time filtering as you type
- Clear search to show all clients

### Search Examples
- Search "UZ-001" → Shows client UZ-001
- Search "John" → Shows all clients with "John" in name
- Search "254" → Shows clients with 254 in ID or name

---

## Keyboard Shortcuts

- **Tab**: Move between fields
- **Enter**: Submit forms (in most cases)
- **Esc**: Close dialogs
- **Ctrl+F**: Focus search (when available)

---

## Troubleshooting

### Cannot Login as Client
- Verify your Client ID is correct (UZ-XXX format)
- Check if client exists in system
- Contact admin if needed

### Cannot See Client Details
- Ensure services have been entered by admin
- Refresh the page
- Re-login if needed

### Cost Shows Zero
- Contact admin to recalculate fees
- Ensure all service details are filled

### Cannot Add Client
- Check all required fields are filled
- Verify numeric fields have valid numbers
- Check database connection

---

## System Requirements

### Minimum Requirements
- Java 17 or higher
- MySQL 8.0 or higher
- 4GB RAM
- 500MB disk space
- 1280x720 screen resolution

### Recommended
- Java 17+
- MySQL 8.0+
- 8GB RAM
- 1GB disk space
- 1920x1080 screen resolution

---

## Support and Contact

For technical support or issues:
1. Check this user guide
2. Review README.md for setup issues
3. Check DATABASE_SETUP.md for database issues
4. Contact system administrator

---

## Security Notes

1. 🔒 Change default admin password after first login
2. 🔒 Do not share admin credentials
3. 🔒 Clients should keep their Client IDs secure
4. 🔒 Regular database backups recommended
5. 🔒 Use secure password for MySQL

---

**Last Updated**: November 2025  
**Version**: 1.0.0  
**System**: Uzima Borehole System

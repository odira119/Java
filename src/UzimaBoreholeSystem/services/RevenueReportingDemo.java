package UzimaBoreholeSystem.services;

import UzimaBoreholeSystem.models.Client;

/**
 * Revenue Reporting Demo
 * 
 * This class demonstrates how to use the revenue tracking features:
 * 1. 16% tax calculation on all services
 * 2. Local authority fee inclusion in total calculations
 * 3. Revenue breakdown by service type
 * 4. Revenue tracking per customer
 * 5. Comprehensive business performance reports
 */
public class RevenueReportingDemo {
    
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║    UZIMA BOREHOLE SYSTEM - REVENUE TRACKING DEMO          ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        // Initialize services
        ClientService clientService = new ClientService();
        RevenueService revenueService = new RevenueService();
        
        // ========== DEMONSTRATION 1: Individual Customer Revenue ==========
        System.out.println("DEMO 1: Getting Revenue from a Specific Customer");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        // Example: Get revenue from client UZ-001
        String clientId = "UZ-001";
        double customerRevenue = clientService.getRevenueFromCustomer(clientId);
        double customerTax = clientService.getTaxPaidByCustomer(clientId);
        
        System.out.println("Client ID: " + clientId);
        System.out.println("Revenue from this customer: KSh " + String.format("%,.2f", customerRevenue));
        System.out.println("Tax paid by this customer: KSh " + String.format("%,.2f", customerTax));
        
        // Get detailed customer report
        String customerReport = clientService.getCustomerRevenueReport(clientId);
        System.out.println("\n" + customerReport);
        
        // ========== DEMONSTRATION 2: Revenue by Service Type ==========
        System.out.println("\nDEMO 2: Revenue Breakdown by Service Type");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        double surveyRevenue = clientService.getTotalRevenueFromSurveyFees();
        double localAuthorityRevenue = clientService.getTotalRevenueFromLocalAuthorityFees();
        double drillingRevenue = clientService.getTotalRevenueFromDrilling();
        double pumpRevenue = clientService.getTotalRevenueFromPumpInstallation();
        double plumbingRevenue = clientService.getTotalRevenueFromPlumbing();
        double depthChargeRevenue = clientService.getTotalRevenueFromDepthCharges();
        double totalTax = clientService.getTotalTaxCollected();
        double totalRevenue = clientService.getTotalRevenueAllServices();
        
        System.out.println("Survey Fees:              KSh " + String.format("%,15.2f", surveyRevenue));
        System.out.println("Local Authority Fees:     KSh " + String.format("%,15.2f", localAuthorityRevenue));
        System.out.println("Drilling Services:        KSh " + String.format("%,15.2f", drillingRevenue));
        System.out.println("Pump Installation:        KSh " + String.format("%,15.2f", pumpRevenue));
        System.out.println("Plumbing Services:        KSh " + String.format("%,15.2f", plumbingRevenue));
        System.out.println("Depth Charges:            KSh " + String.format("%,15.2f", depthChargeRevenue));
        System.out.println("──────────────────────────────────────────────────────────");
        System.out.println("Tax Collected (16%):      KSh " + String.format("%,15.2f", totalTax));
        System.out.println("══════════════════════════════════════════════════════════");
        System.out.println("TOTAL REVENUE:            KSh " + String.format("%,15.2f", totalRevenue));
        
        // ========== DEMONSTRATION 3: Comprehensive Business Report ==========
        System.out.println("\n\nDEMO 3: Comprehensive Business Performance Report");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        String comprehensiveReport = revenueService.generateComprehensiveReport();
        System.out.println(comprehensiveReport);
        
        // ========== DEMONSTRATION 4: Revenue Breakdown Report ==========
        System.out.println("\nDEMO 4: Quick Revenue Breakdown");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        String breakdown = clientService.getRevenueBreakdownReport();
        System.out.println(breakdown);
        
        // ========== DEMONSTRATION 5: Business Statistics ==========
        System.out.println("\nDEMO 5: Additional Business Statistics");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        int totalClients = clientService.getAllClients().size();
        int paidClients = clientService.getPaidClientsCount();
        int pendingClients = clientService.getPendingPaymentsCount();
        double avgRevenue = clientService.getAverageRevenuePerCustomer();
        double pendingAmount = revenueService.getPendingPaymentAmount();
        
        System.out.println("Total Clients:              " + totalClients);
        System.out.println("Paid Clients:               " + paidClients);
        System.out.println("Pending Payments:           " + pendingClients);
        System.out.println("Pending Amount:             KSh " + String.format("%,.2f", pendingAmount));
        System.out.println("Average Revenue per Client: KSh " + String.format("%,.2f", avgRevenue));
        
        // ========== DEMONSTRATION 6: Tax Calculation Details ==========
        System.out.println("\n\nDEMO 6: Tax Calculation Explanation");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        CalculationService calcService = new CalculationService();
        System.out.println("Tax Rate: " + calcService.getTaxRatePercentage() + "%");
        System.out.println("\nHow tax is calculated:");
        System.out.println("1. Subtotal = Survey Fee + Local Authority Fee + Drilling Fee");
        System.out.println("              + Pump Installation + Plumbing + Depth Charge");
        System.out.println("2. Tax = Subtotal × 0.16 (16%)");
        System.out.println("3. Total Cost = Subtotal + Tax");
        System.out.println("\nExample:");
        System.out.println("  Subtotal = KSh 100,000.00");
        System.out.println("  Tax (16%) = KSh  16,000.00");
        System.out.println("  Total    = KSh 116,000.00");
        
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║                    END OF DEMO                             ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Example: How to calculate fees for a new client
     */
    public static void demonstrateClientCalculation() {
        // Create a new client
        Client client = new Client();
        client.setClientId("UZ-999");
        client.setName("Demo Customer");
        client.setClientCategory("Domestic");
        client.setDrillingType("Symmetric");
        client.setPumpType("Submersible electric pump");
        client.setPipeType("Standard");
        client.setPipeDiameter(2);
        client.setPipeLength(50);
        client.setNumberOfOutlets(3);
        client.setDepthOrHeight(150);
        
        // Calculate all fees (including 16% tax)
        CalculationService calcService = new CalculationService();
        calcService.calculateAllFees(client);
        
        // Display the results
        System.out.println("\nCalculation Results for " + client.getName() + ":");
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("Survey Fee:              KSh " + String.format("%,.2f", client.getSurveyFee()));
        System.out.println("Local Authority Fee:     KSh " + String.format("%,.2f", client.getLocalAuthorityFee()));
        System.out.println("Drilling Fee:            KSh " + String.format("%,.2f", client.getDrillingFee()));
        System.out.println("Pump Installation:       KSh " + String.format("%,.2f", client.getPumpInstallationFee()));
        System.out.println("Plumbing Fee:            KSh " + String.format("%,.2f", client.getPlumbingFee()));
        System.out.println("Depth Charge:            KSh " + String.format("%,.2f", client.getDepthCharge()));
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("Subtotal:                KSh " + String.format("%,.2f", client.getSubtotal()));
        System.out.println("Tax (16%):               KSh " + String.format("%,.2f", client.getTaxPaid()));
        System.out.println("═════════════════════════════════════════════════════════════");
        System.out.println("TOTAL COST:              KSh " + String.format("%,.2f", client.getTotalCost()));
        System.out.println("═════════════════════════════════════════════════════════════");
    }
}

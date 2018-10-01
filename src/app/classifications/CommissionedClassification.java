package app.classifications;

import app.SalesReceipt;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CommissionedClassification extends PaymentClassification {

    private double commissionRate;
    private double monthlySalary;
    private Map<Calendar, SalesReceipt> salesReceiptMap = new HashMap<Calendar, SalesReceipt>();

    public CommissionedClassification(double salary, double commissionRate) {
        this.commissionRate = commissionRate;
        this.monthlySalary = salary;
    }

    @Override
    public double calculatePay(PayCheck payCheck) {
        double totalPay = monthlySalary;
        for (SalesReceipt receipt : salesReceiptMap.values()) {
            if (isInPayPeriod(receipt.getDate(), payCheck)) {
                totalPay += receipt.getAmount() * commissionRate;
            }
        }
        return totalPay;
    }

}
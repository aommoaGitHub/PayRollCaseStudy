package app.affiliation;

import app.classifications.PayCheck;

import java.util.Calendar;

public interface Affiliation {
    double calculateDeductions(PayCheck pc);

    void addServiceCharge(Calendar date, double amount);
}

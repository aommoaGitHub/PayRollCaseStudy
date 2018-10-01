package app.affiliation;

import app.classifications.PayCheck;

import java.util.Calendar;

public class NoAffiliation implements Affiliation {

    @Override
    public double calculateDeductions(PayCheck pc) {
        return 0.0;
    }

    @Override
    public void addServiceCharge(Calendar date, double amount) {
        // do nothing
    }

}

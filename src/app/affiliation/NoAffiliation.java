package app.affiliation;

import app.classifications.PayCheck;

public class NoAffiliation implements Affiliation {

    @Override
    public double calculateDeductions(PayCheck pc) {
        return 0.0;
    }

}

package app.methods;

import app.classifications.PayCheck;

public class HoldMethod implements PaymentMethod {
    @Override
    public void pay(PayCheck payCheck)
    {
        payCheck.setField("Disposition", "Hold");
    }
}
package app.methods;

import app.classifications.PayCheck;

public interface PaymentMethod {

    void pay(PayCheck payCheck);
}

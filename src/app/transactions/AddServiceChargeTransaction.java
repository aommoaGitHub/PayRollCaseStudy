package app.transactions;

import app.Employee;
import app.affiliation.Affiliation;
import app.database.PayrollDatabase;

import java.util.Calendar;

public class AddServiceChargeTransaction implements Transaction {

    private PayrollDatabase db = PayrollDatabase.getInstance();

	private Calendar date;
	private double amount;
	private int memberId;

	public AddServiceChargeTransaction(int memberId, Calendar date, double amount ) {
		this.date = date;
		this.amount = amount;
		this.memberId = memberId;
	}

	@Override
	public void execute( ) {
		Employee unionMember = db.getUnionMember( memberId );
        Affiliation affiliation = unionMember.getAffiliation();
		affiliation.addServiceCharge( date, amount );
	}
}

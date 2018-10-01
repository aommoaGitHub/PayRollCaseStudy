package app.transactions;

import app.Employee;
import app.TimeCard;
import app.classifications.HourlyClassification;
import app.classifications.PaymentClassification;
import app.database.PayrollDatabase;

import java.util.Calendar;

public class AddTimeCardTransaction implements Transaction {

	private Calendar date;
	private double hours;
	private int employeeId;

	public AddTimeCardTransaction(Calendar date, double hours, int employeeId ) {
		this.date = date;
		this.hours = hours;
		this.employeeId = employeeId;
	}

	@Override
	public void execute() throws Exception {
		Employee employee = PayrollDatabase.getInstance().getEmployee( employeeId );
		if ( employee != null ) {
			PaymentClassification paymentClassification = employee.getPaymentClassification( );
			if ( paymentClassification instanceof HourlyClassification ) {
				HourlyClassification classification = (HourlyClassification) paymentClassification;
				classification.addTimeCard( new TimeCard( date, hours ) );
			} else {
                throw new Exception("Tried to add time card to non-hourly employee");
			}
		} else {
            throw new Exception("No such employee");
        }
	}

}

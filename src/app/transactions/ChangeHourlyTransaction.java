package app.transactions;

import app.classifications.HourlyClassification;
import app.classifications.PaymentClassification;
import app.schedules.PaymentSchedule;
import app.schedules.WeeklyPaymentSchedule;

public class ChangeHourlyTransaction extends ChangeClassificationTransaction {

	private double hourlySalary;

	public ChangeHourlyTransaction(int employeeId, double hourlySalary ) {
		super( employeeId );
		this.hourlySalary = hourlySalary;
	}

	@Override
    PaymentSchedule getNewPaymentSchedule( ) {
		return new WeeklyPaymentSchedule( );
	}

    @Override
    PaymentClassification getNewPaymentClassification( ) {
        return new HourlyClassification( hourlySalary );
    }

}

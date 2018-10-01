package app.transactions;

import app.classifications.PaymentClassification;
import app.classifications.SalariedClassification;
import app.schedules.MonthlyPaymentSchedule;
import app.schedules.PaymentSchedule;

public class AddSalariedEmployeeTransaction extends AddEmployeeTransaction
{

	private double salary;

	public AddSalariedEmployeeTransaction(int employeeId, String name,
                                          String address, double salary )
	{
		super( employeeId, name, address );
		this.salary = salary;
	}

	protected PaymentSchedule getPaymentSchedule( ) {
		return new MonthlyPaymentSchedule( );
	}

	protected PaymentClassification getPaymentClassification( ) {
		return new SalariedClassification( salary );
	}

}

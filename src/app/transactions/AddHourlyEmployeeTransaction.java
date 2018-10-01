package app.transactions;

import app.classifications.HourlyClassification;
import app.classifications.PaymentClassification;
import app.schedules.PaymentSchedule;
import app.schedules.WeeklyPaymentSchedule;

public class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {

	private double hourlyRate;

	public AddHourlyEmployeeTransaction(int employeeId, String employeeName,
                                        String employeeAddress,
                                        double hourlyRate )
	{
		super( employeeId, employeeName, employeeAddress );
		this.hourlyRate = hourlyRate;
	}

	@Override
	public PaymentClassification getPaymentClassification() {
		return new HourlyClassification(hourlyRate);
	}

	@Override
	public PaymentSchedule getPaymentSchedule() {
		return new WeeklyPaymentSchedule();
	}
}

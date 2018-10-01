package app.transactions;

import app.classifications.PaymentClassification;
import app.classifications.SalariedClassification;
import app.schedules.MonthlyPaymentSchedule;
import app.schedules.PaymentSchedule;

public class AddCommissionedEmployeeTransaction extends AddEmployeeTransaction {

	private double monthlySalary;
	private double commissionRate;

	public AddCommissionedEmployeeTransaction(int employeeId,
											  String employeeName,
											  String employeeAddress,
											  double monthlySalary,
											  double commissionRate ) {
		super( employeeId, employeeName, employeeAddress);
		this.monthlySalary = monthlySalary;
		this.commissionRate = commissionRate;
	}

	//refactor
	@Override
	public PaymentClassification getPaymentClassification() {
		return new SalariedClassification(monthlySalary);
	}

	//refactor
	@Override
	public PaymentSchedule getPaymentSchedule() {
		return new MonthlyPaymentSchedule();
	}
}

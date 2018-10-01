package app.transactions;

import app.classifications.CommissionedClassification;
import app.classifications.PaymentClassification;
import app.schedules.BiWeeklyPaymentSchedule;
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

	@Override
	public PaymentSchedule getPaymentSchedule() {
		return new BiWeeklyPaymentSchedule();
	}

	@Override
	public PaymentClassification getPaymentClassification() {
		return new CommissionedClassification(monthlySalary, commissionRate);
	}
}

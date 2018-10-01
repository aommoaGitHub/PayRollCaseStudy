package app.transactions;

import app.classifications.PaymentClassification;
import app.schedules.PaymentSchedule;

import java.util.Calendar;

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
}

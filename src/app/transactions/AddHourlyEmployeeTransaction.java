package app.transactions;

public class AddHourlyEmployeeTransaction extends AddEmployeeTransaction {

	private double hourlyRate;

	public AddHourlyEmployeeTransaction(int employeeId, String employeeName,
                                        String employeeAddress,
                                        double hourlyRate )
	{
		super( employeeId, employeeName, employeeAddress );
		this.hourlyRate = hourlyRate;
	}

}

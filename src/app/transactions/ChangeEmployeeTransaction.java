package app.transactions;

import app.Employee;
import app.database.PayrollDatabase;

public abstract class ChangeEmployeeTransaction implements Transaction {

	private PayrollDatabase db = PayrollDatabase.getInstance();
	private int empId;

	public ChangeEmployeeTransaction(int empId)
	{
		this.empId = empId;
	}

	@Override
	public void execute( )
	{
		Employee employee = db.getEmployee(empId);
		if (employee != null)
		    changeEmployee( employee );
	}

	public abstract void changeEmployee( Employee employee );

}

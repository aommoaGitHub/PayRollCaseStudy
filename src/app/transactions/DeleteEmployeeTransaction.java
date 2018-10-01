package app.transactions;

import app.database.PayrollDatabase;

public class DeleteEmployeeTransaction implements Transaction {

	private int employeeId;

	public DeleteEmployeeTransaction(int employeeId )
	{
		this.employeeId = employeeId;
	}

	@Override
	public void execute( ) {
		PayrollDatabase.getInstance().deleteEmployee( employeeId );
	}
}

package app.transactions;

import app.Employee;
import app.classifications.PayCheck;
import app.database.PayrollDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PaydayTransaction implements Transaction {

	private Calendar payDate;
	private PayrollDatabase database = PayrollDatabase.getInstance();
	private Map<Integer, PayCheck> payChecks = new HashMap<Integer, PayCheck>( );

	public PaydayTransaction(Calendar payDate) {
		this.payDate = payDate;
	}

	public void execute( ) {
		for ( Integer employeeId : database.getAllEmployeeIds( ) ) {
			Employee employee = database.getEmployee( employeeId );
			if ( employee.isPayDate( payDate ) ) {
				PayCheck payCheck = new PayCheck(employee.getPayPeriodStartDay( payDate ), payDate );
				payChecks.put( employeeId, payCheck );
				employee.payDay( payCheck );
			}
		}
	}

	public PayCheck getPaycheck(int employeeId ) {
		return payChecks.get( employeeId );
	}
}
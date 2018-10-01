package app.transactions;

import app.Employee;
import app.affiliation.Affiliation;

public abstract class ChangeAffiliationTransaction extends ChangeEmployeeTransaction {

	public ChangeAffiliationTransaction(int employeeId )
	{
		super( employeeId );
	}

	protected abstract Affiliation getAffiliation( );

	protected abstract void recordMembership( Employee employee );

    @Override
    public void changeEmployee( Employee employee ) {
        recordMembership( employee );
        employee.setAffiliation(getAffiliation());
    }

}

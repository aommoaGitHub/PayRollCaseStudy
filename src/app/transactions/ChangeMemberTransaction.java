package app.transactions;

import app.Employee;
import app.affiliation.Affiliation;
import app.affiliation.UnionAffiliation;
import app.database.PayrollDatabase;

public class ChangeMemberTransaction extends ChangeAffiliationTransaction {

	private int memberId;
	private double dues;

	public ChangeMemberTransaction(int employeeId, int memberId, double dues ) {
		super( employeeId );
		this.memberId = memberId;
		this.dues = dues;
	}

	@Override
	protected Affiliation getAffiliation( ) {
		return new UnionAffiliation( memberId, dues );
	}

	@Override
	protected void recordMembership( Employee employee ) {
		PayrollDatabase.getInstance().addUnionMember( memberId, employee );
	}

}

package app.transactions;

import app.Employee;
import app.affiliation.Affiliation;
import app.affiliation.NoAffiliation;
import app.affiliation.UnionAffiliation;
import app.database.PayrollDatabase;

public class ChangeUnaffiliatedTransaction extends ChangeAffiliationTransaction {

    public ChangeUnaffiliatedTransaction(int employeeId) { super(employeeId); }

    @Override
    protected Affiliation getAffiliation() {
        return new NoAffiliation();
    }

    @Override
    protected void recordMembership(Employee employee) {
        Affiliation affiliation = employee.getAffiliation();
        if (affiliation instanceof UnionAffiliation) {
            UnionAffiliation unionAffiliation = (UnionAffiliation) affiliation;
            PayrollDatabase.getInstance().removeUnionMember(unionAffiliation.getMemberId());
        }
    }

}

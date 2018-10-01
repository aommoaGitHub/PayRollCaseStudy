import app.Employee;
import app.affiliation.Affiliation;
import app.affiliation.UnionAffiliation;
import app.database.PayrollDatabase;
import app.transactions.AddEmployeeTransaction;
import app.transactions.AddHourlyEmployeeTransaction;
import app.transactions.ChangeMemberTransaction;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ChangeMemberTransactionTest {

	private PayrollDatabase db = PayrollDatabase.getInstance();

	@Test
	public void testChangeMemberTransaction( )
	{
		int employeeId = 2;
		int memberId = 7734;
		AddEmployeeTransaction addEmployeeTransaction = new AddHourlyEmployeeTransaction(
				employeeId, "Bill", "Home", 15.25 );
		addEmployeeTransaction.execute( );

		ChangeMemberTransaction changeMemberTransaction = new ChangeMemberTransaction(
				employeeId, memberId, 99.42 );
		changeMemberTransaction.execute( );

		Employee employee = db.getEmployee( employeeId );
		assertThat( employee, is( notNullValue( ) ) );
		assertThat( employee.getAffiliation( ), is( notNullValue( ) ) );

        Affiliation affiliation = employee.getAffiliation();
        UnionAffiliation unionAffiliation = (UnionAffiliation) affiliation;
		assertThat( unionAffiliation.getDues( ),
				is(closeTo(99.42, 0.00001)));

		Employee member = db.getUnionMember( memberId );
        assertThat( member, is( notNullValue( ) ) );
        assertThat( member, is( employee ) );
	}
}

import app.Employee;
import app.database.PayrollDatabase;
import app.transactions.AddEmployeeTransaction;
import app.transactions.AddHourlyEmployeeTransaction;
import app.transactions.ChangeNameTransaction;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ChangeNameTransactionTest {

	private PayrollDatabase db = PayrollDatabase.getInstance();

	@Test
	public void testChangeNameTransaction( ) {
		int employeeId = 2;
        AddEmployeeTransaction addEmployeeTransaction = new AddHourlyEmployeeTransaction(
				employeeId, "Bill", "Home", 15.25 );
		addEmployeeTransaction.execute( );

		ChangeNameTransaction changeNameTransaction = new ChangeNameTransaction(
				employeeId, "Bob" );
		changeNameTransaction.execute( );

		Employee employee = db.getEmployee( employeeId );
		assertThat( employee.getName( ), is( "Bob" ) );
	}

}

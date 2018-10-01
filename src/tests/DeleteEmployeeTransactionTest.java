import app.Employee;
import app.database.PayrollDatabase;
import app.methods.HoldMethod;
import app.transactions.AddCommissionedEmployeeTransaction;
import app.transactions.DeleteEmployeeTransaction;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class DeleteEmployeeTransactionTest {

	@Rule
	public PayrollDatabase db = PayrollDatabase.getInstance();

	@Test
	public void testDeleteEmployees( ) {
		int employeeId = 3;
		AddCommissionedEmployeeTransaction t = new AddCommissionedEmployeeTransaction(
				 employeeId,
                "Lance",
                "Home",
                2500.0,
                3.2 );
		t.execute( );

		Employee employee = db.getEmployee( employeeId );
		assertThat( employee, is( notNullValue( ) ) );

		DeleteEmployeeTransaction dt = new DeleteEmployeeTransaction( employeeId );
		dt.execute( );

		employee = db.getEmployee( employeeId );
		assertThat( employee, is( nullValue( ) ) );
	}

}

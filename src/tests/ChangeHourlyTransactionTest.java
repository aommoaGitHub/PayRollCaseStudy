import app.Employee;
import app.classifications.HourlyClassification;
import app.database.PayrollDatabase;
import app.schedules.WeeklyPaymentSchedule;
import app.transactions.AddCommissionedEmployeeTransaction;
import app.transactions.AddEmployeeTransaction;
import app.transactions.ChangeHourlyTransaction;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class ChangeHourlyTransactionTest {

	@Rule
	public final PayrollDatabase dbRule = PayrollDatabase.getInstance();

	@Test
	public void testChangeHourlyTransaction( ) {
		int employeeId = 3;
        AddEmployeeTransaction addCommissionedEmployeeTransaction = new AddCommissionedEmployeeTransaction(
				employeeId, "Lance", "Home", 2500, 3.2 );
		addCommissionedEmployeeTransaction.execute( );

		ChangeHourlyTransaction changeHourlyTransaction = new ChangeHourlyTransaction(employeeId, 27.52 );
		changeHourlyTransaction.execute( );

		Employee employee = dbRule.getEmployee( employeeId );
		assertThat( employee.getPaymentClassification( ), is( instanceOf( HourlyClassification.class ) ) );
		HourlyClassification paymentClassification = (HourlyClassification) employee.getPaymentClassification( );
		assertThat( paymentClassification.getHourlyRate( ), is( closeTo( 27.52, 0.00001 ) ) );
		assertThat( employee.getPaymentSchedule( ), is( instanceOf( WeeklyPaymentSchedule.class ) ) );
	}

}

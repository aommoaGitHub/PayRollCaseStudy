import app.Employee;
import app.ServiceCharge;
import app.affiliation.UnionAffiliation;
import app.database.PayrollDatabase;
import app.transactions.AddHourlyEmployeeTransaction;
import app.transactions.AddServiceChargeTransaction;
import org.junit.Rule;
import org.junit.Test;

import static constant.TestConstants.FLOAT_ACCURACY;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AddServiceChargeTransactionTest {

	@Rule
	public PayrollDatabase db = PayrollDatabase.getInstance();

	@Test
	public void testAddServiceCharge( ) {
		int employeeId = 2;
		AddHourlyEmployeeTransaction addEmployeeTransaction = new AddHourlyEmployeeTransaction(
				employeeId, "Bill", "Home", 15.25 );
		addEmployeeTransaction.execute( );

		Employee employee = db.getEmployee( employeeId );
		assertThat( employee, is( notNullValue( ) ) );

		int memberId = 86; //Maxwell smart
        UnionAffiliation unionAffiliation = new UnionAffiliation( memberId,12.5 );
		db.addUnionMember( memberId, employee );
		assertThat( db.getUnionMember( memberId ), is( notNullValue( ) ) );

		Calendar date = new GregorianCalendar( 2001, 11, 01 );
		AddServiceChargeTransaction addServiceChargeTransaction = new AddServiceChargeTransaction(
				memberId, date, 12.95 );
		addServiceChargeTransaction.execute( );
		ServiceCharge serviceCharge = unionAffiliation.getServiceCharge( date );
		assertThat( serviceCharge, is( notNullValue( ) ) );
		assertThat( serviceCharge.getAmount( ), is( closeTo( 12.95, FLOAT_ACCURACY ) ) );

	}

}

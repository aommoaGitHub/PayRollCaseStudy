import app.Employee;
import app.TimeCard;
import app.classifications.HourlyClassification;
import app.classifications.PaymentClassification;
import app.database.PayrollDatabase;
import app.transactions.AddHourlyEmployeeTransaction;
import app.transactions.AddTimeCardTransaction;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class AddTimeCardTransactionTest {

    private PayrollDatabase db = PayrollDatabase.getInstance();

	@Test
	public void testTimeCardTransaction( ) {
		int empId = 2;
		AddHourlyEmployeeTransaction ht = new AddHourlyEmployeeTransaction(
				empId, "Billy", "Home", 15.25 );
		ht.execute( );

		Calendar date = new GregorianCalendar( 2001, 10, 31 );
		AddTimeCardTransaction tct = new AddTimeCardTransaction( date, 8.0, empId );

        try {
            tct.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Employee employee = db.getEmployee( empId );
		assertThat( employee, is( notNullValue( ) ) );

        PaymentClassification paymentClassification = employee.getPaymentClassification();
        HourlyClassification hourlyClassification = (HourlyClassification) paymentClassification;
        assertThat(hourlyClassification, is(notNullValue()));

        TimeCard timeCard = hourlyClassification.getTimeCard(date);
		assertNotNull( timeCard );
		assertThat( timeCard.getItsHours( ), is( 8.0 ) );

	}

}

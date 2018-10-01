import app.transactions.*;
import app.database.PayrollDatabase;
import org.junit.Rule;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import app.classifications.PayCheck;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class PaySingleSalariedEmployeeTest {

	@Rule
	public PayrollDatabase db = PayrollDatabase.getInstance();

	@Test
	public void paySingleSalariedEmployee() {
        final int empId = 1;
        AddSalariedEmployeeTransaction t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home",1000.00);
        t.execute();
        Calendar payDate = new GregorianCalendar(2001, 10, 31);
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate, 1000.0);
    }

    @Test
    public void paySingleSalariedEmployeeOnWrongDate() {
        final int empId = 1;
        AddSalariedEmployeeTransaction t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.00);
        t.execute();
        Calendar payDate = new GregorianCalendar( 2001, 11, 29 );
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        PayCheck pc = pt.getPaycheck(empId);
        assertThat(pc, is(nullValue()));
    }

    @Test
    public void paySingleHourlyEmployeeNoTimeCards() {
        final int empId = 2;
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, "Bill", "Home", 15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar( 2001, 11, 9 );	// Friday
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate,0.00 );
    }

    @Test
    public void paySingleHourlyEmployeeOneTimeCard() {
        final int empId = 2;
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, "Bill", "Home", 15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar( 2001, 11, 9 );	// Friday
        AddTimeCardTransaction tc = new AddTimeCardTransaction(payDate, 2.0, empId);
        try {
            tc.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate,30.50 );
    }

    @Test
    public void paySingleHourlyEmployeeOvertimeOneTimeCard() {
        final int empId = 2;
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, "Bill", "Home", 15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar( 2001, 11, 9 );	// Friday
        AddTimeCardTransaction tc = new AddTimeCardTransaction(payDate, 9.0, empId);
        try {
            tc.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheck(pt, empId, payDate,(8 + 1.5) * 15.25 );
    }

    @Test
    public void paySingleHourlyEmployeeOnWrongDate() {
        final int empId = 2;
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, "Bill", "Home", 15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar( 2001, 11, 8 ); // Thursday

        AddTimeCardTransaction tc = new AddTimeCardTransaction(payDate, 9.0, empId);
        try {
            tc.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        PayCheck pc = pt.getPaycheck(empId);
        assertThat(pc, is(nullValue()));
    }

    @Test
    public void paySingleHourlyEmployeeTwoTimeCards() {
        final int empId = 2;
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, "Bill", "Home", 15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar( 2001, 11, 9 );	// Friday

        AddTimeCardTransaction tc = new AddTimeCardTransaction(payDate, 2.0, empId);
        try {
            tc.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AddTimeCardTransaction tc2 = new AddTimeCardTransaction(new GregorianCalendar( 2001, 11, 3 ), 5.0, empId);
        try {
            tc2.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePaycheck(pt, empId, payDate, 7 * 15.25 );
    }

    @Test
    public void paySingleHourlyEmployeeWithTimeCardsSpanningTwoPayPeriods() throws Exception {
        final int empId = 2;
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, "Bill", "Home", 15.25);
        t.execute();
        Calendar payDate = new GregorianCalendar( 2001, 11, 9 );	// Friday
        Calendar dateInPreviousPayPeriod = new GregorianCalendar( 2001, 11, 2 );	// Friday

        AddTimeCardTransaction tc = new AddTimeCardTransaction(payDate, 2.0, empId);
        tc.execute();
        AddTimeCardTransaction tc2 = new AddTimeCardTransaction(dateInPreviousPayPeriod, 5.0, empId);
        tc2.execute();
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePaycheck(pt, empId, payDate, 2 * 15.25);
    }

    private void validatePaycheck(PaydayTransaction pt, int empId, Calendar payDate, double pay) {
        PayCheck pc = pt.getPaycheck(empId);
        assertThat(pc, is(notNullValue()));
        assertThat(pc.getPayPeriodEnd(), is(payDate));
        assertThat(pc.getGrossPay(), is(pay));
        assertThat(pc.getField("Disposition"), is("Hold"));
        assertThat(pc.getDeductions(), is(0.0));
        assertThat(pc.getNetPay(), is(pay));
    }

    @Test
    public void salariedUnionMemberDues() {
        final int empId = 1;
        AddSalariedEmployeeTransaction t = new AddSalariedEmployeeTransaction(empId, "Bob", "Home", 1000.00);
        t.execute();
        final int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42 );
        cmt.execute();
        Calendar payDate = new GregorianCalendar( 2001, 11, 30 );	// Friday
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();
        validatePaycheckWithDeductions(pt, empId, payDate, 1000.00, 952.90, 47.10);	// 1000 - (9.42 * 5)
    }

    @Test
    public void hourlyUnionMemberServiceCharge() {
        final int empId = 1;
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, "Bill", "Home", 15.24);
        t.execute();
        final int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId,9.42);
        cmt.execute();
        Calendar payDate = new GregorianCalendar( 2001, 11, 9 );	// Friday
        AddServiceChargeTransaction sct = new AddServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();
        AddTimeCardTransaction tct = new AddTimeCardTransaction(payDate, 8.0, empId);
        try {
            tct.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePaycheckWithDeductions(pt, empId, payDate, 8*15.24, (8*15.24)-(9.42+19.42), 9.42+19.42);
    }

    @Test
    public void serviceChargesSpanningMultiplePayPeriods() {
        final int empId = 1;
        AddHourlyEmployeeTransaction t = new AddHourlyEmployeeTransaction(empId, "Bill", "Home", 15.24);
        t.execute();
        final int memberId = 7734;
        ChangeMemberTransaction cmt = new ChangeMemberTransaction(empId, memberId, 9.42);
        cmt.execute();
        Calendar earlyDate = new GregorianCalendar( 2001, 11, 2 );	// previous Friday
        Calendar payDate = new GregorianCalendar( 2001, 11, 9 );	// Friday
        Calendar lateDate = new GregorianCalendar( 2001, 11, 16 );	// next Friday

        AddServiceChargeTransaction sct = new AddServiceChargeTransaction(memberId, payDate, 19.42);
        sct.execute();
        AddServiceChargeTransaction sctEarly = new AddServiceChargeTransaction(memberId, earlyDate, 100.00);
        sctEarly.execute();
        AddServiceChargeTransaction sctLate = new AddServiceChargeTransaction(memberId, lateDate,200.00);
        sctLate.execute();
        AddTimeCardTransaction tct = new AddTimeCardTransaction(payDate, 8.0, empId);
        try {
            tct.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PaydayTransaction pt = new PaydayTransaction(payDate);
        pt.execute();

        validatePaycheckWithDeductions(pt, empId, payDate, 121.92, 93.08, 28.84);
    }

    private void validatePaycheckWithDeductions(PaydayTransaction pt, int empId, Calendar payDate, double grossPay, double netPay, double deductions) {
        PayCheck pc = pt.getPaycheck(empId);
        assertThat(pc, is(notNullValue()));
        assertThat(pc.getPayPeriodEnd(), is(payDate));
        assertThat(pc.getGrossPay(), is(grossPay));
        assertThat(pc.getField("Disposition"), is("Hold"));
        assertThat(pc.getDeductions(), is(deductions));
        assertThat(pc.getNetPay(), is(netPay));
    }

}

import app.Employee;
import app.affiliation.Affiliation;
import app.affiliation.NoAffiliation;
import app.classifications.PaymentClassification;
import app.classifications.SalariedClassification;
import app.database.PayrollDatabase;
import app.methods.HoldMethod;
import app.methods.PaymentMethod;
import app.schedules.MonthlyPaymentSchedule;
import app.schedules.PaymentSchedule;
import app.transactions.AddEmployeeTransaction;
import app.transactions.AddSalariedEmployeeTransaction;
import junit.framework.TestCase;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

public class AddEmployeeTransactionTest extends TestCase {

    private PayrollDatabase db = PayrollDatabase.getInstance();

    public void testAddSalariedEmployee( ) {
        int employeeId = 1;
        AddEmployeeTransaction addEmployeeTransaction = new AddSalariedEmployeeTransaction(
                employeeId, "Bob", "Home", 1000.0);
        addEmployeeTransaction.execute();

        Employee employee = db.getEmployee(employeeId);
        assert (employee.getName().equals("Bob"));

        PaymentClassification pc = employee.getPaymentClassification();
        assertThat(pc, instanceOf(SalariedClassification.class));

        SalariedClassification sc = (SalariedClassification) pc;
        assertThat(sc.getSalary(), closeTo(1000.0, 0.00001));

        PaymentSchedule ps = employee.getPaymentSchedule();
        assertThat(ps, instanceOf(MonthlyPaymentSchedule.class));

        PaymentMethod pm = employee.getPaymentMethod();
        assertThat(pm, instanceOf(HoldMethod.class));

        Affiliation af = employee.getAffiliation();
        assertThat(af, instanceOf(NoAffiliation.class));
    }

}
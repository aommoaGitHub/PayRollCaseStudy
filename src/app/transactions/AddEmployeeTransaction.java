package app.transactions;

import app.Employee;
import app.classifications.PaymentClassification;
import app.database.PayrollDatabase;
import app.methods.HoldMethod;
import app.schedules.PaymentSchedule;

public abstract class AddEmployeeTransaction implements Transaction {
    private int empid;
    private String itsAddress;
    private String itsName;

    public AddEmployeeTransaction( int employeeId, String employeeName, String employeeAddress) {
        this.empid = employeeId;
        this.itsName = employeeName;
        this.itsAddress = employeeAddress;
    }

    @Override
    public void execute() {
            Employee employee = new Employee( empid, itsName, itsAddress );

        employee.setPaymentMethod(new HoldMethod());
        PaymentClassification pc = this.getPaymentClassification();
            employee.setPaymentClassification( pc );
        PaymentSchedule pm = this.getPaymentSchedule();
            employee.setPaymentSchedule( pm );

            PayrollDatabase.getInstance().addEmployee( empid, employee );
    }

    public abstract PaymentClassification getPaymentClassification();

    public abstract PaymentSchedule getPaymentSchedule();


}

package app.transactions;

import app.Employee;
import app.classifications.PaymentClassification;
import app.database.PayrollDatabase;
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

//            employee.setPaymentMethod( new HoldMethod() );
            PaymentClassification pc = employee.getPaymentClassification( );
            employee.setPaymentClassification( pc );
            PaymentSchedule pm = employee.getPaymentSchedule( );
            employee.setPaymentSchedule( pm );

            PayrollDatabase.getInstance().addEmployee( empid, employee );
    }

}

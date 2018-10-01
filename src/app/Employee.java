package app;

import app.affiliation.Affiliation;
import app.affiliation.NoAffiliation;
import app.classifications.PayCheck;
import app.classifications.PaymentClassification;
import app.methods.PaymentMethod;
import app.schedules.PaymentSchedule;

import java.util.Calendar;

public class Employee {

	private int empId;
	private String name;
	private String address;
	private PaymentClassification paymentClassification;
	private PaymentMethod paymentMethod;
	private PaymentSchedule paymentSchedule;
	private Affiliation affiliation = new NoAffiliation();

	public Affiliation getAffiliation( ) {
		return affiliation;
	}

	public Employee(int empId, String name, String address ) {
		this.empId = empId;
		this.name = name;
		this.address = address;
	}

    public void setName( String name )
    {
        this.name = name;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public void setPaymentClassification(PaymentClassification paymentClassification ) {
        this.paymentClassification = paymentClassification;
    }

    public void setAffiliation(Affiliation affiliation )
	{
		this.affiliation = affiliation;
	}

    public void setPaymentSchedule( PaymentSchedule paymentSchedule )
    {
        this.paymentSchedule = paymentSchedule;
    }

    public void setPaymentMethod( PaymentMethod paymentMethod )
    {
        this.paymentMethod = paymentMethod;
    }

    public boolean isPayDate( Calendar payDate )
    {
        return paymentSchedule.isPayDate( payDate );
    }

    public Calendar getPayPeriodStartDay(Calendar payDate )
    {
        return paymentSchedule.getPayPeriodStartDate( payDate );
    }

    public void payDay( PayCheck payCheck ) {
        double grossPay = paymentClassification.calculatePay( payCheck );
        double deductions = affiliation.calculateDeductions( payCheck );
        double netPay = grossPay - deductions;
        payCheck.setGrossPay( grossPay );
        payCheck.setDeductions( deductions );
        payCheck.setNetPay( netPay );

        paymentMethod.pay( payCheck );
    }


    public PaymentClassification getPaymentClassification( )
    {
        return paymentClassification;
    }

	public PaymentSchedule getPaymentSchedule( ) { return paymentSchedule; }

	public PaymentMethod getPaymentMethod( )
	{
		return paymentMethod;
	}

	public String getAddress( )
	{
		return address;
	}

	public String getName( ) { return name; }

}

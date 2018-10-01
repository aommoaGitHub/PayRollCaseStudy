package app;

import java.util.Calendar;

public class ServiceCharge
{

	private Calendar date;
	private double amount;

	public ServiceCharge(Calendar date, double amount )
	{
		this.date = date;
		this.amount = amount;
	}

	public Calendar getDate( )
	{
		return date;
	}

	public double getAmount( )
	{
		return amount;
	}
}

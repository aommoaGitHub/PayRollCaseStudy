package app.classifications;

public class SalariedClassification extends PaymentClassification
{

	private double salary;

	public SalariedClassification(double salary )
	{
		this.salary = salary;
	}

	public double getSalary()
	{
		return salary;
	}
	
	@Override
	public double calculatePay( PayCheck payCheck )
	{
		return salary;
	}
	
}

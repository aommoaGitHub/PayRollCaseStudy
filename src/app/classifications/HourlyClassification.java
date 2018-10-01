package app.classifications;

import app.TimeCard;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * represents payment schedule which is based on the number of working hours
 */
public class HourlyClassification extends PaymentClassification {

	private Map<Calendar, TimeCard> timeCardMap;
	private double hourlyRate;

	public HourlyClassification(double hourlyRate ) {
		this.hourlyRate = hourlyRate;
		timeCardMap = new HashMap<Calendar, TimeCard>( );
	}

    public double getHourlyRate() 
    {
        return hourlyRate;
    }
	
	public void addTimeCard( TimeCard timeCard )
	{
		timeCardMap.put( timeCard.getDate( ), timeCard );
	}

    public TimeCard getTimeCard(Calendar date)
    {
        return timeCardMap.get(date);
    }

	@Override
	public double calculatePay( PayCheck payCheck ) {
		double totalPay = 0;
		for ( TimeCard timeCard : timeCardMap.values( ) )
		{
			if ( isInPayPeriod( timeCard.getDate( ), payCheck ) )
			{
				totalPay += calculatePayForTimeCard( timeCard );
			}
		}
		return totalPay;
	}

	private double calculatePayForTimeCard( TimeCard timeCard ) {
		double hours = timeCard.getItsHours( );
		double overtime = Math.max( 0.0, hours - 8.0 );
		double straightTime = hours - overtime;
		return straightTime * hourlyRate + overtime * hourlyRate * 1.5;
	}

}

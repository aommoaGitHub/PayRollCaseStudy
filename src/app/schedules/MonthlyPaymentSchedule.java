package app.schedules;

import java.util.Calendar;

public class MonthlyPaymentSchedule implements PaymentSchedule {

	public boolean isPayDate( Calendar payDate ) {
        Calendar nextDay = getNextDay(payDate);
        boolean isLastDayOfMonth = nextDay.get(Calendar.MONTH) != payDate.get(Calendar.MONTH);
        return isLastDayOfMonth;
	}

	public Calendar getPayPeriodStartDate(Calendar payDate ) {
		Calendar firstOfMonth = (Calendar) payDate.clone( );
		firstOfMonth.set( Calendar.DAY_OF_MONTH, 1 );
		return firstOfMonth;
	}
	
    private Calendar getNextDay(Calendar date) {
        Calendar nextDay = (Calendar) date.clone();
        nextDay.add(Calendar.DAY_OF_MONTH, 1);
        return nextDay;
    }
}

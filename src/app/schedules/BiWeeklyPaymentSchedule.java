package app.schedules;

import java.util.Calendar;

public class BiWeeklyPaymentSchedule implements PaymentSchedule {
    private final static int LENGTH_OF_BIWEEK = 13;

    @Override
    public boolean isPayDate(Calendar payDate) {
        boolean evenCalendarWeek = payDate.get(Calendar.WEEK_OF_YEAR) % 2 == 0;
        return payDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && evenCalendarWeek;
    }

    @Override
    public Calendar getPayPeriodStartDate(Calendar payDate) {
        Calendar payPeriodStartDate = (Calendar) payDate.clone();
        payPeriodStartDate.add(Calendar.DAY_OF_MONTH, -LENGTH_OF_BIWEEK);
        return payPeriodStartDate;
    }

}

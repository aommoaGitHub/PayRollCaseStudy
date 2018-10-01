package app.affiliation;

import app.ServiceCharge;
import app.classifications.PayCheck;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static app.classifications.PaymentClassification.isInPayPeriod;

public class UnionAffiliation implements Affiliation {

	private double dues;
	private int memberId;

	private Map<Calendar, ServiceCharge> serviceChargeMap = new HashMap<Calendar, ServiceCharge>( );

	public UnionAffiliation(int memberId, double dues ) {
		this.memberId = memberId;
		this.dues = dues;
	}

	public void addServiceCharge(Calendar date, double amount ) {
		this.serviceChargeMap.put( date, new ServiceCharge( date, amount ) );
	}

    public ServiceCharge getServiceCharge(Calendar date) {
        return serviceChargeMap.get( date );
    }

	public double getDues( ) { return dues; }

	public int getMemberId( ) { return memberId; }

	@Override
	public double calculateDeductions(PayCheck payCheck ) {
		double totalDeductions = 0;
		totalDeductions += numberOfFridaysInPayPeriod(
				payCheck.getPayPeriodStart( ), payCheck.getPayPeriodEnd( ) )
				* dues;
		for ( ServiceCharge serviceCharge : serviceChargeMap.values( ) ) {
			if ( isInPayPeriod( serviceCharge.getDate( ), payCheck ) ) {
				totalDeductions += serviceCharge.getAmount( );
			}
		}
		return totalDeductions;
	}

	private int numberOfFridaysInPayPeriod( Calendar payPeriodStart,
			Calendar payPeriodEnd ) {
		int numberOfFridays = 0;
		while ( !payPeriodStart.after( payPeriodEnd ) ) {
			if ( payPeriodStart.get( Calendar.DAY_OF_WEEK ) == Calendar.FRIDAY ) {
				numberOfFridays++;
			}
			payPeriodStart.add( Calendar.DAY_OF_MONTH, 1 );
		}
		return numberOfFridays;
	}

}

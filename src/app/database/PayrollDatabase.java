
package app.database;

import app.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PayrollDatabase
{
    	private static PayrollDatabase payrollDatabaseInstance = new PayrollDatabase( );
	private Map<Integer, Employee> employeeMap = new HashMap<Integer, Employee>( );
	private Map<Integer, Employee> unionMembers = new HashMap<Integer, Employee>( );

	public static PayrollDatabase getInstance(){
		return payrollDatabaseInstance;
	}

    public Employee getEmployee( int employeeId )
    {
        return employeeMap.get( employeeId );
    }

	public void addEmployee( int employeeId, Employee employee )
	{
		employeeMap.put( employeeId, employee );
	}

	public void deleteEmployee( int employeeId ) { employeeMap.put( employeeId, null ); }

	public Employee getUnionMember( int memberId ) { return unionMembers.get( memberId ); }

	public void addUnionMember( int memberId, Employee employee ) { unionMembers.put( memberId, employee ); }

	public void removeUnionMember( int memberId ) { unionMembers.remove( memberId ); }

	public Set<Integer> getAllEmployeeIds( ) { return employeeMap.keySet( ); }

    public void clear() {
        employeeMap.clear();
        unionMembers.clear();
    }

}

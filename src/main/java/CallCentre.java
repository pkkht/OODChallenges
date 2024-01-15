
/*Imagine you have a call center with three levels of employees: respondent, manager,
and director. An incoming telephone call must be first allocated to a respondent who is free. If the
        respondent can't handle the call, he or she must escalate the call to a manager. If the manager is not
        free or not able to handle it, then the call should be escalated to a director. Design the classes and
        data structures for this problem. Implement a method dispatchCall() which assigns a call to
        the first available employee.*/


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CallCentre {
    static List<Employee> employeeAvailabilityStatusList = new ArrayList<>();


    CallCentre(){
        loademployeesAndStatus();
    }

    private void loademployeesAndStatus() {
        employeeAvailabilityStatusList.add(new Employee(1, EmployeeRole.RESPONDENT, false));
        employeeAvailabilityStatusList.add(new Employee(2, EmployeeRole.RESPONDENT, false));
        employeeAvailabilityStatusList.add(new Employee(3, EmployeeRole.RESPONDENT, false));
        employeeAvailabilityStatusList.add(new Employee(4, EmployeeRole.RESPONDENT, false));
        employeeAvailabilityStatusList.add(new Employee(5, EmployeeRole.RESPONDENT, true));
        employeeAvailabilityStatusList.add(new Employee(6, EmployeeRole.RESPONDENT, false));

        employeeAvailabilityStatusList.add(new Employee(7, EmployeeRole.MANAGER, true));
        employeeAvailabilityStatusList.add(new Employee(8, EmployeeRole.MANAGER, false));
        employeeAvailabilityStatusList.add(new Employee(9, EmployeeRole.MANAGER, false));
        employeeAvailabilityStatusList.add(new Employee(10, EmployeeRole.MANAGER, false));

        employeeAvailabilityStatusList.add(new Employee(11, EmployeeRole.DIRECTOR, false));
        employeeAvailabilityStatusList.add(new Employee(12, EmployeeRole.DIRECTOR, true));
    }

    public  List<Employee> getEmployeesByRole(EmployeeRole employeeRole, boolean availabilityStatus){
        List<Employee> filteredEmployeesByRole = employeeAvailabilityStatusList.stream().map(e-> e.getEmployeeBasedOnRole(e, employeeRole, availabilityStatus))
                                                    .collect(Collectors.toList());
        return filteredEmployeesByRole;
    }

    private List<Employee> findAvailableEmployees(){
        List<Employee> allAvailableEmployees = new ArrayList<>();

        List<Employee> availableRespondents = getEmployeesByRole(EmployeeRole.RESPONDENT, true);
        allAvailableEmployees.addAll(availableRespondents);

        boolean noEmployees = allAvailableEmployees.stream().noneMatch(Objects::nonNull);

        if (noEmployees)  {
            List<Employee> availableManagers = getEmployeesByRole(EmployeeRole.MANAGER, true);
            allAvailableEmployees.addAll(availableManagers);
        }

        noEmployees = allAvailableEmployees.stream().noneMatch(Objects::nonNull);
        if (noEmployees ) {
            List<Employee> availableDirectors = getEmployeesByRole(EmployeeRole.DIRECTOR, true);
            allAvailableEmployees.addAll(availableDirectors);
        }

        return allAvailableEmployees;

    }

    Call dispatchCall(){
        List<Employee> allAvailableEmployees = findAvailableEmployees();
        Employee e = allAvailableEmployees
                .stream()
                .filter(o -> o != null)
                .findFirst().get();

        int index =
                IntStream.range(0, employeeAvailabilityStatusList.size())
                        .filter(i -> employeeAvailabilityStatusList.get(i) != null)
                        .findFirst()
                        .orElse(-1 /* Or some other default */);

        Call call = new Call(e);
        System.out.println("Call is assigned to: " + call.getEmployee());
        e.setAvailabilityStatus(false);
        employeeAvailabilityStatusList.set(index, e );
        return call;
    }

    public static void main(String[] args){
        CallCentre callCentre = new CallCentre();
        System.out.println("Initial call log is \n:" );
        employeeAvailabilityStatusList.forEach(System.out::println);

        //Place 2 calls
        for (int i=0; i < 2; i++) {
            Call call = callCentre.dispatchCall();
        }

    }
}

enum EmployeeRole {
    RESPONDENT,
    MANAGER,
    DIRECTOR;

}

class Employee {
    private int employeeId;
    private EmployeeRole employeeRole;

    public Employee(int id, EmployeeRole employeeRole, boolean availabilityStatus) {
        this.employeeId = id;
        this.employeeRole = employeeRole;
        this.availabilityStatus = availabilityStatus;
    }

    private boolean availabilityStatus;

    public Employee getEmployeeBasedOnRole(Employee e, EmployeeRole employeeRole, boolean availabilityStatus) {
        if (e.employeeRole == employeeRole && e.availabilityStatus == availabilityStatus) {
            return e;
        }
        return null;
    }

    public void setAvailabilityStatus(boolean availabilityStatus){
        this.availabilityStatus = availabilityStatus;
    }

    public String toString() {

        return "\nEmployee id is: " + employeeId +
                "\nEmployee role is: " + employeeRole.name() +
                "\nEmployee availability status is: " + availabilityStatus;
    }
}

class Call{
      private Employee employee;

      public Call(Employee employee){
          this.employee = employee;
      }

      public Employee getEmployee(){
          return this.employee;
      }

}

/*Imagine you have a call center with three levels of employees: respondent, manager,
and director. An incoming telephone call must be first allocated to a respondent who is free. If the
        respondent can't handle the call, he or she must escalate the call to a manager. If the manager is not
        free or not able to handle it, then the call should be escalated to a director. Design the classes and
        data structures for this problem. Implement a method dispatchCall() which assigns a call to
        the first available employee.*/


import java.util.HashMap;
import java.util.Map;

public class CallCentre {
    Map<PersonRole, Boolean> personAvailabilityStatus = new HashMap<>();


    CallCentre(){
        loadPersonsAndStatus();
    }

    private void loadPersonsAndStatus() {
        personAvailabilityStatus.put(PersonRole.RESPONDENT, true);
        personAvailabilityStatus.put(PersonRole.MANAGER, true);
        personAvailabilityStatus.put(PersonRole.DIRECTOR, true);
    }

    Call dispatchCall(){
        Call call = new Call();

        if (!personAvailabilityStatus.get(PersonRole.RESPONDENT)) {
            if (personAvailabilityStatus.get(PersonRole.MANAGER)) {
                call = new Call(PersonRole.MANAGER);
            } else {
                if (personAvailabilityStatus.get(PersonRole.DIRECTOR)) {
                    call = new Call(PersonRole.DIRECTOR);
                }
            }
        }
        return call;
    }

    public static void main(String[] args){
        CallCentre callCentre = new CallCentre();
        Call call = callCentre.dispatchCall();
        System.out.println("Call is assigned to: " + call.getPersonRole().name());
    }
}

enum PersonRole {
    RESPONDENT,
    MANAGER,
    DIRECTOR;

    PersonRole getPersonRole(String name){
        return PersonRole.valueOf(name);
    }

    int getOrdinalEscalationOrder(PersonRole personRole){
        return personRole.ordinal();
    }
}

class Call{

      private PersonRole personRole;

      //Default constructor - as the call must be assigned to a respondent first
      public Call(){
          this.personRole = PersonRole.RESPONDENT;
      }

      public Call(PersonRole personRole){
          this.personRole = personRole;
      }

      public PersonRole getPersonRole(){
          return this.personRole;
      }
}
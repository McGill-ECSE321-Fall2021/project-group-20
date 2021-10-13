/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;
import java.sql.Time;

// line 64 "../../../../../librarysystem.ump"
public class Employee extends User
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Role { Librarian, HeadLibrarian }

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<Integer, Employee> employeesByEmployeeID = new HashMap<Integer, Employee>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Employee Attributes
  private Role role;
  private int employeeID;

  //Employee Associations
  private List<Hour> employeehour;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Employee(boolean aIsOnlineAcc, String aFirstName, String aLastName, int aLibraryCardID, boolean aIsVerified, int aDemeritPts, Address aAddress, LibrarySystem aLibrarySystem, Role aRole, int aEmployeeID)
  {
    super(aIsOnlineAcc, aFirstName, aLastName, aLibraryCardID, aIsVerified, aDemeritPts, aAddress, aLibrarySystem);
    role = aRole;
    if (!setEmployeeID(aEmployeeID))
    {
      throw new RuntimeException("Cannot create due to duplicate employeeID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    employeehour = new ArrayList<Hour>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setRole(Role aRole)
  {
    boolean wasSet = false;
    role = aRole;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmployeeID(int aEmployeeID)
  {
    boolean wasSet = false;
    Integer anOldEmployeeID = getEmployeeID();
    if (anOldEmployeeID != null && anOldEmployeeID.equals(aEmployeeID)) {
      return true;
    }
    if (hasWithEmployeeID(aEmployeeID)) {
      return wasSet;
    }
    employeeID = aEmployeeID;
    wasSet = true;
    if (anOldEmployeeID != null) {
      employeesByEmployeeID.remove(anOldEmployeeID);
    }
    employeesByEmployeeID.put(aEmployeeID, this);
    return wasSet;
  }

  public Role getRole()
  {
    return role;
  }

  public int getEmployeeID()
  {
    return employeeID;
  }
  /* Code from template attribute_GetUnique */
  public static Employee getWithEmployeeID(int aEmployeeID)
  {
    return employeesByEmployeeID.get(aEmployeeID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithEmployeeID(int aEmployeeID)
  {
    return getWithEmployeeID(aEmployeeID) != null;
  }
  /* Code from template association_GetMany */
  public Hour getEmployeehour(int index)
  {
    Hour aEmployeehour = employeehour.get(index);
    return aEmployeehour;
  }

  public List<Hour> getEmployeehour()
  {
    List<Hour> newEmployeehour = Collections.unmodifiableList(employeehour);
    return newEmployeehour;
  }

  public int numberOfEmployeehour()
  {
    int number = employeehour.size();
    return number;
  }

  public boolean hasEmployeehour()
  {
    boolean has = employeehour.size() > 0;
    return has;
  }

  public int indexOfEmployeehour(Hour aEmployeehour)
  {
    int index = employeehour.indexOf(aEmployeehour);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEmployeehour()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Hour addEmployeehour(String aWeekday, Time aStart, Time aEnd, Event aEvent, Calendar aCalendar)
  {
    return new Hour(aWeekday, aStart, aEnd, this, aEvent, aCalendar);
  }

  public boolean addEmployeehour(Hour aEmployeehour)
  {
    boolean wasAdded = false;
    if (employeehour.contains(aEmployeehour)) { return false; }
    Employee existingEmployee = aEmployeehour.getEmployee();
    boolean isNewEmployee = existingEmployee != null && !this.equals(existingEmployee);
    if (isNewEmployee)
    {
      aEmployeehour.setEmployee(this);
    }
    else
    {
      employeehour.add(aEmployeehour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEmployeehour(Hour aEmployeehour)
  {
    boolean wasRemoved = false;
    //Unable to remove aEmployeehour, as it must always have a employee
    if (!this.equals(aEmployeehour.getEmployee()))
    {
      employeehour.remove(aEmployeehour);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEmployeehourAt(Hour aEmployeehour, int index)
  {  
    boolean wasAdded = false;
    if(addEmployeehour(aEmployeehour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployeehour()) { index = numberOfEmployeehour() - 1; }
      employeehour.remove(aEmployeehour);
      employeehour.add(index, aEmployeehour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEmployeehourAt(Hour aEmployeehour, int index)
  {
    boolean wasAdded = false;
    if(employeehour.contains(aEmployeehour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEmployeehour()) { index = numberOfEmployeehour() - 1; }
      employeehour.remove(aEmployeehour);
      employeehour.add(index, aEmployeehour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEmployeehourAt(aEmployeehour, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    employeesByEmployeeID.remove(getEmployeeID());
    for(int i=employeehour.size(); i > 0; i--)
    {
      Hour aEmployeehour = employeehour.get(i - 1);
      aEmployeehour.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "employeeID" + ":" + getEmployeeID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "role" + "=" + (getRole() != null ? !getRole().equals(this)  ? getRole().toString().replaceAll("  ","    ") : "this" : "null");
  }
}
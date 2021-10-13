/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;
import java.sql.Time;
import java.sql.Date;

// line 91 "../../../../../librarysystem.ump"
public class Hour
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Hour> hoursByWeekday = new HashMap<String, Hour>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Hour Attributes
  private String weekday;
  private Time start;
  private Time end;

  //Hour Associations
  private Employee employee;
  private Event event;
  private Calendar calendar;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Hour(String aWeekday, Time aStart, Time aEnd, Employee aEmployee, Event aEvent, Calendar aCalendar)
  {
    start = aStart;
    end = aEnd;
    if (!setWeekday(aWeekday))
    {
      throw new RuntimeException("Cannot create due to duplicate weekday. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddEmployee = setEmployee(aEmployee);
    if (!didAddEmployee)
    {
      throw new RuntimeException("Unable to create employeehour due to employee. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aEvent == null || aEvent.getEventhour() != null)
    {
      throw new RuntimeException("Unable to create Hour due to aEvent. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    event = aEvent;
    boolean didAddCalendar = setCalendar(aCalendar);
    if (!didAddCalendar)
    {
      throw new RuntimeException("Unable to create hour due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Hour(String aWeekday, Time aStart, Time aEnd, Employee aEmployee, String aEventIDForEvent, String aNameForEvent, Date aEventDateForEvent, Calendar aCalendar)
  {
    if (!setWeekday(aWeekday))
    {
      throw new RuntimeException("Cannot create due to duplicate weekday. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    start = aStart;
    end = aEnd;
    boolean didAddEmployee = setEmployee(aEmployee);
    if (!didAddEmployee)
    {
      throw new RuntimeException("Unable to create employeehour due to employee. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    event = new Event(aEventIDForEvent, aNameForEvent, aEventDateForEvent, this);
    boolean didAddCalendar = setCalendar(aCalendar);
    if (!didAddCalendar)
    {
      throw new RuntimeException("Unable to create hour due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWeekday(String aWeekday)
  {
    boolean wasSet = false;
    String anOldWeekday = getWeekday();
    if (anOldWeekday != null && anOldWeekday.equals(aWeekday)) {
      return true;
    }
    if (hasWithWeekday(aWeekday)) {
      return wasSet;
    }
    weekday = aWeekday;
    wasSet = true;
    if (anOldWeekday != null) {
      hoursByWeekday.remove(anOldWeekday);
    }
    hoursByWeekday.put(aWeekday, this);
    return wasSet;
  }

  public boolean setStart(Time aStart)
  {
    boolean wasSet = false;
    start = aStart;
    wasSet = true;
    return wasSet;
  }

  public boolean setEnd(Time aEnd)
  {
    boolean wasSet = false;
    end = aEnd;
    wasSet = true;
    return wasSet;
  }

  public String getWeekday()
  {
    return weekday;
  }
  /* Code from template attribute_GetUnique */
  public static Hour getWithWeekday(String aWeekday)
  {
    return hoursByWeekday.get(aWeekday);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithWeekday(String aWeekday)
  {
    return getWithWeekday(aWeekday) != null;
  }

  public Time getStart()
  {
    return start;
  }

  public Time getEnd()
  {
    return end;
  }
  /* Code from template association_GetOne */
  public Employee getEmployee()
  {
    return employee;
  }
  /* Code from template association_GetOne */
  public Event getEvent()
  {
    return event;
  }
  /* Code from template association_GetOne */
  public Calendar getCalendar()
  {
    return calendar;
  }
  /* Code from template association_SetOneToMany */
  public boolean setEmployee(Employee aEmployee)
  {
    boolean wasSet = false;
    if (aEmployee == null)
    {
      return wasSet;
    }

    Employee existingEmployee = employee;
    employee = aEmployee;
    if (existingEmployee != null && !existingEmployee.equals(aEmployee))
    {
      existingEmployee.removeEmployeehour(this);
    }
    employee.addEmployeehour(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setCalendar(Calendar aCalendar)
  {
    boolean wasSet = false;
    //Must provide calendar to hour
    if (aCalendar == null)
    {
      return wasSet;
    }

    if (calendar != null && calendar.numberOfHour() <= Calendar.minimumNumberOfHour())
    {
      return wasSet;
    }

    Calendar existingCalendar = calendar;
    calendar = aCalendar;
    if (existingCalendar != null && !existingCalendar.equals(aCalendar))
    {
      boolean didRemove = existingCalendar.removeHour(this);
      if (!didRemove)
      {
        calendar = existingCalendar;
        return wasSet;
      }
    }
    calendar.addHour(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    hoursByWeekday.remove(getWeekday());
    Employee placeholderEmployee = employee;
    this.employee = null;
    if(placeholderEmployee != null)
    {
      placeholderEmployee.removeEmployeehour(this);
    }
    Event existingEvent = event;
    event = null;
    if (existingEvent != null)
    {
      existingEvent.delete();
    }
    Calendar placeholderCalendar = calendar;
    this.calendar = null;
    if(placeholderCalendar != null)
    {
      placeholderCalendar.removeHour(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "weekday" + ":" + getWeekday()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "start" + "=" + (getStart() != null ? !getStart().equals(this)  ? getStart().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "end" + "=" + (getEnd() != null ? !getEnd().equals(this)  ? getEnd().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "event = "+(getEvent()!=null?Integer.toHexString(System.identityHashCode(getEvent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}
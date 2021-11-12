/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

// line 85 "../../../../../librarysystem.ump"
@Entity
public class Hour
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Hour Attributes
	@Id
  private String weekday;
  private Time startTime;
  private Time endTime;

  //Hour Associations
  @ManyToOne(optional=true)
  private Employee employee;
  @OneToOne(optional=true)
  @OnDelete(action=OnDeleteAction.CASCADE)
  private Event event;
  @ManyToOne(optional=false)
  private Calendar calendar;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  
  public Hour() {
	  
  }
  
  public Hour(String aWeekday, Time aStartTime, Time aEndTime, Employee aEmployee, Calendar aCalendar)
  {
    weekday = aWeekday;
    startTime = aStartTime;
    endTime = aEndTime;
    boolean didAddEmployee = setEmployee(aEmployee);
    if (!didAddEmployee)
    {
      throw new RuntimeException("Unable to create employeehour due to employee. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
    weekday = aWeekday;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public String getWeekday()
  {
    return weekday;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
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

  public boolean hasEvent()
  {
    boolean has = event != null;
    return has;
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
  /* Code from template association_SetOptionalOneToOne */
  public boolean setEvent(Event aNewEvent)
  {
    boolean wasSet = false;
    if (event != null && !event.equals(aNewEvent) && equals(event.getEventhour()))
    {
      //Unable to setEvent, as existing event would become an orphan
      return wasSet;
    }

    event = aNewEvent;
    Hour anOldEventhour = aNewEvent != null ? aNewEvent.getEventhour() : null;

    if (!this.equals(anOldEventhour))
    {
      if (anOldEventhour != null)
      {
        anOldEventhour.event = null;
      }
      if (event != null)
      {
        event.setEventhour(this);
      }
    }
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
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "employee = "+(getEmployee()!=null?Integer.toHexString(System.identityHashCode(getEmployee())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "event = "+(getEvent()!=null?Integer.toHexString(System.identityHashCode(getEvent())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "calendar = "+(getCalendar()!=null?Integer.toHexString(System.identityHashCode(getCalendar())):"null");
  }
}
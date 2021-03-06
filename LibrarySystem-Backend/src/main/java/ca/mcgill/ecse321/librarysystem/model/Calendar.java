/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// line 110 "../../../../../librarysystem.ump"
@Entity
public class Calendar
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Calendar Attributes
  @Id
  @GeneratedValue(generator="system-uuid")
  @GenericGenerator(name="system-uuid", strategy = "uuid")
  private String calendarID;

  //Calendar Associations
  @OneToMany(mappedBy="calendar")
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<Hour> hour;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Calendar(){
	  hour = new ArrayList<Hour>();
  }
	
	
  public Calendar(String aCalendarID)
  {
    calendarID = aCalendarID;
    hour = new ArrayList<Hour>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCalendarID(String aCalendarID)
  {
    boolean wasSet = false;
    calendarID = aCalendarID;
    wasSet = true;
    return wasSet;
  }

  public String getCalendarID()
  {
    return calendarID;
  }
  /* Code from template association_GetMany */
  public Hour getHour(int index)
  {
    Hour aHour = hour.get(index);
    return aHour;
  }

  public List<Hour> getHour()
  {
    List<Hour> newHour = Collections.unmodifiableList(hour);
    return newHour;
  }

  public int numberOfHour()
  {
    int number = hour.size();
    return number;
  }

  public boolean hasHour()
  {
    boolean has = hour.size() > 0;
    return has;
  }

  public int indexOfHour(Hour aHour)
  {
    int index = hour.indexOf(aHour);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfHourValid()
  {
    boolean isValid = numberOfHour() >= minimumNumberOfHour();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHour()
  {
    return 1;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public Hour addHour(String aWeekday, Time aStartTime, Time aEndTime, Employee aEmployee)
  {
    Hour aNewHour = new Hour(aWeekday, aStartTime, aEndTime, aEmployee, this, Hour.Type.System);
    return aNewHour;
  }

  public boolean addHour(Hour aHour)
  {
    boolean wasAdded = false;
    if (hour.contains(aHour)) { return false; }
    Calendar existingCalendar = aHour.getCalendar();
    boolean isNewCalendar = existingCalendar != null && !this.equals(existingCalendar);

    if (isNewCalendar && existingCalendar.numberOfHour() <= minimumNumberOfHour())
    {
      return wasAdded;
    }
    if (isNewCalendar)
    {
      aHour.setCalendar(this);
    }
    else
    {
      hour.add(aHour);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHour(Hour aHour)
  {
    boolean wasRemoved = false;
    //Unable to remove aHour, as it must always have a calendar
    if (this.equals(aHour.getCalendar()))
    {
      return wasRemoved;
    }

    //calendar already at minimum (1)
    if (numberOfHour() <= minimumNumberOfHour())
    {
      return wasRemoved;
    }

    hour.remove(aHour);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHourAt(Hour aHour, int index)
  {  
    boolean wasAdded = false;
    if(addHour(aHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHour()) { index = numberOfHour() - 1; }
      hour.remove(aHour);
      hour.add(index, aHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHourAt(Hour aHour, int index)
  {
    boolean wasAdded = false;
    if(hour.contains(aHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHour()) { index = numberOfHour() - 1; }
      hour.remove(aHour);
      hour.add(index, aHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHourAt(aHour, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=hour.size(); i > 0; i--)
    {
      Hour aHour = hour.get(i - 1);
      aHour.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "calendarID" + ":" + getCalendarID()+ "]" + System.getProperties().getProperty("line.separator")
            ;
  }
}
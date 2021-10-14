/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.sql.Time;

// line 116 "../../../../../librarysystem.ump"
@Entity
public class Calendar
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Calendar> calendarsByCalendarID = new HashMap<String, Calendar>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Calendar Attributes
  @Id
  @GeneratedValue
  private String calendarID;

  //Calendar Associations
  @OneToMany(mappedBy="calendar")
  private List<Hour> hour;
  @OneToOne(mappedBy="calendar")
  private LibrarySystem librarySystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Calendar(String aCalendarID, LibrarySystem aLibrarySystem)
  {
    if (!setCalendarID(aCalendarID))
    {
      throw new RuntimeException("Cannot create due to duplicate calendarID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    hour = new ArrayList<Hour>();
    if (aLibrarySystem == null || aLibrarySystem.getCalendar() != null)
    {
      throw new RuntimeException("Unable to create Calendar due to aLibrarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    librarySystem = aLibrarySystem;
  }

  public Calendar(String aCalendarID, String aSystemIDForLibrarySystem, Address aBuisnessaddressForLibrarySystem)
  {
    if (!setCalendarID(aCalendarID))
    {
      throw new RuntimeException("Cannot create due to duplicate calendarID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    hour = new ArrayList<Hour>();
    librarySystem = new LibrarySystem(aSystemIDForLibrarySystem, aBuisnessaddressForLibrarySystem, this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCalendarID(String aCalendarID)
  {
    boolean wasSet = false;
    String anOldCalendarID = getCalendarID();
    if (anOldCalendarID != null && anOldCalendarID.equals(aCalendarID)) {
      return true;
    }
    if (hasWithCalendarID(aCalendarID)) {
      return wasSet;
    }
    calendarID = aCalendarID;
    wasSet = true;
    if (anOldCalendarID != null) {
      calendarsByCalendarID.remove(anOldCalendarID);
    }
    calendarsByCalendarID.put(aCalendarID, this);
    return wasSet;
  }

  public String getCalendarID()
  {
    return calendarID;
  }
  /* Code from template attribute_GetUnique */
  public static Calendar getWithCalendarID(String aCalendarID)
  {
    return calendarsByCalendarID.get(aCalendarID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithCalendarID(String aCalendarID)
  {
    return getWithCalendarID(aCalendarID) != null;
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
  /* Code from template association_GetOne */
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
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
  public Hour addHour(String aWeekday, Time aStart, Time aEnd, Employee aEmployee, Event aEvent)
  {
    Hour aNewHour = new Hour(aWeekday, aStart, aEnd, aEmployee, aEvent, this);
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
    calendarsByCalendarID.remove(getCalendarID());
    for(int i=hour.size(); i > 0; i--)
    {
      Hour aHour = hour.get(i - 1);
      aHour.delete();
    }
    LibrarySystem existingLibrarySystem = librarySystem;
    librarySystem = null;
    if (existingLibrarySystem != null)
    {
      existingLibrarySystem.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "calendarID" + ":" + getCalendarID()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null");
  }
}
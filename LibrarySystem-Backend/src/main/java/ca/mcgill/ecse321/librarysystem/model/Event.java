/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import java.sql.Date;
import java.sql.Time;

// line 108 "../../../../../librarysystem.ump"
@Entity
public class Event
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Event> eventsByEventID = new HashMap<String, Event>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Event Attributes
  @Id
  @GeneratedValue
  private String eventID;
  private String name;
  private Date eventDate;

  //Event Associations
  @OneToOne
  private Hour eventhour;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Event(String aEventID, String aName, Date aEventDate, Hour aEventhour)
  {
    name = aName;
    eventDate = aEventDate;
    if (!setEventID(aEventID))
    {
      throw new RuntimeException("Cannot create due to duplicate eventID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (aEventhour == null || aEventhour.getEvent() != null)
    {
      throw new RuntimeException("Unable to create Event due to aEventhour. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    eventhour = aEventhour;
  }

  public Event(String aEventID, String aName, Date aEventDate, String aWeekdayForEventhour, Time aStartForEventhour, Time aEndForEventhour, Employee aEmployeeForEventhour, Calendar aCalendarForEventhour)
  {
    if (!setEventID(aEventID))
    {
      throw new RuntimeException("Cannot create due to duplicate eventID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    name = aName;
    eventDate = aEventDate;
    eventhour = new Hour(aWeekdayForEventhour, aStartForEventhour, aEndForEventhour, aEmployeeForEventhour, this, aCalendarForEventhour);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEventID(String aEventID)
  {
    boolean wasSet = false;
    String anOldEventID = getEventID();
    if (anOldEventID != null && anOldEventID.equals(aEventID)) {
      return true;
    }
    if (hasWithEventID(aEventID)) {
      return wasSet;
    }
    eventID = aEventID;
    wasSet = true;
    if (anOldEventID != null) {
      eventsByEventID.remove(anOldEventID);
    }
    eventsByEventID.put(aEventID, this);
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEventDate(Date aEventDate)
  {
    boolean wasSet = false;
    eventDate = aEventDate;
    wasSet = true;
    return wasSet;
  }

  public String getEventID()
  {
    return eventID;
  }
  /* Code from template attribute_GetUnique */
  public static Event getWithEventID(String aEventID)
  {
    return eventsByEventID.get(aEventID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithEventID(String aEventID)
  {
    return getWithEventID(aEventID) != null;
  }

  public String getName()
  {
    return name;
  }

  public Date getEventDate()
  {
    return eventDate;
  }
  /* Code from template association_GetOne */
  public Hour getEventhour()
  {
    return eventhour;
  }

  public void delete()
  {
    eventsByEventID.remove(getEventID());
    Hour existingEventhour = eventhour;
    eventhour = null;
    if (existingEventhour != null)
    {
      existingEventhour.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "eventID" + ":" + getEventID()+ "," +
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "eventDate" + "=" + (getEventDate() != null ? !getEventDate().equals(this)  ? getEventDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "eventhour = "+(getEventhour()!=null?Integer.toHexString(System.identityHashCode(getEventhour())):"null");
  }
}
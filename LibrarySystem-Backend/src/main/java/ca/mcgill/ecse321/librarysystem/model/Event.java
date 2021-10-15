/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

// line 102 "../../../../../librarysystem.ump"
@Entity
public class Event
{

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
  @OneToOne(optional=true)
  private Hour eventhour;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Event(String aEventID, String aName, Date aEventDate, Hour aEventhour)
  {
    eventID = aEventID;
    name = aName;
    eventDate = aEventDate;
    boolean didAddEventhour = setEventhour(aEventhour);
    if (!didAddEventhour)
    {
      throw new RuntimeException("Unable to create event due to eventhour. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEventID(String aEventID)
  {
    boolean wasSet = false;
    eventID = aEventID;
    wasSet = true;
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
  /* Code from template association_SetOneToOptionalOne */
  public boolean setEventhour(Hour aNewEventhour)
  {
    boolean wasSet = false;
    if (aNewEventhour == null)
    {
      //Unable to setEventhour to null, as event must always be associated to a eventhour
      return wasSet;
    }
    
    Event existingEvent = aNewEventhour.getEvent();
    if (existingEvent != null && !equals(existingEvent))
    {
      //Unable to setEventhour, the current eventhour already has a event, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Hour anOldEventhour = eventhour;
    eventhour = aNewEventhour;
    eventhour.setEvent(this);

    if (anOldEventhour != null)
    {
      anOldEventhour.setEvent(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Hour existingEventhour = eventhour;
    eventhour = null;
    if (existingEventhour != null)
    {
      existingEventhour.setEvent(null);
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
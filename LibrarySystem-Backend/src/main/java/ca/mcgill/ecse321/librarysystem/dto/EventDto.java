package ca.mcgill.ecse321.librarysystem.dto;
import java.sql.Date;


public class EventDto {

	  private String eventID;
	  private String name;
	  private Date eventDate;
	  private HourDto eventhour;

	  public EventDto () {}
	  public EventDto(String aName, Date aEventDate, HourDto aEventhour)
	  {
	    name = aName;
	    eventDate = aEventDate;
	    boolean didAddEventhour = setEventhour(aEventhour);
	    if (!didAddEventhour)
	    {
	      throw new RuntimeException("Unable to create event due to eventhour. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
	    }
	  }
	    public EventDto(String aEventID, String aName, Date aEventDate, HourDto aEventhour)
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
	    public HourDto getEventhour()
	    {
	      return eventhour;
	    }
	    
	    public boolean setEventhour(HourDto aNewEventhour)
	    {
	      boolean wasSet = false;
	      if (aNewEventhour == null)
	      {
	        return wasSet;
	      }
	      
	      EventDto existingEvent = aNewEventhour.getEvent();
	      if (existingEvent != null && !equals(existingEvent))
	      {
	        return wasSet;
	      }
	      
	      HourDto anOldEventhour = eventhour;
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
	      HourDto existingEventhour = eventhour;
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

package ca.mcgill.ecse321.librarysystem.dto;
import ca.mcgill.ecse321.librarysystem.model.Event;

import java.sql.Time;

public class HourDto {
	private int hourId;
	 private String weekday;
	  private Time startTime;
	  private Time endTime;
	  private EmployeeDto employee;
	  private String event;
	  private CalendarDto calendar;
	  private Type type;

	  public enum Type {System, Event, Shift}



	public HourDto() {
		  
	  }
	  
	  public HourDto(int id, Event aEvent, String aWeekday, Time aStartTime, Time aEndTime, EmployeeDto aEmployee, CalendarDto aCalendar, Type aType)
	  {
	    weekday = aWeekday;
	    startTime = aStartTime;
	    endTime = aEndTime;
	    employee = aEmployee;
		calendar = aCalendar;
		type = aType;
		hourId = id;
		if (aEvent != null) event = aEvent.getEventID();
	  }

	

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
	  public EmployeeDto getEmployee()
	  {
	    return employee;
	  }
	  public String getEvent()
	  {
	    return event;
	  }

	  public int getHourId() {
		  return hourId;
	  }

	  public Type getType() {
		  return type;
	  }

	  public boolean hasEvent()
	  {
	    boolean has = event != null;
	    return has;
	  }
	  public CalendarDto getCalendar()
	  {
	    return calendar;
	  }
	  public boolean setEmployee(EmployeeDto aEmployee)
	  {
	    boolean wasSet = false;
	    if (aEmployee == null)
	    {
	      return wasSet;
	    }

	    EmployeeDto existingEmployee = employee;
	    employee = aEmployee;
	    if (existingEmployee != null && !existingEmployee.equals(aEmployee))
	    {
	      existingEmployee.removeEmployeehour(this);
	    }
	    employee.addEmployeehour(this);
	    wasSet = true;
	    return wasSet;
	  }
	  public boolean setCalendar(CalendarDto aCalendar)
	  {
	    boolean wasSet = false;
	    if (aCalendar == null)
	    {
	      return wasSet;
	    }

	    if (calendar != null && calendar.numberOfHour() <= calendar.minimumNumberOfHour())
	    {
	      return wasSet;
	    }

	    CalendarDto existingCalendar = calendar;
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
	    wasSet = true;
	    return wasSet;
	  }

	  public void delete()
	  {
	    EmployeeDto placeholderEmployee = employee;
	    this.employee = null;
	    if(placeholderEmployee != null)
	    {
	      placeholderEmployee.removeEmployeehour(this);
	    }
	    CalendarDto placeholderCalendar = calendar;
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

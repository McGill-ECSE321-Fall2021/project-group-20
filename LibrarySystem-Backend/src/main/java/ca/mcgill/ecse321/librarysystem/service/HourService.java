package ca.mcgill.ecse321.librarysystem.service;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.HourRepository;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.model.Event;
import ca.mcgill.ecse321.librarysystem.model.Hour;



@Service
public class HourService {
	
	@Autowired
	private HourRepository hourRepository;
	

	
	@Transactional
	public Hour createHour(String aWeekday, Time aStartTime, Time aEndTime, Employee aEmployee, Calendar aCalendar, String aType) {
		 if ( aEndTime == null ) throw new IllegalArgumentException("Please enter a valid end Time");
		  if(aStartTime == null)throw new IllegalArgumentException("Please enter a valid start Time");
		  if (aWeekday == null || aWeekday.length()==0) throw new IllegalArgumentException("Please enter a valid date");
	      if( aEmployee == null )throw new IllegalArgumentException("Please enter a valid employee");
	      if (aCalendar == null) throw new IllegalArgumentException("Please enter valid calendar");
	      Hour newHour = new Hour ( aWeekday,  aStartTime,  aEndTime,  aEmployee,  aCalendar, Hour.Type.valueOf(aType));
	      hourRepository.save(newHour);
	      return newHour;		
	}

	@Transactional
	public List<Hour> getAllHours() {
		List<Hour> hours = new ArrayList<>();
		for (Hour h : hourRepository.findAll()) {
			hours.add(h);
		}
		return hours;
	}

	@Transactional
	public Hour getHour(int id) {
		return hourRepository.findByHourId(id);
	}
	
	
	@Transactional
	 public List<Hour> getHourlistBycalendar (Calendar acalendar){
		  if (acalendar == null ) throw new IllegalArgumentException("Please enter a valid calendar");
		  List<Hour> hours = hourRepository.findBycalendar(acalendar);
	      if (hours.size() == 0) throw new NullPointerException("Hours not found");
	      return hours;
		  
	  }
	
	@Transactional
	public List<Hour> getHourlistByEmployee (Employee aEmployee){
		if (aEmployee == null) throw new IllegalArgumentException("Please enter a valid employee");
		List<Hour> hours = hourRepository.findByemployee(aEmployee);
		if (hours.size() == 0) throw new NullPointerException("Hours not found");
		return hours;
	}
	
	@Transactional
	public List<Hour> getHourListbyendTime (Time aEndTime){
		if (aEndTime == null) throw new IllegalArgumentException("Please enter a valid end Time");
		List<Hour> hours = hourRepository.findByendTime(aEndTime);
		if (hours.size()== 0 ) throw new NullPointerException("Hours not found");
		return hours;
	}
	
	@Transactional 
	public Hour getHourbyEvent (Event aEvent) {
		if (aEvent == null) throw new IllegalArgumentException("Please enter a valid Event");
		return (Hour) hourRepository.getByevent(aEvent);
	}

	@Transactional
	public List<Hour> getHourListbystartTime (Time aStartTime){
		if (aStartTime == null) throw new IllegalArgumentException("Please enter a valid start Time");
		List<Hour> hours = hourRepository.findBystartTime(aStartTime);
		if (hours.size()== 0 ) throw new NullPointerException("Hours not found");
		return hours;
	}
	
	@Transactional
	public Hour getHourbyWeekday (String aWeekday) {
		if (!aWeekday.equals("Monday") && !aWeekday.equals("monday") && !aWeekday.equals("Tuesday") && !aWeekday.equals("tuesday") && !aWeekday.equals("Wednesday") && !aWeekday.equals("wednesday") && !aWeekday.equals("Thursday") && !aWeekday.equals("thursday")  && !aWeekday.equals("Friday") && !aWeekday.equals("friday") ) throw new IllegalArgumentException("Please enter a valid Weekday, with either a Capital first letter or all lowercase");
		return (Hour) hourRepository.findByweekday(aWeekday);
	}
	

	@Transactional
	public boolean deleteHourbyEmployee(Employee aEmployee) {
		if (aEmployee == null) throw new IllegalArgumentException("Please enter a valid Employee");
		List<Hour> hours = hourRepository.findByemployee(aEmployee);
		if (hours.size() == 0) throw new NullPointerException("Hours not found");
        for (Hour hour : hours) {
            
            hourRepository.delete(hour);
            hour.delete();
        }
        List<Hour> myHour = hourRepository.findByemployee(aEmployee);
        return (myHour.size() == 0);
	}
	

	@Transactional
	public void deleteHourbyEvent(Event aEvent) {
		if (aEvent == null ) throw new IllegalArgumentException("Please enter a valid event");
		Hour myHour = hourRepository.getByevent(aEvent);
		hourRepository.delete(myHour);
		myHour.delete();
		return;
	}
	

	
	@Transactional
	public void deleteHourbyWeekday (String aWeekday) {
	if	(!aWeekday.equals("Monday") && !aWeekday.equals("monday") && !aWeekday.equals("Tuesday") && !aWeekday.equals("tuesday") && !aWeekday.equals("Wednesday") && !aWeekday.equals("wednesday") && !aWeekday.equals("Thursday") && !aWeekday.equals("thursday")  && !aWeekday.equals("Friday") && !aWeekday.equals("friday") )  throw new IllegalArgumentException("Please enter a valid Weekday, with either a Capital first letter or all lowercase");
	Hour myHour = hourRepository.findByweekday(aWeekday);
	hourRepository.delete(myHour);
	myHour.delete();
	return;
	}
	
	@Transactional
	public boolean updateHourStartTimebyWeekday(String aWeekday, Time updateStartT) {
		if	(!aWeekday.equals("Monday") && !aWeekday.equals("monday") && !aWeekday.equals("Tuesday") && !aWeekday.equals("tuesday") && !aWeekday.equals("Wednesday") && !aWeekday.equals("wednesday") && !aWeekday.equals("Thursday") && !aWeekday.equals("thursday")  && !aWeekday.equals("Friday") && !aWeekday.equals("friday") ) throw new IllegalArgumentException("Please enter a valid Weekday, with either a Capital first letter or all lowercase");
		if (updateStartT == null ) throw new IllegalArgumentException("Pleace enter a valid startTIME");
		Hour myHour = hourRepository.findByweekday(aWeekday);
		if (myHour.setStartTime(updateStartT)) {
			hourRepository.save(myHour);
			return true;
		}
		return false;
	}
	
	
	@Transactional
	public boolean updateHourEndTimebyWeekday(String aWeekday, Time updateEndT) {
		if	(!aWeekday.equals("Monday") && !aWeekday.equals("monday") && !aWeekday.equals("Tuesday") && !aWeekday.equals("tuesday") && !aWeekday.equals("Wednesday") && !aWeekday.equals("wednesday") && !aWeekday.equals("Thursday") && !aWeekday.equals("thursday")  && !aWeekday.equals("Friday") && !aWeekday.equals("friday") ) throw new IllegalArgumentException("Please enter a valid Weekday, with either a Capital first letter or all lowercase");
		if (updateEndT == null ) throw new IllegalArgumentException("Pleace enter a valid EndTime");
		Hour myHour = hourRepository.findByweekday(aWeekday);
		if (myHour.setEndTime(updateEndT)) {
			hourRepository.save(myHour);
			return true;
		}
		return false;
		
	}
	
	@Transactional 
	public boolean updateHourEmployeebyWeekday(String aWeekday, Employee updatedEmp) {
		if	(!aWeekday.equals("Monday") && !aWeekday.equals("monday") && !aWeekday.equals("Tuesday") && !aWeekday.equals("tuesday") && !aWeekday.equals("Wednesday") && !aWeekday.equals("wednesday") && !aWeekday.equals("Thursday") && !aWeekday.equals("thursday")  && !aWeekday.equals("Friday") && !aWeekday.equals("friday") )  throw new IllegalArgumentException("Please enter a valid Weekday, with either a Capital first letter or all lowercase");
		if (updatedEmp == null ) throw new IllegalArgumentException("Pleace enter a valid Employee");
		Hour myHour = hourRepository.findByweekday(aWeekday);
		if (myHour.setEmployee(updatedEmp)) {
			hourRepository.save(myHour);
			return true;
		}
		return false;
		
		
	}

	@Transactional
	public Hour updateHour(int id, Time startTime, Time endTime) {
		Hour h = getHour(id);
		h.setStartTime(startTime);
		h.setEndTime(endTime);
		hourRepository.save(h);
		return h;
	}
	


	@Transactional
	public boolean updateEventatThisHourbyWeekday(String aWeekday, Event updatedEvent) {
		if	(!aWeekday.equals("Monday") && !aWeekday.equals("monday") && !aWeekday.equals("Tuesday") && !aWeekday.equals("tuesday") && !aWeekday.equals("Wednesday") && !aWeekday.equals("wednesday") && !aWeekday.equals("Thursday") && !aWeekday.equals("thursday")  && !aWeekday.equals("Friday") && !aWeekday.equals("friday") ) throw new IllegalArgumentException("Please enter a valid Weekday, with either a Capital first letter or all lowercase");
		if (updatedEvent == null ) throw new IllegalArgumentException("Pleace enter a valid Event Object");
		Hour myHour = hourRepository.findByweekday(aWeekday);
		if (myHour.setEvent(updatedEvent)) {
			hourRepository.save(myHour);
			return true;
		}
		return false;
	}
	
	
	}
	
	
	
	
	
	
	
	
	
	



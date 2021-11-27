package ca.mcgill.ecse321.librarysystem.dto;

import ca.mcgill.ecse321.librarysystem.model.Hour;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CalendarDto {

    private String calendarID;
    private List<String> hour;

    public CalendarDto(){
        hour = new ArrayList<String>();
    }


    public CalendarDto(String aCalendarID, List<Hour> hours) {
        calendarID = aCalendarID;
        hour = new ArrayList<String>();
        if (hours != null) {
            for (Hour h : hours) {
                hour.add(h.getWeekday());
            }
        }
    }

    public boolean setCalendarID(String aCalendarID) {
        boolean wasSet = false;
        calendarID = aCalendarID;
        wasSet = true;
        return wasSet;
    }

    public String getCalendarID() {
        return calendarID;
    }

    public int numberOfHour() {
        int number = hour.size();
        return number;
    }

    public boolean hasHour() {
        boolean has = hour.size() > 0;
        return has;
    }

    public int indexOfHour(HourDto aHour) {
        int index = hour.indexOf(aHour);
        return index;
    }

    public boolean isNumberOfHourValid() {
        boolean isValid = numberOfHour() >= minimumNumberOfHour();
        return isValid;
    }

    public static int minimumNumberOfHour() {
        return 1;
    }


    public boolean removeHour(HourDto aHour) {
        boolean wasRemoved = false;
        //Unable to remove aHour, as it must always have a calendar
        if (this.equals(aHour.getCalendar())) {
            return wasRemoved;
        }
        //calendar already at minimum (1)
        if (numberOfHour() <= minimumNumberOfHour()) {
            return wasRemoved;
        }

        hour.remove(aHour);
        wasRemoved = true;
        return wasRemoved;
    }
    
}

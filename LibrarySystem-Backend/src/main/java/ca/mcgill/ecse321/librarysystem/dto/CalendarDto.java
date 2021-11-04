package ca.mcgill.ecse321.librarysystem.dto;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CalendarDto {

    private String calendarID;
    private List<HourDto> hour;

    public CalendarDto(){
        hour = new ArrayList<HourDto>();
    }


    public CalendarDto(String aCalendarID) {
        calendarID = aCalendarID;
        hour = new ArrayList<HourDto>();
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

    public HourDto getHour(int index)
    {
        HourDto aHour = hour.get(index);
        return aHour;
    }

    public List<HourDto> getHour() {
        List<HourDto> newHour = Collections.unmodifiableList(hour);
        return newHour;
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

    public void delete() {
        for(int i=hour.size(); i > 0; i--) {
            HourDto aHour = hour.get(i - 1);
            aHour.delete();
        }
    }
}

package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.CalendarRepository;
import ca.mcgill.ecse321.librarysystem.dao.HourRepository;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@Service
public class CalendarService {

    @Autowired
    CalendarRepository calendarRepository;

    @Autowired
    HourRepository hourRepository;

    /**
     * Creates a calendar and saves to the database.
     * @return newly created calendar
     */
    @Transactional
    public Calendar createCalendar() {
        Calendar calendar = new Calendar();
        calendarRepository.save(calendar);
        return calendar;
    }

    /**
     * Finds the calendar with calendarID and deletes it.
     * @param calendarID
     * @return true if calendar has been properly deleted, false else.
     */
    @Transactional
    public boolean deleteCalendar(String calendarID) {
        Calendar calendar = calendarRepository.findCalendarByCalendarID(calendarID);
        if (calendar == null) throw new NullPointerException("Calendar not found");
        calendarRepository.delete(calendar);
        calendar.delete();

        Calendar test = calendarRepository.findCalendarByCalendarID(calendarID);
        return (test == null);
    }

    /**
     * Retrieves the calendar from the database with calendarID.
     * @param calendarID
     * @return calendar
     */
    @Transactional
    public Calendar getCalendar(String calendarID) {
        Calendar calendar = calendarRepository.findCalendarByCalendarID(calendarID);
        if (calendar == null) throw new NullPointerException("Calendar not found");
        return calendar;
    }

    /**
     * Retrieves all calendars from the LibrarySystem.
     * @return list of calendars
     */
    @Transactional
    public List<Calendar> getAllCalendars() {
        return toList(calendarRepository.findAll());
    }

    /**
     *
     * @param calendarID
     * @return true if the calendar with calendarID exits, false else.
     */
    @Transactional
    public boolean checkCalendarExists(String calendarID) {
       return calendarRepository.existsByCalendarID(calendarID);
    }

    @Transactional
    public boolean addHourToCalendar(String calendarID, Hour hour) {
        if (calendarID == null || calendarID.length() == 0) throw new IllegalArgumentException("Please enter a valid calendarID");
        if (hour == null) throw new IllegalArgumentException("Please enter a valid Hour");
        Calendar calendar = calendarRepository.findCalendarByCalendarID(calendarID);
        if (calendar == null) throw new NullPointerException("Cannot find Calendar with given ID");
        if (calendar.addHour(hour)) {
            calendarRepository.save(calendar);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean removeHour(String calendarID, Hour hour) {
        if (calendarID == null || calendarID.length() == 0) throw new IllegalArgumentException("Please enter a valid calendarID");
        if (hour == null) throw new IllegalArgumentException("Please enter a valid Hour to remove");
        Calendar calendar = calendarRepository.findCalendarByCalendarID(calendarID);
        if (calendar == null) throw new NullPointerException("Cannot find Calendar with given ID");
        if (calendar.removeHour(hour)) {
            calendarRepository.save(calendar);
            return true;
        }
        return false;
    }
}

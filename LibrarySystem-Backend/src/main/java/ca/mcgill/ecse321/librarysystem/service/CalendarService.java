package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.CalendarRepository;
import ca.mcgill.ecse321.librarysystem.model.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalendarService {

    @Autowired
    CalendarRepository calendarRepository;

    @Transactional
    public Calendar createCalendar() {
        Calendar calendar = new Calendar();
        calendarRepository.save(calendar);
        return calendar;
    }

    @Transactional
    public boolean deleteCalendar(String calendarID) {
        Calendar calendar = calendarRepository.findCalendarByCalendarID(calendarID);
        if (calendar == null) throw new NullPointerException("Calendar not found");
        calendar.delete();
        calendarRepository.delete(calendar);

        Calendar test = calendarRepository.findCalendarByCalendarID(calendarID);
        return (test == null);
    }

    // ADD MORE
}

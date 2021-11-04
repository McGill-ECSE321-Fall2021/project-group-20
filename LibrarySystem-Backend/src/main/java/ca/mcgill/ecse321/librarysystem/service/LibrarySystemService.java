package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.apache.tomcat.jni.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@Service
public class LibrarySystemService {

    @Autowired
    LibrarySystemRepository librarySystemRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CalendarRepository calendarRepository;


    @Transactional
    public LibrarySystem createLibrarySystem() {
        LibrarySystem ls = new LibrarySystem();
        librarySystemRepository.save(ls);
        return ls;
    }

    @Transactional
    public boolean deleteLibrarySystem(String systemID) {
        LibrarySystem ls = librarySystemRepository.findLibrarySystemBySystemID(systemID);
        if (ls == null) throw new NullPointerException("Library System not found");
        librarySystemRepository.delete(ls);
        return librarySystemRepository.existsById(systemID);
    }

    @Transactional
    public LibrarySystem getLibrarySystem(String systemID) {
        LibrarySystem ls = librarySystemRepository.findLibrarySystemBySystemID(systemID);
        if (ls == null) throw new NullPointerException(("Library System not found for this system ID"));
        return ls;
    }

    @Transactional
    public LibrarySystem getLibrarySystem(User user) {
        LibrarySystem ls = librarySystemRepository.findLibrarySystemByUsers(user);
        if (ls == null) throw new NullPointerException(("Library System not found for this user"));
        return ls;
    }

    @Transactional
    public LibrarySystem getLibrarySystem(Address businessaddress) {
        LibrarySystem ls = librarySystemRepository.findLibrarySystemByBusinessaddress(businessaddress);
        if (ls == null) throw new NullPointerException(("Library System not found for this business address"));
        return ls;
    }

    @Transactional
    public LibrarySystem getLibrarySystem(Item items) {
        LibrarySystem ls = librarySystemRepository.findLibrarySystemByItems(items);
        if (ls == null) throw new NullPointerException(("Library System not found for this item"));
        return ls;
    }

    @Transactional
    public LibrarySystem getLibrarySystem(Calendar calendar) {
        LibrarySystem ls = librarySystemRepository.findLibrarySystemByCalendar(calendar);
        if (ls == null) throw new NullPointerException(("Library System not found for this calendar"));
        return ls;
    }


}

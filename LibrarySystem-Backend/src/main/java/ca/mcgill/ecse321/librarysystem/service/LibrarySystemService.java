package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public LibrarySystem createLibrarySystem(Address aBusinessaddress) {
        if (aBusinessaddress == null) throw new NullPointerException("Address cannot be null");
        Calendar c = new Calendar();
        calendarRepository.save(c);
        LibrarySystem ls = new LibrarySystem(aBusinessaddress, c);
        librarySystemRepository.save(ls);
        return ls;
    }

    @Transactional
    public LibrarySystem changeAddress(LibrarySystem ls, Address newAddress) {
        if (newAddress == null || ls == null) throw new IllegalArgumentException("Please enter a valid address");
        if (ls.setBusinessaddress(newAddress)) {
            librarySystemRepository.save(ls);
            return ls;
        }
        return null;
    }

    @Transactional
    public LibrarySystem changeAddress(String id, Address newAddress) {
        if (id == null || id.length() == 0) throw new IllegalArgumentException("Please enter a valid ID");
        LibrarySystem ls = librarySystemRepository.findLibrarySystemBySystemID(id);
        if (ls == null) throw new NullPointerException("Cannot find LibrarySystem with given ID");
        if (ls.setBusinessaddress(newAddress)) {
            librarySystemRepository.save(ls);
            return ls;
        }
        return null;
    }

    @Transactional
    public boolean deleteLibrarySystem(String systemID) {
        if (systemID == null) throw new IllegalArgumentException("SystemID cannot be null");
        LibrarySystem ls = librarySystemRepository.findLibrarySystemBySystemID(systemID);
        if (ls == null) throw new NullPointerException("Library System not found");
        librarySystemRepository.delete(ls);
        return librarySystemRepository.existsById(systemID);
    }

    @Transactional
    public LibrarySystem getLibrarySystem(String systemID) {
        if (systemID == null) throw new IllegalArgumentException("SystemID cannot be null");
        LibrarySystem ls = librarySystemRepository.findLibrarySystemBySystemID(systemID);
        if (ls == null) throw new NullPointerException(("Library System not found for this system ID"));
        return ls;
    }
}
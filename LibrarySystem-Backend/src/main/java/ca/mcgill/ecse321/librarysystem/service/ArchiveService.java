package ca.mcgill.ecse321.librarysystem.service;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.dao.ArchiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Archive;

import ca.mcgill.ecse321.librarysystem.model.Title;

@Service
public class ArchiveService {
    @Autowired
    private ArchiveRepository archiveRepository;

    @Transactional
    public Archive createArchive(Status aStatus, long aItemBarcode, Title aTitle) {
        if (aStatus == null || aTitle == null) throw new IllegalArgumentException("Please enter a valid status or title");
        Archive item = new Archive(aStatus, aItemBarcode, aTitle);
        archiveRepository.save(item);
        return item;
    }

    @Transactional
    public Archive getArchiveById(Long itemBarcode) {
        Archive item = (Archive) archiveRepository.findItemByItemBarcode(itemBarcode);
        if (item == null) throw new IllegalArgumentException("No Archive found");

        return item;
    }

    @Transactional
    public List<Archive> getArchiveByStat(Status status) {
        @SuppressWarnings("unchecked")
        List<Archive> item = (List<Archive>)(List<?>) archiveRepository.findItemByStatus(status);
        for (Archive iteme : item) {
            if (iteme == null) throw new IllegalArgumentException("No Archive found");
        }
        return item;
    }

    @Transactional
    public List<Archive> getArchiveByTitle(Title title) {
        @SuppressWarnings("unchecked")
        List<Archive> item = (List<Archive>)(List<?>) archiveRepository.findItemByTitle(title);
        for (Archive iteme : item) {
            if (iteme == null) throw new IllegalArgumentException("No Archive found");
        }
        return item;
    }

    @Transactional
    public boolean getexistanceByArchiveBarcode(Long itemBarcode) {
        boolean booleanOfArchive = archiveRepository.existsByItemBarcode(itemBarcode);
        return booleanOfArchive;
    }

    @Transactional
    public Archive getArchiveByArchiveBooking(Booking booking) {
        Archive item = (Archive) archiveRepository.findItemByBooking(booking);
        if (item == null) throw new IllegalArgumentException("No Archive found");
        return item;
    }

    @Transactional
    public void deleatArchiveById(Long itemBarcode) {
        Archive item = (Archive) archiveRepository.findItemByItemBarcode(itemBarcode);
        if (item == null) throw new IllegalArgumentException("No Archive found");
        archiveRepository.delete(item);
        item.delete();
    }

    @Transactional
    public void deleatArchiveByStat(Status status) {
        @SuppressWarnings("unchecked")
        List<Archive> item = (List<Archive>)(List<?>) archiveRepository.findItemByStatus(status);
        for (Archive iteme : item) {
            if (iteme == null) throw new IllegalArgumentException("No Archive found");
        }
        archiveRepository.deleteAll(item);
        for (Item iteme : item) {
            iteme.delete();;
        }
    }

    @Transactional
    public void deleatArchiveByTitle(Title title) {
        @SuppressWarnings("unchecked")
        List<Archive> item = (List<Archive>)(List<?>) archiveRepository.findItemByTitle(title);
        for (Archive iteme : item) {
            if (iteme == null) throw new IllegalArgumentException("No Archive found");
        }
        archiveRepository.deleteAll(item);

        for (Item iteme : item) {
            iteme.delete();;
        }
    }


    @Transactional
    public void deleatArchiveByArchiveBooking(Booking booking) {
        Archive item = (Archive) archiveRepository.findItemByBooking(booking);
        if (item == null) throw new IllegalArgumentException("No Archive found");
        archiveRepository.delete(item);
        item.delete();
    }


    @Transactional
    public void updateArchive(Status aStatus, long aArchiveBarcode, Title aTitle) {
        Archive item = (Archive) archiveRepository.findItemByItemBarcode(aArchiveBarcode);
        if (item == null) throw new IllegalArgumentException("No Archive found");
        item.setStatus(aStatus);
        item.setTitle(aTitle);
        archiveRepository.save(item);
    }

    @Transactional
    public void updateArchive(long aArchiveBarcode, Title aTitle) {
        Archive item = (Archive) archiveRepository.findItemByItemBarcode(aArchiveBarcode);
        if (item == null) throw new IllegalArgumentException("No Archive found");
        item.setTitle(aTitle);
        archiveRepository.save(item);
    }

    @Transactional
    public void updateArchive(Status aStatus,long aArchiveBarcode) {
        Archive item = (Archive) archiveRepository.findItemByItemBarcode(aArchiveBarcode);
        if (item == null) throw new IllegalArgumentException("No Archive found");
        item.setStatus(aStatus);
        archiveRepository.save(item);
    }

    @Transactional
    public List<Archive> getArchives() {
        List<Archive> Archives = new ArrayList<>();
        for (Item i : archiveRepository.findAll()) {
            Archives.add((Archive) i);
        }
        return Archives;
    }

}

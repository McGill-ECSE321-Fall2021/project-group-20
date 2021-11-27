package ca.mcgill.ecse321.librarysystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.ArchiveRepository;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Archive;

import ca.mcgill.ecse321.librarysystem.model.Title;

@Service
public class ArchiveService {
	@Autowired
	private ArchiveRepository ArchiveRepository;

	@Transactional
	public Archive createArchive(Status aStatus, long aArchiveBarcode, Title aTitle) {
		if (aStatus == null || aTitle == null) throw new IllegalArgumentException("Please enter a valid status, title or Id");
		Archive item = new Archive(aStatus, aArchiveBarcode, aTitle);
		ArchiveRepository.save(item);
		return item;
	}
	
	@Transactional
	public Archive createArchive(Status aStatus, Title aTitle) {
		if (aStatus == null || aTitle == null) throw new IllegalArgumentException("Please enter a valid status, title or Id");
		Archive item = new Archive(aStatus,aTitle);
		ArchiveRepository.save(item);
		return item;
	}


	@Transactional
	public Archive getArchiveById(Long itemBarcode) {
		Archive item = (Archive) ArchiveRepository.findItemByItemBarcode(itemBarcode);
		if (item == null) throw new IllegalArgumentException("No Archive found");
		
		return item;
	}

	@Transactional
	public List<Archive> getArchiveByStat(Status status) {
		@SuppressWarnings("unchecked")
		List<Archive> item = (List<Archive>)(List<?>) ArchiveRepository.findItemByStatus(status);
		for (Archive iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Archive found");
		}
		for (Item iteme : item) {
			if (!(iteme instanceof Archive)) {
				item.remove(iteme);
			}
		}
		return item;
	}

	@Transactional
	public List<Archive> getArchiveByTitle(Title title) {
		@SuppressWarnings("unchecked")
		List<Archive> item = (List<Archive>)(List<?>) ArchiveRepository.findItemByTitle(title);
		for (Archive iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Archive found");
		}	
		for (Item iteme : item) {
			if (!(iteme instanceof Archive)) {
				item.remove(iteme);
			}
		}
		return item;
	}

	@Transactional
	public boolean getexistanceByArchiveBarcode(Long itemBarcode) {
		boolean booleanOfArchive = ArchiveRepository.existsByItemBarcode(itemBarcode);
		return booleanOfArchive;
	}

	@Transactional
	public Archive getArchiveByArchiveBooking(Booking booking) {
		if (ArchiveRepository.findItemByBooking(booking) instanceof Archive) {
		Archive item = (Archive) ArchiveRepository.findItemByBooking(booking);
		if (item == null) throw new IllegalArgumentException("No Archive found");
		return item;
		}
		return null;
	}
	
	@Transactional
	public void deleatArchiveById(Long itemBarcode) {
		Archive item = (Archive) ArchiveRepository.findItemByItemBarcode(itemBarcode);
		if (item == null) throw new IllegalArgumentException("No Archive found");
		ArchiveRepository.delete(item);
		item.delete();
	}

	@Transactional
	public void deleatArchiveByStat(Status status) {
		@SuppressWarnings("unchecked")
		List<Archive> item = (List<Archive>)(List<?>) ArchiveRepository.findItemByStatus(status);
		for (Archive iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Archive found");
		}	
		for (Item iteme : item) {
			if (!(iteme instanceof Archive)) {
				item.remove(iteme);
			}
		}
		ArchiveRepository.deleteAll(item);
		for (Item iteme : item) {
			iteme.delete();;
		}	
	}

	@Transactional
	public void deleatArchiveByTitle(Title title) {
		@SuppressWarnings("unchecked")
		List<Archive> item = (List<Archive>)(List<?>) ArchiveRepository.findItemByTitle(title);
		for (Archive iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Archive found");
		}	
		for (Item iteme : item) {
			if (!(iteme instanceof Archive)) {
				item.remove(iteme);
			}
		}
		ArchiveRepository.deleteAll(item);
		
		for (Item iteme : item) {
			iteme.delete();;
		}	
	}


	@Transactional
	public void deleatArchiveByArchiveBooking(Booking booking) {
		if(ArchiveRepository.findItemByBooking(booking) instanceof Archive) {
		Archive item = (Archive) ArchiveRepository.findItemByBooking(booking);
		if (item == null) throw new IllegalArgumentException("No Archive found");
		ArchiveRepository.delete(item);
		item.delete();
		}
	}
	
	
	@Transactional 
	public void updateArchive(Status aStatus, long aArchiveBarcode, Title aTitle) {
		Archive item = (Archive) ArchiveRepository.findItemByItemBarcode(aArchiveBarcode);
		if (item == null) throw new IllegalArgumentException("No Archive found");
		item.setStatus(aStatus);
		item.setTitle(aTitle);
		ArchiveRepository.save(item);
		
	}
	
	@Transactional 
	public void updateArchive(long aArchiveBarcode, Title aTitle) {
		Archive item = (Archive) ArchiveRepository.findItemByItemBarcode(aArchiveBarcode);
		if (item == null) throw new IllegalArgumentException("No Archive found");
		item.setTitle(aTitle);
		ArchiveRepository.save(item);
	}
	
	@Transactional 
	public void updateArchive(Status aStatus,long aArchiveBarcode) {
		Archive item = (Archive) ArchiveRepository.findItemByItemBarcode(aArchiveBarcode);
		if (item == null) throw new IllegalArgumentException("No Archive found");
		item.setStatus(aStatus);
		ArchiveRepository.save(item);
	}

	@Transactional
	public List<Archive> getAllArchives() {
		List<Archive> Archives = new ArrayList<>();
		for (Item i : ArchiveRepository.findAll()) {
			if (i instanceof Archive) Archives.add((Archive) i);
		}
		for (Item iteme : Archives) {
			if (!(iteme instanceof Archive)) {
				Archives.remove(iteme);
			}
		}
		
		return Archives;
	}

}

package ca.mcgill.ecse321.librarysystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.NewspaperRepository;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Newspaper;

import ca.mcgill.ecse321.librarysystem.model.Title;

@Service
public class NewspaperService {
	@Autowired
	private NewspaperRepository newspaperRepository;

	@Transactional
	public Newspaper createNewspaper(Status aStatus, long aNewspaperBarcode, Title aTitle) {
		if (aStatus == null || aTitle == null) throw new IllegalArgumentException("Please enter a valid status or title");
		Newspaper item = new Newspaper(aStatus, aNewspaperBarcode, aTitle);
		newspaperRepository.save(item);
		return item;
	}

	@Transactional
	public Newspaper getNewspaperById(Long itemBarcode) {
		Newspaper item = (Newspaper) newspaperRepository.findItemByItemBarcode(itemBarcode);
		if (item == null) throw new IllegalArgumentException("No Newspaper found");
		
		return item;
	}

	@Transactional
	public List<Newspaper> getNewspaperByStat(Status status) {
		@SuppressWarnings("unchecked")
		List<Newspaper> item = (List<Newspaper>)(List<?>) newspaperRepository.findItemByStatus(status);
		for (Newspaper iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Newspaper found");
		}	
		return item;
	}

	@Transactional
	public List<Newspaper> getNewspaperByTitle(Title title) {
		@SuppressWarnings("unchecked")
		List<Newspaper> item = (List<Newspaper>)(List<?>) newspaperRepository.findItemByTitle(title);
		for (Newspaper iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Newspaper found");
		}	
		return item;
	}

	@Transactional
	public boolean getexistanceByNewspaperBarcode(Long itemBarcode) {
		boolean booleanOfNewspaper = newspaperRepository.existsByItemBarcode(itemBarcode);
		return booleanOfNewspaper;
	}

	@Transactional
	public Newspaper getNewspaperByNewspaperBooking(Booking booking) {
		Newspaper item = (Newspaper) newspaperRepository.findItemByBooking(booking);
		if (item == null) throw new IllegalArgumentException("No Newspaper found");
		return item;
	}
	
	@Transactional
	public void deleatNewspaperById(Long itemBarcode) {
		Newspaper item = (Newspaper) newspaperRepository.findItemByItemBarcode(itemBarcode);
		if (item == null) throw new IllegalArgumentException("No Newspaper found");
		newspaperRepository.delete(item);
		item.delete();
	}

	@Transactional
	public void deleatNewspaperByStat(Status status) {
		@SuppressWarnings("unchecked")
		List<Newspaper> item = (List<Newspaper>)(List<?>) newspaperRepository.findItemByStatus(status);
		for (Newspaper iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Newspaper found");
		}	
		newspaperRepository.deleteAll(item);
		for (Item iteme : item) {
			iteme.delete();;
		}	
	}

	@Transactional
	public void deleatNewspaperByTitle(Title title) {
		@SuppressWarnings("unchecked")
		List<Newspaper> item = (List<Newspaper>)(List<?>) newspaperRepository.findItemByTitle(title);
		for (Newspaper iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Newspaper found");
		}	
		newspaperRepository.deleteAll(item);
		
		for (Item iteme : item) {
			iteme.delete();;
		}	
	}


	@Transactional
	public void deleatNewspaperByNewspaperBooking(Booking booking) {
		Newspaper item = (Newspaper) newspaperRepository.findItemByBooking(booking);
		if (item == null) throw new IllegalArgumentException("No Newspaper found");
		newspaperRepository.delete(item);
		item.delete();
	}
	
	
	@Transactional 
	public void updateNewspaper(Status aStatus, long aNewspaperBarcode, Title aTitle) {
		Newspaper item = (Newspaper) newspaperRepository.findItemByItemBarcode(aNewspaperBarcode);
		if (item == null) throw new IllegalArgumentException("No Newspaper found");
		item.setStatus(aStatus);
		item.setTitle(aTitle);
		newspaperRepository.save(item);
	}
	
	@Transactional 
	public void updateNewspaper(long aNewspaperBarcode, Title aTitle) {
		Newspaper item = (Newspaper) newspaperRepository.findItemByItemBarcode(aNewspaperBarcode);
		if (item == null) throw new IllegalArgumentException("No Newspaper found");
		item.setTitle(aTitle);
		newspaperRepository.save(item);
	}
	
	@Transactional 
	public void updateNewspaper(Status aStatus,long aNewspaperBarcode) {
		Newspaper item = (Newspaper) newspaperRepository.findItemByItemBarcode(aNewspaperBarcode);
		if (item == null) throw new IllegalArgumentException("No Newspaper found");
		item.setStatus(aStatus);
		newspaperRepository.save(item);
	}

	@Transactional
	public List<Newspaper> getNewspapers() {
		List<Newspaper> newspapers = new ArrayList<>();
		for (Item i : newspaperRepository.findAll()) {
			newspapers.add((Newspaper) i);
		}
		return newspapers;
	}

}

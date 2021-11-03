package ca.mcgill.ecse321.librarysystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.MusicAlbumRepository;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.MusicAlbum;
import ca.mcgill.ecse321.librarysystem.model.Title;

@Service
public class MovieService {
	@Autowired
	private MusicAlbumRepository musicAlbumRepository;

	@Transactional
	public MusicAlbum createItem(Status aStatus, long aItemBarcode, Title aTitle, int duration) {
		if (aStatus == null || aTitle == null) throw new IllegalArgumentException("Please enter a valid status or title");
		MusicAlbum item = new MusicAlbum(aStatus, aItemBarcode, aTitle,duration);
		musicAlbumRepository.save(item);
		return item;
	}

	@Transactional
	public MusicAlbum getItemById(Long itemBarcode) {
		MusicAlbum item = (MusicAlbum) musicAlbumRepository.findItemByItemBarcode(itemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		
		return item;
	}

	@Transactional
	public List<MusicAlbum> getItemByStat(Status status) {
		@SuppressWarnings("unchecked")
		List<MusicAlbum> item = (List<MusicAlbum>)(List<?>) musicAlbumRepository.findItemByStatus(status);
		for (MusicAlbum iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		return item;
	}

	@Transactional
	public List<MusicAlbum> getItemByTitle(Title title) {
		@SuppressWarnings("unchecked")
		List<MusicAlbum> item = (List<MusicAlbum>)(List<?>) musicAlbumRepository.findItemByTitle(title);
		for (MusicAlbum iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		return item;
	}

	@Transactional
	public boolean getexistanceByItemBarcode(Long itemBarcode) {
		boolean booleanOfItem = musicAlbumRepository.existsByItemBarcode(itemBarcode);
		return booleanOfItem;
	}

	@Transactional
	public MusicAlbum getItemByItemBooking(Booking booking) {
		MusicAlbum item = (MusicAlbum) musicAlbumRepository.findItemByBooking(booking);
		if (item == null) throw new IllegalArgumentException("No Item found");
		return item;
	}
	
	@Transactional
	public void deleatItemById(Long itemBarcode) {
		MusicAlbum item = (MusicAlbum) musicAlbumRepository.findItemByItemBarcode(itemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		musicAlbumRepository.delete(item);
		
		item.delete();
	}

	@Transactional
	public void deleatItemByStat(Status status) {
		@SuppressWarnings("unchecked")
		List<MusicAlbum> item = (List<MusicAlbum>)(List<?>) musicAlbumRepository.findItemByStatus(status);
		for (MusicAlbum iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		musicAlbumRepository.deleteAll(item);
		for (Item iteme : item) {
			iteme.delete();;
		}	
	}

	@Transactional
	public void deleatItemByTitle(Title title) {
		@SuppressWarnings("unchecked")
		List<MusicAlbum> item = (List<MusicAlbum>)(List<?>) musicAlbumRepository.findItemByTitle(title);
		for (MusicAlbum iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		musicAlbumRepository.deleteAll(item);
		
		for (Item iteme : item) {
			iteme.delete();;
		}	
	}


	@Transactional
	public void deleatItemByItemBooking(Booking booking) {
		MusicAlbum item = (MusicAlbum) musicAlbumRepository.findItemByBooking(booking);
		if (item == null) throw new IllegalArgumentException("No Item found");
		musicAlbumRepository.delete(item);
		item.delete();
	}
	
	
	@Transactional 
	public void updateItem(Status aStatus, long aItemBarcode, Title aTitle,int duration) {
		MusicAlbum item = (MusicAlbum) musicAlbumRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setStatus(aStatus);
		item.setTitle(aTitle);
		item.setDuration(duration);
		musicAlbumRepository.save(item);
	}
	
	@Transactional 
	public void updateItem(Status aStatus, long aItemBarcode, Title aTitle) {
		MusicAlbum item = (MusicAlbum) musicAlbumRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setStatus(aStatus);
		item.setTitle(aTitle);
		musicAlbumRepository.save(item);
	}
	
	@Transactional 
	public void updateItem(long aItemBarcode, Title aTitle) {
		MusicAlbum item = (MusicAlbum) musicAlbumRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setTitle(aTitle);
		musicAlbumRepository.save(item);
	}
	
	@Transactional 
	public void updateItem(Status aStatus,long aItemBarcode) {
		MusicAlbum item = (MusicAlbum) musicAlbumRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setStatus(aStatus);
		musicAlbumRepository.save(item);
	}
	
	@Transactional 
	public void updateItem(int duration,long aItemBarcode) {
		MusicAlbum item = (MusicAlbum) musicAlbumRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setDuration(duration);
		musicAlbumRepository.save(item);
	}

}

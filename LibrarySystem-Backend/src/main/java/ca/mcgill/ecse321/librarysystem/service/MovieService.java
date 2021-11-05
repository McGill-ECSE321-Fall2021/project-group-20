package ca.mcgill.ecse321.librarysystem.service;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.dao.MovieRepository;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.MovieRepository;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;

@Service
public class MovieService {
	@Autowired
	private MovieRepository movieRepository;

	@Transactional
	public Movie createItem(Status aStatus, long aItemBarcode, Title aTitle, int length) {
		if (aStatus == null || aTitle == null) throw new IllegalArgumentException("Please enter a valid status or title");
		Movie item = new Movie(aStatus, aItemBarcode, aTitle,length);
		movieRepository.save(item);
		return item;
	}

	@Transactional
	public Movie getItemById(Long itemBarcode) {
		Movie item = (Movie) movieRepository.findItemByItemBarcode(itemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		
		return item;
	}

	@Transactional
	public List<Movie> getItemByStat(Status status) {
		@SuppressWarnings("unchecked")
		List<Movie> item = (List<Movie>)(List<?>) movieRepository.findItemByStatus(status);
		for (Movie iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		return item;
	}

	@Transactional
	public List<Movie> getItemByTitle(Title title) {
		@SuppressWarnings("unchecked")
		List<Movie> item = (List<Movie>)(List<?>) movieRepository.findItemByTitle(title);
		for (Movie iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		return item;
	}

	@Transactional
	public boolean getexistanceByItemBarcode(Long itemBarcode) {
		boolean booleanOfItem = movieRepository.existsByItemBarcode(itemBarcode);
		return booleanOfItem;
	}

	@Transactional
	public Movie getItemByItemBooking(Booking booking) {
		Movie item = (Movie) movieRepository.findItemByBooking(booking);
		if (item == null) throw new IllegalArgumentException("No Item found");
		return item;
	}
	
	@Transactional
	public void deleatItemById(Long itemBarcode) {
		Movie item = (Movie) movieRepository.findItemByItemBarcode(itemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		movieRepository.delete(item);
		
		item.delete();
	}

	@Transactional
	public void deleatItemByStat(Status status) {
		@SuppressWarnings("unchecked")
		List<Movie> item = (List<Movie>)(List<?>) movieRepository.findItemByStatus(status);
		for (Movie iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		movieRepository.deleteAll(item);
		for (Item iteme : item) {
			iteme.delete();;
		}	
	}

	@Transactional
	public void deleatItemByTitle(Title title) {
		@SuppressWarnings("unchecked")
		List<Movie> item = (List<Movie>)(List<?>) movieRepository.findItemByTitle(title);
		for (Movie iteme : item) {
			if (iteme == null) throw new IllegalArgumentException("No Item found");
		}	
		movieRepository.deleteAll(item);
		
		for (Item iteme : item) {
			iteme.delete();;
		}	
	}


	@Transactional
	public void deleatItemByItemBooking(Booking booking) {
		Movie item = (Movie) movieRepository.findItemByBooking(booking);
		if (item == null) throw new IllegalArgumentException("No Item found");
		movieRepository.delete(item);
		item.delete();
	}
	
	
	@Transactional 
	public void updateItem(Status aStatus, long aItemBarcode, Title aTitle,int length) {
		Movie item = (Movie) movieRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setStatus(aStatus);
		item.setTitle(aTitle);
		item.setLength(length);
		movieRepository.save(item);
	}
	
	@Transactional 
	public void updateItem(Status aStatus, long aItemBarcode, Title aTitle) {
		Movie item = (Movie) movieRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setStatus(aStatus);
		item.setTitle(aTitle);
		movieRepository.save(item);
	}
	
	@Transactional 
	public void updateItem(long aItemBarcode, Title aTitle) {
		Movie item = (Movie) movieRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setTitle(aTitle);
		movieRepository.save(item);
	}
	
	@Transactional 
	public void updateItem(Status aStatus,long aItemBarcode) {
		Movie item = (Movie) movieRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setStatus(aStatus);
		movieRepository.save(item);
	}
	
	@Transactional 
	public void updateItem(int length,long aItemBarcode) {
		Movie item = (Movie) movieRepository.findItemByItemBarcode(aItemBarcode);
		if (item == null) throw new IllegalArgumentException("No Item found");
		item.setLength(length);
		movieRepository.save(item);
	}

	@Transactional
	public List<Movie> getMovies() {
		List<Movie> movies = new ArrayList<>();
		for (Item i : movieRepository.findAll()) {
			movies.add((Movie) i);
		}
		return movies;
	}

}

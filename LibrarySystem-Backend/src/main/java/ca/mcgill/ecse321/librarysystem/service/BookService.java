package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.BookRepository;
import ca.mcgill.ecse321.librarysystem.model.Book;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;
import ca.mcgill.ecse321.librarysystem.model.Title;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    BookRepository bookRepository;


    @Transactional
    public Book createBook(Status aStatus,long aItemBarcode,Title aTitle, String aIsbn, String aNumPages) {
        if (aStatus == null || aTitle == null || aIsbn==null ||aNumPages.isEmpty() ||aIsbn.isEmpty() ||aNumPages.isEmpty()) throw new IllegalArgumentException("Please enter a valid status, title or Id");
        if (aIsbn == null || aIsbn.length() == 0) throw new IllegalArgumentException("Please enter a valid ISBN");
        if (aNumPages == null || aNumPages.length() == 0 || aNumPages.contains("-")) throw new IllegalArgumentException("Please enter a valid status, title or Id");
        Book item = new Book(aStatus,aItemBarcode, aTitle, aIsbn, aNumPages);
        bookRepository.save(item);
        return item;
    }
    @Transactional
    public Book createBook(Status aStatus,Title aTitle, String aIsbn, String aNumPages) {
        if (aStatus == null || aTitle == null || aIsbn==null ||aNumPages.isEmpty() ||aIsbn.isEmpty() ||aNumPages.isEmpty()) throw new IllegalArgumentException("Please enter a valid status, title or Id");
        if (aIsbn == null || aIsbn.length() == 0) throw new IllegalArgumentException("Please enter a valid ISBN");
        if (aNumPages == null || aNumPages.length() == 0 || aNumPages.contains("-")) throw new IllegalArgumentException("Please enter a valid status, title or Id");
        Book item = new Book(aStatus, aTitle, aIsbn, aNumPages);
        bookRepository.save(item);
        return item;
    }

    @Transactional
    public Book getBookById(Long itemBarcode) {
        Book item = (Book) bookRepository.findItemByItemBarcode(itemBarcode);
        if (item == null) throw new IllegalArgumentException("No Book found");

        return item;
    }

    @Transactional
    public List<Book> getBookByStat(Status status) {
        @SuppressWarnings("unchecked")
        List<Book> item = (List<Book>)(List<?>) bookRepository.findItemByStatus(status);
        for (Book iteme : item) {
            if (iteme == null) throw new IllegalArgumentException("No Book found");
        }
        for (Item iteme : item) {
			if (!(iteme instanceof Book)) {
				item.remove(iteme);
			}
		}
        return item;
    }

    @Transactional
    public List<Book> getBookByTitle(Title title) {
        @SuppressWarnings("unchecked")
        List<Book> item = (List<Book>)(List<?>) bookRepository.findItemByTitle(title);
        for (Book iteme : item) {
            if (iteme == null) throw new IllegalArgumentException("No Book found");
        }
        for (Item iteme : item) {
     			if (!(iteme instanceof Book)) {
     				item.remove(iteme);
     			}
     		}
        return item;
    }

    @Transactional
    public boolean getexistanceByBookBarcode(Long itemBarcode) {
        boolean booleanOfBook = bookRepository.existsByItemBarcode(itemBarcode);
        return booleanOfBook;
    }

    @Transactional
    public Book getBookByBookBooking(Booking booking) {
    	if (bookRepository.findItemByBooking(booking) instanceof Book) {
        Book item = (Book) bookRepository.findItemByBooking(booking);
        if (item == null) throw new IllegalArgumentException("No Book found");
        return item;
    	}
    	return null;
    }

    @Transactional
    public void deleatBookById(Long itemBarcode) {
        Book item = (Book) bookRepository.findItemByItemBarcode(itemBarcode);
        if (item == null) throw new IllegalArgumentException("No Book found");
        bookRepository.delete(item);
        item.delete();
    }

    @Transactional
    public void deleatBookByStat(Status status) {
        @SuppressWarnings("unchecked")
        List<Book> item = (List<Book>)(List<?>) bookRepository.findItemByStatus(status);
        for (Book iteme : item) {
            if (iteme == null) throw new IllegalArgumentException("No Book found");
        }
    	for (Item iteme : item) {
			if (!(iteme instanceof Book)) {
				item.remove(iteme);
			}
		}
        bookRepository.deleteAll(item);
        
        for (Item iteme : item) {
            iteme.delete();;
        }
    }

    @Transactional
    public void deleatBookByTitle(Title title) {
        @SuppressWarnings("unchecked")
        List<Book> item = (List<Book>)(List<?>) bookRepository.findItemByTitle(title);
        for (Book iteme : item) {
            if (iteme == null) throw new IllegalArgumentException("No Book found");
        }
    	for (Item iteme : item) {
			if (!(iteme instanceof Book)) {
				item.remove(iteme);
			}
		}
        bookRepository.deleteAll(item);

        for (Item iteme : item) {
            iteme.delete();;
        }
    }


    @Transactional
    public void deleatBookByBookBooking(Booking booking) {
    	if(bookRepository.findItemByBooking(booking) instanceof Book) {
    		Book item = (Book) bookRepository.findItemByBooking(booking);
    		if (item == null) throw new IllegalArgumentException("No Book found");
    		bookRepository.delete(item);
    		item.delete();
    		}
    }


    @Transactional
    public void updateBook(Status aStatus, long aBookBarcode, Title aTitle) {
        Book item = (Book) bookRepository.findItemByItemBarcode(aBookBarcode);
        if (item == null) throw new IllegalArgumentException("No Book found");
        item.setStatus(aStatus);
        item.setTitle(aTitle);
        bookRepository.save(item);
    }

    @Transactional
    public void updateBook(long aBookBarcode, Title aTitle) {
        Book item = (Book) bookRepository.findItemByItemBarcode(aBookBarcode);
        if (item == null) throw new IllegalArgumentException("No Book found");
        item.setTitle(aTitle);
        bookRepository.save(item);
    }

    @Transactional
    public void updateBook(Status aStatus, long aBookBarcode) {
        Book item = (Book) bookRepository.findItemByItemBarcode(aBookBarcode);
        if (item == null) throw new IllegalArgumentException("No Book found");
        item.setStatus(aStatus);
        bookRepository.save(item);
    }
    
    @Transactional
    public void updateBookIsbn(String isbn, long aBookBarcode) {
        Book item = (Book) bookRepository.findItemByItemBarcode(aBookBarcode);
        if (item == null) throw new IllegalArgumentException("No Book found");
        item.setIsbn(isbn);
        bookRepository.save(item);
    }
    @Transactional
    public void updateBookPages(String page, long aBookBarcode) {
        Book item = (Book) bookRepository.findItemByItemBarcode(aBookBarcode);
        if (item == null) throw new IllegalArgumentException("No Book found");
        item.setNumPages(page);
        bookRepository.save(item);
    }

    @Transactional
    public List<Book> getBooks() {
        List<Book> Books = new ArrayList<>();
        for (Item i : bookRepository.findAll()) {
            if (i instanceof Book) Books.add((Book) i);
        }
        return Books;
    }
}

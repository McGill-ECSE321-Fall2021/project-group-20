package ca.mcgill.ecse321.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.librarysystem.dao.AuthorRepository;
import ca.mcgill.ecse321.librarysystem.dao.TitleRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Customer;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.model.Item.Status;

@ExtendWith(MockitoExtension.class)
public class TestTitleService {
	
	private static final String NAME_1 = "Harry Potter and the Philosopher Stone";
	private static final String PUB_DATE_1 = "22/10/21";
	private static final String TITLE_ID = "1835022";
	
	private static final String NAME_3 = "Harry Potter and the Chamber of Secrets";
	private static final String PUB_DATE_3 = "23/10/21";
	private static final String TITLE_ID_3 = "1835021";
	
	
	private static final String FIRSTNAME_1 = "J.K.";
	private static final String LASTNAME_1 = "ROWLING";
	private static final String AUTHOR_ID = "12345";
	
	private static final String FIRSTNAME_3 = "Joker";
	private static final String LASTNAME_3 = "LOL";
	private static final String AUTHOR_ID_3 = "123456789";
	
	private static final Author AUTHOR_1 = new Author(AUTHOR_ID, FIRSTNAME_1,LASTNAME_1);
	
	private static final Author AUTHOR_2 = new Author("1234", "Screw","Up");
	
	private static final Author AUTHOR_3 = new Author(AUTHOR_ID_3, FIRSTNAME_3,LASTNAME_3);
	
	
	private static final Status status = Status.Available;
	private static final Long itemId = (long) 12;
	private static final Author myAuthor = new Author("Jack", "Wilde");
	private static final Title myTitle = new Title("Kakao", "October 31th, 2021", myAuthor);
	
	private Item myItem = new Item(status, itemId, myTitle);
	
	private static final Status status2 = Status.Available;
	private static final Long itemId2 = (long) 13;
	private static final Author myAuthor2 = new Author("Jack", "Wilde");
	private static final Title myTitle2 = new Title("Kakao", "October 31th, 2021", myAuthor2);
	
	private Item myItem2 = new Item(status2, itemId2, myTitle2);
	
	private static List <Author> list_authors = new ArrayList<Author>();
	
	
	
	
	
	@Mock
	private TitleRepository titleDao;
	
	@Mock
	private AuthorRepository authorDao;
	
	@InjectMocks
	private TitleService titleService;
	
	
	@BeforeEach
    public void setMockOutput() {
		
		list_authors.add(AUTHOR_1);
		list_authors.add(AUTHOR_3);
		
		
		lenient().when(titleDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			return new ArrayList<Title>();
		}); 
		 
		
		lenient().when(titleDao.findByTitleID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TITLE_ID)) {
            	
            	Title title = new Title(NAME_1,PUB_DATE_1, AUTHOR_1);
            	title.setTitleID(TITLE_ID);
            	return title;
            }
            return null;
        });
		
		lenient().when(titleDao.findByAuthor(any(Author.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(AUTHOR_1)) {
            	
            	List <Title> titles = new ArrayList<Title>();
            	Title title = new Title(NAME_1,PUB_DATE_1, AUTHOR_1);
            	title.setTitleID("18350223");
            	titles.add(title);
            	return titles;
            }
            return new ArrayList<>();
        });
		
		lenient().when(titleDao.findByName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(NAME_1)) {
            	
            	List <Title> titles = new ArrayList<Title>();
            	Title title = new Title(NAME_1,PUB_DATE_1, AUTHOR_1);
            	title.setTitleID("18350224");
            	titles.add(title);
            	return titles;
            }
            return new ArrayList<>();
        });
		
		lenient().when(titleDao.findByPubDate(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(PUB_DATE_1)) {
            	
            	List <Title> titles = new ArrayList<Title>();
            	Title title = new Title(NAME_1,PUB_DATE_1, AUTHOR_1);
            	title.setTitleID("18350226");
            	titles.add(title);
            	return titles;
            }
            return new ArrayList<>();
        });
		
		lenient().when(titleDao.findByNameAndPubDate(anyString(),anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(NAME_1) && invocation.getArgument(1).equals(PUB_DATE_1)) {
            	
            	
            	Title title = new Title(NAME_1,PUB_DATE_1, AUTHOR_1);
            	title.setTitleID("18350227");
            	
            	return title;
            }
            return null;
        });
		

		
		lenient().when(titleDao.findByItem(any(Item.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(myItem)) {
            	
            	
            	Title title = new Title("Kakao","October 31th, 2021", myAuthor);
            	title.setTitleID("18350225");
            	return title;
            }
            return null; 
        });
		
		

		
		
		lenient().when(titleDao.existsByTitleID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(TITLE_ID)) {            	
                return true;
            }
            return false;
        });
		
		lenient().when(titleDao.existsByItem(any(Item.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(myItem)) {            	
                return true;
            }
            return false;
        });
		
		lenient().when(titleDao.existsByNameAndPubDate(anyString(),anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(NAME_1) && invocation.getArgument(1).equals(PUB_DATE_1)) {            	
                return true;
            }
            return false;
        });
		

		
		Answer<?> returnParamAsAnswer = (InvocationOnMock invocation) -> {return invocation.getArgument(0);};
		lenient().when(titleDao.save(any(Title.class))).thenAnswer(returnParamAsAnswer);
		lenient().when(authorDao.save(any(Author.class))).thenAnswer(returnParamAsAnswer);
		
	}
	
	@Test
	public void createTitleSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.createTitle(NAME_1, PUB_DATE_1, AUTHOR_1);			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(title);
        assertEquals(title.getName(), NAME_1);
        assertEquals(title.getPubDate(), PUB_DATE_1);
        assertEquals(title.getAuthor().size(), 1);
        assertEquals(title.getAuthor(0).getFirstName(), FIRSTNAME_1);
        assertEquals(title.getAuthor(0).getLastName(), LASTNAME_1);
        assertEquals(title.getAuthor(0).getAuthorID(),AUTHOR_ID);
	}
	
	@Test
	public void createTitleNameNull() {
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.createTitle(null, PUB_DATE_1, AUTHOR_1);			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		assertNull(title);
        assertEquals("Please enter a valid title name", error);
	}
	
	@Test
	public void createTitlePubDateNull() {
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.createTitle(NAME_1, null, AUTHOR_1);			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		assertNull(title);
        assertEquals("Please enter a valid publish date", error);
	}
	
	@Test
	public void createTitleAuthorNull() {
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.createTitle(NAME_1, PUB_DATE_1, null);			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		assertNull(title);
        assertEquals("Please enter a list of valid authors", error);
	}
	
	@Test
	public void getTitlesSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		List<Title> titles = null;
		String error =null;
		try {
			titles = titleService.getTitles();	
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		assertNotNull(titles);
		assertEquals(titles.size(),0);
	}
	
	
	@Test
	public void getTitleByTitleIDSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.getTitleByTitleID(TITLE_ID);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertNotNull(title);
		assertEquals(title.getTitleID(),TITLE_ID);
		assertEquals(title.getName(),NAME_1);
		assertEquals(title.getPubDate(),PUB_DATE_1);
	}
	
	@Test
	public void getTitleByTitleIDInvalidInput() {
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.getTitleByTitleID(null);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertNull(title);
		assertEquals("Please enter a valid title id", error);
	}
	
	@Test
	public void getTitleByTitleIDFailedInput() {
		
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.getTitleByTitleID("InvalidID");	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertNull(title);
		assertEquals("Title not found", error);
	}
	
	
	
	@Test
	public void getTitlesByAuthorIDSuccessful() {
		
		assertEquals(titleService.getTitles().size(),0);
		List <Title> titles = null;
		String error =null;
		try {
			titles = titleService.getTitlesByAuthorID(AUTHOR_1);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(titles);
		assertEquals(titles.size(),1);
		assertEquals(titles.get(0).getTitleID(),"18350223");
		assertEquals(titles.get(0).getName(),NAME_1);
		assertEquals(titles.get(0).getPubDate(),PUB_DATE_1);
	}
	
	
	@Test
	public void getTitlesByAuthorIDFail() {
		
		assertEquals(titleService.getTitles().size(),0);
		List <Title> titles = null;
		String error =null;
		try {
			titles = titleService.getTitlesByAuthorID(AUTHOR_2);	
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(titles);
		assertEquals("Titles not found", error);
	}
	
	@Test
	public void getTitlesByAuthorIDNull() {
		
		assertEquals(titleService.getTitles().size(),0);
		List <Title> titles = null;
		String error =null;
		try {
			titles = titleService.getTitlesByAuthorID(null);	
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(titles);
		assertEquals("Please enter a valid author", error);
	}
	

	@Test
	public void getTitleByItemBarcodeSuccessful() {
		
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.getTitleByItemBarcode(myItem);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(title);
		assertEquals(title.getTitleID(),"18350225");
		assertEquals(title.getName(),"Kakao");
		assertEquals(title.getPubDate(),"October 31th, 2021");
	}
	
	@Test
	public void getTitleByItemBarcodeNull() {
		
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.getTitleByItemBarcode(null);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNull(title);
		assertEquals(error,"Please enter a valid item");
		
	}
	
	@Test
	public void getTitleByItemBarcodeFail() {
		
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.getTitleByItemBarcode(myItem2);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNull(title);
		assertEquals(error,"Title not found");
		
	}
	
	@Test
	public void getTitlesByNameSuccessful() {
		
		assertEquals(titleService.getTitles().size(),0);
		List <Title> titles = null;
		String error =null;
		try {
			titles = titleService.getTitlesByName(NAME_1);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(titles);
		assertEquals(titles.size(),1);
		assertEquals(titles.get(0).getTitleID(),"18350224");
		assertEquals(titles.get(0).getName(),NAME_1);
		assertEquals(titles.get(0).getPubDate(),PUB_DATE_1);
	}
	
	
	@Test
	public void getTitlesByNameFail() {
		
		assertEquals(titleService.getTitles().size(),0);
		List <Title> titles = null;
		String error =null;
		try {
			titles = titleService.getTitlesByName(NAME_3);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNull(titles);
		assertEquals("Titles not found", error);
	}
	
	@Test
	public void getTitlesByNameNull() {
		
		assertEquals(titleService.getTitles().size(),0);
		List <Title> titles = null;
		String error =null;
		try {
			titles = titleService.getTitlesByName(null);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNull(titles);
		assertEquals("Please enter a valid title name", error);
	}
	
	@Test
	public void getTitlesByPubDateSuccessful() {
		
		assertEquals(titleService.getTitles().size(),0);
		List <Title> titles = null;
		String error =null;
		try {
			titles = titleService.getTitlesByPubDate(PUB_DATE_1);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(titles);
		assertEquals(titles.size(),1);
		assertEquals(titles.get(0).getTitleID(),"18350226");
		assertEquals(titles.get(0).getName(),NAME_1);
		assertEquals(titles.get(0).getPubDate(),PUB_DATE_1);
	}
	
	
	@Test
	public void getTitlesByPubDateFail() {
		
		assertEquals(titleService.getTitles().size(),0);
		List <Title> titles = null;
		String error =null;
		try {
			titles = titleService.getTitlesByPubDate(PUB_DATE_3);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNull(titles);
		assertEquals("Titles not found", error);
	}
	
	@Test
	public void getTitlesByPubDateNull() {
		
		assertEquals(titleService.getTitles().size(),0);
		List <Title> titles = null;
		String error =null;
		try {
			titles = titleService.getTitlesByPubDate(null);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNull(titles);
		assertEquals("Please enter a valid publish date", error);
	}
	
	@Test
	public void getTitleByNameAndPubDateSuccessful() {
		
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.getTitleByNameAndPubDate(NAME_1,PUB_DATE_1);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(title);
		assertEquals(title.getTitleID(),"18350227");
		assertEquals(title.getName(),NAME_1);
		assertEquals(title.getPubDate(),PUB_DATE_1);
	}
	
	
	@Test
	public void getTitleByNameAndPubDateFail() {
		
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.getTitleByNameAndPubDate(NAME_3,PUB_DATE_3);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNull(title);
		assertEquals("Title not found", error);
	}
	
	@Test
	public void getTitleByNameAndPubDateInvalidName() { 
		
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.getTitleByNameAndPubDate("",null);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNull(title);
		assertEquals("Please enter a valid title name", error);
	}
	
	@Test
	public void getTitleByNameAndPubDateInvalidPubDate() {
		
		assertEquals(titleService.getTitles().size(),0);
		Title title = null;
		String error =null;
		try {
			title = titleService.getTitleByNameAndPubDate(NAME_1,null);	
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNull(title);
		assertEquals("Please enter a valid publish date", error);
	}
	
		
	@Test
	public void updateNameSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updateName(TITLE_ID, FIRSTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,true);
	}
	
	@Test
	public void updateNameInvalidID() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updateName(null, FIRSTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid title id");
	}
	
	@Test
	public void updateNameInvalidTitleName() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updateName(TITLE_ID,  null);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid title name");
	}
	
	@Test
	public void updateNameFailedInput() {
		
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updateName(TITLE_ID_3, FIRSTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Title not found");
	}
	
	@Test
	public void updatePubDateSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updatePubDate(TITLE_ID, PUB_DATE_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,true);
	}
	 
	@Test
	public void updatePubDateInvalidID() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updatePubDate(null, PUB_DATE_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid title id");
	}
	
	@Test
	public void updatePubDateInvalidTitlePubDate() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updatePubDate(TITLE_ID, null);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid publish date");
	}
	
	@Test
	public void updatePubDateFailedInput() {
		
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updatePubDate(TITLE_ID_3, PUB_DATE_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Title not found");
	}
	
	@Test
	public void updateNameAndPubDateSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updateNameAndPubDate(TITLE_ID, PUB_DATE_1, FIRSTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,true);
	}
	
	@Test
	public void updateNameAndPubDateInvalidID() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updateNameAndPubDate(null, FIRSTNAME_3, PUB_DATE_1);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid title id");
	}
	
	@Test
	public void updateNameAndPubDateInvalidTitleName() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updateNameAndPubDate(TITLE_ID, null, PUB_DATE_1);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid title name");
	}
	
	@Test
	public void updateNameAndPubDateInvalidTitlePubDate() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updateNameAndPubDate(TITLE_ID, FIRSTNAME_3, null);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid publish date");
	}
	
	@Test
	public void updateNameAndPubDateFailedInput() {
		
		assertEquals(titleService.getTitles().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = titleService.updateNameAndPubDate(TITLE_ID_3, FIRSTNAME_3, PUB_DATE_1);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Title not found");
	}
	
	@Test
	public void addAuthorToTitleSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isAdded = false;
		String error =null;
		try {
			isAdded = titleService.addAuthorToTitle(TITLE_ID, AUTHOR_2);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isAdded,true);
	}
	
	@Test
	public void addAuthorToTitleAuthorNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isAdded = false;
		String error =null;
		try {
			isAdded = titleService.addAuthorToTitle(TITLE_ID, null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isAdded,false);
		assertEquals(error,"Please enter a valid author");
	}
	
	@Test
	public void addAuthorToTitleAuthorFail() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isAdded = false;
		String error =null;
		try {
			isAdded = titleService.addAuthorToTitle(TITLE_ID_3, AUTHOR_2);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isAdded,false);
		assertEquals(error,"Title not found");
	}
	
	@Test
	public void addAuthorsToTitleSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isAdded = false;
		String error =null;
		try {
			List<Author> list_authors = new ArrayList<Author>();
			list_authors.add(AUTHOR_2);
			isAdded = titleService.addAuthorsToTitle(TITLE_ID, list_authors);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isAdded,true);
	}
	
	@Test
	public void addAuthorsToTitleAuthorNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isAdded = false;
		String error =null;
		try {
			List<Author> list_authors = new ArrayList<Author>();
			list_authors.add(AUTHOR_2);
			isAdded = titleService.addAuthorsToTitle(TITLE_ID, null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isAdded,false);
		assertEquals(error,"Please enter a list of valid authors");
	}
	
	@Test
	public void addAuthorsToTitleAuthorFail() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isAdded = false;
		String error =null;
		try {
			List<Author> list_authors = new ArrayList<Author>();
			list_authors.add(AUTHOR_2);
			isAdded = titleService.addAuthorsToTitle(TITLE_ID_3, list_authors);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isAdded,false);
		assertEquals(error,"Title not found");
	}
	
//	@Test
//	public void removeAuthorFromTitleSuccessful1() {
//		assertEquals(titleService.getTitles().size(),0);
//		boolean isRemoved = false;
//		String error =null;
//		try {
//			isRemoved = titleService.removeAuthorFromTitle(TITLE_ID, AUTHOR_1);			
//		} catch (IllegalArgumentException | NullPointerException msg) {
//			error= msg.getMessage();
//		}
//		assertEquals(error,null);
//		assertEquals(isRemoved,true);
//	}
	
	@Test
	public void removeAuthorFromTitleSuccessful2() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isRemoved = false;
		String error =null;
		try {
			isRemoved = titleService.removeAuthorFromTitle(TITLE_ID, AUTHOR_2);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(error,null);
		assertEquals(isRemoved,false);
	}
	
	@Test
	public void removeAuthorsFromTitleAuthorSuccessful2() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isRemoved = false;
		String error =null;
		try {
			List<Author> list_authors = new ArrayList<Author>();
			list_authors.add(AUTHOR_2);
			isRemoved = titleService.removeAuthorsFromTitle(TITLE_ID, list_authors);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isRemoved,false);
	}
	
	@Test
	public void removeAuthorsFromTitleAuthorTitleNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isAdded = false;
		String error =null;
		try {
			isAdded = titleService.removeAuthorFromTitle(null, AUTHOR_2);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isAdded,false);
		assertEquals(error,"Please enter a valid title id");
	}
	
	@Test
	public void removeAuthorFromTitleAuthorAuthorNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isAdded = false;
		String error =null;
		try {
			isAdded = titleService.removeAuthorFromTitle(TITLE_ID, null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isAdded,false);
		assertEquals(error,"Please enter a valid author");
	}
	
	@Test
	public void removeAuthorsFromTitleAuthorAuthorNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isRemoved = false;
		String error =null;
		try {
			isRemoved = titleService.removeAuthorsFromTitle(TITLE_ID, null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isRemoved,false);
		assertEquals(error, "Please enter a list of valid authors"); 
	}
	
//	@Test
//	public void deleteTitleByTitleIDSuccessful() {
//		assertEquals(titleService.getTitles().size(),0);
//		boolean isDeleted = false;
//		String error =null;
//		try {
//			isDeleted = titleService.deleteTitleByTitleID(TITLE_ID);			
//		} catch (IllegalArgumentException | NullPointerException msg) {
//			error= msg.getMessage();
//		}
//		
//		assertEquals(isDeleted,true);
//	}
	
	@Test
	public void deleteTitleByTitleIDSuccessful2() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = titleService.deleteTitleByTitleID(TITLE_ID_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Title not found");
	}
	
	@Test
	public void deleteTitleByTitleIDNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = titleService.deleteTitleByTitleID(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid title id");
	}
	
//	@Test
//	public void deleteTitleByTitleIDSuccessful() {
//		assertEquals(titleService.getTitles().size(),0);
//		boolean isDeleted = false;
//		String error =null;
//		try {
//			isDeleted = titleService.deleteTitleByTitleID(TITLE_ID);			
//		} catch (IllegalArgumentException | NullPointerException msg) {
//			error= msg.getMessage();
//		}
//		
//		assertEquals(isDeleted,true);
//	}
	

	@Test
	public void deleteTitlesByNameSuccessful2() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = titleService.deleteTitlesByName(NAME_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Titles not found");
	}
	
	@Test
	public void deleteTitlesByNameNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = titleService.deleteTitlesByName(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid title name");
	}
	
//	@Test
//	public void deleteTitlesByPubDateSuccessful() {
//		assertEquals(titleService.getTitles().size(),0);
//		boolean isDeleted = false;
//		String error =null;
//		try {
//			isDeleted = titleService.deleteTitleByTitleID(TITLE_ID);			
//		} catch (IllegalArgumentException | NullPointerException msg) {
//			error= msg.getMessage();
//		}
//		
//		assertEquals(isDeleted,true);
//	}
	

	@Test
	public void deleteTitlesByPubDateSuccessful2() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = titleService.deleteTitlesByName(PUB_DATE_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Titles not found");
	}
	
	@Test
	public void deleteTitlesByPubDateNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = titleService.deleteTitlesByPubDate(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid publish date");
	}
	
	@Test
	public void isTitleExistsByTitleIDSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = titleService.isTitleExistsByTitleID(TITLE_ID);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,true);
	}
	
	@Test
	public void isTitleExistsByTitleIDSuccessful2() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = titleService.isTitleExistsByTitleID(TITLE_ID_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
	}
	
	@Test
	public void isTitleExistsByTitleIDNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = titleService.isTitleExistsByTitleID(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid title id");
	}
	
	
	@Test
	public void isTitleExistsByItemSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = titleService.isTitleExistsByItem(myItem);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,true);
	}
	
	@Test
	public void isTitleExistsByItemSuccessful2() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = titleService.isTitleExistsByItem(myItem2);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
	}
	
	@Test
	public void isTitleExistsByItemNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = titleService.isTitleExistsByTitleID(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid title id");
	}
	
	@Test
	public void isTitleExistsByNameAndPubDateSuccessful() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = titleService.isTitleExistsByNameAndPubDate(NAME_1,PUB_DATE_1);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,true);
	}
	
	@Test
	public void isTitleExistsByNameAndPubDateSuccessful2() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = titleService.isTitleExistsByNameAndPubDate(TITLE_ID_3,PUB_DATE_1);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
	}
	
	@Test
	public void isTitleExistsByNameAndPubDateNameNull() {
		assertEquals(titleService.getTitles().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = titleService.isTitleExistsByNameAndPubDate(null, PUB_DATE_1);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid title name");
	}
	
	@Test
	public void isTitleExistsByNameAndPubDatePubDateNull() {
		assertEquals(titleService.getTitles().size(),0); 
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = titleService.isTitleExistsByNameAndPubDate(NAME_1,null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid publish date");
	}

}

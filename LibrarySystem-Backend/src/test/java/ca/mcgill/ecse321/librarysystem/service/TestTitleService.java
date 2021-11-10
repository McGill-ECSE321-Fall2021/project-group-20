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
import ca.mcgill.ecse321.librarysystem.model.Title;

@ExtendWith(MockitoExtension.class)
public class TestTitleService {
	
	private static final String NAME_1 = "Harry Potter and the Philosopher Stone";
	private static final String PUB_DATE_1 = "22/10/21";
	private static final String TITLE_ID = "1835022";
	
	private static final String NAME_3 = "Harry Potter and the Chamber of Secrets";
	private static final String PUB_DATE_3 = "22/10/21";
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
	
	//private static final Item ITEM_1 = new item
	
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
		
		lenient().when(titleDao.findByAuthorIn(any(List.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(list_authors)) {
            	
            	List <Title> titles = new ArrayList<Title>();
            	Title title1 = new Title(NAME_1,PUB_DATE_1, AUTHOR_1);
            	title1.setTitleID("18350224");
            	Title title2 = new Title(NAME_3,PUB_DATE_3, AUTHOR_3);
            	title2.setTitleID("1835021");
            	titles.add(title1);
            	titles.add(title2);
            	return titles;
            }
            return new ArrayList<>();
        });

//		
//		
//		lenient().when(titleDao.existsByTitleID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
//            if (invocation.getArgument(0).equals(TITLE_ID)) {            	
//                return true;
//            }
//            return false;
//        });
//		
//		lenient().when(titleDao.existsByNameAndPubDate(anyString(),anyString())).thenAnswer((InvocationOnMock invocation) -> {
//            if (invocation.getArgument(0).equals(NAME_1) && invocation.getArgument(0).equals(PUB_DATE_1)) {            	
//                return true;
//            }
//            return false;
//        });
		
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
		assertEquals("Please enter a valid titleID", error);
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
		
		//assertNull(titles);
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
	public void getTitlesByAuthorIDsNull() {
		
		assertEquals(titleService.getTitles().size(),0);
		List <Title> titles = null;
		String error =null;
		try {
			
			titles = titleService.getTitlesByAuthorIDs(null);	
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(titles);
		assertEquals("Please enter a list of valid authors",error);
	}
	
//	@Test
//	public void getTitleByItemBarcodeSuccessful() {
//		
//		assertEquals(titleService.getTitles().size(),0);
//		Title title = null;
//		String error =null;
//		try {
//			title = titleService.getTitleByItemBarcode(TITLE_ID);	
//		} catch (IllegalArgumentException | NullPointerException msg) {
//			error= msg.getMessage();
//		}
//		assertNotNull(title);
//		assertEquals(title.getTitleID(),TITLE_ID);
//		assertEquals(title.getName(),NAME_1);
//		assertEquals(title.getPubDate(),PUB_DATE_1);
//	}
	

}

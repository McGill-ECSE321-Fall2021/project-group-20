package ca.mcgill.ecse321.librarysystem.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.librarysystem.dao.AuthorRepository;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Title;

@ExtendWith(MockitoExtension.class)
public class TestAuthorService {
	
	
	@Mock
	private AuthorRepository authorDao;
	
	@InjectMocks
	private AuthorService authorService;
	
	
	private static final String FIRSTNAME_1 = "J.K.";
	private static final String LASTNAME_1 = "ROWLING";
	private static final String AUTHOR_ID = "12345";
	
	private static final String TITLE_ID_3 = "1835021";
	private static final String AUTHOR_ID_3 = "123456789";
	private static final String FIRSTNAME_3 = "Joker";
	private static final String LASTNAME_3 = "LOL";

	
	private static List<Title> list_titles = new ArrayList<Title>();
	
	
	
	private static final String NAME_1 = "Harry Potter and the Philosopher Stone";
	private static final String PUB_DATE_1 = "22/10/21";
	private static final String TITLE_ID = "1835022";
	
	private static final Author dummy_author = new Author("LG5","Hello","World");
	
	@BeforeEach
	public void setMockOutput() {
		
		//authorRepository.findByAuthorID(authorID);
		
		lenient().when(authorDao.findByAuthorID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals("1")) {
            	
            	Author author = new Author("1",FIRSTNAME_1,LASTNAME_1);
            	return author;
            } 
            else if (invocation.getArgument(0).equals("10")) {
            	Author author = new Author("10",FIRSTNAME_1,LASTNAME_1);
            	return author;
            }
            else if (invocation.getArgument(0).equals("20")) {
            	Author author = new Author("20",FIRSTNAME_1,LASTNAME_1);
            	return author;
            }
            else if (invocation.getArgument(0).equals("30")) {
            	Author author = new Author("30",FIRSTNAME_1,LASTNAME_1);
            	return author;
            }
            else if (invocation.getArgument(0).equals("40")) {
            	Author author = new Author("40",FIRSTNAME_1,LASTNAME_1);
            	return author;
            }
            else if (invocation.getArgument(0).equals("50")) {
            	Author author = new Author("50",FIRSTNAME_1,LASTNAME_1);
            	return author;
            }
            else if (invocation.getArgument(0).equals("60")) {
            	Author author = new Author("60",FIRSTNAME_1,LASTNAME_1);
            	return author;
            }
            else if (invocation.getArgument(0).equals("70")) {
            	Author author = new Author("70",FIRSTNAME_1,LASTNAME_1);
            	return author;
            }
            else return null;
        });
		
		lenient().when(authorDao.findByFirstName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(FIRSTNAME_1)) {
            	
            	Author author = new Author("2",FIRSTNAME_1,LASTNAME_1);
            	List<Author> authors = new ArrayList<Author>();
            	authors.add(author);
            	return authors;
            }
            return new ArrayList<>();
        });
		
		lenient().when(authorDao.findByLastName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(LASTNAME_1)) {
            	
            	Author author = new Author("3",FIRSTNAME_1,LASTNAME_1);
            	List<Author> authors = new ArrayList<Author>();
            	authors.add(author);
            	return authors;
            }
            return new ArrayList<>();
        });
		
		
		lenient().when(authorDao.findByFirstNameAndLastName(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(FIRSTNAME_1) && invocation.getArgument(1).equals(LASTNAME_1)) {
            	
            	Author author = new Author("4",FIRSTNAME_1,LASTNAME_1);
            	List<Author> authors = new ArrayList<Author>();
            	authors.add(author);
            	return authors;
            }
            return new ArrayList<>();
        });
		//
		//findByTitlesIn
		
		lenient().when(authorDao.findByTitlesIn(any(List.class))).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(list_titles)) {
            	
            	List<Author> authors = new ArrayList<Author>();
            	authors.add(dummy_author);
            	return authors;
            }
            return new ArrayList<>();
        });
		
		lenient().when(authorDao.existsByFirstName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(FIRSTNAME_1)) {
            	return true;
            }
            return false;
        });
		
		lenient().when(authorDao.existsByLastName(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(LASTNAME_1)) {
            	return true;
            }
            return false;
        });
		
		lenient().when(authorDao.existsByFirstNameAndLastName(anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(FIRSTNAME_1) && invocation.getArgument(1).equals(LASTNAME_1)) {
            	return true;
            }
            return false;
        });
		
		lenient().when(authorDao.existsByAuthorID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(AUTHOR_ID)) {
            	return true;
            }
            return false;
        });
		
		lenient().when(authorDao.findAll()).thenAnswer((InvocationOnMock invocation) -> {
			return new ArrayList<Author>();
		}); 

		
		Answer<?> returnParamAsAnswer = (InvocationOnMock invocation) -> {return invocation.getArgument(0);};
		lenient().when(authorDao.save(any(Author.class))).thenAnswer(returnParamAsAnswer);		
		
	}
	
//  @Transactional
//  public List<Author> getAuthors() {
//      List<Author> authors = new ArrayList<>();
//      for (Author a : authorRepository.findAll()) {
//          authors.add(a);
//      }
//      return authors;
//  }
	
	/*
	 * Testing the create author service methods
	 * 
	 * */
	@Test
	public void createAuthorSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.createAuthor("6456", FIRSTNAME_1, LASTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(author);
		
		assertEquals(author.getAuthorID(),"6456");
		assertEquals(author.getFirstName(),FIRSTNAME_1);
		assertEquals(author.getLastName(),LASTNAME_1);
	}
	
	@Test
	public void createAuthorIDNull() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.createAuthor(null, FIRSTNAME_1, LASTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(author);
		
		assertEquals(error,"Please enter a valid author id");
	}
	
	@Test
	public void createAuthorIDLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.createAuthor("", FIRSTNAME_1, LASTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(author);
		
		assertEquals(error,"Please enter a valid author id");
	}
	
	@Test
	public void createAuthorFirstNameNull() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.createAuthor(AUTHOR_ID, null, LASTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(author);
		
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void createAuthorFirstNameLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.createAuthor(AUTHOR_ID, "", LASTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(author);
		
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void createAuthorLastNameNull() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.createAuthor(AUTHOR_ID, FIRSTNAME_1, null);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(author);
		
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void createAuthorLastNameLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.createAuthor(AUTHOR_ID, FIRSTNAME_1, "");
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(author);
		
		assertEquals(error,"Please enter a valid name");
	} 
	
	@Test
	public void getAuthorByAuthorIDSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.getAuthorByAuthorID("1");
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(author);
		
		assertEquals(author.getAuthorID(),"1");
		assertEquals(author.getFirstName(),FIRSTNAME_1);
		assertEquals(author.getLastName(),LASTNAME_1);
	}
	
	/*
	 * Testing the get author service methods
	 * 
	 * */
	
	@Test
	public void getAuthorByAuthorIDNull() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.getAuthorByAuthorID(null);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(author);
		assertEquals(error,"Please enter a valid author id");
	}
	
	@Test
	public void getAuthorByAuthorIDLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.getAuthorByAuthorID("");
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(author);
		assertEquals(error,"Please enter a valid author id");
	}
	
	@Test
	public void getAuthorByAuthorIDFail() {
		assertEquals(authorService.getAuthors().size(),0);
		Author author =null;
		String error = null;
		
		try {
			author = authorService.getAuthorByAuthorID(AUTHOR_ID_3);
			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertNull(author);
		assertEquals(error,"Author not found");
	}
	
	@Test
	public void getAuthorsByFirstNameSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByFirstName(FIRSTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(authors);
		assertEquals(authors.size(),1);
		assertEquals(authors.get(0).getAuthorID(),"2");
		assertEquals(authors.get(0).getFirstName(),FIRSTNAME_1);
		assertEquals(authors.get(0).getLastName(),LASTNAME_1);
	}
	
	@Test
	public void getAuthorsByFirstNameNull() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByFirstName(null);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Please enter a valid first name");
	}
	
	@Test
	public void getAuthorsByFirstNameLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByFirstName("");
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Please enter a valid first name");
	}
	
	@Test
	public void getAuthorsByFirstNameFail() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByFirstName("Non-existing name");
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Authors not found");
	}
	
	@Test
	public void getAuthorsByLastNameSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByLastName(LASTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(authors);
		assertEquals(authors.size(),1);
		assertEquals(authors.get(0).getAuthorID(),"3");
		assertEquals(authors.get(0).getFirstName(),FIRSTNAME_1);
		assertEquals(authors.get(0).getLastName(),LASTNAME_1);
	}
	
	@Test
	public void getAuthorsByLastNameNull() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByLastName(null);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Please enter a valid last name");
	}
	
	@Test
	public void getAuthorsByLastNameLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByLastName("");
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Please enter a valid last name");
	}
	
	@Test
	public void getAuthorsByLastNameFail() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByLastName("Non-existing name");
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Authors not found");
	}
	
	@Test
	public void getAuthorsByFirstNameAndLastNameSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByFirstNameAndLastName(FIRSTNAME_1,LASTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(authors);
		assertEquals(authors.size(),1);
		assertEquals(authors.get(0).getAuthorID(),"4");
		assertEquals(authors.get(0).getFirstName(),FIRSTNAME_1);
		assertEquals(authors.get(0).getLastName(),LASTNAME_1);
	}
	
	@Test
	public void getAuthorsByFirstNameAndLastNameFirstNull() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByFirstNameAndLastName(null,LASTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void getAuthorsByFirstNameAndLastNameFirstLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByFirstNameAndLastName("",LASTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void getAuthorsByFirstNameAndLastNameLastNull() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByFirstNameAndLastName(FIRSTNAME_1,null);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void getAuthorsByFirstNameAndLastNameLastLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByFirstNameAndLastName(FIRSTNAME_1,"");
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void getAuthorsByFirstNameAndLastNameFail() {
		assertEquals(authorService.getAuthors().size(),0);
		List <Author> authors =null;
		String error = null;
		
		try {
			authors = authorService.getAuthorsByFirstNameAndLastName(LASTNAME_1, FIRSTNAME_1);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error,"Authors not found");
	}
	
	
	@Test
	public void getAuthorsByTitlesSuccessful() {
		
		assertEquals(authorService.getAuthors().size(),0);
		List<Author> authors = null;
		String error = null;
		
		try {
			Title title = new Title(NAME_1,PUB_DATE_1, dummy_author);
			title.setTitleID("5");
			list_titles.add(title);
			authors = authorService.getAuthorsByTitles(list_titles);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNotNull(authors);
		assertEquals(authors.size(),1);
		assertEquals(authors.get(0).getAuthorID(),"LG5");
		assertEquals(authors.get(0).getFirstName(),"Hello");
		assertEquals(authors.get(0).getLastName(),"World");
		
	}
	
	@Test
	public void getAuthorsByTitlesEmptyList() {
		
		assertEquals(authorService.getAuthors().size(),0);
		List<Author> authors = null;
		String error = null;
		
		try {
			List <Title> title = new ArrayList<Title>();
			authors = authorService.getAuthorsByTitles(title);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error, "Please enter a valid list of titles");
		
	}
	
	@Test
	public void getAuthorsByTitlesNull() {
		
		assertEquals(authorService.getAuthors().size(),0);
		List<Author> authors = null;
		String error = null;
		
		try {
			List <Title> title = null;
			authors = authorService.getAuthorsByTitles(title);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertNull(authors);
		assertEquals(error, "Please enter a valid list of titles");
		
	}
	
	/*
	 * Testing the update author service methods
	 * 
	 * */
	
	@Test
	public void updateFirstNameSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateFirstName("10", FIRSTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,true);
	}
	
	@Test
	public void updateFirstNameInvalidID() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateFirstName(null, FIRSTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid author id");
	}
	
	@Test
	public void updateFirstNameInvalidTitleName() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateFirstName("20",  null);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid first name");
	}
	
	@Test
	public void updateFirstNameFailedInput() {
		
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateFirstName(AUTHOR_ID_3, FIRSTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Author not found");
	}
	
	@Test
	public void updateLastNameSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateLastName("30", LASTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,true);
	}
	
	@Test
	public void updateLastNameInvalidID() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateLastName(null, LASTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid author id");
	}
	
	@Test
	public void updateLastNameInvalidTitleName() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateLastName("40",  null);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid last name");
	}
	
	@Test
	public void updateLastNameFailedInput() {
		
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateLastName(AUTHOR_ID_3, LASTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Author not found");
	}
	
	
	@Test
	public void updateFullNameSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateFullName("50", FIRSTNAME_3,LASTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,true);
	}
	
	@Test
	public void updateFullNameInvalidID() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateFullName(null, FIRSTNAME_3,LASTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid author id");
	}
	
	@Test
	public void updateFullNameInvalidAuthorFirstLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateFullName("60",  "", LASTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void updateFullNameInvalidAuthorLastLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateFullName("70",  FIRSTNAME_3, "");
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Please enter a valid name");
	}
	
	
	@Test
	public void updateFullNameFailedInput() {
		
		assertEquals(authorService.getAuthors().size(),0);
		boolean isUpdated = false;
		String error =null;
		try {
			isUpdated = authorService.updateFullName(AUTHOR_ID_3, FIRSTNAME_3,LASTNAME_3);
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		assertEquals(isUpdated,false);
		assertEquals(error,"Author not found");
	}
	
	/*
	 * Testing the delete author service methods
	 * 
	 * */
	
	@Test
	public void deleteAuthorByAuthorIDInvalidInput() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorByAuthorID(AUTHOR_ID_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Author not found");
	}
	
	@Test
	public void deleteAuthorByAuthorIDNullInput() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorByAuthorID(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid author id");
	}
	
	@Test
	public void deleteAuthorByAuthorIDInputLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorByAuthorID("");			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid author id");
	}
	
	@Test
	public void deleteAuthorsByFirstNameSuccessful2() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByFirstName(FIRSTNAME_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Authors not found");
	}
	
	@Test
	public void deleteAuthorsByFirstNameNull() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByFirstName(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid first name");
	}
	
	@Test
	public void deleteAuthorsByLastNameSuccessful2() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByLastName(LASTNAME_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Authors not found");
	}
	
	@Test
	public void deleteAuthorsByLastNameNull() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByLastName(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid last name");
	}
	
	@Test
	public void deleteAuthorsByFirstNameAndLastNameSuccessful2() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByFirstNameAndLastName(FIRSTNAME_3, LASTNAME_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Authors not found");
	}
	
	@Test
	public void deleteAuthorsByFirstNameAndLastNameFirstNull() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByFirstNameAndLastName(null,LASTNAME_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void deleteAuthorsByFirstNameAndLastNameFirstLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByFirstNameAndLastName("",LASTNAME_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void deleteAuthorsByFirstNameAndLastNameLastNull() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByFirstNameAndLastName(FIRSTNAME_3,null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void deleteAuthorsByFirstNameAndLastNameLastLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByFirstNameAndLastName(FIRSTNAME_3,"");			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid name");
	}
	
	
	@Test
	public void deleteAuthorsByTitlesSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			Title title = new Title(NAME_1,PUB_DATE_1, dummy_author);
			title.setTitleID("5");
			list_titles.add(title);
			isDeleted = authorService.deleteAuthorsByTitles(list_titles);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isDeleted,false);
	}
	
	@Test
	public void deleteAuthorsByTitlesNull() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByTitles(null);
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid list of titles");
	}
	
	@Test
	public void deleteAuthorsByTitlesEmptyList() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isDeleted = false;
		String error =null;
		try {
			isDeleted = authorService.deleteAuthorsByTitles(new ArrayList<Title>());
			
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		assertEquals(isDeleted,false);
		assertEquals(error,"Please enter a valid list of titles");
	}
	
	/*
	 * Testing the boolean existing author service methods
	 * 
	 * */
    
    @Test
	public void isAuthorsExistsByFirstNameSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByFirstName(FIRSTNAME_1);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,true);
	}
	
	@Test
	public void isAuthorsExistsByFirstNameSuccessful2() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByFirstName(FIRSTNAME_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
	}
	
	@Test
	public void isAuthorsExistsByFirstNameNull() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByFirstName(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid first name");
	}
	
	@Test
	public void isAuthorsExistsByFirstNameLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByFirstName("");			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid first name");
	}
	
    @Test
	public void isAuthorsExistsByLastNameSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByLastName(LASTNAME_1);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,true);
	}
	
	@Test
	public void isAuthorsExistsByLastNameSuccessful2() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByLastName(LASTNAME_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
	}
	
	@Test
	public void isAuthorsExistsByLastNameNull() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByLastName(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid last name");
	}
	
	@Test
	public void isAuthorsExistsByLastNameLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByLastName("");			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid last name");
	}
	
    @Test
	public void isAuthorsExistsByFirstNameAndLastNameSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByFirstNameAndLastName(FIRSTNAME_1, LASTNAME_1);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,true);
	}
	
	@Test
	public void isAuthorsExistsByFirstNameAndLastNameSuccessful2() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByFirstNameAndLastName(FIRSTNAME_3,LASTNAME_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
	}
	
	@Test
	public void isAuthorsExistsByFirstNameAndLastNameFirstNull() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByFirstNameAndLastName(null, LASTNAME_1);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void isAuthorsExistsByFirstNameAndLastNameFirstLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByFirstNameAndLastName("",LASTNAME_1);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void isAuthorsExistsByFirstNameAndLastNameLastNull() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByFirstNameAndLastName(FIRSTNAME_1, null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid name");
	}
	
	@Test
	public void isAuthorsExistsByFirstNameAndLastNameLastLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByFirstNameAndLastName(FIRSTNAME_1,"");			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid name");
	}
	
    @Test
	public void isAuthorsExistsByAuthorIDSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByAuthorID(AUTHOR_ID);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,true);
	}
	
	@Test
	public void isAuthorsExistsByAuthorIDSuccessful2() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByAuthorID(AUTHOR_ID_3);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
	}
	
	@Test
	public void isAuthorsExistsByAuthorIDNull() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByAuthorID(null);			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid author id");
	}
	
	@Test
	public void isAuthorsExistsByAuthorIDLength0() {
		assertEquals(authorService.getAuthors().size(),0);
		boolean isExisted = false;
		String error =null;
		try {
			isExisted = authorService.isAuthorsExistsByAuthorID("");			
		} catch (IllegalArgumentException | NullPointerException msg) {
			error= msg.getMessage();
		}
		
		assertEquals(isExisted,false);
		assertEquals(error,"Please enter a valid author id");
	}
	
	/*
	 *Testing if program can get all authors from database
	 */
	@Test
	public void getAuthorsSuccessful() {
		assertEquals(authorService.getAuthors().size(),0);
		List<Author> authors = null;
		String error =null;
		try {
			authors = authorService.getAuthors();	
		} catch (IllegalArgumentException msg) {
			error= msg.getMessage();
		}
		assertNotNull(authors);
		assertEquals(authors.size(),0);
	}
}

package ca.mcgill.ecse321.librarysystem.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.librarysystem.dao.TitleRepository;
import ca.mcgill.ecse321.librarysystem.dto.AuthorDto;
import ca.mcgill.ecse321.librarysystem.dto.ItemDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.service.AuthorService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")
@RestController
public class AuthorRestController {
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private TitleService titleService;
	
	
	@GetMapping(value = { "/authors", "/authors/"})
    public ResponseEntity getAllAuthors() {
        List<AuthorDto> titleDtos = new ArrayList<>();
		List<Author> authors = authorService.getAuthors();
		if (authors.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any authors");

        for (Author author : authors) {
            titleDtos.add(convertToAuthorDto(author));
        }
        return new ResponseEntity<>(titleDtos, HttpStatus.OK);
    }
	
	@GetMapping(value = { "/author/{id}", "/author/{id}/"})
    public ResponseEntity getAuthorByAuthorID(@PathVariable("id") String id) {
		Author author;
		try {
			author = authorService.getAuthorByAuthorID(id);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
        return new ResponseEntity<>(convertToAuthorDto(author), HttpStatus.OK);
    }
	
	@GetMapping(value = { "/authors/getByFirstName", "/authors/getByFirstName/"})
    public ResponseEntity getAuthorsByFirstName(@RequestParam String firstname) {
		List<Author> author;
		try {
			author = authorService.getAuthorsByFirstName(firstname);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		return new ResponseEntity<>(convertToAuthorsDto(author), HttpStatus.OK);
	}
	
	@GetMapping(value = { "/authors/getByLastName", "/authors/getByLastName/"})
    public ResponseEntity getAuthorByLastName(@RequestParam String lastname) {
		List<Author> author;
		try {
			author = authorService.getAuthorsByLastName(lastname);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		return new ResponseEntity<>(convertToAuthorsDto(author), HttpStatus.OK);
    }
	
	@GetMapping(value = { "/authors/getByFirstNameAndLastName", "/authors/getByFirstNameAndLastName/"})
    public ResponseEntity getAuthorsByFirstNameAndLastName(@RequestParam String firstname, @RequestParam String lastname) {
		List<Author> author;
		try {
			author = authorService.getAuthorsByFirstNameAndLastName(firstname, lastname);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		return new ResponseEntity<>(convertToAuthorsDto(author), HttpStatus.OK);
    }
	
	@GetMapping(value = { "/authors/getByTitles", "/authors/getByTitles/"})
    public ResponseEntity getAuthorsByTitles(@RequestParam String titles) {
		List<Title> titleList = new ArrayList<>();
        List<String> seperatedAuthorStringList = Arrays.asList(titles.split(","));
        for (String s : seperatedAuthorStringList) {
			try {
				titleList.add(titleService.getTitleByTitleID(s));
			} catch (IllegalArgumentException | NullPointerException msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
        }
        return new ResponseEntity<>(convertToAuthorsDto(authorService.getAuthorsByTitles(titleList)), HttpStatus.OK);
    }
	
	@PostMapping(value = {"/author/create", "/author/create/"})
	public ResponseEntity createByAuthorIDAndFirstNameAndLastName(@RequestParam String firstname, @RequestParam String lastname) {
		Author author;
		try {
			author = authorService.createAuthor(firstname, lastname);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (author == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot create Author");
		return new ResponseEntity<>(author, HttpStatus.OK);
	}
	
	@PutMapping(value = { "/author/{id}/updateFirstName", "/author/{id}/updateFirstName/" })
	public ResponseEntity updateFirstName(@PathVariable("id") String id, @RequestParam String firstname) {
		boolean b;
		try {
			b = authorService.updateFirstName(id, firstname);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Firstname has been updated");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot update firstname");
	}
	
	@PutMapping(value = { "/author/{id}/updateLastName", "/author/{id}/updateLastName/" })
	public ResponseEntity updateLastName(@PathVariable("id") String id, @RequestParam String lastname) {
		boolean b;
		try {
			b = authorService.updateLastName(id, lastname);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Lastname has been updated");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot update lastname");
	}
	
	@PutMapping(value = { "/author/{id}/updateFullName", "/author/{id}/updateFullName/" })
	public ResponseEntity updateFullName(@PathVariable("id") String id, @RequestParam String firstname, @RequestParam String lastname) {
		boolean b;
		try {
			b = authorService.updateFullName(id, firstname, lastname);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Name has been updated");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot update name");
	}
	
	@DeleteMapping(value = {"/author/{id}", "/author/{id}/"})
	public ResponseEntity deleteAuthorByAuthorID(@PathVariable("id") String id) {
		boolean b;
		try {
			b = authorService.deleteAuthorByAuthorID(id);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Author has been deleted");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot delete author");
	}
	
	@DeleteMapping(value = {"/author/deleteAuthorsByFirstName", "/author/deleteAuthorsByFirstName/"})
	public ResponseEntity deleteAuthorsByFirstName(@RequestParam String firstname) {
		boolean b;
		try {
			b = authorService.deleteAuthorsByFirstName(firstname);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Authors have been deleted");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot delete author");
	}
	
	@DeleteMapping(value = {"/author/deleteAuthorsByLastName", "/author/deleteAuthorsByLastName/"})
	public ResponseEntity deleteAuthorsByLastName(@RequestParam String lastname) {
		boolean b;
		try {
			b = authorService.deleteAuthorsByLastName(lastname);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Authors have been deleted");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot delete author");
	}
	
	@DeleteMapping(value = {"/author/deleteAuthorsByFirstNameAndLastName", "/author/deleteAuthorsByFirstNameAndLastName/"})
	public ResponseEntity deleteAuthorsByFirstNameAndLastName(@RequestParam String firstname, @RequestParam String lastname) {
		boolean b;
		try {
			b = authorService.deleteAuthorsByFirstNameAndLastName(firstname, lastname);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Authors have been deleted");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot delete author");
	}
	
	
	@DeleteMapping(value = {"/author/deleteAuthorsByTitles", "/author/deleteAuthorsByTitles/"})
	public ResponseEntity deleteAuthorsByTitles(@RequestParam String titles) {
		List<Title> titleList = new ArrayList<>();
        List<String> seperatedAuthorStringList = Arrays.asList(titles.split(","));
        for (String s : seperatedAuthorStringList) {
			try {
				titleList.add(titleService.getTitleByTitleID(s));
			} catch (IllegalArgumentException | NullPointerException msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
        }
		boolean b;
		try {
			b = authorService.deleteAuthorsByTitles(titleList);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Authors have been deleted");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot delete author");
	}
	
	private AuthorDto convertToAuthorDto(Author author) throws IllegalArgumentException, NullPointerException {
		if (author == null) throw new NullPointerException("Cannot find this Title");
		return new AuthorDto(author.getAuthorID(),author.getFirstName(),author.getLastName());
	}
	
	private List <AuthorDto> convertToAuthorsDto(List<Author> authors) throws IllegalArgumentException, NullPointerException {
		if (authors == null || authors.size() == 0) throw new NullPointerException("Cannot find this Title");
		List <AuthorDto> list_authors = new ArrayList<AuthorDto>();
		for (int i = 0; i < authors.size(); i++) list_authors.add(convertToAuthorDto(authors.get(i)));
		return list_authors; 
	}
	
}

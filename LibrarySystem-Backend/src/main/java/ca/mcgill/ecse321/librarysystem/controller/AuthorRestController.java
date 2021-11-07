package ca.mcgill.ecse321.librarysystem.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
    public List<AuthorDto> getAllAuthors() {
        List<AuthorDto> titleDtos = new ArrayList<>();
        for (Author author : authorService.getAuthors()) {
            titleDtos.add(convertToAuthorDto(author));
        }
        return titleDtos;
    }
	
	@GetMapping(value = { "/author/{id}", "/author/{id}/"})
    public AuthorDto getAuthorByAuthorID(@PathVariable("id") String id) throws IllegalArgumentException, NullPointerException {
        return convertToAuthorDto(authorService.getAuthorByAuthorID(id));
    }
	
	@GetMapping(value = { "/authors/getByFirstName", "/authors/getByFirstName/"})
    public List<AuthorDto> getAuthorByFirstName(@RequestParam String firstnName)
            throws IllegalArgumentException, NullPointerException {
        return convertToAuthorsDto(authorService.getAuthorsByFirstName(firstnName));
    }
	
	@GetMapping(value = { "/authors/getByLastName", "/authors/getByLastName/"})
    public List<AuthorDto> getAuthorByLastName(@RequestParam String lastnName)
            throws IllegalArgumentException, NullPointerException {
        return convertToAuthorsDto(authorService.getAuthorsByLastName(lastnName));
    }
	
	@GetMapping(value = { "/authors/getByFirstNameAndLastName", "/authors/getByFirstNameAndLastName/"})
    public List<AuthorDto> getAuthorsByFirstNameAndLastName(@RequestParam String firstnName, @RequestParam String lastName)
            throws IllegalArgumentException, NullPointerException {
        return convertToAuthorsDto(authorService.getAuthorsByFirstNameAndLastName(firstnName, lastName));
    }
	
	@GetMapping(value = { "/authors/getByTitles", "/authors/getByTitles/"})
    public List<AuthorDto> getAuthorsByTitles(@RequestParam String titles)
            throws IllegalArgumentException, NullPointerException {
		List<Title> titleList = new ArrayList<>();
        List<String> seperatedAuthorStringList = Arrays.asList(titles.split(","));
        for (String s : seperatedAuthorStringList) {
        	titleList.add(titleService.getTitleByTitleID(s));
        }
        return convertToAuthorsDto(authorService.getAuthorsByTitles(titleList));
    }
	
	@PostMapping(value = {"/author/createByAuthorIDAndFirstNameAndLastName", "/author/createByAuthorIDAndFirstNameAndLastName/"})
	public AuthorDto createByAuthorIDAndFirstNameAndLastName(@RequestParam String authorID, @RequestParam String firstName, @RequestParam String lastName) {
		return convertToAuthorDto(authorService.createAuthor(authorID,firstName,lastName));
	}
	
	@PutMapping(value = { "/author/{id}/updateFirstName", "/author/{id}/updateFirstName/" })
	public boolean updateFirstName(@PathVariable("id") String id, @RequestParam String firstName) {
		return authorService.updateFirstName(id, firstName);
	}
	
	@PutMapping(value = { "/author/{id}/updateLastName", "/author/{id}/updateLastName/" })
	public boolean updateLastName(@PathVariable("id") String id, @RequestParam String lastName) {
		return authorService.updateLastName(id, lastName);
	}
	
	@PutMapping(value = { "/author/{id}/updateFullName", "/author/{id}/updateFullName/" })
	public boolean updateFullName(@PathVariable("id") String id, @RequestParam String firstName, @RequestParam String lastName) {
		return authorService.updateFullName(id, firstName, lastName);
	}
	
	@PutMapping(value = { "/author/{id}/addTitleByAuthorID", "/author/{id}/addTitleByAuthorID/" })
	public boolean addTitleByAuthorID(@PathVariable("id") String id, @RequestParam String titleID) {
		return authorService.addTitleByAuthorID(id, titleService.getTitleByTitleID(titleID));
	}
	
	@PutMapping(value = { "/author/{id}/removeTitleByAuthorID", "/author/{id}/removeTitleByAuthorID/" })
	public boolean removeTitleByAuthorID(@PathVariable("id") String id, @RequestParam String titleID) {
		return authorService.removeTitleByAuthorID(id, titleService.getTitleByTitleID(titleID));
	}
	
	@DeleteMapping(value = {"/author/{id}", "/author/{id}/"})
	public boolean deleteAuthorByAuthorID(@PathVariable("id") String id) throws IllegalArgumentException, NullPointerException {
		return authorService.deleteAuthorByAuthorID(id);
	}
	
	@DeleteMapping(value = {"/author/deleteAuthorsByFirstName", "/author/deleteAuthorsByFirstName/"})
	public boolean deleteAuthorsByFirstName(@RequestParam String firstName) throws IllegalArgumentException, NullPointerException {
		return authorService.deleteAuthorsByFirstName(firstName);
	}
	
	@DeleteMapping(value = {"/author/deleteAuthorsByLastName", "/author/deleteAuthorsByLastName/"})
	public boolean deleteAuthorsByLastName(@RequestParam String lastName) throws IllegalArgumentException, NullPointerException {
		return authorService.deleteAuthorsByFirstName(lastName);
	}
	
	@DeleteMapping(value = {"/author/deleteAuthorsByFirstNameAndLastName", "/author/deleteAuthorsByFirstNameAndLastName/"})
	public boolean deleteAuthorsByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) throws IllegalArgumentException, NullPointerException {
		return authorService.deleteAuthorsByFirstNameAndLastName(firstName,lastName);
	}
	
	
	@DeleteMapping(value = {"/author/deleteAuthorsByTitles", "/author/deleteAuthorsByTitles/"})
	public boolean deleteAuthorsByTitles(@RequestParam String titles) throws IllegalArgumentException, NullPointerException {
		List<Title> titleList = new ArrayList<>();
        List<String> seperatedAuthorStringList = Arrays.asList(titles.split(","));
        for (String s : seperatedAuthorStringList) {
        	titleList.add(titleService.getTitleByTitleID(s));
        }
		return authorService.deleteAuthorsByTitles(titleList);
	}
	
	
	@GetMapping(value = {"/author/isAuthorsExistsByFirstName","/title/isAuthorsExistsByFirstName/"}) 
	public boolean isAuthorsExistsByFirstName(@RequestParam String firstName) throws IllegalArgumentException, NullPointerException {
		return authorService.isAuthorsExistsByFirstName(firstName);
	}
	
	@GetMapping(value = {"/author/isAuthorsExistsByLastName","/title/isAuthorsExistsByLastName/"}) 
	public boolean isAuthorsExistsByLastName(@RequestParam String lastName) throws IllegalArgumentException, NullPointerException {
		return authorService.isAuthorsExistsByLastName(lastName);
	}
	

	@GetMapping(value = {"/author/isAuthorsExistsByFirstNameAndLastName","/title/isAuthorsExistsByFirstNameAndLastName/"}) 
	public boolean isAuthorsExistsByFirstNameAndLastName(@RequestParam String firstName, @RequestParam String lastName) throws IllegalArgumentException, NullPointerException {
		return authorService.isAuthorsExistsByFirstNameAndLastName(firstName, lastName);
	}
	
	@GetMapping(value = {"/author/isAuthorsExistsByAuthorID","/title/isAuthorsExistsByAuthorID/"}) 
	public boolean isAuthorsExistsByAuthorID(@RequestParam String authorID) throws IllegalArgumentException, NullPointerException {
		return authorService.isAuthorsExistsByAuthorID(authorID);
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

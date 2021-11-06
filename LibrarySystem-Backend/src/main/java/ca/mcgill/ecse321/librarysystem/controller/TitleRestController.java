package ca.mcgill.ecse321.librarysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.dto.AuthorDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.service.AuthorService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")
@RestController
public class TitleRestController {
	
	@Autowired
	private TitleService titleService;
	
	@Autowired
	private AuthorService authorService;
	
	@GetMapping(value = { "/titles", "/titles/"})
	public List<TitleDto> getAllTitles() {
		List<TitleDto> titleDtos = new ArrayList<>();
		for (Title title : titleService.getTitles()) {
			titleDtos.add(convertToTitleDto(title));
		}
		return titleDtos;
	}
	
	@GetMapping(value = { "/title/{id}", "/title/{id}/"})
	public TitleDto getTitleByTitleID(@PathVariable("id") String id) {
		return convertToTitleDto(titleService.getTitleByTitleID(id));
	}
	
	@GetMapping(value = { "/title/getByNameAndPubDate", "/title/getByNameAndPubDate/"})
	public TitleDto getTitleByNameAndPubDate(@RequestParam String name, @RequestParam String pubDate) throws IllegalArgumentException, NullPointerException
	{
		return convertToTitleDto(titleService.getTitleByNameAndPubDate(name, pubDate));
	}
	 
	@GetMapping(value = { "/title/authorID","/title/authorID/"})
	public List <TitleDto> getTitlesByAuthorID(@RequestParam String authorID) {
		return convertToTitlesDto(titleService.getTitlesByAuthorID(authorService.getAuthorByAuthorID(authorID)));
	}
	
//	@GetMapping(value = {})
//	public TitleDto getTitlesByAuthorIDs() {
//		return null; 
//	}
//	
//	@GetMapping(value = {})
//	public TitleDto getTitlesByItemBarcode() {
//		titleService.getTitlesByItemBarcode(null);
//		return null;
//	}
//	
//	@GetMapping(value = {})
//	public TitleDto getTitlesByItemBarcodes() {
//		titleService.getTitlesByItemBarcodes(null);
//		return null;
//	}
//	
//	@GetMapping(value = {})
//	public TitleDto getTitlesByName() {
//		titleService.getTitlesByName(null);
//		return null;
//	}
//	
//	@GetMapping(value = {})
//	public TitleDto getTitlesByPubDate() {
//		titleService.getTitlesByPubDate(null);
//		return null;
//	}
	
	private TitleDto convertToTitleDto(Title title) {
		if (title == null) throw new NullPointerException("Cannot find this Title");
		return new TitleDto(title.getName(),title.getPubDate(),convertToAuthorDto(title.getAuthor()));
	}
	
	
	private AuthorDto[] convertToAuthorDto(List<Author> authors) {
		if (authors == null || authors.size() == 0) throw new IllegalArgumentException("Cannot find these authors");
		AuthorDto[] arrayAuthors = new AuthorDto[authors.size()];
		for (int i = 0; i < authors.size(); i++) {
			Author current_author = authors.get(i);
			arrayAuthors[i] = new AuthorDto(current_author.getAuthorID(),current_author.getFirstName(),current_author.getLastName());
		}
 		return arrayAuthors;
	}
	
	private List <TitleDto> convertToTitlesDto(List<Title> titles) {
		if (titles == null || titles.size() == 0) throw new NullPointerException("Cannot find this Title");
		List <TitleDto> list_titles = new ArrayList<TitleDto>();
		for (int i = 0; i < titles.size(); i++) list_titles.add(convertToTitleDto(titles.get(i)));
		return list_titles; 
	} 
}

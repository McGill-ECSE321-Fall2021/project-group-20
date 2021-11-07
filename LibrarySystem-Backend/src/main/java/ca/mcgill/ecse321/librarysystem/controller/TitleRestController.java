package ca.mcgill.ecse321.librarysystem.controller;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.dto.AuthorDto;
import ca.mcgill.ecse321.librarysystem.dto.ItemDto;
import ca.mcgill.ecse321.librarysystem.dto.TitleDto;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;
import ca.mcgill.ecse321.librarysystem.service.AuthorService;
import ca.mcgill.ecse321.librarysystem.service.ItemService;
import ca.mcgill.ecse321.librarysystem.service.TitleService;

@CrossOrigin(origins = "*")
@RestController
public class TitleRestController {
	
	@Autowired
	private TitleService titleService;
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private ItemService itemService;
	
	@GetMapping(value = { "/titles", "/titles/"})
    public List<TitleDto> getAllTitles() {
        List<TitleDto> titleDtos = new ArrayList<>();
        for (Title title : titleService.getTitles()) {
            titleDtos.add(convertToTitleDto(title));
        }
        return titleDtos;
    }

    @GetMapping(value = { "/title/{id}", "/title/{id}/"})
    public TitleDto getTitleByTitleID(@PathVariable("id") String id) throws IllegalArgumentException, NullPointerException {
        return convertToTitleDto(titleService.getTitleByTitleID(id));
    }

    @GetMapping(value = { "/title/getByNameAndPubDate", "/title/getByNameAndPubDate/"})
    public TitleDto getTitleByNameAndPubDate(@RequestParam String name, @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") String pubDate)
            throws IllegalArgumentException, NullPointerException {
        return convertToTitleDto(titleService.getTitleByNameAndPubDate(name, pubDate));
    }

    @GetMapping(value = { "/titles/author/{id}","/titles/author/{id}/"})
    public List <TitleDto> getTitlesByAuthorID(@RequestParam String author) throws IllegalArgumentException, NullPointerException {
    	return convertToTitlesDto(titleService.getTitlesByAuthorID(authorService.getAuthorByAuthorID(author)));
    }

    @GetMapping(value = {"/titles/authors","/titles/authors/"})
    public List<TitleDto> getTitlesByAuthorIDs(@RequestParam String authors) {
        List<Author> authorList = new ArrayList<>();
        List<String> seperatedAuthorStringList = Arrays.asList(authors.split(","));
        for (String s : seperatedAuthorStringList) {
            authorList.add(authorService.getAuthorByAuthorID(s));
        }
        return convertToTitlesDto(titleService.getTitlesByAuthorIDs(authorList));
    }
	
	@GetMapping(value = {"/title/getTitleByItemBarcode","/title/getTitleByItemBarcode/"})
	public TitleDto getTitlesByItemBarcode(@RequestParam String itemBarCode) throws IllegalArgumentException, NullPointerException {
		return convertToTitleDto(titleService.getTitleByItemBarcode(itemService.getItemById(Long.parseLong(itemBarCode))));
	}
	
	@GetMapping(value = {"/titles/getTitlesByItemBarcodes","/titles/getTitlesByItemBarcodes/"})
	public List<TitleDto> getTitlesByItemBarcodes(@RequestParam String itemBarCodes) {
		List<Item> itemList = new ArrayList<>();
        List<String> seperatedItemStringList = Arrays.asList(itemBarCodes.split(","));
        for (String s : seperatedItemStringList) {
        	itemList.add(itemService.getItemById(Long.parseLong(s)));
        }
		return convertToTitlesDto(titleService.getTitlesByItemBarcodes(itemList));
	}
	
	@GetMapping(value = {"/titles/getTitlesByName","/titles/getTitlesByName/"})
	public List<TitleDto> getTitlesByName(@RequestParam String name) throws IllegalArgumentException, NullPointerException{
		return convertToTitlesDto(titleService.getTitlesByName(name));
	}
	
	@GetMapping(value = {"/titles/getTitlesByPubDate","/titles/getTitlesByPubDate/"})
	public List<TitleDto> getTitlesByPubDate(@RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") String pubDate) throws IllegalArgumentException, NullPointerException{
		return convertToTitlesDto(titleService.getTitlesByPubDate(pubDate));
	}
	
	@PostMapping(value = {"/title/createTitleByNameAndPubDateAndAuthors", "/title/createTitleByNameAndPubDateAndAuthors/"})
	public TitleDto createTitleByNameAndPubDateAndAuthors(@RequestParam String name, @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") String pubDate, @RequestParam String authors) 
			throws IllegalArgumentException, NullPointerException{
		
        List<String> seperatedAuthorStringList = Arrays.asList(authors.split(","));
        Author authorList [] = new Author[seperatedAuthorStringList.size()];
        
        for (int i =0; i < seperatedAuthorStringList.size(); i++) {
        	authorList[i] = authorService.getAuthorByAuthorID(seperatedAuthorStringList.get(i));
        }
        
		return convertToTitleDto(titleService.createTitle(name, pubDate, authorList));
	}
	
	
	@PutMapping(value = { "/title/{id}/updateName", "/title/{id}/updateName/" })
	public boolean updateName(@PathVariable("id") String id, @RequestParam String name) {
		return titleService.updateName(id, name);
	}
	
	@PutMapping(value = { "/title/{id}/updatePubDate", "/title/{id}/updatePubDate/" })
	public boolean updatePubDate(@PathVariable("id") String id, @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") String pubDate) {
		return titleService.updatePubDate(id, pubDate);
	}
	
	@PutMapping(value = { "/title/{id}/updateNameAndPubDate", "/title/{id}/updateNameAndPubDate/" })
	public boolean updateNameAndPubDate(@PathVariable("id") String id, @RequestParam String name, @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") String pubDate) {
		return titleService.updateNameAndPubDate(id, name, pubDate);
	}
	
	@PutMapping(value = { "/title/{id}/addAuthorToTitle", "/title/{id}/addAuthorToTitle/" })
	public boolean addAuthorToTitle(@PathVariable("id") String id, @RequestParam String author) {
		return titleService.addAuthorToTitle(id, authorService.getAuthorByAuthorID(author));
	}
	
	@PutMapping(value = { "/title/{id}/addAuthorsToTitle", "/title/{id}/addAuthorsToTitle/" })
	public boolean addAuthorsToTitle(@PathVariable("id") String id, @RequestParam String authors) {
		List<Author> authorList = new ArrayList<>();
        List<String> seperatedAuthorStringList = Arrays.asList(authors.split(","));
        for (String s : seperatedAuthorStringList) {
            authorList.add(authorService.getAuthorByAuthorID(s));
        }
		return titleService.addAuthorsToTitle(id, authorList);
	}
	
	@PutMapping(value = { "/title/{id}/removeAuthorFromTitle", "/title/{id}/removeAuthorFromTitle/" })
	public boolean removeAuthorFromTitle(@PathVariable("id") String id, @RequestParam String author) {
		return titleService.removeAuthorFromTitle(id, authorService.getAuthorByAuthorID(author));
	}
	
	@PutMapping(value = { "/title/{id}/removeAuthorsFromTitle", "/title/{id}/removeAuthorsFromTitle/" })
	public boolean removeAuthorsFromTitle(@PathVariable("id") String id, @RequestParam String authors) {
		List<Author> authorList = new ArrayList<>();
        List<String> seperatedAuthorStringList = Arrays.asList(authors.split(","));
        for (String s : seperatedAuthorStringList) {
            authorList.add(authorService.getAuthorByAuthorID(s));
        }
		return titleService.removeAuthorsFromTitle(id, authorList);
	}
	
	
	@DeleteMapping(value = {"/title/{id}", "/title/{id}/"})
	public boolean deleteTitleByTitleID(@PathVariable("id") String id) throws IllegalArgumentException, NullPointerException {
		return titleService.deleteTitleByTitleID(id);
	}
	
	@DeleteMapping(value = {"/title/deleteByNameAndPubDate", "/title/deleteByNameAndPubDate/"})
	public boolean deleteTitleByNameAndPubDate(@RequestParam String name, @RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") String pubDate) throws IllegalArgumentException, NullPointerException {
		return titleService.deleteTitleByNameAndPubDate(name, pubDate);
	}
	
	@DeleteMapping(value = {"/title/deleteByAuthorID", "/title/deleteByAuthorID/"})
	public boolean deleteTitleByAuthorID(@RequestParam String author) throws IllegalArgumentException, NullPointerException {
		return titleService.deleteTitlesByAuthorID(authorService.getAuthorByAuthorID(author));
	}
	
	@DeleteMapping(value = {"/title/deleteTitleByAuthorIDs", "/title/deleteTitleByAuthorIDs/"})
	public boolean deleteTitleByAuthorIDs(@RequestParam String authors) throws IllegalArgumentException, NullPointerException {
		List<Author> authorList = new ArrayList<>();
        List<String> seperatedAuthorStringList = Arrays.asList(authors.split(","));
        for (String s : seperatedAuthorStringList) {
            authorList.add(authorService.getAuthorByAuthorID(s));
        }
		return titleService.deleteTitlesByAuthorIDs(authorList);
	}
	
	
	@DeleteMapping(value = {"/title/deleteByItemBarCode", "/title/deleteByItemBarCode/"})
	public boolean deleteTitleByItemBarCode(String itemBarCode) throws IllegalArgumentException, NullPointerException {
		return titleService.deleteTitlesByItemBarcode((itemService.getItemById(Long.parseLong(itemBarCode))));
	}
	
	@DeleteMapping(value = {"/title/deleteByItemBarCodes", "/title/deleteByItemBarCodes/"})
	public boolean deleteTitleByItemBarCodes(String itemBarCodes) throws IllegalArgumentException, NullPointerException {
		List<Item> itemList = new ArrayList<>();
        List<String> seperatedItemStringList = Arrays.asList(itemBarCodes.split(","));
        for (String s : seperatedItemStringList) {
        	itemList.add(itemService.getItemById(Long.parseLong(s)));
        }
		return titleService.deleteTitlesByItemBarcodes(itemList);
	}
	
	@DeleteMapping(value = {"/title/deleteByName", "/title/deleteByName/"})
	public boolean deleteTitleByName(@RequestParam String name) 
			throws IllegalArgumentException, NullPointerException {
		return titleService.deleteTitlesByName(name);
	}
	
	@DeleteMapping(value = {"/title/deleteByPubDate", "/title/deleteByPubDate/"})
	public boolean deleteTitlesByPubDate(@RequestParam @DateTimeFormat(pattern="MM/dd/yyyy") String pubDate) 
			throws IllegalArgumentException, NullPointerException {
		return titleService.deleteTitlesByPubDate(pubDate);
	}
	
	
	//should it be GetMapping?
	@GetMapping(value = {"/title/isTitleExistsByTitleID","/title/isTitleExistsByTitleID/"}) 
	public boolean isTitleExistsByTitleID(@RequestParam String titleID) throws IllegalArgumentException, NullPointerException {
		return titleService.isTitleExistsByTitleID(titleID);
	}
	
	@GetMapping(value = {"/title/isTitleExistsByItem","/title/isTitleExistsByItem/"}) 
	public boolean isTitleExistsByItemBarCode(@RequestParam String itemBarCode) throws IllegalArgumentException, NullPointerException {
		
		return titleService.isTitleExistsByItem(itemService.getItemById(Long.parseLong(itemBarCode)));
	}
	
	@GetMapping(value = {"/title/isTitleExistsByNameAndPubDate","/title/isTitleExistsByNameAndPubDate/"}) 
	public boolean isTitleExistsByNameAndPubDate(@RequestParam String name, @RequestParam @DateTimeFormat String pubDate) throws IllegalArgumentException, NullPointerException {
		return titleService.isTitleExistsByNameAndPubDate(name, pubDate);
	}
	
	private TitleDto convertToTitleDto(Title title) throws IllegalArgumentException, NullPointerException {
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
	
//	private Author convertToDomainAuthorObject(AuthorDto author) {
//		List<Author> list_authors = authorService.getAuthors();
//		for (Author a : list_authors) {
//			if (a.getAuthorID().equals(author.getAuthorID()) 
//					&& a.getFirstName().equals(author.getFirstName()) 
//					&& a.getLastName().equals(author.getLastName()))
//				return a;
//		}
//		return null;
//	}
	
//	private Item convertToDomainItemObject(ItemDto itemDto) {
//		List<Item> list_items = itemService.getAllItems();
//		for (Item item : list_items) {
//			if (item.getItemBarcode() == itemDto.getItemBarcode())
//				return item;
//		}
//		return null;
//	}
	
}

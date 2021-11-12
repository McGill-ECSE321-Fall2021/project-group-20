package ca.mcgill.ecse321.librarysystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ca.mcgill.ecse321.librarysystem.dto.AuthorDto;
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
    public ResponseEntity getAllTitles() {
        List<TitleDto> titleDtos = new ArrayList<>();
		List<Title> titles = titleService.getTitles();
		if (titles.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any titles");
        for (Title title : titleService.getTitles()) {
            titleDtos.add(convertToTitleDto(title));
        }
        return new ResponseEntity<>(titleDtos, HttpStatus.OK);
    }

    @GetMapping(value = { "/title/{id}", "/title/{id}/"})
    public ResponseEntity getTitleByTitleID(@PathVariable("id") String id) {
		Title title;
		try {
			title = titleService.getTitleByTitleID(id);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
        return new ResponseEntity<>(convertToTitleDto(title), HttpStatus.OK);
    }

    @GetMapping(value = { "/title/getByNameAndPubDate", "/title/getByNameAndPubDate/"})
    public ResponseEntity getTitleByNameAndPubDate(@RequestParam String name, @RequestParam String pubDate) {
		if (!pubDate.contains("/")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format pubDate in MM/dd/yyyy");
		Title title;
		try {
			title = titleService.getTitleByNameAndPubDate(name, pubDate);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		return new ResponseEntity<>(convertToTitleDto(title), HttpStatus.OK);
    }

    @GetMapping(value = { "/titles/author/{id}","/titles/author/{id}/"})
    public ResponseEntity getTitlesByAuthorID(@RequestParam String author) {
		Author a;
		List<Title> titles;
		try {
			a = authorService.getAuthorByAuthorID(author);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		try {
			titles = titleService.getTitlesByAuthorID(a);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());

		}
		return new ResponseEntity<>(convertToTitlesDto(titles), HttpStatus.OK);
    }

    @GetMapping(value = {"/titles/authors","/titles/authors/"})
    public ResponseEntity getTitlesByAuthorIDs(@RequestParam String authors) {
        List<Author> authorList = new ArrayList<>();
        List<String> seperatedAuthorStringList = Arrays.asList(authors.split(","));
        for (String s : seperatedAuthorStringList) {
            try {
				authorList.add(authorService.getAuthorByAuthorID(s));
			} catch (IllegalArgumentException | NullPointerException msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
        }
		List<Title> titles;
		try {
			titles = titleService.getTitlesByAuthorIDs(authorList);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
        return new ResponseEntity<>(convertToTitlesDto(titles), HttpStatus.OK);
    }
	
	@GetMapping(value = {"/title/getTitleByItemBarcode","/title/getTitleByItemBarcode/"})
	public ResponseEntity getTitleByItemBarcode(@RequestParam String itemBarCode) {
		Item item;
		try {
			item = itemService.getItemById(Long.parseLong(itemBarCode));
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		Title title;
		try {
			title = titleService.getTitleByItemBarcode(item);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		return new ResponseEntity<>(convertToTitleDto(title), HttpStatus.OK);
	}
	
//	@GetMapping(value = {"/titles/getTitlesByItemBarcodes","/titles/getTitlesByItemBarcodes/"})
//	public ResponseEntity getTitlesByItemBarcodes(@RequestParam String itemBarCodes) {
//		List<Item> itemList = new ArrayList<>();
//        List<String> seperatedItemStringList = Arrays.asList(itemBarCodes.split(","));
//        for (String s : seperatedItemStringList) {
//			try {
//				itemList.add(itemService.getItemById(Long.parseLong(s)));
//			} catch (IllegalArgumentException | NullPointerException msg) {
//				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
//			}
//        }
//		List<Title> titles;
//		try {
//			titles = titleService.getTitlesByItemBarcodes(itemList);
//		} catch (IllegalArgumentException | NullPointerException msg) {
//			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
//		}
//		return new ResponseEntity<>(convertToTitlesDto(titles), HttpStatus.OK);
//	}
	
	@GetMapping(value = {"/titles/getTitlesByName","/titles/getTitlesByName/"})
	public ResponseEntity getTitlesByName(@RequestParam String name) {
		List<Title> titles;
		try {
			titles = titleService.getTitlesByName(name);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		return new ResponseEntity<>(convertToTitlesDto(titles), HttpStatus.OK);
	}
	
	@GetMapping(value = {"/titles/getTitlesByPubDate","/titles/getTitlesByPubDate/"})
	public ResponseEntity getTitlesByPubDate(@RequestParam String pubDate) {
		if (!pubDate.contains("/")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format pubDate in MM/dd/yyyy");
		List<Title> titles;
		try {
			titles = titleService.getTitlesByPubDate(pubDate);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		return new ResponseEntity<>(convertToTitlesDto(titles), HttpStatus.OK);
	}
	
	@PostMapping(value = {"/title/create", "/title/create/"})
	public ResponseEntity createTitleByNameAndPubDateAndAuthors(@RequestParam String name, @RequestParam String pubDate, @RequestParam String authors) {
		if (!pubDate.contains("/")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter date in mm/dd/yyyy format");
        List<String> seperatedAuthorStringList = Arrays.asList(authors.split(","));
        Author authorList [] = new Author[seperatedAuthorStringList.size()];
        
        for (int i =0; i < seperatedAuthorStringList.size(); i++) {
			try {
				authorList[i] = authorService.getAuthorByAuthorID(seperatedAuthorStringList.get(i));
			} catch (IllegalArgumentException | NullPointerException msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
        }
        Title title;
		try {
			title = titleService.createTitle(name, pubDate, authorList);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		return new ResponseEntity<>(convertToTitleDto(title), HttpStatus.OK);
	}
	
	
	@PutMapping(value = { "/title/{id}/updateName", "/title/{id}/updateName/" })
	public ResponseEntity updateName(@PathVariable("id") String id, @RequestParam String name) {
		boolean b;
		try {
			b = titleService.updateName(id, name);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Updated name successfully");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update name");
	}
	
	@PutMapping(value = { "/title/{id}/updatePubDate", "/title/{id}/updatePubDate/" })
	public ResponseEntity updatePubDate(@PathVariable("id") String id, @RequestParam String pubDate) {
		if (!pubDate.contains("/")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format pubDate in MM/dd/yyyy");
		boolean b;
		try {
			b = titleService.updatePubDate(id, pubDate);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Updated pubDate successfully");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update pubDate");
	}
	
	@PutMapping(value = { "/title/{id}/updateNameAndPubDate", "/title/{id}/updateNameAndPubDate/" })
	public ResponseEntity updateNameAndPubDate(@PathVariable("id") String id, @RequestParam String name, @RequestParam String pubDate) {
		if (!pubDate.contains("/")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format pubDate in MM/dd/yyyy");
		boolean b;
		try {
			b = titleService.updateNameAndPubDate(id, name, pubDate);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Updated name & pubDate successfully");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not update name & pubDate");
	}
	
	@PutMapping(value = { "/title/{id}/addAuthorToTitle", "/title/{id}/addAuthorToTitle/" })
	public ResponseEntity addAuthorToTitle(@PathVariable("id") String id, @RequestParam String author) {
		Author a;
		try {
			a = authorService.getAuthorByAuthorID(author);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		boolean b;
		try {
			b = titleService.addAuthorToTitle(id, a);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Added author successfully");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not add author");
	}
	
	@PutMapping(value = { "/title/{id}/addAuthorsToTitle", "/title/{id}/addAuthorsToTitle/" })
	public ResponseEntity addAuthorsToTitle(@PathVariable("id") String id, @RequestParam String authors) {
		List<Author> authorList = new ArrayList<>();
        List<String> seperatedAuthorStringList = Arrays.asList(authors.split(","));
        for (String s : seperatedAuthorStringList) {
			try {
				authorList.add(authorService.getAuthorByAuthorID(s));
			} catch (IllegalArgumentException | NullPointerException msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
        }
		boolean b;
		try {
			b = titleService.addAuthorsToTitle(id, authorList);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Added authors successfully");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not add authors");
	}
	
	@PutMapping(value = { "/title/{id}/removeAuthorFromTitle", "/title/{id}/removeAuthorFromTitle/" })
	public ResponseEntity removeAuthorFromTitle(@PathVariable("id") String id, @RequestParam String author) {
		Author a;
		try {
			a = authorService.getAuthorByAuthorID(author);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		boolean b;
		try {
			b = titleService.removeAuthorFromTitle(id, a);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Removed author successfully");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not remove author");
	}
	
	@PutMapping(value = { "/title/{id}/removeAuthorsFromTitle", "/title/{id}/removeAuthorsFromTitle/" })
	public ResponseEntity removeAuthorsFromTitle(@PathVariable("id") String id, @RequestParam String authors) {
		List<Author> authorList = new ArrayList<>();
		List<String> seperatedAuthorStringList = Arrays.asList(authors.split(","));
		for (String s : seperatedAuthorStringList) {
			try {
				authorList.add(authorService.getAuthorByAuthorID(s));
			} catch (IllegalArgumentException | NullPointerException msg) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
			}
		}
		boolean b;
		try {
			b = titleService.removeAuthorsFromTitle(id, authorList);
		} catch (IllegalArgumentException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Removed authors successfully");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not remove authors");
	}
	
	
	@DeleteMapping(value = {"/title/{id}", "/title/{id}/"})
	public ResponseEntity deleteTitleByTitleID(@PathVariable("id") String id) {
		boolean b;
		try {
			b = titleService.deleteTitleByTitleID(id);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Title has been deleted");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot delete title");
	}
	
	@DeleteMapping(value = {"/titles/deleteByName", "/titles/deleteByName/"})
	public ResponseEntity deleteTitlesByName(@RequestParam String name) {
		boolean b;
		try {
			b = titleService.deleteTitlesByName(name);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Titles have been deleted");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot delete titles");
	}
	
	@DeleteMapping(value = {"/titles/deleteByPubDate", "/titles/deleteByPubDate/"})
	public ResponseEntity deleteTitlesByPubDate(@RequestParam String pubDate) {
		if (!pubDate.contains("/")) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please format pubDate in MM/dd/yyyy");
		boolean b;
		try {
			b = titleService.deleteTitlesByPubDate(pubDate);
		} catch (IllegalArgumentException | NullPointerException msg) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
		}
		if (b) return ResponseEntity.status(HttpStatus.OK).body("Titles have been deleted");
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Cannot delete titles");
	}
	
	private TitleDto convertToTitleDto(Title title) throws IllegalArgumentException, NullPointerException {
		if (title == null) throw new NullPointerException("Cannot find this Title");
		return new TitleDto(title.getTitleID(), title.getName(),title.getPubDate(),convertToAuthorDto(title.getAuthor()));
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

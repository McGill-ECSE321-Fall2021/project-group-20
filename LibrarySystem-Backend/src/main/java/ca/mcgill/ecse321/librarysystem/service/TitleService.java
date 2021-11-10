package ca.mcgill.ecse321.librarysystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.TitleRepository;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;


@Service
public class TitleService {

    @Autowired
    TitleRepository titleRepository;

    @Transactional
    public Title createTitle() {
        Title title = new Title();
        titleRepository.save(title);
        return title;
    }

    @Transactional
    public Title createTitle(String name, String pubDate, Author... author) {
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
    	if (author == null || author.length == 0) throw new IllegalArgumentException("Please enter a list of valid authors"); 
        Title title = new Title(name,pubDate,author);
        titleRepository.save(title);
        return title;
    }

    @Transactional
    public List<Title> getTitles() {
        List<Title> titles = new ArrayList<>();
        for (Title t : titleRepository.findAll()) {
            titles.add(t);
        }
        return titles;
    }

    @Transactional
    public Title getTitleByTitleID(String titleID) {
    	if (titleID == null || titleID.length() == 0) throw new IllegalArgumentException("Please enter a valid titleID"); 
        Title title = titleRepository.findByTitleID(titleID);
        if (title == null) throw new NullPointerException("Title not found");
        return title;
    }

    @Transactional
    public List<Title> getTitlesByAuthorID(Author authorID) {
    	if (authorID == null) throw new IllegalArgumentException("Please enter a valid author"); 
        List<Title> titles = titleRepository.findByAuthor(authorID);
        if (titles.size() == 0) throw new IllegalArgumentException("Titles not found");
        return titles;
    }

    @Transactional 
    public List<Title> getTitlesByAuthorIDs(List<Author> authorID) {
    	if (authorID == null || authorID.size() == 0) throw new IllegalArgumentException("Please enter a list of valid authors"); 
        List<Title> titles = titleRepository.findByAuthorIn(authorID);
        if (titles.size() == 0) throw new IllegalArgumentException("Titles not found");
        return titles;
    }

    @Transactional
    public Title getTitleByItemBarcode(Item itemBarcode) {
    	if (itemBarcode == null) throw new IllegalArgumentException("Please enter a valid item");
        Title title = titleRepository.findByItem(itemBarcode);
        if (title == null) throw new NullPointerException("Title not found");
        return title;
    }

    @Transactional
    public List<Title> getTitlesByItemBarcodes(List<Item> itemBarcode) {
    	if (itemBarcode == null || itemBarcode.size() == 0) throw new IllegalArgumentException("Please enter a valid list of items");
        List<Title> titles = titleRepository.findByItemIn(itemBarcode);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        return titles;
    }

    @Transactional
    public List<Title> getTitlesByName(String name) {
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
        List<Title> titles = titleRepository.findByName(name);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        return titles;
    }

    @Transactional
    public List<Title> getTitlesByPubDate(String pubDate) {
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
        List<Title> titles = titleRepository.findByPubDate(pubDate);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        return titles;
    }

    @Transactional
    public Title getTitleByNameAndPubDate(String name, String pubDate) {
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
        Title title = titleRepository.findByNameAndPubDate(name, pubDate);
        if (title == null) throw new NullPointerException("Title not found");
        return title;
    }
    
    @Transactional
    public boolean updateName(String titleID, String name) {
    	Title title = getTitleByTitleID(titleID);
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
    	if (title.setName(name)) {
            titleRepository.save(title);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean updatePubDate(String titleID, String pubDate) {
    	Title title = getTitleByTitleID(titleID);
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
    	if (title.setPubDate(pubDate)) {
            titleRepository.save(title);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean updateNameAndPubDate(String titleID, String name, String pubDate) {
    	Title title = getTitleByTitleID(titleID);
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
    	if (title.setName(name) && title.setPubDate(pubDate)) {
            titleRepository.save(title);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean addAuthorToTitle(String titleID, Author authorID) {
    	Title title = getTitleByTitleID(titleID);
    	if (authorID == null) throw new IllegalArgumentException("Please enter a valid author"); 
    	if (title.addAuthor(authorID)) {
            titleRepository.save(title);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean addAuthorsToTitle(String titleID, List<Author> authorID) {
    	Title title = getTitleByTitleID(titleID);
    	if (authorID == null || authorID.size() == 0) throw new IllegalArgumentException("Please enter a list of valid authors"); 
    	boolean isAdded = true;
    	for (Author author : authorID) isAdded = isAdded && title.addAuthor(author);
    	if (isAdded) {
            titleRepository.save(title);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean removeAuthorFromTitle(String titleID, Author authorID) {
    	Title title = getTitleByTitleID(titleID);
    	if (authorID == null) throw new IllegalArgumentException("Please enter a valid author"); 
    	if (title.removeAuthor(authorID)) {
            titleRepository.save(title);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean removeAuthorsFromTitle(String titleID, List<Author> authorID) {
    	Title title = getTitleByTitleID(titleID);
    	if (authorID == null || authorID.size() == 0) throw new IllegalArgumentException("Please enter a list of valid authors"); 
    	boolean isRemoved = true;
    	for (Author author : authorID) isRemoved = isRemoved && title.removeAuthor(author);
    	if (isRemoved) {
            titleRepository.save(title);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean deleteTitleByTitleID(String titleID) {
    	if (titleID == null || titleID.length() == 0) throw new IllegalArgumentException("Please enter a valid titleID"); 
        Title title = titleRepository.findByTitleID(titleID);
        if (title == null) throw new NullPointerException("Title not found");
        titleRepository.delete(title);
        title.delete();
        Title test = titleRepository.findByTitleID(titleID);
        return (test == null);
    }

    @Transactional
    public boolean deleteTitlesByName(String name) {
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
        List<Title> titles = titleRepository.findByName(name);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        for (Title title : titles) {
        	titleRepository.delete(title);
            title.delete();
        }
        List<Title> test = titleRepository.findByName(name);
        return (test.size() == 0);
    }

    @Transactional
    public boolean deleteTitlesByPubDate(String pubDate) {
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
        List<Title> titles = titleRepository.findByPubDate(pubDate);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        for (Title title : titles) {
        	titleRepository.delete(title);
            title.delete();
        }
        List<Title> test = titleRepository.findByPubDate(pubDate);
        return (test.size() == 0);
    }


    @Transactional
    public boolean isTitleExistsByTitleID(String titleID) {
    	if (titleID == null || titleID.length() == 0) throw new IllegalArgumentException("Please enter a valid titleID"); 
        return (titleRepository.existsByTitleID(titleID));
    }

    @Transactional
    public boolean isTitleExistsByItem(Item itemBarcode) {
    	if (itemBarcode == null) throw new IllegalArgumentException("Please enter a valid item"); 
        return (titleRepository.existsByItem(itemBarcode));
    }

    @Transactional
    public boolean isTitleExistsByNameAndPubDate(String name, String pubDate) {
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
        return (titleRepository.existsByNameAndPubDate(name, pubDate));
    }

}

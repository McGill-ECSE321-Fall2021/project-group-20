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
    
    /*
    Creates a new Title using the default constructor
     */
    @Transactional
    public Title createTitle() {
        Title title = new Title();
        titleRepository.save(title);
        return title;
    }
    
    /*
    Creates a new Title using title name, publish date and list of authors 
     */
    @Transactional
    public Title createTitle(String name, String pubDate, Author... author) {
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
    	if (author == null || author.length == 0) throw new IllegalArgumentException("Please enter a list of valid authors"); 
        Title title = new Title(name,pubDate,author);
        titleRepository.save(title);
        return title;
    }
    
    /*
    Get all the titles from the database
     */
    @Transactional
    public List<Title> getTitles() {
        List<Title> titles = new ArrayList<>();
        for (Title t : titleRepository.findAll()) {
            titles.add(t);
        }
        return titles;
    }
    
    /*
    Get title given title id
     */
    @Transactional
    public Title getTitleByTitleID(String titleID) {
    	if (titleID == null || titleID.length() == 0) throw new IllegalArgumentException("Please enter a valid title id"); 
        Title title = titleRepository.findByTitleID(titleID);
        if (title == null) throw new NullPointerException("Title not found");
        return title;
    }
    
    /*
    Get list of titles given an author object
     */
    @Transactional
    public List<Title> getTitlesByAuthorID(Author authorID) {
    	if (authorID == null) throw new IllegalArgumentException("Please enter a valid author"); 
        List<Title> titles = titleRepository.findByAuthor(authorID);
        if (titles.size() == 0) throw new IllegalArgumentException("Titles not found");
        return titles;
    }
    
    /*
    Get list of titles given a list of author objects 
     */
    @Transactional 
    public List<Title> getTitlesByAuthorIDs(List<Author> authorID) {
    	if (authorID == null || authorID.size() == 0) throw new IllegalArgumentException("Please enter a list of valid authors"); 
        List<Title> titles = titleRepository.findByAuthorIn(authorID);
        if (titles.size() == 0) throw new IllegalArgumentException("Titles not found");
        return titles;
    }
    
    /*
    Get list of titles given item object
     */
    @Transactional
    public Title getTitleByItemBarcode(Item itemBarcode) {
    	if (itemBarcode == null) throw new IllegalArgumentException("Please enter a valid item");
        Title title = titleRepository.findByItem(itemBarcode);
        if (title == null) throw new NullPointerException("Title not found");
        return title;
    }
    
    /*unnecessary*/
//    @Transactional
//    public List<Title> getTitlesByItemBarcodes(List<Item> itemBarcode) {
//    	if (itemBarcode == null || itemBarcode.size() == 0) throw new IllegalArgumentException("Please enter a valid list of items");
//        List<Title> titles = titleRepository.findByItemIn(itemBarcode);
//        if (titles.size() == 0) throw new NullPointerException("Titles not found");
//        return titles;
//    }
    
    
    /*
    Get list of titles given title name 
     */
    @Transactional
    public List<Title> getTitlesByName(String name) {
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
        List<Title> titles = titleRepository.findByName(name);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        return titles;
    }
    
    /*
    Get list of titles given publish date
     */
    @Transactional
    public List<Title> getTitlesByPubDate(String pubDate) {
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
        List<Title> titles = titleRepository.findByPubDate(pubDate);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        return titles;
    }
    
    /*
    Get list of titles given title name and publish date
     */
    @Transactional
    public Title getTitleByNameAndPubDate(String name, String pubDate) {
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
        Title title = titleRepository.findByNameAndPubDate(name, pubDate);
        if (title == null) throw new NullPointerException("Title not found");
        return title;
    }
    
    /*
    Updates a title name of a title given its title id and the new title name
     */
    @Transactional
    public boolean updateName(String titleID, String name) {
    	if (titleID == null || titleID.length() == 0) throw new IllegalArgumentException("Please enter a valid title id");
    	Title title = getTitleByTitleID(titleID);
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
    	if (title.setName(name)) {
            titleRepository.save(title);
            return true;
        }
        return false;
    }
    
    /*
    Updates a publish date of a title given its title id and new publish date
     */
    @Transactional
    public boolean updatePubDate(String titleID, String pubDate) {
    	if (titleID == null || titleID.length() == 0) throw new IllegalArgumentException("Please enter a valid title id");
    	Title title = getTitleByTitleID(titleID);
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
    	if (title.setPubDate(pubDate)) {
            titleRepository.save(title);
            return true;
        }
        return false;
    }
    
    /*
    Updates a title name and publish date of a title given its title id and new name and new publish date
     */
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
    
    /*
    Adds an author to a title given its title id and the new author
     */
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
    
    /*
    Adds a list of authors to a title given its title and list of new authors
     */
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
    
    /*
    Removes an author from a title given its title and the new author
     */
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
    
    /*
    Removes a list of authors from a title given its title and list of new authors
     */
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
    
    /*
    Deletes the title given his id
     */
    @Transactional
    public boolean deleteTitleByTitleID(String titleID) {
    	if (titleID == null || titleID.length() == 0) throw new IllegalArgumentException("Please enter a valid title id"); 
        Title title = titleRepository.findByTitleID(titleID);
        if (title == null) throw new NullPointerException("Title not found");
        titleRepository.delete(title);
        title.delete();
        Title test = titleRepository.findByTitleID(titleID);
        return (test == null);
    }
    
    /*
    Deletes the titles with the same given name
     */
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
    
    /*
    Deletes the titles with the same given publish date
     */
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
    
    /*
    Determines if a title exists given a title id
     */
    @Transactional
    public boolean isTitleExistsByTitleID(String titleID) {
    	if (titleID == null || titleID.length() == 0) throw new IllegalArgumentException("Please enter a valid title id"); 
        return (titleRepository.existsByTitleID(titleID));
    }
    
    /*
    Determines if titles exist given an item object
     */
    @Transactional
    public boolean isTitleExistsByItem(Item itemBarcode) {
    	if (itemBarcode == null) throw new IllegalArgumentException("Please enter a valid item"); 
        return (titleRepository.existsByItem(itemBarcode));
    }
    
    /*
    Determines if titles exist given a title name and a publish date
     */
    @Transactional
    public boolean isTitleExistsByNameAndPubDate(String name, String pubDate) {
    	if (name == null || name.length() == 0) throw new IllegalArgumentException("Please enter a valid title name");
    	if (pubDate == null || pubDate.length() == 0) throw new IllegalArgumentException("Please enter a valid publish date");
        return (titleRepository.existsByNameAndPubDate(name, pubDate));
    }

}

package ca.mcgill.ecse321.librarysystem.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.AuthorRepository;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Title;


@Service
public class AuthorService {

    @Autowired
    AuthorRepository authorRepository;
    
    
    /*
    Creates a new Author using the default constructor
     */
    @Transactional
    public Author createAuthor() {
        Author author = new Author();
        authorRepository.save(author);
        return author;
    } 
    
    /*
    Creates a new Author using first and last name
     */
    @Transactional
    public Author createAuthor(String firstName, String lastName) {
    	if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        Author author = new Author(firstName, lastName);
        authorRepository.save(author);
        return author; 
    }

    /*
    Creates a new Author using id and first and last names
     */
    @Transactional
    public Author createAuthor(String authorID, String firstName, String lastName) {
    	if (authorID == null || authorID.length() == 0) throw new IllegalArgumentException("Please enter a valid author id"); 
    	if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        Author author = new Author(authorID, firstName, lastName);
        authorRepository.save(author);
        return author;
    }
    
    /*
    Get Author by author id
     */
    @Transactional
    public Author getAuthorByAuthorID(String authorID) {
    	if (authorID == null || authorID.length() == 0) throw new IllegalArgumentException("Please enter a valid author id"); 
        Author author = authorRepository.findByAuthorID(authorID);
        if (author == null) throw new NullPointerException("Author not found");
        return author;
    }
    
    /*
    Get all the Authors that have the same fist name 
     */
    @Transactional
    public List<Author> getAuthorsByFirstName(String firstName) {
    	if (firstName == null || firstName.length() == 0) throw new IllegalArgumentException("Please enter a valid first name");
        List<Author> authors = authorRepository.findByFirstName(firstName);
        if (authors.size() == 0) throw new IllegalArgumentException("Authors not found");
        return authors;
    }
    
    /*
    Get all the Authors that have the same last name 
     */
    @Transactional
    public List<Author> getAuthorsByLastName(String lastName) {
    	if (lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid last name");
        List<Author> authors = authorRepository.findByLastName(lastName);
        if (authors.size() == 0) throw new IllegalArgumentException("Authors not found");
        return authors;
    }
    
    /*
    Get all the Authors that have the same fist and last name 
     */
    @Transactional
    public List<Author> getAuthorsByFirstNameAndLastName(String firstName, String lastName) {
    	if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        List<Author> authors = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (authors.size() == 0) throw new IllegalArgumentException("Authors not found");
        return authors;
    }
    
    /*
    Get all the Authors that have the given titles
     */
    @Transactional
    public List<Author> getAuthorsByTitles(List<Title> titles) {
    	if (titles == null || titles.size() == 0) throw new IllegalArgumentException("Please enter a valid list of titles");
        List<Author> authors = authorRepository.findByTitlesIn(titles);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        return authors;
    }
    
    /*
    Updates an author's first name given his id and the new first name
     */
    @Transactional
    public boolean updateFirstName(String authorID, String firstName) {
    	Author author = getAuthorByAuthorID(authorID);
    	if (firstName == null || firstName.length() == 0) throw new IllegalArgumentException("Please enter a valid first name");
    	if (author.setFirstName(firstName)) {
            authorRepository.save(author);
            return true;
        }
        return false;
    }
    
    /*
    Updates an author's last name given his id and the new last name
     */
    @Transactional
    public boolean updateLastName(String authorID, String lastName) {
    	Author author = getAuthorByAuthorID(authorID);
    	if (lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid last name");
    	if (author.setLastName(lastName)) {
            authorRepository.save(author);
            return true;
        }
        return false;
    }
    
    /*
    Updates an author's first and last name given his author id, the new first name and the new last name
     */
    @Transactional
    public boolean updateFullName(String authorID, String firstName, String lastName) {
    	Author author = getAuthorByAuthorID(authorID);
    	if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
    	if (author.setFirstName(firstName) && author.setLastName(lastName)) {
            authorRepository.save(author);
            return true;
        }
        return false;
    }
    
    /*
    Deletes the author given its id
     */
    @Transactional
    public boolean deleteAuthorByAuthorID(String authorID) {
    	if (authorID == null || authorID.length() == 0) throw new IllegalArgumentException("Please enter a valid author id"); 
        Author author = authorRepository.findByAuthorID(authorID);
        if (author == null) throw new NullPointerException("Author not found");
        authorRepository.delete(author);
        author.delete();
        Author test = authorRepository.findByAuthorID(authorID);
        return (test == null);
    }
    
    /*
    Deletes the authors having the given first name 
     */
    @Transactional
    public boolean deleteAuthorsByFirstName(String firstName) {
    	if (firstName == null || firstName.length() == 0) throw new IllegalArgumentException("Please enter a valid first name");
        List<Author> authors = authorRepository.findByFirstName(firstName);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        for (Author author : authors) {
        	authorRepository.delete(author);
        	author.delete();   
        }
        List<Author> test = authorRepository.findByFirstName(firstName);
        return (test.size() == 0);
    }
    
    /*
    Deletes the authors having the given last name 
     */
    @Transactional
    public boolean deleteAuthorsByLastName(String lastName) {
    	if (lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid last name");
        List<Author> authors = authorRepository.findByLastName(lastName);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        for (Author author : authors) {
            authorRepository.delete(author);
            author.delete();
        }
        List<Author> test = authorRepository.findByLastName(lastName);
        return (test.size() == 0);
    }
    
    /*
    Deletes the authors with the first and last name given
     */
    @Transactional
    public boolean deleteAuthorsByFirstNameAndLastName(String firstName, String lastName) {
    	if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        List<Author> authors = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        for (Author author : authors) {
        	authorRepository.delete(author);
            author.delete();
        }
        List<Author> test = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        return (test.size() == 0);
    }
    
    /*
    Deletes all the authors that have titles in given list of titles
     */
    @Transactional
    public boolean deleteAuthorsByTitles(List<Title> titles) {
    	if (titles == null || titles.size() == 0) throw new IllegalArgumentException("Please enter a valid list of titles");
        List<Author> authors = authorRepository.findByTitlesIn(titles);
        if (authors.size() == 0) throw new IllegalArgumentException("Authors not found");
        for (Author author : authors) {
        	authorRepository.delete(author);
            author.delete();
        }
        List<Author> test = authorRepository.findByTitlesIn(titles);
        return (test.size() == 0);
    }
    
    /*
    Determines if an author exists given a first name
     */
    @Transactional
    public boolean isAuthorsExistsByFirstName(String firstName) {
    	if (firstName == null || firstName.length() == 0) throw new IllegalArgumentException("Please enter a valid first name");
        return (authorRepository.existsByFirstName(firstName));
    }
    
    /*
    Determines if an author exists given a last name
     */
    @Transactional
    public boolean isAuthorsExistsByLastName(String lastName) {
    	if (lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid last name");
        return (authorRepository.existsByLastName(lastName));
    }
    
    /*
    Determines if an author exists given a first and last name
     */
    @Transactional
    public boolean isAuthorsExistsByFirstNameAndLastName(String firstName, String lastName) {
    	if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        return (authorRepository.existsByFirstNameAndLastName(firstName, lastName));
    }
    
    /*
    Determines if an author exists given an author id
     */
    @Transactional
    public boolean isAuthorsExistsByAuthorID(String authorID) {
    	if (authorID == null || authorID.length() == 0) throw new IllegalArgumentException("Please enter a valid author id"); 
        return (authorRepository.existsByAuthorID(authorID));
    }
    
    /*
    Get all authors from the database
     */
    @Transactional 
    public List<Author> getAuthors() {
        List<Author> authors = new ArrayList<>();
        for (Author a : authorRepository.findAll()) {
            authors.add(a);
        }
        return authors;
    }
}
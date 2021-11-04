package ca.mcgill.ecse321.librarysystem.service;

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

    @Transactional
    public Author createAuthor() {
        Author author = new Author();
        authorRepository.save(author);
        return author;
    }

    @Transactional
    public Author createAuthor(String firstName, String lastName) {
    	if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        Author author = new Author(firstName, lastName);
        authorRepository.save(author);
        return author; 
    }

//	I don't think we need this method because when we save author to the database, the id is auto-generated
    @Transactional
    public Author createAuthor(String authorID, String firstName, String lastName) {
    	if (authorID == null || authorID.length() == 0) throw new IllegalArgumentException("Please enter a valid authorID"); 
    	if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        Author author = new Author(authorID, firstName, lastName);
        authorRepository.save(author);
        return author;
    }

    @Transactional
    public Author getAuthorByAuthorID(String authorID) {
    	if (authorID == null || authorID.length() == 0) throw new IllegalArgumentException("Please enter a valid authorID"); 
        Author author = authorRepository.findByAuthorID(authorID);
        if (author == null) throw new NullPointerException("Author not found");
        return author;
    }

    @Transactional
    public List<Author> getAuthorsByFirstName(String firstName) {
    	if (firstName == null || firstName.length() == 0) throw new IllegalArgumentException("Please enter a valid first name");
        List<Author> authors = authorRepository.findByFirstName(firstName);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        return authors;
    }

    @Transactional
    public List<Author> getAuthorsByLastName(String lastName) {
    	if (lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid last name");
        List<Author> authors = authorRepository.findByLastName(lastName);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        return authors;
    }

    @Transactional
    public List<Author> getAuthorsByFirstNameAndLastName(String firstName, String lastName) {
    	if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        List<Author> authors = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        return authors;
    }


    @Transactional
    public List<Author> getAuthorsByTitles(List<Title> titles) {
    	if (titles == null || titles.size() == 0) throw new IllegalArgumentException("Please enter a valid list of titles");
        List<Author> authors = authorRepository.findByTitlesIn(titles);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        return authors;
    }
    
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
    
    @Transactional
    public boolean addTitleByAuthor(String authorID, Title title) {
    	Author author = getAuthorByAuthorID(authorID);
    	if (title == null) throw new NullPointerException("Please enter a valid title");
    	if (author.addTitle(title)) {
            authorRepository.save(author);
            return true;
        }
        return false;
    }
    
    @Transactional
    public boolean removeTitleByAuthor(String authorID, Title title) {
    	Author author = getAuthorByAuthorID(authorID);
    	if (title == null) throw new NullPointerException("Please enter a valid title");
    	if (author.removeTitle(title)) {
            authorRepository.save(author);
            return true;
        }
        return false;
    }


    @Transactional
    public boolean deleteAuthorByAuthorID(String authorID) {
    	if (authorID == null || authorID.length() == 0) throw new IllegalArgumentException("Please enter a valid authorID"); 
        Author author = authorRepository.findByAuthorID(authorID);
        if (author == null) throw new NullPointerException("Author not found");
        authorRepository.delete(author);
        author.delete();
        Author test = authorRepository.findByAuthorID(authorID);
        return (test == null);
    }

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


    @Transactional
    public boolean deleteAuthorsByTitles(List<Title> titles) {
        List<Author> authors = authorRepository.findByTitlesIn(titles);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        for (Author author : authors) {
        	authorRepository.delete(author);
            author.delete();
        }
        List<Author> test = authorRepository.findByTitlesIn(titles);
        return (test.size() == 0);
    }


    @Transactional
    public boolean isAuthorsExistsByFirstName(String firstName) {
    	if (firstName == null || firstName.length() == 0) throw new IllegalArgumentException("Please enter a valid first name");
        return (authorRepository.existsByFirstName(firstName));
    }

    @Transactional
    public boolean isAuthorsExistsByLastName(String lastName) {
    	if (lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid last name");
        return (authorRepository.existsByLastName(lastName));
    }

    @Transactional
    public boolean isAuthorsExistsByFirstNameAndLastName(String firstName, String lastName) {
    	if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        return (authorRepository.existsByFirstNameAndLastName(firstName, lastName));
    }

    @Transactional
    public boolean isAuthorsExistsByAuthorID(String authorID) {
    	if (authorID == null || authorID.length() == 0) throw new IllegalArgumentException("Please enter a valid authorID"); 
        return (authorRepository.existsByAuthorID(authorID));
    }

}
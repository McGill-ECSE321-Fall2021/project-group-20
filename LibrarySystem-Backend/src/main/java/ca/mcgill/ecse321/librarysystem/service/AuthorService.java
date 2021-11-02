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
    public boolean deleteAuthorByAuthorID(String authorID) {
    	if (authorID == null || authorID.length() == 0) throw new IllegalArgumentException("Please enter a valid authorID"); 
        Author author = authorRepository.findByAuthorID(authorID);
        if (author == null) throw new NullPointerException("Author not found");
        author.delete();
        authorRepository.delete(author);
        Author test = authorRepository.findByAuthorID(authorID);
        return (test == null);
    }

    @Transactional
    public boolean deleteAuthorsByFirstName(String firstName) {
    	if (firstName == null || firstName.length() == 0) throw new IllegalArgumentException("Please enter a valid first name");
        List<Author> authors = authorRepository.findByFirstName(firstName);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        for (Author author : authors) {
            author.delete();
            authorRepository.delete(author);
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
            author.delete();
            authorRepository.delete(author);
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
            author.delete();
            authorRepository.delete(author);
        }
        List<Author> test = authorRepository.findByFirstNameAndLastName(firstName, lastName);
        return (test.size() == 0);
    }


    @Transactional
    public boolean deleteAuthorsByTitles(List<Title> titles) {
        List<Author> authors = authorRepository.findByTitlesIn(titles);
        if (authors.size() == 0) throw new NullPointerException("Authors not found");
        for (Author author : authors) {
            author.delete();
            authorRepository.delete(author);
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
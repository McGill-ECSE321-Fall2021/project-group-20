package ca.mcgill.ecse321.librarysystem.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.User;


public interface UserRepository extends CrudRepository<User, Integer> {
	
	/* Find user by LibraryCardID */
	User findUserByLibraryCardID(Integer libraryCardID);
	
	/* Find list of users by value of isOnlineAcc */
	List<User> findUserByIsOnlineAcc(boolean isOnlineAcc);
	
	/* Find user by username */
	User findUserByUsername(String username);
	
	/* Find user by email */
	User findUserByEmail(String email);
	
	/* Find list of users by firstName */
	List<User> findUserByFirstName(String firstName);
	
	/* Find list of users by lastName */
	List<User> findUserByLastName(String lastName);
	
	/* Find list of users by firstName and lastName */
	List<User> findUserByFirstNameAndLastName(String firstName, String lastName);
	
	/* Find list of users by value of isVerified */
	List<User> findUserByIsVerified(boolean isVerified);
	
	/* Find list of users by number of demeritPts */
	List<User> findUserByDemeritPts(int demeritPts);
	
	/* Find list of users by address */
	List<User> findUserByAddress(Address address);
	
	/* Find list of users by librarySystem */
	List<User> findUserByLibrarySystem(LibrarySystem librarySystem);
	
	/* Find user by a userbooking */
	User findUserByUserbooking(Booking userbooking);
	
	/* Check if user exists by a given libraryCardID */
	boolean existsByLibraryCardID(Integer libraryCardID);
	
	/* Check if user exists by a given username */
	boolean existsByUsername(String username);
	
	/* Check if user exists by a given email */
	boolean existsByEmail(String email);
	
	/* Check if user exists by a given userbooking */
	boolean existsByUserbooking(Booking userbooking);
	
	/* Check if user exists by a given first and last name */
	boolean existsByFirstNameAndLastName(String firstName, String lastName);
}
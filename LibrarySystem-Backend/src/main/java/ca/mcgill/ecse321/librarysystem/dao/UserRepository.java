package ca.mcgill.ecse321.librarysystem.dao;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import ca.mcgill.ecse321.librarysystem.model.User;


public interface UserRepository extends CrudRepository<User, Integer> {
	
	User findUserByLibraryCardID(Integer libraryCardID);
	List<User> findUserByIsOnlineAcc(boolean isOnlineAcc);
	User findUserByUsername(String username);
	User findUserByEmail(String email);
	List<User> findUserByFirstName(String firstName);
	List<User> findUserByLastName(String lastName);
	List<User> findUserByFirstNameAndLastName(String firstName, String lastName);
	List<User> findUserByIsVerified(boolean isVerified);
	List<User> findUserByDemeritPts(int demeritPts);
	List<User> findUserByAddress(Address address);
	List<User> findUserByLibrarySystem(LibrarySystem librarySystem);
	User findUserByUserbooking(Booking userbooking);
}
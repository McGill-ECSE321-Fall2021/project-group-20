package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(String firstName, String lastName, Address address) {
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (address == null) throw new IllegalArgumentException("Please enter a valid address");
        Customer newCustomer = new Customer(false, firstName, lastName, true, 0, address);
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    @Transactional
    public Customer createOnlineCustomer(String firstName, String lastName, String email, String username, String password, Address address) {
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (email == null || email.length() == 0 || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (password == null || password.length() < 8) throw new IllegalArgumentException("Please enter a password that is at least 8 characters long");
        if (address == null) throw new IllegalArgumentException("Please enter a valid address");
        Customer newOnlineCustomer = new Customer(true, firstName, lastName, false, 0, address);
        newOnlineCustomer.setEmail(email);
        newOnlineCustomer.setUsername(username);
        newOnlineCustomer.setPassword(password);
        customerRepository.save(newOnlineCustomer);
        return newOnlineCustomer;
    }

    @Transactional
    public boolean validateCustomerByEmail(String email) {
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        return customer.setIsVerified(true);
    }

    @Transactional
    public boolean validateCustomerByUsername(String username) {
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        return customer.setIsVerified(true);
    }

    @Transactional
    public boolean validateCustomerByID(int libraryCardID) {
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        return customer.setIsVerified(true);
    }

    @Transactional
    public Customer getCustomer(int libraryCardID) {
        return (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
    }

    @Transactional
    public Customer getCustomerByUsername(String username) {
        return (Customer) customerRepository.findUserByUsername(username);
    }

    @Transactional
    public Customer getCustomerByEmail(String email) {
        return (Customer) customerRepository.findUserByEmail(email);
    }

    // Add methods for finding lists

    @Transactional
    public boolean convertLocalToOnline(int libraryCardID, String username, String password, String email) {
        if (libraryCardID == 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (password == null || password.length() < 8) throw new IllegalArgumentException("Please enter a password that is at least 8 characters long");
        if (email == null || email.length() == 0 || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (!customer.setUsername(username)) return false;
        if (!customer.setPassword(password)) return false;
        if (!customer.setEmail(email)) return false;
        return customer.setIsOnlineAcc(true);
    }

    @Transactional
    public boolean updateUsername(int libraryCardID, String newUsername) {
        if (libraryCardID == 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (newUsername == null || newUsername.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        return customer.setUsername(newUsername);
    }

    @Transactional
    public boolean updateUsername(String oldUsername, String newUsername) {
        if (oldUsername == null || oldUsername.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (newUsername == null || newUsername.length() == 0) throw new IllegalArgumentException("Please enter a valid new username");
        Customer customer = (Customer) customerRepository.findUserByUsername(oldUsername);
        return customer.setUsername(newUsername);
    }

    @Transactional
    public boolean changePassword(int libraryCardID, String oldPass, String newPass) {
        if (libraryCardID == 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (oldPass == null || oldPass.length() < 8) throw new IllegalArgumentException("Please enter your password that is at least 8 characters long");
        if (newPass == null || newPass.length() < 8) throw new IllegalArgumentException("Please enter a new password that is at least 8 characters long");
        if (oldPass.equals(newPass)) throw new IllegalArgumentException("Your new password cannot be the same as the old password");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        return customer.setPassword(newPass);
    }

    @Transactional
    public boolean changePassword(String username, String oldPass, String newPass) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (oldPass == null || oldPass.length() < 8) throw new IllegalArgumentException("Please enter your password that is at least 8 characters long");
        if (newPass == null || newPass.length() < 8) throw new IllegalArgumentException("Please enter a new password that is at least 8 characters long");
        if (oldPass.equals(newPass)) throw new IllegalArgumentException("Your new password cannot be the same as the old password");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        return customer.setPassword(newPass);
    }

    @Transactional
    public boolean changeEmail(int libraryCardID, String email) {
        if (libraryCardID == 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (email == null || email.length() == 0 || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        return customer.setEmail(email);
    }

    @Transactional
    public boolean changeEmail(String username, String email) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (email == null || email.length() == 0 || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        return customer.setEmail(email);
    }

    @Transactional
    public boolean changeName(int libraryCardID, String firstName, String lastName) {
        if (libraryCardID == 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (!customer.setFirstName(firstName)) return false;
        if (!customer.setLastName(lastName)) return false;
        return true;
    }

    @Transactional
    public boolean changeName(String username, String firstName, String lastName) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (!customer.setFirstName(firstName)) return false;
        if (!customer.setLastName(lastName)) return false;
        return true;
    }
}

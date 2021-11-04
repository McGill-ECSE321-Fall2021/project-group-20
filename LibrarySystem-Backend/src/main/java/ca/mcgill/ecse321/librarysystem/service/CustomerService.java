package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.AddressRepository;
import ca.mcgill.ecse321.librarysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.librarysystem.dao.LibrarySystemRepository;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private LibrarySystemRepository librarySystemRepository;

    /*
    Creates a new customer locally (by staff)
     */
    @Transactional
    public Customer createCustomer(String firstName, String lastName, String civic, String street, String city, String postalCode, String province, String country) {
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
        postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        Address a = new Address(civic, street, city, postalCode, province, country);
        addressRepository.save(a);
        Customer newCustomer = new Customer(false, false, firstName, lastName, true, 0, a);
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    /*
    Creates a new online customer (Done by user itself).  Will need to be verified
     */
    @Transactional
    public Customer createOnlineCustomer(String firstName, String lastName, String email, String username, String password, String civic, String street, String city, String postalCode, String province, String country) {
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (password == null || password.length() < 8) throw new IllegalArgumentException("Please enter a password that is at least 8 characters long");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 || postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        if(customerRepository.existsByEmail(email)) throw new IllegalArgumentException("Email in use, please enter a new email");
        if (customerRepository.existsByUsername(username)) throw new IllegalArgumentException("Username in use, please enter a new username");
        Address a = new Address(civic, street, city, postalCode, province, country);
        addressRepository.save(a);
        Customer newOnlineCustomer = new Customer(true, true, firstName, lastName, false, 0, a);
        newOnlineCustomer.setEmail(email);
        newOnlineCustomer.setUsername(username);
        newOnlineCustomer.setPassword(password);
        customerRepository.save(newOnlineCustomer);
        LibrarySystem librarySystem = librarySystemRepository.findLibrarySystemByUsers(newOnlineCustomer);
        if (!a.getCity().equalsIgnoreCase(librarySystem.getBusinessaddress().getCity()) || !a.getCountry().equalsIgnoreCase(librarySystem.getBusinessaddress().getCountry()) || !a.getProvince().equalsIgnoreCase(librarySystem.getBusinessaddress().getProvince())) {
            newOnlineCustomer.setOutstandingBalance(newOnlineCustomer.getOutstandingBalance() + 50);
            System.out.println("Note: you will have to pay " + newOnlineCustomer.getOutstandingBalance() + " before you can start using the Library");
            customerRepository.save(newOnlineCustomer);
        }
        return newOnlineCustomer;
    }

    @Transactional
    public boolean modifyOutstandingBalance(int libraryCardID, int toModify) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer == null) throw new NullPointerException("Cannot find Customer with this ID");
        if (customer.setOutstandingBalance(customer.getOutstandingBalance() + toModify)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean modifyOutstandingBalance(String username, int toModify) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (customer == null) throw new NullPointerException("Cannot find Customer with this username");
        if (customer.setOutstandingBalance(customer.getOutstandingBalance() + toModify)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean modifyOutstandingBalanceByEmail(String email, int toModify) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (customer == null) throw new NullPointerException("Cannot find Customer with this email");
        if (customer.setOutstandingBalance(customer.getOutstandingBalance() + toModify)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean login(int libraryCardID, String password) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer == null || !customer.getIsOnlineAcc()) throw new NullPointerException("Cannot find Online Customer with this ID");
        if (!customer.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password!");
        if (customer.setIsLoggedIn(true)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean login(String username, String password) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (customer == null || !customer.getIsOnlineAcc()) throw new NullPointerException("Cannot find Online Customer with this ID");
        if (!customer.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password!");
        if (customer.setIsLoggedIn(true)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean loginByEmail(String email, String password) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (customer == null || !customer.getIsOnlineAcc()) throw new NullPointerException("Cannot find Online Customer with this ID");
        if (!customer.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password!");
        if (customer.setIsLoggedIn(true)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean logout(int libraryCardID) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer == null || !customer.getIsOnlineAcc()) throw new NullPointerException("Cannot find Online Customer with this ID");
        if (customer.setIsLoggedIn(false)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean logout(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (customer == null || !customer.getIsOnlineAcc()) throw new NullPointerException("Cannot find Online Customer with this ID");
        if (customer.setIsLoggedIn(false)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean logoutByEmail(String email) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (customer == null || !customer.getIsOnlineAcc()) throw new NullPointerException("Cannot find Online Customer with this ID");
        if (customer.setIsLoggedIn(false)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Deletes a Customer by a given libraryCardID
     */
    @Transactional
    public boolean deleteCustomerByID(int libraryCardID) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer == null) throw new NullPointerException("Cannot find Customer matching this libraryCardID");
        addressRepository.delete(customer.getAddress());
        customer.delete();
        customerRepository.delete(customer);
        return (!customerRepository.existsByLibraryCardID(libraryCardID));
    }

    /*
    Deletes a Customer by a given email
     */
    @Transactional
    public boolean deleteCustomerByEmail(String email) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (customer == null) throw new NullPointerException("Cannot find Customer matching this Email");
        addressRepository.delete(customer.getAddress());
        customer.delete();
        customerRepository.delete(customer);
        return (!customerRepository.existsByEmail(email));
    }

    /*
    Deletes a Customer by a given username
     */
    @Transactional
    public boolean deleteCustomerByUsername(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (customer == null) throw new NullPointerException("Cannot find Customer matching this Username");
        addressRepository.delete(customer.getAddress());
        customer.delete();
        customerRepository.delete(customer);
        return (!customerRepository.existsByUsername(username));
    }

    /*
    Validates a Customer by his email
     */
    @Transactional
    public boolean validateCustomerByEmail(String email) {
        if (email == null || email.length() == 0) throw new IllegalArgumentException("Please enter a valid email");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (customer == null) throw new NullPointerException("Cannot find Customer with given email");
        if (customer.getOutstandingBalance() != 0) throw new IllegalStateException("Please ensure Customer pays outstanding balance before validating the account");
        return customer.setIsVerified(true);
    }

    /*
    Validates a Customer by his Username
     */
    @Transactional
    public boolean validateCustomerByUsername(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (customer == null) throw new NullPointerException("Cannot find Customer with given username");
        if (customer.getOutstandingBalance() != 0) throw new IllegalStateException("Please ensure Customer pays outstanding balance before validating the account");
        return customer.setIsVerified(true);
    }

    /*
    Validates a Customer by his ID
     */
    @Transactional
    public boolean validateCustomerByID(int libraryCardID) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer == null) throw new NullPointerException("Cannot find Customer with given ID");
        if (customer.getOutstandingBalance() != 0) throw new IllegalStateException("Please ensure Customer pays outstanding balance before validating the account");
        return customer.setIsVerified(true);
    }

    /*
    Returns a Customer account based on the given ID
     */
    @Transactional
    public Customer getCustomer(int libraryCardID) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        return (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
    }

    /*
    Returns a Customer account based on given Username
     */
    @Transactional
    public Customer getCustomer(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        return (Customer) customerRepository.findUserByUsername(username);
    }

    @Transactional
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (User u: customerRepository.findAll()) {
            customers.add((Customer) u);
        }
        return customers;
    }

    /*
    Returns a Customer account based on given Email
     */
    @Transactional
    public Customer getCustomerByEmail(String email) {
        if (email == null || email.length() == 0) throw new IllegalArgumentException("Please enter a valid email");
        return (Customer) customerRepository.findUserByEmail(email);
    }

    /*
    Returns a list of Customers based on first name
     */
    @Transactional
    public List<Customer> getCustomersByFirstName(String firstName) {
        if (firstName == null || firstName.length() == 0) throw new IllegalArgumentException("Please enter a valid firstName");
        return convertListToCustomer(customerRepository.findUserByFirstName(firstName));
    }

    /*
    Returns a list of Customers based on last name
     */
    @Transactional
    public List<Customer> getCustomersByLastName(String lastName) {
        if (lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid firstName");
        return convertListToCustomer(customerRepository.findUserByLastName(lastName));
    }

    /*
    Returns a list of customers based on first and last names
     */
    @Transactional
    public List<Customer> getCustomersByFirstAndLastName(String firstName, String lastName) {
        if (firstName == null || firstName.length() == 0 || lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        return convertListToCustomer(customerRepository.findUserByFirstNameAndLastName(firstName, lastName));
    }

    @Transactional
    public List<Customer> getCustomersByDemeritPts(int demeritPts) {
        if (demeritPts < 0) throw new IllegalArgumentException("Please enter a positive demeritPts");
        return convertListToCustomer(customerRepository.findUserByDemeritPts(demeritPts));
    }

    @Transactional
    public List<Customer> getCustomersByAddress(Address address) {
        if (address == null) throw new IllegalArgumentException("Please enter a valid address");
        return convertListToCustomer(customerRepository.findUserByAddress(address));
    }

    @Transactional
    public List<Customer> getCustomersByAddress(String civic, String street, String city, String postalCode, String province, String country) {
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        List<Address> addressList = addressRepository.findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(civic, street, city, postalCode, province, country);
        if (addressList.isEmpty()) throw new NullPointerException("Cannot find address in system");
        return getCustomersByAddress(addressList.get(0));
    }

    /*
    Returns a list of Customers based on their online account status
     */
    @Transactional
    public List<Customer> getCustomersByOnlineStatus(boolean isOnlineAcc) {
        return convertListToCustomer(customerRepository.findUserByIsOnlineAcc(isOnlineAcc));
    }

    /*
    Returns a list of Customers based on their verification status
     */
    @Transactional
    public List<Customer> getCustomersByVerifiedStatus(boolean isVerified) {
        return convertListToCustomer(customerRepository.findUserByIsVerified(isVerified));
    }

    /*
    Converts a local account to an online one if username and email not in use
     */
    @Transactional
    public boolean convertLocalToOnline(int libraryCardID, String username, String password, String email) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (password == null || password.length() < 8) throw new IllegalArgumentException("Please enter a password that is at least 8 characters long");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if(!customerRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find a Customer with given ID");
        if (customerRepository.existsByUsername(username)) throw new IllegalArgumentException("Username in use, please select a different one");
        if (customerRepository.existsByEmail(email)) throw new IllegalArgumentException("Email in use, please select a different one");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (!customer.setUsername(username)) return false;
        if (!customer.setPassword(password)) return false;
        if (!customer.setEmail(email)) return false;
        if (customer.setIsOnlineAcc(true)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Updates username if new one is valid
     */
    @Transactional
    public boolean updateUsername(int libraryCardID, String newUsername) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (newUsername == null || newUsername.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (!customerRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find Customer with given ID");
        if (customerRepository.existsByUsername(newUsername)) throw new IllegalArgumentException("New username in use, please choose a valid one");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer.setUsername(newUsername)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Updates username if new one is valid
     */
    @Transactional
    public boolean updateUsername(String oldUsername, String newUsername) {
        if (oldUsername == null || oldUsername.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (newUsername == null || newUsername.length() == 0) throw new IllegalArgumentException("Please enter a valid new username");
        if (!customerRepository.existsByUsername(oldUsername)) throw new NullPointerException("Cannot find Customer with given Username");
        if (customerRepository.existsByUsername(newUsername)) throw new IllegalArgumentException("New username in use, please choose a valid one");
        Customer customer = (Customer) customerRepository.findUserByUsername(oldUsername);
        if (customer.setUsername(newUsername)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Updates username if new one is valid
     */
    @Transactional
    public boolean updateUsernameByEmail(String email, String newUsername) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (newUsername == null || newUsername.length() == 0) throw new IllegalArgumentException("Please enter a valid new username");
        if (!customerRepository.existsByEmail(email)) throw new NullPointerException("Cannot find a Customer with given Email");
        if (customerRepository.existsByUsername(newUsername)) throw new IllegalArgumentException("New username in use, please choose a valid one");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (customer.setUsername(newUsername)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Changes password if correct current password and different new password
     */
    @Transactional
    public boolean changePassword(int libraryCardID, String oldPass, String newPass) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (oldPass == null || oldPass.length() < 8) throw new IllegalArgumentException("Please enter your password that is at least 8 characters long");
        if (newPass == null || newPass.length() < 8) throw new IllegalArgumentException("Please enter a new password that is at least 8 characters long");
        if (oldPass.equals(newPass)) throw new IllegalArgumentException("Your new password cannot be the same as the old password");
        if (!customerRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find Customer with given ID");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (!customer.getPassword().equals(oldPass)) throw new IllegalArgumentException("Entered current password is invalid");
        if (customer.setPassword(newPass)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Changes password if correct current password and different new password
     */
    @Transactional
    public boolean changePassword(String username, String oldPass, String newPass) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (oldPass == null || oldPass.length() < 8) throw new IllegalArgumentException("Please enter your password that is at least 8 characters long");
        if (newPass == null || newPass.length() < 8) throw new IllegalArgumentException("Please enter a new password that is at least 8 characters long");
        if (oldPass.equals(newPass)) throw new IllegalArgumentException("Your new password cannot be the same as the old password");
        if (!customerRepository.existsByUsername(username)) throw new NullPointerException("Cannot find Customer with given username");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (!customer.getPassword().equals(oldPass)) throw new IllegalArgumentException("Entered current password is invalid");
        if (customer.setPassword(newPass)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Changes password if correct current password and different new password
     */
    @Transactional
    public boolean changePasswordByEmail(String email, String oldPass, String newPass) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (oldPass == null || oldPass.length() < 8) throw new IllegalArgumentException("Please enter your password that is at least 8 characters long");
        if (newPass == null || newPass.length() < 8) throw new IllegalArgumentException("Please enter a new password that is at least 8 characters long");
        if (oldPass.equals(newPass)) throw new IllegalArgumentException("Your new password cannot be the same as the old password");
        if (!customerRepository.existsByEmail(email)) throw new NullPointerException("Cannot find Customer with given email");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (!customer.getPassword().equals(oldPass)) throw new IllegalArgumentException("Entered current password is invalid");
        if (customer.setPassword(newPass)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Changes email if new one is available
     */
    @Transactional
    public boolean changeEmail(int libraryCardID, String email) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (!customerRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find Customer with given ID");
        if (customerRepository.existsByEmail(email)) throw new IllegalArgumentException("Email in use, please enter a different one");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer.setEmail(email)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Changes email if new one is available
     */
    @Transactional
    public boolean changeEmail(String username, String email) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (!customerRepository.existsByUsername(username)) throw new NullPointerException("Cannot find Customer with given username");
        if (customerRepository.existsByEmail(email)) throw new IllegalArgumentException("Email in use, please enter a different one");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (customer.setEmail(email)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Changes email if new one is available
     */
    @Transactional
    public boolean changeEmailByEmail(String oldEmail, String newEmail) {
        if (oldEmail == null || !oldEmail.contains("@")) throw new IllegalArgumentException("Please enter a valid current email");
        if (newEmail == null || !newEmail.contains("@")) throw new IllegalArgumentException("Please enter a valid new email");
        if (!customerRepository.existsByEmail(oldEmail)) throw new NullPointerException("Cannot find Customer with given email");
        if (customerRepository.existsByEmail(newEmail)) throw new IllegalArgumentException("Email in use, please enter a different one");
        Customer customer = (Customer) customerRepository.findUserByEmail(oldEmail);
        if (customer.setEmail(newEmail)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    /*
    Changes a Customer's first and last name
     */
    @Transactional
    public boolean changeName(int libraryCardID, String firstName, String lastName) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (!customerRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find Customer with given ID");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer.setFirstName(firstName)) {
            if (customer.setLastName(lastName)) {
                customerRepository.save(customer);
                return true;
            }
            return false;
        }
        return false;
    }

    /*
    Changes a Customer's first and last name
     */
    @Transactional
    public boolean changeName(String username, String firstName, String lastName) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (!customerRepository.existsByUsername(username)) throw new NullPointerException("Cannot find Customer with given username");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (customer.setFirstName(firstName)) {
            if (customer.setLastName(lastName)) {
                customerRepository.save(customer);
                return true;
            }
            return false;
        }
        return false;
    }

    /*
    Changes a Customer's first and last name
     */
    @Transactional
    public boolean changeNameByEmail(String email, String firstName, String lastName) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (!customerRepository.existsByEmail(email)) throw new NullPointerException("Cannot find Customer with given email");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (customer.setFirstName(firstName)) {
            if (customer.setLastName(lastName)) {
                customerRepository.save(customer);
                return true;
            }
            return false;
        }
        return false;
    }

    @Transactional
    public boolean changeAddress(int libraryCardID, String civic, String street, String city, String postalCode, String province, String country) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        if (!customerRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find Customer with given ID");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        Address a = customer.getAddress();
        if (!a.setCivicNumber(civic)) return false;
        if (!a.setStreet(street)) return false;
        if (!a.setCity(city)) return false;
        if (!a.setPostalCode(postalCode)) return false;
        if (!a.setProvince(province)) return false;
        if (!a.setCountry(country)) return false;
        customer.setAddress(a);
        addressRepository.save(a);
        customerRepository.save(customer);
        return true;
    }

    @Transactional
    public boolean changeAddress(String username, String civic, String street, String city, String postalCode, String province, String country) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        if (!customerRepository.existsByUsername(username)) throw new NullPointerException("Cannot find Customer with given username");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        Address a = customer.getAddress();
        if (!a.setCivicNumber(civic)) return false;
        if (!a.setStreet(street)) return false;
        if (!a.setCity(city)) return false;
        if (!a.setPostalCode(postalCode)) return false;
        if (!a.setProvince(province)) return false;
        if (!a.setCountry(country)) return false;
        customer.setAddress(a);
        addressRepository.save(a);
        customerRepository.save(customer);
        return true;
    }

    @Transactional
    public boolean changeAddressByEmail(String email, String civic, String street, String city, String postalCode, String province, String country) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        if (!customerRepository.existsByEmail(email)) throw new NullPointerException("Cannot find Customer with given email");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        Address a = customer.getAddress();
        if (!a.setCivicNumber(civic)) return false;
        if (!a.setStreet(street)) return false;
        if (!a.setCity(city)) return false;
        if (!a.setPostalCode(postalCode)) return false;
        if (!a.setProvince(province)) return false;
        if (!a.setCountry(country)) return false;
        customer.setAddress(a);
        addressRepository.save(a);
        customerRepository.save(customer);
        return true;
    }

    @Transactional
    public boolean addBooking(String username, Booking booking) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (booking == null) throw new IllegalArgumentException("Please enter a valid booking");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (customer == null) throw new NullPointerException("Cannot find Employee with this username");
        if (customer.addUserbooking(booking)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean addBookingByEmail(String email, Booking booking) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (booking == null) throw new IllegalArgumentException("Please enter a valid booking");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (customer == null) throw new NullPointerException("Cannot find Employee with this username");
        if (customer.addUserbooking(booking)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public Customer changeDemeritPts(int libraryCardID, int toAdd) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer == null) throw new NullPointerException("Cannot find Customer with this ID");
        if (customer.setDemeritPts(customer.getDemeritPts() + toAdd)) {
            customerRepository.save(customer);
            return customer;
        }
        return null;
    }

    @Transactional
    public Customer changeDemeritPts(String username, int toAdd) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (customer == null) throw new NullPointerException("Cannot find Customer with this username");
        if (customer.setDemeritPts(customer.getDemeritPts() + toAdd)) {
            customerRepository.save(customer);
            return customer;
        }
        return null;
    }

    @Transactional
    public Customer changeDemeritPtsEmail(String email, int toAdd) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (customer == null) throw new NullPointerException("Cannot find Customer with this ID");
        if (customer.setDemeritPts(customer.getDemeritPts() + toAdd)) {
            customerRepository.save(customer);
            return customer;
        }
        return null;
    }

    // Helper methods for converting User to Customer
    private List<Customer> convertListToCustomer(List<User> users) {
        List<Customer> customers = new ArrayList<>();

        for (User user:users) {
            customers.add((Customer) user);
        }

        return customers;
    }
}

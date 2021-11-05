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
    public Customer login(String username, String password) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Customer customer = (Customer) customerRepository.findUserByUsername(username);
        if (customer == null || !customer.getIsOnlineAcc()) throw new NullPointerException("Cannot find Online Customer with this ID");
        if (!customer.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password!");
        if (customer.setIsLoggedIn(true)) {
            customerRepository.save(customer);
            return customer;
        }
        return null;
    }

    @Transactional
    public Customer loginByEmail(String email, String password) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Customer customer = (Customer) customerRepository.findUserByEmail(email);
        if (customer == null || !customer.getIsOnlineAcc()) throw new NullPointerException("Cannot find Online Customer with this ID");
        if (!customer.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password!");
        if (customer.setIsLoggedIn(true)) {
            customerRepository.save(customer);
            return customer;
        }
        return null;
    }

    @Transactional
    public Customer login(int libraryCardID, String password) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer == null || !customer.getIsOnlineAcc()) throw new NullPointerException("Cannot find Online Customer with this ID");
        if (!customer.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password!");
        if (customer.setIsLoggedIn(true)) {
            customerRepository.save(customer);
            return customer;
        }
        return null;
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

    @Transactional
    public List<Customer> getCustomersByLoggedIn(boolean isLoggedIn) {
        return convertListToCustomer(customerRepository.findByIsLogged(isLoggedIn));
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
        if (customer.getIsOnlineAcc()) throw new IllegalArgumentException("Account is already fully configured");
        if (!customer.setUsername(username)) return false;
        if (!customer.setPassword(password)) return false;
        if (!customer.setEmail(email)) return false;
        if (customer.setIsOnlineAcc(true)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateOnlineInfo(int libraryCardID, String username, String password, String email) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (password == null || password.length() < 8) throw new IllegalArgumentException("Please enter a valid password of min 8 chars long");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (!customerRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find Customer with given ID");
        if (customerRepository.existsByUsername(username)) throw new IllegalArgumentException("New username in use, please choose a valid one");
        if (customerRepository.existsByEmail(email)) throw new IllegalArgumentException("New email in use, please choose another");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer.setUsername(username) && customer.setPassword(password) && customer.setEmail(email)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changeInfo(int libraryCardID,  String firstName, String lastName, String civic, String street, String city, String postalCode, String province, String country) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        if (!customerRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find Customer with given ID");        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer.setFirstName(firstName) && customer.setLastName(lastName)) {
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
        return false;
    }

    @Transactional
    public boolean addBooking(int libraryCardID, Booking booking) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        if (booking == null) throw new IllegalArgumentException("Please enter a valid booking");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer == null) throw new NullPointerException("Cannot find Employee with this username");
        if (customer.addUserbooking(booking)) {
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteBooking(int libraryCardID, Booking booking) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        if (booking == null) throw new IllegalArgumentException("Please enter a valid booking");
        Customer customer = (Customer) customerRepository.findUserByLibraryCardID(libraryCardID);
        if (customer == null) throw new NullPointerException("Cannot find Employee with this username");
        if (customer.removeUserbooking(booking)) {
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

    // Helper methods for converting User to Customer
    private List<Customer> convertListToCustomer(List<User> users) {
        List<Customer> customers = new ArrayList<>();

        for (User user:users) {
            customers.add((Customer) user);
        }

        return customers;
    }
}

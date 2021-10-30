package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.CustomerRepository;
import ca.mcgill.ecse321.librarysystem.dao.LibrarySystemRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Customer;
import ca.mcgill.ecse321.librarysystem.model.LibrarySystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LibrarySystemService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    LibrarySystemRepository librarySystemRepository;

    // MISSING METHOD TO GET LIBRARYSYSTEM!
    //@Transactional
    //public Customer createOnlineCustomer(String firstName, String lastName, Address address) {
    //    Customer customer = new Customer(true, firstName, lastName, false, 0, address, )
    //}
}

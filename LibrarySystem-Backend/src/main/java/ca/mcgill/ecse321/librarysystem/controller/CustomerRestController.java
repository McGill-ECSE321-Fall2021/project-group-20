package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.dto.CustomerDto;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Customer;
import ca.mcgill.ecse321.librarysystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = { "/customers", "/customers/" })
    public ResponseEntity getAllCustomers() {
        List<CustomerDto> customerDtos = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        try {
            customers = customerService.getAllCustomers();
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Customer c : customers) {
            customerDtos.add(convertToDto(c));
        }
        if (customerDtos.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any Customers in System");
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping(value = { "/customer/{id}", "/customer/{id}/" })
    public ResponseEntity getCustomerByID(@PathVariable("id") String id) {
        Customer customer;
        try {
            customer = customerService.getCustomer(Integer.parseInt(id));
        } catch (IllegalArgumentException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (customer == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find Customer with id #" + id);
        return new ResponseEntity<>(convertToDto(customer), HttpStatus.OK);
    }

    @PostMapping(value = { "/customer/createLocal", "/customer/createLocal/" })
    public ResponseEntity createLocalCustomer(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String civic,
                                      @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                      @RequestParam String province, @RequestParam String country) {
        Customer customer = null;
        try {
            customer = customerService.createCustomer(firstname, lastname, civic, street, city, postalCode, province, country);
        } catch (IllegalArgumentException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        return new ResponseEntity<>(convertToDto(customer), HttpStatus.OK);
    }

    @PostMapping(value = { "/customer/create", "/customer/create/"})
    public ResponseEntity createOnlineCustomer(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String username, @RequestParam String password,
                                      @RequestParam String civic, @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                      @RequestParam String province, @RequestParam String country) throws IllegalArgumentException {
        Customer customer = null;
        try {
            customer = customerService.createOnlineCustomer(firstName, lastName, email, username, password, civic, street, city, postalCode, province, country);
        } catch (IllegalArgumentException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        return new ResponseEntity<>(convertToDto(customer), HttpStatus.OK);
    }

    @DeleteMapping(value = { "/customer/{id}", "/customer/{id}/" })
    public ResponseEntity deleteCustomer(@PathVariable("id") String id){
        Customer c;
        try {
            c = customerService.deleteCustomerByID(Integer.parseInt(id));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (c == null) return ResponseEntity.status(HttpStatus.OK).body("Customer with ID " + id + " has been successfully deleted");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not delete customer");
    }

    @PutMapping(value = { "/customer/login", "/customer/login/"})
    public ResponseEntity loginCustomer(@RequestParam String name, @RequestParam String password) {
        Customer c;
        if (name.contains("@")) {
            try {
                c = customerService.loginByEmail(name, password);
            } catch (IllegalArgumentException | NullPointerException msg) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
            }
            if (c == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not log in");
            return new ResponseEntity<>(convertToDto(c), HttpStatus.OK);
        }
        try {
            c = customerService.login(name, password);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (c == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not log in");
        return new ResponseEntity<>(convertToDto(c), HttpStatus.OK);
    }

    @PutMapping(value = {"/customer/login/{id}", "/customer/login/{id}/"})
    public ResponseEntity login(@PathVariable("id") String id, @RequestParam String password) {
        Customer c;
        try {
            c = customerService.login(Integer.parseInt(id), password);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (c == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not log in");
        return new ResponseEntity<>(convertToDto(c), HttpStatus.OK);
    }

    @PutMapping(value = { "/customer/logout/{id}", "/customer/logout/{id}/"})
    public ResponseEntity logoutID(@PathVariable("id") String id) {
        boolean b;
        try {
            b = customerService.logout(Integer.parseInt(id));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (b) return ResponseEntity.status(HttpStatus.OK).body("Logged out");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Did not log out");
    }

    @PutMapping(value = { "/customer/logout", "/customer/logout/"})
    public ResponseEntity logout(@RequestParam String name) {
        boolean b;
        if (name.contains("@")) {
            try {
                b = customerService.logoutByEmail(name);
            } catch (IllegalArgumentException | NullPointerException msg) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
            }
            if (b) return ResponseEntity.status(HttpStatus.OK).body("Logged out");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Did not log out");
        }
        try {
            b = customerService.logout(name);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (b) return ResponseEntity.status(HttpStatus.OK).body("Logged out");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Did not log out");
    }

    @GetMapping(value = { "/customers/name", "/customers/name/" })
    public ResponseEntity getCustomersByFirstAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        List<CustomerDto> cDto = new ArrayList<>();
        List<Customer> c = new ArrayList<>();
        try {
            c = customerService.getCustomersByFirstAndLastName(firstName, lastName);
        } catch (IllegalArgumentException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }

        for (Customer cust : c) {
            cDto.add(convertToDto(cust));
        }
        if (cDto.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No customers match this request");
        return new ResponseEntity<>(cDto, HttpStatus.OK);
    }

    @GetMapping(value = { "/customers/getByAddress", "/customers/getByAddress/" })
    public ResponseEntity getCustomersByAddress(@RequestParam String civic, @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                                   @RequestParam String province, @RequestParam String country) throws IllegalArgumentException, NullPointerException {
        List<CustomerDto> customerDtos = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        try {
            customers = customerService.getCustomersByAddress(civic, street, city, postalCode, province, country);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Customer c : customers) {
            customerDtos.add(convertToDto(c));
        }
        if (customerDtos.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find Customers matching this Address");
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping(value = { "/customer/username", "/customer/username/" })
    public ResponseEntity getCustomerByUsername(@RequestParam String username) {
        Customer c;
        try {
            c = customerService.getCustomer(username);
        } catch (IllegalArgumentException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (c == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find Customer with this username");
        return new ResponseEntity<>(convertToDto(c), HttpStatus.OK);
    }

    @GetMapping(value = { "/customer/email", "customer/email/" })
    public ResponseEntity getCustomerByEmail(@RequestParam String email) {
        Customer c;
        try {
            c = customerService.getCustomerByEmail(email);
        } catch (IllegalArgumentException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (c == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find Customer with this email");
        return new ResponseEntity<>(convertToDto(c), HttpStatus.OK);
    }

    @GetMapping(value = { "/customers/demeritPts/{pts}", "/customers/demeritPts/{pts}/" })
    public ResponseEntity getByDemeritPts(@PathVariable("pts") String pts) {
        List<CustomerDto> customerDtos = new ArrayList<>();
        List<Customer> customers;
        try {
            customers = customerService.getCustomersByDemeritPts(Integer.parseInt(pts));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Customer c : customers) {
            customerDtos.add(convertToDto(c));
        }
        if (customerDtos.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find Customers that have " + pts + " demerit points");
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @GetMapping(value = { "/customers/online", "/customers/online/" })
    public ResponseEntity getOnlineCustomers() {
        List<CustomerDto> customerDtos = new ArrayList<>();
        List<Customer> customers;
        try {
            customers = customerService.getCustomersByLoggedIn(true);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Customer c : customers) {
            customerDtos.add(convertToDto(c));
        }
        if (customerDtos.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find online customers");
        return new ResponseEntity<>(customerDtos, HttpStatus.OK);
    }

    @PutMapping(value = { "/customer/balance/{id}", "/customer/balance/{id}/" })
    public ResponseEntity modifyBalance(@PathVariable("id") String id, @RequestParam String toModify) {
        Customer c = null;
        try {
            c = customerService.modifyOutstandingBalance(Integer.parseInt(id), Integer.parseInt(toModify));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        return new ResponseEntity<>(convertToDto(c), HttpStatus.OK);
    }

    @PutMapping(value = { "/customer/validate/{id}", "/customer/validate/{id}/" })
    public ResponseEntity validateCustomer(@PathVariable("id") String id) {
        Customer c;
        try {
            c = customerService.validateCustomerByID(Integer.parseInt(id));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (c == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not validate customer");
        return new ResponseEntity<>(convertToDto(c), HttpStatus.OK);
    }

    @PutMapping(value = { "/customer/convert/{id}", "/customer/convert/{id}/" })
    public ResponseEntity convertLocal(@PathVariable("id") String id, @RequestParam String username, @RequestParam String password, @RequestParam String email) {
        Customer c;
        try {
            c = customerService.convertLocalToOnline(Integer.parseInt(id), username, password, email);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (c == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot convert this Customer account");
        return new ResponseEntity<>(convertToDto(c), HttpStatus.OK);
    }

    @PutMapping(value = { "/customer/updateOnline/{id}", "/customer/updateOnline/{id}/" })
    public ResponseEntity updateOnlineInfo(@PathVariable("id") String id, @RequestParam String username, @RequestParam String password, @RequestParam String email) throws IllegalArgumentException, NullPointerException {
        Customer c;
        try {
            c = customerService.updateOnlineInfo(Integer.parseInt(id), username, password, email);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (c == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot update this Customer's online account");
        return new ResponseEntity<>(convertToDto(c), HttpStatus.OK);
    }

    @PutMapping(value = { "/customer/update/{id}", "/customer/update/{id}/" })
    public ResponseEntity updateInfo(@PathVariable("id") String id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String civic,
                                  @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                  @RequestParam String province, @RequestParam String country) throws IllegalArgumentException, NullPointerException {
        Customer c;
        try {
            c = customerService.changeInfo(Integer.parseInt(id), firstName, lastName, civic, street, city, postalCode, province, country);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (c == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot convert this Customer account");
        return new ResponseEntity<>(convertToDto(c), HttpStatus.OK);
    }

    private CustomerDto convertToDto(Customer c) {
        if (c == null) throw new NullPointerException("Cannot find this Customer");
        else if (c.getIsOnlineAcc()) {
            return new CustomerDto(c.getLibraryCardID(), c.getIsOnlineAcc(), c.getIsLoggedIn(), c.getFirstName(),
                    c.getLastName(), c.getIsVerified(), c.getDemeritPts(), convertToDto(c.getAddress()), c.getUsername(),
                    c.getEmail(), c.getOutstandingBalance());
        }
        return new CustomerDto(c.getLibraryCardID(), c.getIsOnlineAcc(), c.getIsLoggedIn(), c.getFirstName(),
                c.getLastName(), c.getIsVerified(), c.getDemeritPts(), convertToDto(c.getAddress()), c.getOutstandingBalance());
    }

    private AddressDto convertToDto(Address a) {
        if (a == null) throw new NullPointerException("Cannot find Address");
        return new AddressDto(a.getAddressID(), a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(), a.getCountry());
    }
}

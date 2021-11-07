package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.dto.CustomerDto;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Customer;
import ca.mcgill.ecse321.librarysystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = { "/customers", "/customers/" })
    public List<CustomerDto> getAllCustomers() {
        List<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer c : customerService.getAllCustomers()) {
            customerDtos.add(convertToDto(c));
        }
        return customerDtos;
    }

    @GetMapping(value = { "/customer/{id}", "/customer/{id}/" })
    public CustomerDto getCustomerByID(@PathVariable("id") String id) throws IllegalArgumentException, NullPointerException {
        return convertToDto(customerService.getCustomer(Integer.parseInt(id)));
    }

    @PostMapping(value = { "/customer/createLocal", "/customer/createLocal/" })
    public CustomerDto createLocalCustomer(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String civic,
                                      @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                      @RequestParam String province, @RequestParam String country) throws IllegalArgumentException {
        return convertToDto(customerService.createCustomer(firstname,lastname,civic,street,city,postalCode,province,country));
    }

    @PostMapping(value = { "/customer/create", "/customer/create/"})
    public CustomerDto createOnlineCustomer(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String email, @RequestParam String username, @RequestParam String password,
                                      @RequestParam String civic, @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                      @RequestParam String province, @RequestParam String country) throws IllegalArgumentException {
        return convertToDto(customerService.createOnlineCustomer(firstName,lastName,email,username,password,civic,street,city,postalCode,province,country));
    }

    @DeleteMapping(value = { "/customer/{id}", "/customer/{id}/" })
    public boolean deleteCustomer(@PathVariable("id") String id) throws IllegalArgumentException, NullPointerException {
        if (customerService.deleteCustomerByID(Integer.parseInt(id)) == null) return true;
        return false;
    }

    @GetMapping(value = { "/customer/login", "/customer/login/"})
    public CustomerDto loginCustomer(@RequestParam String name, @RequestParam String password) throws IllegalArgumentException, NullPointerException {
        if (name.contains("@")) {
            Customer c = customerService.loginByEmail(name, password);
            return convertToDto(c);
        }
        return convertToDto(customerService.login(name, password));
    }

    @GetMapping(value = {"/customer/login/{id}", "/customer/login/{id}/"})
    public CustomerDto login(@PathVariable("id") String id, @RequestParam String password) throws IllegalArgumentException, NullPointerException {
        return convertToDto(customerService.login(Integer.parseInt(id), password));
    }

    @GetMapping(value = { "/customers/name", "/customers/name/" })
    public List<CustomerDto> getCustomersByFirstAndLastName(@RequestParam String firstName, @RequestParam String lastName) {
        List<CustomerDto> cDto = new ArrayList<>();
        for (Customer c : customerService.getCustomersByFirstAndLastName(firstName, lastName)) {
            cDto.add(convertToDto(c));
        }
        return cDto;
    }

    @GetMapping(value = { "/customers/getByAddress", "/customers/getByAddress/" })
    public List<CustomerDto> getCustomersByAddress(@RequestParam String civic, @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                                   @RequestParam String province, @RequestParam String country) throws IllegalArgumentException, NullPointerException {
        List<CustomerDto> cDto = new ArrayList<>();
        for (Customer c : customerService.getCustomersByAddress(civic, street, city, postalCode, province, country)) {
            cDto.add(convertToDto(c));
        }
        return cDto;
    }

    @GetMapping(value = { "/customer/username", "/customer/username/" })
    public CustomerDto getCustomerByUsername(@RequestParam String username) throws IllegalArgumentException, NullPointerException {
        return convertToDto(customerService.getCustomer(username));
    }

    @GetMapping(value = { "/customer/email", "customer/email/" })
    public CustomerDto getCustomerByEmail(@RequestParam String email) throws IllegalArgumentException, NullPointerException {
        return convertToDto(customerService.getCustomerByEmail(email));
    }

    @GetMapping(value = { "/customers/demeritPts/{pts}", "/customers/demeritPts/{pts}/" })
    public List<CustomerDto> getByDemeritPts(@PathVariable("pts") String pts) {
        List<CustomerDto> cDto = new ArrayList<>();
        for (Customer c : customerService.getCustomersByDemeritPts(Integer.parseInt(pts))) {
            cDto.add(convertToDto(c));
        }
        return cDto;
    }

    @GetMapping(value = { "/customers/online", "/customers/online/" })
    public List<CustomerDto> getOnlineCustomers() {
        List<CustomerDto> cDto = new ArrayList<>();
        for (Customer c : customerService.getCustomersByLoggedIn(true)) {
            cDto.add(convertToDto(c));
        }
        return cDto;
    }

    @PutMapping(value = { "/customer/balance/{id}", "/customer/balance/{id}/" })
    public CustomerDto modifyBalance(@PathVariable("id") String id, @RequestParam String toModify) throws IllegalArgumentException, NullPointerException {
        if (customerService.modifyOutstandingBalance(Integer.parseInt(id), Integer.parseInt(toModify)) != null) return convertToDto(customerService.getCustomer(Integer.parseInt(id)));
        return null;
    }

    @PutMapping(value = { "/customer/validate/{id}", "/customer/validate/{id}/" })
    public CustomerDto validateCustomer(@PathVariable("id") String id) {
        if (customerService.validateCustomerByID(Integer.parseInt(id)).getIsVerified()) return convertToDto(customerService.getCustomer(Integer.parseInt(id)));
        return null;
    }

    @PutMapping(value = { "/customer/convert/{id}", "/customer/convert/{id}/" })
    public CustomerDto convertLocal(@PathVariable("id") String id, @RequestParam String username, @RequestParam String password, @RequestParam String email) throws IllegalArgumentException, NullPointerException {
        if (customerService.convertLocalToOnline(Integer.parseInt(id), username, password, email) != null)
            return convertToDto((customerService.getCustomer(Integer.parseInt(id))));
        return null;
    }

    @PutMapping(value = { "/customer/updateOnline/{id}", "/customer/updateOnline/{id}/" })
    public CustomerDto updateOnlineInfo(@PathVariable("id") String id, @RequestParam String username, @RequestParam String password, @RequestParam String email) throws IllegalArgumentException, NullPointerException {
        if (customerService.updateOnlineInfo(Integer.parseInt(id), username, password, email) != null)
            return convertToDto(customerService.getCustomer(Integer.parseInt(id)));
        return null;
    }

    @PutMapping(value = { "/customer/update/{id}", "/customer/update/{id}/" })
    public CustomerDto updateInfo(@PathVariable("id") String id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String civic,
                                  @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                  @RequestParam String province, @RequestParam String country) throws IllegalArgumentException, NullPointerException {
        if (customerService.changeInfo(Integer.parseInt(id), firstName, lastName, civic, street, city, postalCode, province, country) != null)
            return convertToDto(customerService.getCustomer(Integer.parseInt(id)));
        return null;
    }

    private CustomerDto convertToDto(Customer c) {
        if (c == null) throw new NullPointerException("Cannot find this Customer");
        return new CustomerDto(c.getLibraryCardID(), c.getIsOnlineAcc(), c.getIsLoggedIn(), c.getFirstName(),
                c.getLastName(), c.getIsVerified(), c.getDemeritPts(), convertToDto(c.getAddress()), c.getOutstandingBalance());
    }

    private AddressDto convertToDto(Address a) {
        if (a == null) throw new NullPointerException("Cannot find Address");
        return new AddressDto(a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(), a.getCountry());
    }
}

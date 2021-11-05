package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.dto.CustomerDto;
import ca.mcgill.ecse321.librarysystem.dto.EmployeeDto;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Customer;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = {"/employees", "/employees/"})
    public List<EmployeeDto> getAllEmployees() {
        List<EmployeeDto> employeeList = new ArrayList<>();
        for (Employee e : employeeService.getAllEmployees()) {
            employeeList.add(convertToDto(e));
        }
        return employeeList;
    }

    @GetMapping(value = {"/employee/{id}", "/employee/{id}/"})
    public EmployeeDto getEmployee(@PathVariable("id") String id) {
        return convertToDto(employeeService.getEmployee(Integer.parseInt(id)));
    }

    @GetMapping(value = {"/employee/username", "/employee/username/"})
    public EmployeeDto getEmployeeByUsername(@RequestParam String username) {
        return convertToDto(employeeService.getEmployee(username));
    }

    @GetMapping(value = {"/employee/email", "/employee/email/"})
    public EmployeeDto getEmployeeByEmail(@RequestParam String email) {
        return convertToDto(employeeService.getEmployeeByEmail(email));
    }

    @GetMapping(value = { "/employees/firstname", "/employees/firstname/"})
    public List<EmployeeDto> getEmployeesByFirstName(@RequestParam String firstname) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee e : employeeService.getEmployeesByFirstName(firstname)) {
            employeeDtos.add(convertToDto(e));
        }
        return employeeDtos;
    }

    @GetMapping(value = { "/employees/lastname", "/employees/lastname/"})
    public List<EmployeeDto> getEmployeesByLastName(@RequestParam String lastname) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee e : employeeService.getEmployeesByLastName(lastname)) {
            employeeDtos.add(convertToDto(e));
        }
        return employeeDtos;
    }

    @GetMapping(value = {"/employees/getByName", "/employees/getByName/"})
    public List<EmployeeDto> getEmployeesByName(@RequestParam String firstname, @RequestParam String lastname) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee e : employeeService.getEmployeesByFirstAndLastName(firstname, lastname)) {
            employeeDtos.add(convertToDto(e));
        }
        return employeeDtos;
    }

    @GetMapping(value = {"/employees/role/{role}", "/employees/role/{role}/"})
    public List<EmployeeDto> getEmployeesByRole(@RequestParam String role) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee e : employeeService.getEmployeesByRole(Employee.Role.valueOf(role))) {
            employeeDtos.add(convertToDto(e));
        }
        return employeeDtos;
    }

    @GetMapping(value = {"/employees/demerit", "/employees/demerit/"})
    public List<EmployeeDto> getEmployeesByDemerit(@RequestParam String demeritPts) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        for (Employee e : employeeService.getEmployeesByDemeritPts(Integer.parseInt(demeritPts))) {
            employeeDtos.add(convertToDto(e));
        }
        return employeeDtos;
    }

    @GetMapping(value = {"/employees/address", "/employees/address/"})
    public List<EmployeeDto> getEmployeesByAddress(@RequestParam String civic, @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                                   @RequestParam String province, @RequestParam String country) {
        List<EmployeeDto> eDto = new ArrayList<>();
        for (Employee e : employeeService.getEmployeesByAddress(civic, street, city, postalCode, province, country)) {
            eDto.add(convertToDto(e));
        }
        return eDto;
    }

    @PostMapping(value = {"/employee/create", "/employee/create/"})
    public EmployeeDto createEmployee(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String civic, @RequestParam String street,
                                      @RequestParam String city, @RequestParam String postalCode, @RequestParam String province, @RequestParam String country,
                                      @RequestParam String email, @RequestParam String username, @RequestParam String password, @RequestParam String role) throws IllegalArgumentException, NullPointerException {
        return convertToDto(employeeService.createEmployee(firstName,lastName,civic,street,city,postalCode,province,country,email,username,password,Employee.Role.valueOf(role)));
    }

    @PutMapping(value = {"/employee/demerit/{id}", "/employee/demerit/{id}/"})
    public EmployeeDto changeDemeritPts(@PathVariable("id") String id, @RequestParam String toChange) throws IllegalArgumentException, NullPointerException {
        Employee e = employeeService.changeDemeritPts(Integer.parseInt(id), Integer.parseInt(toChange));
            if (e != null) return convertToDto(e);
        return null;
    }

    @PutMapping(value = { "/employee/balance/{id}", "/employee/balance/{id}/"})
    public EmployeeDto changeBalance(@PathVariable("id") String id, @RequestParam String toChange) throws IllegalArgumentException, NullPointerException {
        if (employeeService.modifyOutstandingBalance(Integer.parseInt(id), Integer.parseInt(toChange)))
            return convertToDto(employeeService.getEmployee(Integer.parseInt(id)));
        return null;
    }

    @PutMapping(value = {"/employee/login/{name}", "/employee/login/{name}/"})
    public EmployeeDto login(@PathVariable("name") String name, @RequestParam String password) {
        if (name.contains("@")) {
            if (employeeService.loginByEmail(name, password)) return convertToDto(employeeService.getEmployeeByEmail(name));
            return null;
        }
        else if (employeeService.login(name, password)) return convertToDto(employeeService.getEmployee(name));
        return null;
    }

    @PutMapping(value = {"/employee/updateOnline/{id}", "/employee/updateOnline/{id}/"})
    public EmployeeDto updateOnline(@PathVariable("id") String id, @RequestParam String username, @RequestParam String password, @RequestParam String email) {
        if (employeeService.updateOnlineInfo(Integer.parseInt(id), username, password, email))
            return convertToDto(employeeService.getEmployee(Integer.parseInt(id)));
        return null;
    }

    @PutMapping(value = { "/employee/update/{id}", "/employee/update/{id}/" })
    public EmployeeDto updateInfo(@PathVariable("id") String id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String civic,
                                  @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                  @RequestParam String province, @RequestParam String country) throws IllegalArgumentException, NullPointerException {
        if (employeeService.changeInfo(Integer.parseInt(id), firstName, lastName, civic, street, city, postalCode, province, country))
            return convertToDto(employeeService.getEmployee(Integer.parseInt(id)));
        return null;
    }

    @PutMapping(value = {"/employee/role/{id}", "/employee/role/{id}/"})
    public EmployeeDto updateRole(@PathVariable("id") String id, @RequestParam String role) {
        if (employeeService.updateRole(Integer.parseInt(id), Employee.Role.valueOf(role)))
            return convertToDto(employeeService.getEmployee(Integer.parseInt(id)));
        return null;
    }

    private EmployeeDto convertToDto(Employee e) {
        if (e == null) throw new IllegalArgumentException("Cannot find this Employee");
        return new EmployeeDto(e.getLibraryCardID(), e.getIsLoggedIn(), e.getIsOnlineAcc(), e.getFirstName(),
                e.getLastName(), e.getIsVerified(), e.getDemeritPts(), convertToDto(e.getAddress()),
                EmployeeDto.Role.valueOf(e.getRole().name()), e.getOutstandingBalance());
    }

    private AddressDto convertToDto(Address a) {
        if (a == null) throw new NullPointerException("Cannot find Address");
        return new AddressDto(a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(), a.getCountry());
    }
}

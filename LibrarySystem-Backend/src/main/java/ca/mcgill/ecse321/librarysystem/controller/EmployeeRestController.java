package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.dto.CustomerDto;
import ca.mcgill.ecse321.librarysystem.dto.EmployeeDto;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Customer;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping(value = {"/employees", "/employees/"})
    public ResponseEntity getAllEmployees() {
        List<Employee> employeeList;
        List<EmployeeDto> employees = new ArrayList<>();
        try {
            employeeList = employeeService.getAllEmployees();
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Employee e : employeeList) {
            employees.add(convertToDto(e));
        }
        if (employees.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any Employees in System");
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(value = {"/employee/{id}", "/employee/{id}/"})
    public ResponseEntity getEmployee(@PathVariable("id") String id) {
        Employee e;
        try {
            e = employeeService.getEmployee(Integer.parseInt(id));
        } catch (IllegalArgumentException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (e == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find employee with this ID");
        return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    @GetMapping(value = {"/employee/username", "/employee/username/"})
    public ResponseEntity getEmployeeByUsername(@RequestParam String username) {
        Employee e;
        try {
            e = employeeService.getEmployee(username);
        } catch (IllegalArgumentException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (e == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find employee with this username");
        return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    @GetMapping(value = {"/employee/email", "/employee/email/"})
    public ResponseEntity getEmployeeByEmail(@RequestParam String email) {
        Employee e;
        try {
            e = employeeService.getEmployeeByEmail(email);
        } catch (IllegalArgumentException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (e == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find employee with this username");
        return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    @GetMapping(value = {"/employees/getByName", "/employees/getByName/"})
    public ResponseEntity getEmployeesByName(@RequestParam String firstname, @RequestParam String lastname) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        List<Employee> employees;
        try {
            employees = employeeService.getEmployeesByFirstAndLastName(firstname, lastname);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Employee e : employees) {
            employeeDtos.add(convertToDto(e));
        }
        if (employeeDtos.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any employees with this name");
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }

    @GetMapping(value = {"/employees/role/{role}", "/employees/role/{role}/"})
    public ResponseEntity getEmployeesByRole(@RequestParam String role) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        List<Employee> employees;
        try {
            employees = employeeService.getEmployeesByRole(Employee.Role.valueOf(role));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Employee e : employees) {
            employeeDtos.add(convertToDto(e));
        }
        if (employeeDtos.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any employees with this role");
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }

    @GetMapping(value = {"/employees/demerit", "/employees/demerit/"})
    public ResponseEntity getEmployeesByDemerit(@RequestParam String demeritPts) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        List<Employee> employees;
        try {
            employees = employeeService.getEmployeesByDemeritPts(Integer.parseInt(demeritPts));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Employee e : employees) {
            employeeDtos.add(convertToDto(e));
        }
        if (employeeDtos.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any employees with this number of demerit points");
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }

    @GetMapping(value = {"/employees/address", "/employees/address/"})
    public ResponseEntity getEmployeesByAddress(@RequestParam String civic, @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                                   @RequestParam String province, @RequestParam String country) {
        List<EmployeeDto> employeeDtos = new ArrayList<>();
        List<Employee> employees;
        try {
            employees = employeeService.getEmployeesByAddress(civic, street, city, postalCode, province, country);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Employee e : employees) {
            employeeDtos.add(convertToDto(e));
        }
        if (employeeDtos.size() == 0) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find any employees with this address");
        return new ResponseEntity<>(employeeDtos, HttpStatus.OK);
    }

    @PostMapping(value = {"/employee/create", "/employee/create/"})
    public ResponseEntity createEmployee(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String civic, @RequestParam String street,
                                         @RequestParam String city, @RequestParam String postalCode, @RequestParam String province, @RequestParam String country,
                                         @RequestParam String email, @RequestParam String username, @RequestParam String password, @RequestParam String role) {
        Employee e;
        try {
            e = employeeService.createEmployee(firstName,lastName,civic,street,city,postalCode,province,country,email,username,password,Employee.Role.valueOf(role));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    @DeleteMapping(value = { "/employee/{id}", "/employee/{id}/"})
    public ResponseEntity deleteEmployee(@PathVariable("id") String id) {
        Employee e;
        try {
            e = employeeService.deleteEmployeeByID(Integer.parseInt(id));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (e == null) return ResponseEntity.status(HttpStatus.OK).body("Employee with id " + id + " has been deleted");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: could not delete Employee");
    }

    @PutMapping(value = {"/employee/demerit/{id}", "/employee/demerit/{id}/"})
    public ResponseEntity changeDemeritPts(@PathVariable("id") String id, @RequestParam String toChange) {
        Employee e;
        try {
            e = employeeService.changeDemeritPts(Integer.parseInt(id), Integer.parseInt(toChange));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (e == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: could not change Demerit points of Employee #" + id);
        return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    @PutMapping(value = { "/employee/balance/{id}", "/employee/balance/{id}/"})
    public ResponseEntity changeBalance(@PathVariable("id") String id, @RequestParam String toChange) throws IllegalArgumentException, NullPointerException {
        Employee e;
        try {
            e = employeeService.modifyOutstandingBalance(Integer.parseInt(id), Integer.parseInt(toChange));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (e == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: could not change balance of Employee #" + id);
        return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    @PutMapping(value = {"/employee/login", "/employee/login/"})
    public ResponseEntity login(@RequestParam String name, @RequestParam String password) {
        Employee e;
        if (name.contains("@")) {
            try {
                e = employeeService.loginByEmail(name, password);
            } catch (IllegalArgumentException | NullPointerException msg) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
            }
            if (e == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not log in");
            return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
        }
        try {
            e = employeeService.login(name, password);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (e == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not log in");
        return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    @PutMapping(value = { "/employee/login/{id}", "/employee/login/{id}/"})
    public ResponseEntity loginID(@PathVariable("id") String id, @RequestParam String password) {
        Employee e;
        try {
            e = employeeService.login(Integer.parseInt(id), password);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (e == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not log in");
        return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    @PutMapping(value = { "/employee/logout", "/employee/logout/"})
    public ResponseEntity logout(@RequestParam String name) {
        boolean b;
        if (name.contains("@")) {
            try {
                b = employeeService.logoutByEmail(name);
            } catch (IllegalArgumentException | NullPointerException msg) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
            }
            if (b) return ResponseEntity.status(HttpStatus.OK).body("Logged out");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Did not log out");
        }
        try {
            b = employeeService.logout(name);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (b) return ResponseEntity.status(HttpStatus.OK).body("Logged out");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Did not log out");
    }

    @PutMapping(value = { "/employee/logout/{id}", "/employee/logout/{id}/"})
    public ResponseEntity logoutID(@PathVariable("id") String id) {
        boolean b;
        try {
            b = employeeService.logout(Integer.parseInt(id));
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (b) return ResponseEntity.status(HttpStatus.OK).body("Logged out");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Did not log out");
    }

    @PutMapping(value = {"/employee/updateOnline/{id}", "/employee/updateOnline/{id}/"})
    public ResponseEntity updateOnline(@PathVariable("id") String id, @RequestParam String username, @RequestParam String password, @RequestParam String email) {
        Employee e;
        try {
            e = employeeService.updateOnlineInfo(Integer.parseInt(id), username, password, email);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (e == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not update online Employee");
        return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    @PutMapping(value = { "/employee/update/{id}", "/employee/update/{id}/" })
    public ResponseEntity updateInfo(@PathVariable("id") String id, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String civic,
                                  @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
                                  @RequestParam String province, @RequestParam String country) throws IllegalArgumentException, NullPointerException {
        Employee e;
        try {
            e = employeeService.changeInfo(Integer.parseInt(id), firstName, lastName, civic, street, city, postalCode, province, country);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (e == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not update Employee information");
        return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    @PutMapping(value = {"/employee/role/{id}", "/employee/role/{id}/"})
    public ResponseEntity updateRole(@PathVariable("id") String id, @RequestParam String role) {
       Employee e;
       try {
           e = employeeService.updateRole(Integer.parseInt(id), Employee.Role.valueOf(role));
       } catch (IllegalArgumentException | NullPointerException msg) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
       }
       if (e == null) return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Error: Could not update role");
       return new ResponseEntity<>(convertToDto(e), HttpStatus.OK);
    }

    private EmployeeDto convertToDto(Employee e) {
        if (e == null) throw new IllegalArgumentException("Cannot find this Employee");
        return new EmployeeDto(e.getLibraryCardID(), e.getIsLoggedIn(), e.getIsOnlineAcc(), e.getFirstName(),
                e.getLastName(), e.getIsVerified(), e.getDemeritPts(), convertToDto(e.getAddress()),
                EmployeeDto.Role.valueOf(e.getRole().name()), e.getOutstandingBalance());
    }

    private AddressDto convertToDto(Address a) {
        if (a == null) throw new NullPointerException("Cannot find Address");
        return new AddressDto(a.getAddressID(), a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(), a.getCountry());
    }
}

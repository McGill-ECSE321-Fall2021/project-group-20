package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.AddressRepository;
import ca.mcgill.ecse321.librarysystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.librarysystem.dao.HourRepository;
import ca.mcgill.ecse321.librarysystem.dao.UserRepository;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    HourRepository hourRepository;

    @Transactional
    public Employee createEmployee(String firstName, String lastName, String civic, String street, String city, String postalCode, String province, String country, String email, String username, String password, Employee.Role role) {
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (password == null || password.length() < 8) throw new IllegalArgumentException("Please enter a password that is at least 8 characters long");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        if (employeeRepository.existsByEmail(email)) throw new IllegalArgumentException("Email in use, please choose another");
        if (employeeRepository.existsByUsername(username)) throw new IllegalArgumentException("Username in use, please choose another");
        if (role == null || (!role.equals(Employee.Role.HeadLibrarian) && !role.equals(Employee.Role.Librarian))) throw new IllegalArgumentException("Please select either <Librarian> or <HeadLibrarian> as role");
        Address a = new Address(civic, street, city, postalCode, province, country);
        addressRepository.save(a);
        Employee newEmployee = new Employee(true, false, firstName, lastName, true, 0, a, role);
        newEmployee.setEmail(email);
        newEmployee.setUsername(username);
        newEmployee.setPassword(password);
        employeeRepository.save(newEmployee);
        return newEmployee;
    }

    @Transactional
    public Employee deleteEmployeeByID(int libraryCardID) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        Employee e = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (e == null) throw new NullPointerException("Cannot find Employee matching this libraryCardID");
        employeeRepository.delete(e);
        addressRepository.delete(e.getAddress());
        for (Hour h : e.getEmployeehour()) {
            hourRepository.delete(h);
        }
        e.delete();
        if (e.getAddress() == null && e.numberOfUserbooking() == 0 && e.numberOfEmployeehour() == 0) return null;
        return e;
    }

    @Transactional
    public Employee changeDemeritPts(int libraryCardID, int toAdd) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        Employee employee = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (employee == null) throw new NullPointerException("Cannot find Employee with this ID");
        if (employee.setDemeritPts(employee.getDemeritPts() + toAdd)) {
            employeeRepository.save(employee);
            return employee;
        }
        return null;
    }

    @Transactional
    public Employee modifyOutstandingBalance(int libraryCardID, int toModify) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        Employee employee = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (employee == null) throw new NullPointerException("Cannot find Employee with this ID");
        if (employee.setOutstandingBalance(employee.getOutstandingBalance() + toModify)) {
            employeeRepository.save(employee);
            return employee;
        }
        return null;
    }

    @Transactional
    public Employee login(int libraryCardID, String password) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        Employee employee = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (employee == null || !employee.getIsOnlineAcc()) throw new NullPointerException("Cannot find Employee with this ID");
        if (!employee.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password!");
        if (employee.setIsLoggedIn(true)) {
            employeeRepository.save(employee);
            return employee;
        }
        return null;
    }

    @Transactional
    public Employee login(String username, String password) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        if (employee == null || !employee.getIsOnlineAcc()) throw new NullPointerException("Cannot find Employee with this username");
        if (!employee.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password!");
        if (employee.setIsLoggedIn(true)) {
            employeeRepository.save(employee);
            return employee;
        }
        return null;
    }

    @Transactional
    public Employee loginByEmail(String email, String password) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null || !employee.getIsOnlineAcc()) throw new NullPointerException("Cannot find Employee with this email");
        if (!employee.getPassword().equals(password)) throw new IllegalArgumentException("Incorrect password!");
        if (employee.setIsLoggedIn(true)) {
            employeeRepository.save(employee);
            return employee;
        }
        return null;
    }

    @Transactional
    public boolean logout(int libraryCardID) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid ID");
        Employee employee = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (employee == null || !employee.getIsOnlineAcc()) throw new NullPointerException("Cannot find Employee with this ID");
        if (employee.setIsLoggedIn(false)) {
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean logout(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Employee employee = employeeRepository.findEmployeeByUsername(username);
        if (employee == null || !employee.getIsOnlineAcc()) throw new NullPointerException("Cannot find Employee with this username");
        if (employee.setIsLoggedIn(false)) {
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean logoutByEmail(String email) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Employee employee = employeeRepository.findEmployeeByEmail(email);
        if (employee == null || !employee.getIsOnlineAcc()) throw new NullPointerException("Cannot find Employee with this email");
        if (employee.setIsLoggedIn(false)) {
            employeeRepository.save(employee);
            return true;
        }
        return false;
    }

    @Transactional
    public Employee getEmployee(int libraryCardID) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        return employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
    }

    @Transactional
    public Employee getEmployee(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        return employeeRepository.findEmployeeByUsername(username);
    }

    @Transactional
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Transactional
    public Employee getEmployeeByEmail(String email) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        return employeeRepository.findEmployeeByEmail(email);
    }

    @Transactional
    public List<Employee> getEmployeesByFirstAndLastName(String firstName, String lastName) {
        if (firstName == null || firstName.length() == 0 || lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        return employeeRepository.findEmployeesByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional
    public Employee getEmployeeByBooking(Booking booking) {
        if (booking == null) throw new IllegalArgumentException("Please enter a valid booking");
        return employeeRepository.findEmployeeByUserbooking(booking);
    }

    @Transactional
    public List<Employee> getEmployeesByHour(Hour hour) {
        if (hour == null) throw new IllegalArgumentException("Please enter a valid hour");
        return employeeRepository.findEmployeesByEmployeehour(hour);
    }

    @Transactional
    public List<Employee> getEmployeesByRole(Employee.Role role) {
        if (role == null || (!role.equals(Employee.Role.Librarian)) && !role.equals(Employee.Role.HeadLibrarian)) throw new IllegalArgumentException("Please enter a valid role to search");
        return employeeRepository.findEmployeesByRole(role);
    }

    @Transactional
    public List<Employee> getEmployeesByDemeritPts(int demeritPts) {
        if (demeritPts < 0) throw new IllegalArgumentException("Please enter a positive demeritPts int");
        return employeeRepository.findEmployeesByDemeritPts(demeritPts);
    }

    @Transactional
    public List<Employee> getEmployeesByAddress(Address address) {
        if (address == null) throw new IllegalArgumentException("Please enter a valid address");
        return employeeRepository.findEmployeesByAddress(address);
    }

    @Transactional
    public List<Employee> getEmployeesByAddress(String civic, String street, String city, String postalCode, String province, String country) {
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        List<Address> addressList = addressRepository.findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(civic, street, city, postalCode, province, country);
        if (addressList.isEmpty()) throw new NullPointerException("Cannot find address in system");
        return getEmployeesByAddress(addressList.get(0));
    }

    @Transactional
    public Employee updateOnlineInfo(int libraryCardID, String username, String password, String email) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (password == null || password.length() < 8) throw new IllegalArgumentException("Please enter a valid password of min 8 chars long");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (!employeeRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find Employee with given ID");
        if (employeeRepository.existsByUsername(username)) throw new IllegalArgumentException("New username in use, please choose a valid one");
        if (employeeRepository.existsByEmail(email)) throw new IllegalArgumentException("New email in use, please choose another");
        Employee employee = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (employee.setUsername(username) && employee.setPassword(password) && employee.setEmail(email)) {
            employeeRepository.save(employee);
            return employee;
        }
        return null;
    }

    @Transactional
    public Employee changeInfo(int libraryCardID,  String firstName, String lastName, String civic, String street, String city, String postalCode, String province, String country) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        if (!employeeRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find Employee with given ID");
        Employee employee = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (employee.setFirstName(firstName) && employee.setLastName(lastName)) {
            Address a = employee.getAddress();
            if (!a.setCivicNumber(civic)) return null;
            if (!a.setStreet(street)) return null;
            if (!a.setCity(city)) return null;
            if (!a.setPostalCode(postalCode)) return null;
            if (!a.setProvince(province)) return null;
            if (!a.setCountry(country)) return null;
            employee.setAddress(a);
            addressRepository.save(a);
            employeeRepository.save(employee);
            return employee;
        }
        return null;
    }

    public Employee updateRole(int libraryCardID, Employee.Role role) {
        if (role == null || (!role.equals(Employee.Role.Librarian)) && !role.equals(Employee.Role.HeadLibrarian)) throw new IllegalArgumentException("Please enter a valid role");
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        Employee emp = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this ID");
        if (emp.setRole(role)) {
            employeeRepository.save(emp);
            return emp;
        }
        return null;
    }
}

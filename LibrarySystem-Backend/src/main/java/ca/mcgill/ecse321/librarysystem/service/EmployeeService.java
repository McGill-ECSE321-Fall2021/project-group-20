package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.AddressRepository;
import ca.mcgill.ecse321.librarysystem.dao.EmployeeRepository;
import ca.mcgill.ecse321.librarysystem.dao.UserRepository;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Customer;
import ca.mcgill.ecse321.librarysystem.model.Employee;
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

    @Transactional
    public Employee createEmployee(String firstName, String lastName, String civic, String street, String city, String postalCode, String province, String country, String email, String username, String password, Employee.Role role) {
        if (firstName == null || lastName == null || firstName.length() == 0 || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (password == null || password.length() < 8) throw new IllegalArgumentException("Please enter a password that is at least 8 characters long");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        Address a = new Address(civic, street, city, postalCode, province, country);
        addressRepository.save(a);
        Employee newEmployee = new Employee(true, firstName, lastName, true, 0, a, role);
        newEmployee.setEmail(email);
        newEmployee.setUsername(username);
        newEmployee.setPassword(password);
        employeeRepository.save(newEmployee);
        return newEmployee;
    }

    // Add method for delete

    @Transactional
    public Employee getEmployee(int libraryCardID) {
        if (libraryCardID == 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
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
    public List<Employee> getEmployeesByFirstName(String firstName) {
        if (firstName == null || firstName.length() == 0) throw new IllegalArgumentException("Please enter a valid firstName");
        return employeeRepository.findEmployeesByFirstName(firstName);
    }

    @Transactional
    List<Employee> getEmployeesByLastName(String lastName) {
        if (lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid lastName");
        return employeeRepository.findEmployeesByLastName(lastName);
    }

    @Transactional
    List<Employee> getEmployeesByFirstAndLastName(String firstName, String lastName) {
        if (firstName == null || firstName.length() == 0 || lastName == null || lastName.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        return employeeRepository.findEmployeesByFirstNameAndLastName(firstName, lastName);
    }

    @Transactional
    Employee getEmployeeByBooking(Booking booking) {
        if (booking == null) throw new IllegalArgumentException("Please enter a valid booking");
        return employeeRepository.findEmployeeByUserbooking(booking);
    }

    @Transactional
    List<Employee> getEmployeesByRole(Employee.Role role) {
        if (role == null) throw new IllegalArgumentException("Please enter a valid role to search");
        return employeeRepository.findEmployeesByRole(role);
    }

    @Transactional
    List<Employee> getEmployeesByDemeritPts(int demeritPts) {
        if (demeritPts < 0) throw new IllegalArgumentException("Please enter a positive demeritPts int");
        return employeeRepository.findEmployeesByDemeritPts(demeritPts);
    }

    @Transactional
    List<Employee> getEmployeesByAddress(Address address) {
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
    public boolean updateUsername(int libraryCardID, String username) {
        if (libraryCardID == 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (userRepository.existsByUsername(username)) throw new IllegalArgumentException("Username not available, please choose another one");
        Employee emp = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this libraryCardID");
        if (emp.setUsername(username)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateUsername(String oldUsername, String newUsername) {
        if (oldUsername == null || oldUsername.length() == 0) throw new IllegalArgumentException("Please enter a valid current username");
        if (newUsername == null || newUsername.length() == 0) throw new IllegalArgumentException("Please enter a valid new username");
        if (userRepository.existsByUsername(newUsername)) throw new IllegalArgumentException("Username not available, please choose another one");
        Employee emp = employeeRepository.findEmployeeByUsername(oldUsername);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this Username");
        if (emp.setUsername(newUsername)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean updateUsernameByEmail(String email, String username) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (username ==null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (userRepository.existsByUsername(username)) throw new IllegalArgumentException("Username not available, please choose another one");
        Employee emp = employeeRepository.findEmployeeByEmail(email);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this Email");
        if (emp.setUsername(username)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    public boolean updateRole(int libraryCardID, Employee.Role role) {
        if (role == null) throw new IllegalArgumentException("Please enter a valid role");
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        Employee emp = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this ID");
        if (emp.setRole(role)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    public boolean updateRole(String username, Employee.Role role) {
        if (role == null) throw new IllegalArgumentException("Please enter a valid role");
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this username");
        if (emp.setRole(role)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    public boolean updateRoleByEmail(String email, Employee.Role role) {
        if (role == null) throw new IllegalArgumentException("Please enter a valid role");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        Employee emp = employeeRepository.findEmployeeByEmail(email);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this email");
        if (emp.setRole(role)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changePassword(int libraryCardID, String oldPass, String newPass) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (oldPass == null || oldPass.length() < 8) throw new IllegalArgumentException("Please enter your password that is at least 8 characters long");
        if (newPass == null || newPass.length() < 8) throw new IllegalArgumentException("Please enter a new password that is at least 8 characters long");
        Employee emp = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this ID");
        if (!emp.getPassword().equals(oldPass)) throw new IllegalArgumentException("Entered current password is invalid");
        if (emp.setPassword(newPass)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changePassword(String username, String oldPass, String newPass) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (oldPass == null || oldPass.length() < 8) throw new IllegalArgumentException("Please enter your password that is at least 8 characters long");
        if (newPass == null || newPass.length() < 8) throw new IllegalArgumentException("Please enter a new password that is at least 8 characters long");
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this username");
        if (!emp.getPassword().equals(oldPass)) throw new IllegalArgumentException("Entered current password is invalid");
        if (emp.setPassword(newPass)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changePasswordByEmail(String email, String oldPass, String newPass) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (oldPass == null || oldPass.length() < 8) throw new IllegalArgumentException("Please enter your password that is at least 8 characters long");
        if (newPass == null || newPass.length() < 8) throw new IllegalArgumentException("Please enter a new password that is at least 8 characters long");
        Employee emp = employeeRepository.findEmployeeByEmail(email);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this email");
        if (!emp.getPassword().equals(oldPass)) throw new IllegalArgumentException("Entered current password is invalid");
        if (emp.setPassword(newPass)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changeEmail(int libraryCardID, String email) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if(userRepository.existsByEmail(email)) throw new IllegalArgumentException("Email not available, please choose another one");
        Employee emp = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this ID");
        if (emp.setEmail(email)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changeEmail(String username, String email) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if(userRepository.existsByEmail(email)) throw new IllegalArgumentException("Email not available, please choose another one");
        Employee emp = employeeRepository.findEmployeeByEmail(email);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this username");
        if (emp.setEmail(email)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changeEmailByEmail(String email, String newEmail) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (newEmail == null || !newEmail.contains("@")) throw new IllegalArgumentException("Please enter a valid new email");
        if(userRepository.existsByEmail(newEmail)) throw new IllegalArgumentException("Email not available, please choose another one");
        Employee emp = employeeRepository.findEmployeeByEmail(email);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this email");
        if (emp.setEmail(newEmail)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changeName(int libraryCardID, String firstname, String lastname) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (firstname == null || firstname.length() == 0 || lastname == null || lastname.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        Employee emp = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this ID");
        if (emp.setFirstName(firstname)) {
            if (emp.setLastName(lastname)) {
                employeeRepository.save(emp);
                return true;
            }
            return false;
        }
        return false;
    }

    @Transactional
    public boolean changeName(String username, String firstname, String lastname) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (firstname == null || firstname.length() == 0 || lastname == null || lastname.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this username");
        if (emp.setFirstName(firstname)) {
            if (emp.setLastName(lastname)) {
                employeeRepository.save(emp);
                return true;
            }
            return false;
        }
        return false;
    }

    @Transactional
    public boolean changeNameByEmail(String email, String firstname, String lastname) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (firstname == null || firstname.length() == 0 || lastname == null || lastname.length() == 0) throw new IllegalArgumentException("Please enter a valid name");
        Employee emp = employeeRepository.findEmployeeByEmail(email);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this username");
        if (emp.setFirstName(firstname)) {
            if (emp.setLastName(lastname)) {
                employeeRepository.save(emp);
                return true;
            }
            return false;
        }
        return false;
    }

    @Transactional
    public boolean changeDemeritPts(int libraryCardID, int demeritPts) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (demeritPts < 0) throw new IllegalArgumentException("Please enter a valid demeritPts");
        Employee emp = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this ID");
        if (emp.setDemeritPts(demeritPts)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changeDemeritPts(String username, int demeritPts) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (demeritPts < 0) throw new IllegalArgumentException("Please enter a valid demeritPts");
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this username");
        if (emp.setDemeritPts(demeritPts)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changeDemeritPtsByEmail(String email, int demeritPts) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (demeritPts < 0) throw new IllegalArgumentException("Please enter a valid demeritPts");
        Employee emp = employeeRepository.findEmployeeByEmail(email);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this email");
        if (emp.setDemeritPts(demeritPts)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changeAddress(int libraryCardID, String civic, String street, String city, String postalCode, String province, String country) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        if (!employeeRepository.existsByLibraryCardID(libraryCardID)) throw new NullPointerException("Cannot find Employee with given ID");
       Employee emp = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        Address a = emp.getAddress();
        if (!a.setCivicNumber(civic)) return false;
        if (!a.setStreet(street)) return false;
        if (!a.setCity(city)) return false;
        if (!a.setPostalCode(postalCode)) return false;
        if (!a.setProvince(province)) return false;
        if (!a.setCountry(country)) return false;
        emp.setAddress(a);
        addressRepository.save(a);
        employeeRepository.save(emp);
        return true;
    }

    @Transactional
    public boolean changeAddress(String username, String civic, String street, String city, String postalCode, String province, String country) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("Please enter a valid username");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        if (!employeeRepository.existsByUsername(username)) throw new NullPointerException("Cannot find Employee with given username");
        Employee emp = employeeRepository.findEmployeeByUsername(username);
        Address a = emp.getAddress();
        if (!a.setCivicNumber(civic)) return false;
        if (!a.setStreet(street)) return false;
        if (!a.setCity(city)) return false;
        if (!a.setPostalCode(postalCode)) return false;
        if (!a.setProvince(province)) return false;
        if (!a.setCountry(country)) return false;
        emp.setAddress(a);
        addressRepository.save(a);
        employeeRepository.save(emp);
        return true;
    }

    @Transactional
    public boolean changeAddressByEmail(String email, String civic, String street, String city, String postalCode, String province, String country) {
        if (email == null || !email.contains("@")) throw new IllegalArgumentException("Please enter a valid email");
        if (civic == null || civic.equals("0") || civic.length() == 0 || street == null || street.length() == 0 || city == null || city.length() == 0 ||
                postalCode == null || postalCode.length() == 0 || province == null || province.length() != 2 || country == null || country.length() == 0) throw new IllegalArgumentException("Please enter a valid address");
        if (!employeeRepository.existsByEmail(email)) throw new NullPointerException("Cannot find Employee with given email");
        Employee emp = employeeRepository.findEmployeeByEmail(email);
        Address a = emp.getAddress();
        if (!a.setCivicNumber(civic)) return false;
        if (!a.setStreet(street)) return false;
        if (!a.setCity(city)) return false;
        if (!a.setPostalCode(postalCode)) return false;
        if (!a.setProvince(province)) return false;
        if (!a.setCountry(country)) return false;
        emp.setAddress(a);
        addressRepository.save(a);
        employeeRepository.save(emp);
        return true;
    }

    // Check for Booking and Hour
    @Transactional
    public boolean addBooking(int libraryCardID, Booking booking) {
        if (libraryCardID <= 0) throw new IllegalArgumentException("Please enter a valid libraryCardID");
        if (booking == null) throw new IllegalArgumentException("Please enter a valid booking");
        Employee emp = employeeRepository.findEmployeeByLibraryCardID(libraryCardID);
        if (emp == null) throw new NullPointerException("Cannot find Employee with this libraryCardID");
        if (emp.addUserbooking(booking)) {
            employeeRepository.save(emp);
            return true;
        }
        return false;
    }
}

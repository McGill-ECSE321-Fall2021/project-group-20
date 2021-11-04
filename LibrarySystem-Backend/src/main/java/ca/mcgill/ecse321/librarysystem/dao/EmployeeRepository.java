package ca.mcgill.ecse321.librarysystem.dao;

import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.model.Booking;
import ca.mcgill.ecse321.librarysystem.model.Employee;
import ca.mcgill.ecse321.librarysystem.model.Hour;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, String> {

    Employee findEmployeeByLibraryCardID(int libraryCardID);

    List<Employee> findEmployeesByRole(Employee.Role role);

    Employee findEmployeeByUsername(String username);

    Employee findEmployeeByEmail(String email);

    List<Employee> findEmployeesByFirstName(String firstName);

    List<Employee> findEmployeesByLastName(String lastName);

    List<Employee> findEmployeesByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> findEmployeesByDemeritPts(int demeritPts);

    List<Employee> findEmployeesByAddress(Address address);

    List<Employee> findEmployeesByEmployeehour(Hour employeehour);

    Employee findEmployeeByUserbooking(Booking userbooking);

    boolean existsByLibraryCardID(int libraryCardID);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUserbooking(Booking userbooking);

    boolean existsByFirstNameAndLastName(String firstName, String lastName);
}

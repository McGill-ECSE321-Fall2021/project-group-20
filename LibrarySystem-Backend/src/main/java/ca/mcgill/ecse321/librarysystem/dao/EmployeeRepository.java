package ca.mcgill.ecse321.librarysystem.dao;

public interface EmployeeRepository extends UserRepository {
	/* As an Employee is a subclass of User, this repository is used to extend the User's repository interface onto Employee */
}

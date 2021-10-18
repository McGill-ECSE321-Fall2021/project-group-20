package ca.mcgill.ecse321.librarysystem.dao;

public interface CustomerRepository extends UserRepository {
	/* As a Customer is a subclass of User, this repository is used to extend the User's repository interface onto Customer */
}

package ca.mcgill.ecse321.librarysystem.dao;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "customer_data", path = "customer_data")
public interface CustomerRepository extends UserRepository {
	/* As a Customer is a subclass of User, this repository is used to extend the User's repository interface onto Customer */
}

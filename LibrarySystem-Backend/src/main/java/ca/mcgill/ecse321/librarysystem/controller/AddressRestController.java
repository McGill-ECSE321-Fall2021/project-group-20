package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.model.Address;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class AddressRestController {
    private AddressDto convertToDto(Address a) {
        if (a == null) throw new NullPointerException("Cannot find Address");
        return new AddressDto(a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(), a.getCountry());
    }
}

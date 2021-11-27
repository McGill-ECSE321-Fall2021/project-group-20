package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AddressRestController {

    @Autowired
    private AddressService addressService;

    /**
     * Converts a model class to a dto class.
     * @param a - Object of type Address.
     * @return an address dto object.
     */
    private AddressDto convertToDto(Address a) {
        if (a == null) throw new NullPointerException("Cannot find Address");
        return new AddressDto(a.getAddressID(), a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(),
                a.getCountry());
    }

    /**
     * Creates an address.
     * @param civicNumber
     * @param street
     * @param city
     * @param postalCode
     * @param province
     * @param country
     * @return an address object with given set of parameters.
     */
    @PostMapping(value = {"/address/create","/address/create/"} )
    public ResponseEntity createAddress(@RequestParam String civicNumber, @RequestParam String street,
                                        @RequestParam String city, @RequestParam String postalCode,
                                        @RequestParam String province, @RequestParam String country) {
        AddressDto addressDto;
        try {
            Address a = addressService.createAddress(civicNumber, street, city, postalCode, province, country);
            addressDto = convertToDto(a);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }

        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    /**
     * Deletes an address with the given id parameter.
     * @param id
     * @return a message "Address deleted" if successfully deleted, else "Could not delete address".
     */
    @DeleteMapping(value = {"/address/{id}", "/address/{id}/"})
    public ResponseEntity deleteAddress(@PathVariable("id") String id) {
        boolean b;
        try {
            b = addressService.deleteAddress(id);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (!b) return ResponseEntity.status(HttpStatus.OK).body("Address deleted");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Could not delete address");
    }

    /**
     * Retrieves an address with the given id parameter.
     * @param id
     * @return an address dto  object if successful, else a message "Could not find address".
     */
    @GetMapping(value = {"/address/{id}", "/address/{id}/"})
    public ResponseEntity getAddressByID(@PathVariable("id") String id) {
        Address address;
        try {
            address = addressService.getAddressByAddressID(id);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (address == null) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not find address");
        return new ResponseEntity<>(convertToDto(address), HttpStatus.OK);
    }

    /**
     * Retrieves a list of addresses given the civic number.
     * @param civicNumber
     * @return a list of addresses if successful, else a message "Could not find addresses".
     */
    @GetMapping(value = {"/address/civic/{civicNumber}", "/address/civic/{civicNumber}/"})
    public ResponseEntity getAddressByCivicNumber(@PathVariable("civicNumber") String civicNumber) {
        List<AddressDto> addressDtos = new ArrayList<>();
        List<Address> addresses;
        try {
            addresses = addressService.getAddressByCivicNumber(civicNumber);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Address a : addresses) {
            addressDtos.add(convertToDto(a));
        }
        if (addressDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not find addresses");
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    /**
     * Retrieves a list of addresses given the street.
     * @param street
     * @return a list of addresses if successful, else a message "Could not find addresses".
     */
    @GetMapping(value = {"/address/street/{street}", "/address/street/{street}/"})
    public ResponseEntity getAddressByCStreet(@PathVariable("street") String street) {
        List<AddressDto> addressDtos = new ArrayList<>();
        List<Address> addresses;
        try {
            addresses = addressService.getAddressByStreet(street);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Address a : addresses) {
            addressDtos.add(convertToDto(a));
        }
        if (addressDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not find addresses");
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    /**
     * Retrieves a list of addresses given the city.
     * @param city
     * @return a list of addresses if successful, else a message "Could not find addresses".
     */
    @GetMapping(value = {"/address/city/{city}", "/address/city/{city}/"})
    public ResponseEntity getAddressByCity(@PathVariable("city") String city) {
        List<AddressDto> addressDtos = new ArrayList<>();
        List<Address> addresses;
        try {
            addresses = addressService.getAddressByCity(city);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Address a : addresses) {
            addressDtos.add(convertToDto(a));
        }
        if (addressDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not find addresses");
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    /**
     * Retrieves a list of addresses given the postal code.
     * @param postalCode
     * @return a list of addresses if successful, else a message "Could not find addresses".
     */
    @GetMapping(value = {"/address/post/{postalCode}", "/address/post/{postalCode}/"})
    public ResponseEntity getAddressByPostalCode(@PathVariable("postalCode") String postalCode) {
        List<AddressDto> addressDtos = new ArrayList<>();
        List<Address> addresses;
        try {
            addresses = addressService.getAddressByPostalCode(postalCode);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Address a : addresses) {
            addressDtos.add(convertToDto(a));
        }
        if (addressDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not find addresses");
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    /**
     * Retrieves a list of addresses given the province.
     * @param province
     * @return a list of addresses if successful, else a message "Could not find addresses".
     */
    @GetMapping(value = {"/address/province/{province}", "/address/province/{province}/"})
    public ResponseEntity getAddressByProvince(@PathVariable("province") String province) {
        List<AddressDto> addressDtos = new ArrayList<>();
        List<Address> addresses;
        try {
            addresses = addressService.getAddressByProvince(province);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Address a : addresses) {
            addressDtos.add(convertToDto(a));
        }
        if (addressDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not find addresses");
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    /**
     * Retrieves a list of addresses given the country.
     * @param country
     * @return a list of addresses if successful, else a message "Could not find addresses".
     */
    @GetMapping(value = {"/address/country/{country}", "/address/country/{country}/"})
    public ResponseEntity getAddressByCountry(@PathVariable("country") String country) {
        List<AddressDto> addressDtos = new ArrayList<>();
        List<Address> addresses;
        try {
            addresses = addressService.getAddressByCountry(country);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Address a : addresses) {
            addressDtos.add(convertToDto(a));
        }
        if (addressDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not find addresses");
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    /**
     * Retrieves a list of addresses given the parameters.
     * @param civicNumber
     * @param street
     * @param city
     * @param postalCode
     * @param province
     * @param country
     * @return a list of addresses if successful, else a message "Could not find addresses".
     */
    @GetMapping(value = {"/address/fullInfo", "/address/fullInfo/"})
    public ResponseEntity getAddressByFullInfo(@RequestParam String civicNumber, @RequestParam String street,
                                               @RequestParam String city, @RequestParam String postalCode,
                                               @RequestParam String province, @RequestParam String country) {
        List<AddressDto> addressDtos = new ArrayList<>();
        List<Address> addresses;
        try {
            addresses = addressService.getAddressByFullInfo(civicNumber, street, city, postalCode, province, country);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        for (Address a : addresses) {
            addressDtos.add(convertToDto(a));
        }
        if (addressDtos.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Could not find addresses");
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    /**
     * Retrieves all addresses.
     * @return a list of addresses.
     */
    @GetMapping(value = {"/addresses", "/addresses/"})
    public ResponseEntity getAllAddresses() {
        List<AddressDto> addressDtos = new ArrayList<>();
        List<Address> addresses;
        try {
            addresses = addressService.getAllAddresses();
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        if (addresses.size() == 0) return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Cannot find any addresses");
        for (Address a : addressService.getAllAddresses()) {
            addressDtos.add(convertToDto(a));
        }
        return new ResponseEntity<>(addressDtos, HttpStatus.OK);
    }

    /**
     * Updates an address with given id.
     * @param id
     * @param civicNumber
     * @param street
     * @param city
     * @param postalCode
     * @param province
     * @param country
     * @return an address dto objects with the associated parameters.
     */
    @PutMapping(value = {"/address/update/{id}", "/address/update/{id}/"})
    public ResponseEntity modifyAddress(@PathVariable("id") String id, @RequestParam String civicNumber, @RequestParam String street,
                                        @RequestParam String city, @RequestParam String postalCode,
                                        @RequestParam String province, @RequestParam String country) {
        Address address;
        try {
            address = addressService.updateAddress(id, civicNumber, street, city, postalCode, province, country);
        } catch (IllegalArgumentException | NullPointerException msg) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg.getMessage());
        }
        return new ResponseEntity<>(convertToDto(address), HttpStatus.OK);
    }
}

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

    private AddressDto convertToDto(Address a) {
        if (a == null) throw new NullPointerException("Cannot find Address");
        return new AddressDto(a.getAddressID(), a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(),
                a.getCountry());
    }

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

    @GetMapping(value = {"/address/{civicNumber}", "/address/{civicNumber}/"})
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

    @GetMapping(value = {"/address/{street}", "/address/{street}/"})
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

    @GetMapping(value = {"/address/{city}", "/address/{city}/"})
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

    @GetMapping(value = {"/address/{postalCode}", "/address/{postalCode}/"})
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

    @GetMapping(value = {"/address/{province}", "/address/{province}/"})
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

    @GetMapping(value = {"/address/{country}", "/address/{country}/"})
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

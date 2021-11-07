package ca.mcgill.ecse321.librarysystem.controller;

import ca.mcgill.ecse321.librarysystem.dto.AddressDto;
import ca.mcgill.ecse321.librarysystem.model.Address;
import ca.mcgill.ecse321.librarysystem.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AddressRestController {

    @Autowired
    private AddressService addressService;

    public AddressDto convertToDto(Address a) {
        if (a == null) throw new NullPointerException("Cannot find Address");
        return new AddressDto(a.getCivicNumber(), a.getStreet(), a.getCity(), a.getPostalCode(), a.getProvince(),
                a.getCountry());
    }

    @PostMapping(value = {"/address/create","/address/create/"} )
    public AddressDto createAddress(@RequestParam String civicNumber, @RequestParam String street,
                                     @RequestParam String city, @RequestParam String postalCode,
                                     @RequestParam String province, @RequestParam String country)
            throws IllegalArgumentException {
        return convertToDto(addressService.createAddress(civicNumber, street, city, postalCode, province, country));
    }

    @DeleteMapping(value = {"/address/id", "/address/id/"})
    public boolean deleteAddress(@PathVariable("id") String id) throws IllegalArgumentException,
            NullPointerException {
        return addressService.deleteAddress(id);
    }

    @GetMapping(value = {"/address/id", "/address/id/"})
    public AddressDto getAddressByID(@PathVariable("id") String id) throws IllegalArgumentException,
            NullPointerException {
        return convertToDto(addressService.getAddressByAddressID(id));
    }

    @GetMapping(value = {"/address/civicNumber", "/address/civicNumber/"})
    public List<AddressDto> getAddressByCivicNumber(@PathVariable("civicNumber") String civicNumber)
            throws IllegalArgumentException, NullPointerException {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address a : addressService.getAddressByCivicNumber(civicNumber)) {
            addressDtos.add(convertToDto(a));
        }
        return addressDtos;
    }

    @GetMapping(value = {"/address/street", "/address/street/"})
    public List<AddressDto> getAddressByCStreet(@PathVariable("street") String street)
            throws IllegalArgumentException, NullPointerException {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address a : addressService.getAddressByStreet(street)) {
            addressDtos.add(convertToDto(a));
        }
        return addressDtos;
    }

    @GetMapping(value = {"/address/city", "/address/city/"})
    public List<AddressDto> getAddressByCity(@PathVariable("city") String city)
            throws IllegalArgumentException, NullPointerException {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address a : addressService.getAddressByCity(city)) {
            addressDtos.add(convertToDto(a));
        }
        return addressDtos;
    }

    @GetMapping(value = {"/address/postalCode", "/address/postalCode/"})
    public List<AddressDto> getAddressByPostalCode(@PathVariable("postalCode") String postalCode)
            throws IllegalArgumentException, NullPointerException {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address a : addressService.getAddressByPostalCode(postalCode)) {
            addressDtos.add(convertToDto(a));
        }
        return addressDtos;
    }

    @GetMapping(value = {"/address/province", "/address/province/"})
    public List<AddressDto> getAddressByProvince(@PathVariable("province") String province)
            throws IllegalArgumentException, NullPointerException {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address a : addressService.getAddressByProvince(province)) {
            addressDtos.add(convertToDto(a));
        }
        return addressDtos;
    }

    @GetMapping(value = {"/address/country", "/address/country/"})
    public List<AddressDto> getAddressByCountry(@PathVariable("country") String country)
            throws IllegalArgumentException, NullPointerException {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address a : addressService.getAddressByCountry(country)) {
            addressDtos.add(convertToDto(a));
        }
        return addressDtos;
    }

    @GetMapping(value = {"/address/fullInfo", "/address/fullInfo/"})
    public List<AddressDto> getAddressByFullInfo(@RequestParam String civicNumber, @RequestParam String street,
                                                  @RequestParam String city, @RequestParam String postalCode,
                                                  @RequestParam String province, @RequestParam String country)
            throws IllegalArgumentException, NullPointerException {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address a : addressService.getAddressByFullInfo(civicNumber, street, city, postalCode, province, country)) {
            addressDtos.add(convertToDto(a));
        }
        return addressDtos;
    }

    @GetMapping(value = {"/addresses", "/addresses/"})
    public List<AddressDto> getAllAddresses() {
        List<AddressDto> addressDtos = new ArrayList<>();
        for (Address a : addressService.getAllAddresses()) {
            addressDtos.add(convertToDto(a));
        }
        return addressDtos;
    }

    @PutMapping(value = {"/address/update/id", "/address/update/id/"})
    public AddressDto modifyAddress(@PathVariable("id") String id, @RequestParam String civicNumber, @RequestParam String street,
                                    @RequestParam String city, @RequestParam String postalCode,
                                    @RequestParam String province, @RequestParam String country)
        throws IllegalArgumentException {
        return convertToDto(addressService.updateAddress(id, civicNumber, street, city, postalCode, province, country));
    }


}

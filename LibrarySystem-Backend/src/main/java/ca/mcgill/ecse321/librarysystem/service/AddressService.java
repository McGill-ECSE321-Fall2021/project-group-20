package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LibrarySystemRepository librarySystemRepository;

    @Transactional
    public Address createAddress() {
        Address address = new Address();
        addressRepository.save(address);
        return address;
    }

    @Transactional
    public Address createAddress(String civic, String street, String city, String postalCode, String province, String country) {
        if (civic == null || street == null || city == null || postalCode == null || province == null || country == null) {
            throw new IllegalArgumentException("Any address info cannot be null");
        }
        Address address = new Address(civic, street, city, postalCode, province, country);
        addressRepository.save(address);
        return address;
    }

    @Transactional
    public boolean deleteAddress(String addressID) {
        if (addressID == null) throw new IllegalArgumentException("AddressID cannot be null");
        Address address = addressRepository.findByAddressID(addressID);
        if (address == null) throw new NullPointerException("Address with address ID cannot be deleted since it does not exists");
        //if (userRepository.findUserByAddress(address) == null) throw new IllegalArgumentException("Cannot delete address associated with user since user does not exists");
        //if (librarySystemRepository.findLibrarySystemByBusinessaddress(address) == null) throw new IllegalArgumentException("Cannot delete address associated with the System since System does not exists");
        addressRepository.delete(address);
        return addressRepository.existsByAddressID(addressID);
    }

    @Transactional
    public List<Address> getAllAddresses() {
        List<Address> addresses = new ArrayList<>();
        for (Address a: addressRepository.findAll()) {
            addresses.add(a);
        }
        return addresses;
    }

    @Transactional
    public Address getAddressByAddressID(String addressID) {
        if (addressID == null) throw new IllegalArgumentException("AddressId cannot be null");
        Address address = addressRepository.findByAddressID(addressID);
        if (address == null) throw new NullPointerException("Address with address ID is not found");
        return address;
    }

    @Transactional
    public List<Address> getAddressByCivicNumber(String civicNumber) {
        if (civicNumber == null) throw new IllegalArgumentException("Civic Number can't be null");
        List<Address> addresses = addressRepository.findAddressByCivicNumber(civicNumber);
        if (addresses.size() == 0) throw new NullPointerException("No address with that civic number found");
        return addressRepository.findAddressByCivicNumber(civicNumber);
    }

    @Transactional
    public List<Address> getAddressByStreet(String street) {
        if (street == null) throw new IllegalArgumentException("Street can't be null");
        if (addressRepository.findAddressByStreet(street).size() == 0) throw new NullPointerException("No addresses with that street");
        return addressRepository.findAddressByStreet(street);
    }

    @Transactional
    public List<Address> getAddressByCity(String city) {
        if (city == null) throw new IllegalArgumentException("City can't be null");
        if (addressRepository.findAddressByCity(city).size() == 0) throw new NullPointerException("No addresses with that city");
        return addressRepository.findAddressByCity(city);
    }

    @Transactional
    public List<Address> getAddressByPostalCode(String postalCode) {
        if (postalCode == null) throw new IllegalArgumentException("Postal code can't be null");
        if (addressRepository.findAddressByPostalCode(postalCode).size() == 0) throw new NullPointerException("No addresses with that postal code");
        return addressRepository.findAddressByPostalCode(postalCode);
    }

    @Transactional
    public List<Address> getAddressByProvince(String province) {
        if (province == null) throw new IllegalArgumentException("Province can't be null");
        if (addressRepository.findAddressByProvince(province).size() == 0) throw new NullPointerException("No addresses found with that province");
        return addressRepository.findAddressByProvince(province);
    }

    @Transactional
    public List<Address> getAddressByCountry(String country) {
        if (country == null) throw new IllegalArgumentException("Country can't be null");
        if (addressRepository.findAddressByCountry(country).size() == 0) throw new NullPointerException("No addresses found with that country");
        return addressRepository.findAddressByCountry(country);
    }

    @Transactional
    public List<Address> getAddressByFullInfo(String CivicNumber, String street, String city, String postalCode, String province,
                              String country) {
        if (CivicNumber == null || street == null || city == null || postalCode == null || province == null || country == null) {
            throw new IllegalArgumentException("No info can be null");
        }
        if (addressRepository.findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(CivicNumber,
                street, city, postalCode, province, country).size() == 0) throw new NullPointerException("No addresses found");

        return addressRepository.findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(
                CivicNumber, street, city, postalCode, province, country);
    }

    @Transactional
    public boolean checkAddressExists(String addressID) {
        if (addressID == null) throw new IllegalArgumentException("Address id can't be null");
        return addressRepository.existsByAddressID(addressID);
    }

    @Transactional
    public boolean checkAddressExists(String CivicNumber, String street, String city, String postalCode,
                                      String province, String country) {
        if (CivicNumber == null || street == null || city == null || postalCode == null || province == null || country == null) {
            throw new IllegalArgumentException("Info can't be null");
        }
            return addressRepository.existsByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(CivicNumber,
                street, city, postalCode, province, country);
    }

    @Transactional
    public Address updateAddress(String addressID, String civic, String street, String city, String postalCode,
                                 String province, String country) {

        if (addressID == null || civic == null || street == null || city == null || postalCode == null || province == null || country == null) {
            throw new IllegalArgumentException("Info can't be null");
        }
        Address address = getAddressByAddressID(addressID);
        address.setCivicNumber(civic);
        address.setStreet(street);
        address.setCity(city);
        address.setPostalCode(postalCode);
        address.setProvince(province);
        address.setCountry(country);
        addressRepository.save(address);
        return address;
    }
}

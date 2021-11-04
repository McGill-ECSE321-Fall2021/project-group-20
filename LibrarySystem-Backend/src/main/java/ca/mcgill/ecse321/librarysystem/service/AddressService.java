package ca.mcgill.ecse321.librarysystem.service;

import ca.mcgill.ecse321.librarysystem.dao.*;
import ca.mcgill.ecse321.librarysystem.model.*;
import org.apache.tomcat.jni.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import static org.hibernate.internal.util.collections.ArrayHelper.toList;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public Address createAddress() {
        Address address = new Address();
        addressRepository.save(address);
        return address;
    }

    @Transactional
    public boolean deleteAddress(String addressID) {
        Address address = addressRepository.findByAddressID(addressID);
        if (address == null) throw new NullPointerException("Address with address ID cannot be deleted");
        addressRepository.delete(address);
        return addressRepository.existsByAddressID(addressID);
    }

    @Transactional
    public Address getAddressByAddressID(String addressID) {
        Address address = addressRepository.findByAddressID(addressID);
        if (address == null) throw new NullPointerException("Address with address ID is not found");
        return address;
    }

    @Transactional
    public List<Address> getAddressByCivicNumber(String civicNumber) {
        return toList(addressRepository.findAddressByCivicNumber(civicNumber));
    }

    @Transactional
    public List<Address> getAddressByStreet(String street) {
        return toList(addressRepository.findAddressByStreet(street));
    }

    @Transactional
    public List<Address> getAddressByCity(String city) {
        return toList(addressRepository.findAddressByCity(city));
    }

    @Transactional
    public List<Address> getAddressByPostalCode(String postalCode) {
        return toList(addressRepository.findAddressByPostalCode(postalCode));
    }

    @Transactional
    public List<Address> getAddressByProvince(String province) {
        return toList(addressRepository.findAddressByPostalCode(province));
    }

    @Transactional
    public List<Address> getAddressByCountry(String country) {
        return toList(addressRepository.findAddressByCountry(country));
    }

    @Transactional
    public List<Address> getAddressByFullInfo(String CivicNumber, String street, String city, String postalCode, String province,
                              String country) {
        return toList(addressRepository.findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(
                CivicNumber, street, city, postalCode, province, country));
    }

    @Transactional
    public boolean checkAddressExists(String addressID) {
        return addressRepository.existsByAddressID(addressID);
    }

    @Transactional
    public boolean checkAddressExists(String CivicNumber, String street, String city, String postalCode,
                                      String province, String country) {
        return addressRepository.existsByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(CivicNumber,
                street, city, postalCode, province, country);
    }
}
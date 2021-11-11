package ca.mcgill.ecse321.librarysystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


import ca.mcgill.ecse321.librarysystem.model.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import ca.mcgill.ecse321.librarysystem.dao.AddressRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TestAddressService {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    /**
     * Setting up the mock address 1 object.
     */
    private static final String ADDRESS_ID = "ADDRESS1";
    private static final String CIVIC_NUM = "4609";
    private static final String STREET = "Sherbrooke";
    private static final String CITY = "Montreal";
    private static final String POSTAL_CODE = "ABC DEF";
    private static final String PROVINCE = "QC";
    private static final String COUNTRY = "Canada";

    /**
     * Setting up the mock address 2 object.
     */
    private static final String ADDRESS_ID_2 = "ADDRESS2";
    private static final String CIVIC_NUM_2 = "4160";
    private static final String STREET_2 = "Chomedey";
    private static final String CITY_2 = "Laval";
    private static final String POSTAL_CODE_2 = "ABG ABB";
    private static final String PROVINCE_2 = "QC";
    private static final String COUNTRY_2 = "Canada";

    @BeforeEach
    public void setMockOutput() {
        lenient().when(addressRepository.findByAddressID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ADDRESS_ID)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                return address;
            }
            else if (invocation.getArgument(0).equals(ADDRESS_ID_2)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID_2);
                address.setCivicNumber(CIVIC_NUM_2);
                address.setStreet(STREET_2);
                address.setCity(CITY_2);
                address.setPostalCode(POSTAL_CODE_2);
                address.setProvince(PROVINCE_2);
                address.setCountry(COUNTRY_2);
                return address;
            }
            return null;
        });

        lenient().when(addressRepository.findAddressByCivicNumber(anyString())).thenAnswer((InvocationOnMock invocation)
                -> {
            List<Address> addresses = new ArrayList<>();
            if (invocation.getArgument(0).equals(CIVIC_NUM) && invocation.getArgument(0).equals(CIVIC_NUM_2)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);

                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(CIVIC_NUM)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(CIVIC_NUM_2)) {
                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            return new ArrayList<>();
        });

        lenient().when(addressRepository.findAddressByStreet(anyString())).thenAnswer((InvocationOnMock invocation)
                -> {
            List<Address> addresses = new ArrayList<>();
            if (invocation.getArgument(0).equals(STREET) && invocation.getArgument(0).equals(STREET_2)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);

                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(STREET)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(STREET_2)) {
                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            return new ArrayList<>();
        });

        lenient().when(addressRepository.findAddressByCity(anyString())).thenAnswer((InvocationOnMock invocation)
                -> {
            List<Address> addresses = new ArrayList<>();
            if (invocation.getArgument(0).equals(CITY) && invocation.getArgument(0).equals(CITY_2)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);

                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(CITY)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(CITY_2)) {
                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            return new ArrayList<>();
        });

        lenient().when(addressRepository.findAddressByPostalCode(anyString())).thenAnswer((InvocationOnMock invocation)
                -> {
            List<Address> addresses = new ArrayList<>();
            if (invocation.getArgument(0).equals(POSTAL_CODE) && invocation.getArgument(0).equals(POSTAL_CODE_2)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);

                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(POSTAL_CODE)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(POSTAL_CODE_2)) {
                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            return new ArrayList<>();
        });

        lenient().when(addressRepository.findAddressByProvince(anyString())).thenAnswer((InvocationOnMock invocation)
                -> {
            List<Address> addresses = new ArrayList<>();
            if (invocation.getArgument(0).equals(PROVINCE) && invocation.getArgument(0).equals(PROVINCE_2)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);

                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(PROVINCE)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(PROVINCE_2)) {
                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            return new ArrayList<>();
        });

        lenient().when(addressRepository.findAddressByCountry(anyString())).thenAnswer((InvocationOnMock invocation)
                -> {
            List<Address> addresses = new ArrayList<>();
            if (invocation.getArgument(0).equals(COUNTRY) && invocation.getArgument(0).equals(COUNTRY_2)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);

                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(COUNTRY)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);
                return addresses;
            }
            else if (invocation.getArgument(0).equals(COUNTRY_2)) {
                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }

            return new ArrayList<>();
        });

        lenient().when(addressRepository.findAddressByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(
                anyString(), anyString(), anyString(), anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation)
                -> {
            List<Address> addresses = new ArrayList<>();
            if (invocation.getArgument(0).equals(CIVIC_NUM) && invocation.getArgument(1).equals(STREET)
                    && invocation.getArgument(2).equals(CITY) && invocation.getArgument(3).equals(POSTAL_CODE) &&
                    invocation.getArgument(4).equals(PROVINCE) && invocation.getArgument(5).equals(COUNTRY)) {
                Address address = new Address();
                address.setAddressID(ADDRESS_ID);
                address.setCivicNumber(CIVIC_NUM);
                address.setStreet(STREET);
                address.setCity(CITY);
                address.setPostalCode(POSTAL_CODE);
                address.setProvince(PROVINCE);
                address.setCountry(COUNTRY);
                addresses.add(address);
                return addresses;
            }
            if (invocation.getArgument(0).equals(CIVIC_NUM_2) && invocation.getArgument(1).equals(STREET_2)
                    && invocation.getArgument(2).equals(CITY_2) && invocation.getArgument(3).equals(POSTAL_CODE_2) &&
                    invocation.getArgument(4).equals(PROVINCE_2) && invocation.getArgument(5).equals(COUNTRY_2)) {
                Address address2 = new Address();
                address2.setAddressID(ADDRESS_ID_2);
                address2.setCivicNumber(CIVIC_NUM_2);
                address2.setStreet(STREET_2);
                address2.setCity(CITY_2);
                address2.setPostalCode(POSTAL_CODE_2);
                address2.setProvince(PROVINCE_2);
                address2.setCountry(COUNTRY_2);
                addresses.add(address2);
                return addresses;
            }
            return new ArrayList<>();
        });

        lenient().when(addressRepository.existsByAddressID(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            if (invocation.getArgument(0).equals(ADDRESS_ID) || invocation.getArgument(0).equals(ADDRESS_ID_2)) {
                return true;
            }
            return false;
        });

        lenient().when(addressRepository.existsByCivicNumberAndStreetAndCityAndPostalCodeAndProvinceAndCountry(anyString(),
                anyString(), anyString(), anyString(), anyString(), anyString())).thenAnswer((InvocationOnMock invocation) -> {

            if ((invocation.getArgument(0).equals(CIVIC_NUM) && invocation.getArgument(1).equals(STREET)
                    && invocation.getArgument(2).equals(CITY) && invocation.getArgument(3).equals(POSTAL_CODE) &&
                    invocation.getArgument(4).equals(PROVINCE) && invocation.getArgument(5).equals(COUNTRY))
            || (invocation.getArgument(0).equals(CIVIC_NUM_2) && invocation.getArgument(1).equals(STREET_2)
                    && invocation.getArgument(2).equals(CITY_2) && invocation.getArgument(3).equals(POSTAL_CODE_2) &&
                    invocation.getArgument(4).equals(PROVINCE_2) && invocation.getArgument(5).equals(COUNTRY_2))) {
                return true;
            }
            return false;
        });

        lenient().when(addressRepository.findAll()).thenAnswer((InvocationOnMock invocation) -> {
            List<Address> addresses = new ArrayList<>();

//            Address address1 = new Address();
//            address1.setAddressID(ADDRESS_ID);
//            address1.setCivicNumber(CIVIC_NUM);
//            address1.setStreet(STREET);
//            address1.setCity(CITY);
//            address1.setPostalCode(POSTAL_CODE);
//            address1.setProvince(PROVINCE);
//            address1.setCountry(COUNTRY);
//            addresses.add(address1);

            Address address2 = new Address();
            address2.setAddressID(ADDRESS_ID_2);
            address2.setCivicNumber(CIVIC_NUM_2);
            address2.setStreet(STREET_2);
            address2.setCity(CITY_2);
            address2.setPostalCode(POSTAL_CODE_2);
            address2.setProvince(PROVINCE_2);
            address2.setCountry(COUNTRY_2);
            addresses.add(address2);

            return addresses;
        });

        Answer<?> returnParamAsAnswer = (InvocationOnMock invocation) -> {return invocation.getArgument(0);};
        lenient().when(addressRepository.save(any(Address.class))).thenAnswer(returnParamAsAnswer);
    }

    @Test
    public void testCreateAddressSuccessful() {

        assertEquals(1, addressService.getAllAddresses().size());
        Address address = null;
        try {
            address = addressService.createAddress(CIVIC_NUM, STREET, CITY, POSTAL_CODE, PROVINCE, COUNTRY);
            address.setAddressID(ADDRESS_ID);
        } catch (Exception msg) {
            fail(msg.getMessage());
        }
        assertNotNull(address);
        assertEquals(ADDRESS_ID, address.getAddressID());
        assertEquals(CITY, address.getCity());
        assertEquals(COUNTRY, address.getCountry());
        assertEquals(CIVIC_NUM, address.getCivicNumber());
        assertEquals(PROVINCE, address.getProvince());
        assertEquals(POSTAL_CODE, address.getPostalCode());
        assertEquals(STREET, address.getStreet());
    }

    @Test
    public void testCreateAddressFail() {
        assertEquals(1, addressService.getAllAddresses().size());

        Address address = null;
        String error = null;
        try {
            address = addressService.createAddress(null, STREET, CITY, POSTAL_CODE, PROVINCE, COUNTRY);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(address);
        assertEquals("Any address info cannot be null", error);
    }

    @Test
    public void testDeleteAddressSuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());

        int numOfAddressesBefore = addressService.getAllAddresses().size();
        try {
            addressService.deleteAddress(ADDRESS_ID_2);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        int numOfAddressesAfter = numOfAddressesBefore - 1;
        assertEquals(0 , numOfAddressesAfter);
    }

    @Test
    public void testDeleteAddressFailInvalidID() {
        assertEquals(1, addressService.getAllAddresses().size());
        String error = null;

        try {
            addressService.deleteAddress(null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertEquals("AddressID cannot be null", error);
    }

    @Test
    public void testDeleteAddressFailInvalidAddress() {
        assertEquals(1, addressService.getAllAddresses().size());
        String error = null;

        try {
            addressService.deleteAddress("1234");
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertEquals("Address with address ID cannot be deleted since it does not exists", error);
    }

    @Test
    public void testGetAddressByAddressIDSuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        Address address = null;
        try {
            address = addressService.getAddressByAddressID("ADDRESS2");
        } catch (NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(address);
        assertEquals(ADDRESS_ID_2, address.getAddressID());
        assertEquals(CITY_2, address.getCity());
        assertEquals(COUNTRY_2, address.getCountry());
        assertEquals(CIVIC_NUM_2, address.getCivicNumber());
        assertEquals(PROVINCE_2, address.getProvince());
        assertEquals(POSTAL_CODE_2, address.getPostalCode());
        assertEquals(STREET_2, address.getStreet());
    }

    @Test
    public void testGetAddressByAddressIDFailInvalidID() {
        assertEquals(1, addressService.getAllAddresses().size());
        Address address = null;
        String error = null;
        try {
            address = addressService.getAddressByAddressID(null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertNull(address);
        assertEquals("AddressId cannot be null", error);
    }

    @Test
    public void testGetAddressByAddressIDFailInvalidAddress() {
        assertEquals(1, addressService.getAllAddresses().size());
        Address address = null;
        String error = null;
        try {
            address = addressService.getAddressByAddressID("1234");
        } catch (NullPointerException msg) {
            error = msg.getMessage();
        }
        assertNull(address);
        assertEquals("Address with address ID is not found", error);
    }

    @Test
    public void testGetAddressByCivicNumberSuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        List<Address> addresses = null;
        try {
            addresses = addressService.getAddressByCivicNumber(CIVIC_NUM_2);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(addresses);
        assertEquals(ADDRESS_ID_2, addresses.get(0).getAddressID());
        assertEquals(CITY_2, addresses.get(0).getCity());
        assertEquals(COUNTRY_2, addresses.get(0).getCountry());
        assertEquals(CIVIC_NUM_2, addresses.get(0).getCivicNumber());
        assertEquals(PROVINCE_2, addresses.get(0).getProvince());
        assertEquals(POSTAL_CODE_2, addresses.get(0).getPostalCode());
        assertEquals(STREET_2, addresses.get(0).getStreet());
    }

    @Test
    public void testGetAddressByStreetSuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        List<Address> addresses = null;
        try {
            addresses = addressService.getAddressByStreet(STREET_2);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(addresses);
        assertEquals(ADDRESS_ID_2, addresses.get(0).getAddressID());
        assertEquals(CITY_2, addresses.get(0).getCity());
        assertEquals(COUNTRY_2, addresses.get(0).getCountry());
        assertEquals(CIVIC_NUM_2, addresses.get(0).getCivicNumber());
        assertEquals(PROVINCE_2, addresses.get(0).getProvince());
        assertEquals(POSTAL_CODE_2, addresses.get(0).getPostalCode());
        assertEquals(STREET_2, addresses.get(0).getStreet());
    }

    @Test
    public void testGetAddressByCitySuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        List<Address> addresses = null;
        try {
            addresses = addressService.getAddressByCity(CITY_2);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(addresses);
        assertEquals(ADDRESS_ID_2, addresses.get(0).getAddressID());
        assertEquals(CITY_2, addresses.get(0).getCity());
        assertEquals(COUNTRY_2, addresses.get(0).getCountry());
        assertEquals(CIVIC_NUM_2, addresses.get(0).getCivicNumber());
        assertEquals(PROVINCE_2, addresses.get(0).getProvince());
        assertEquals(POSTAL_CODE_2, addresses.get(0).getPostalCode());
        assertEquals(STREET_2, addresses.get(0).getStreet());
    }

    @Test
    public void testGetAddressByPostalCodeSuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        List<Address> addresses = null;
        try {
            addresses = addressService.getAddressByPostalCode(POSTAL_CODE_2);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(addresses);
        assertEquals(ADDRESS_ID_2, addresses.get(0).getAddressID());
        assertEquals(CITY_2, addresses.get(0).getCity());
        assertEquals(COUNTRY_2, addresses.get(0).getCountry());
        assertEquals(CIVIC_NUM_2, addresses.get(0).getCivicNumber());
        assertEquals(PROVINCE_2, addresses.get(0).getProvince());
        assertEquals(POSTAL_CODE_2, addresses.get(0).getPostalCode());
        assertEquals(STREET_2, addresses.get(0).getStreet());
    }

    @Test
    public void testGetAddressByProvinceSuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        List<Address> addresses = null;
        try {
            addresses = addressService.getAddressByProvince(PROVINCE_2);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(addresses);
        assertEquals(ADDRESS_ID, addresses.get(0).getAddressID());
        assertEquals(CITY, addresses.get(0).getCity());
        assertEquals(COUNTRY, addresses.get(0).getCountry());
        assertEquals(CIVIC_NUM, addresses.get(0).getCivicNumber());
        assertEquals(PROVINCE, addresses.get(0).getProvince());
        assertEquals(POSTAL_CODE, addresses.get(0).getPostalCode());
        assertEquals(STREET, addresses.get(0).getStreet());

        assertEquals(ADDRESS_ID_2, addresses.get(1).getAddressID());
        assertEquals(CITY_2, addresses.get(1).getCity());
        assertEquals(COUNTRY_2, addresses.get(1).getCountry());
        assertEquals(CIVIC_NUM_2, addresses.get(1).getCivicNumber());
        assertEquals(PROVINCE_2, addresses.get(1).getProvince());
        assertEquals(POSTAL_CODE_2, addresses.get(1).getPostalCode());
        assertEquals(STREET_2, addresses.get(1).getStreet());
    }

    @Test
    public void testGetAddressByCountrySuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        List<Address> addresses = null;
        try {
            addresses = addressService.getAddressByCountry(COUNTRY_2);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(addresses);
        assertEquals(ADDRESS_ID, addresses.get(0).getAddressID());
        assertEquals(CITY, addresses.get(0).getCity());
        assertEquals(COUNTRY, addresses.get(0).getCountry());
        assertEquals(CIVIC_NUM, addresses.get(0).getCivicNumber());
        assertEquals(PROVINCE, addresses.get(0).getProvince());
        assertEquals(POSTAL_CODE, addresses.get(0).getPostalCode());
        assertEquals(STREET, addresses.get(0).getStreet());

        assertEquals(ADDRESS_ID_2, addresses.get(1).getAddressID());
        assertEquals(CITY_2, addresses.get(1).getCity());
        assertEquals(COUNTRY_2, addresses.get(1).getCountry());
        assertEquals(CIVIC_NUM_2, addresses.get(1).getCivicNumber());
        assertEquals(PROVINCE_2, addresses.get(1).getProvince());
        assertEquals(POSTAL_CODE_2, addresses.get(1).getPostalCode());
        assertEquals(STREET_2, addresses.get(1).getStreet());
    }
    @Test
    public void testGetAddressByFullInfoSuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        List<Address> addresses = null;
        try {
            addresses = addressService.getAddressByFullInfo(CIVIC_NUM, STREET, CITY, POSTAL_CODE, PROVINCE, COUNTRY);
        } catch (IllegalArgumentException | NullPointerException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(addresses);
        assertEquals(ADDRESS_ID, addresses.get(0).getAddressID());
        assertEquals(CITY, addresses.get(0).getCity());
        assertEquals(COUNTRY, addresses.get(0).getCountry());
        assertEquals(CIVIC_NUM, addresses.get(0).getCivicNumber());
        assertEquals(PROVINCE, addresses.get(0).getProvince());
        assertEquals(POSTAL_CODE, addresses.get(0).getPostalCode());
        assertEquals(STREET, addresses.get(0).getStreet());
    }

    @Test
    public void checkAddressExistsSuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        Boolean exists = false;
        try {
            exists = addressService.checkAddressExists(ADDRESS_ID_2);
        } catch (IllegalArgumentException msg) {
            fail(msg.getMessage());
        }
        assertEquals(true, exists);
    }

    @Test
    public void testCheckAddressExistsFail() {
        assertEquals(1, addressService.getAllAddresses().size());
        Boolean exists = true;
        String error;
        try {
            exists = addressService.checkAddressExists("1234");
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertEquals(false, exists);
    }

    @Test
    public void testCheckAddressExistsFailInvalidID() {
        assertEquals(1, addressService.getAllAddresses().size());
        Boolean exists = true;
        String error = null;
        try {
            exists = addressService.checkAddressExists(null);
        } catch (IllegalArgumentException msg) {
            error = msg.getMessage();
        }
        assertEquals("Address id can't be null", error);
    }

    @Test
    public void testCheckAddressExistsFullInfoSuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        Boolean exists = false;
        try {
            exists = addressService.checkAddressExists(CIVIC_NUM, STREET, CITY, POSTAL_CODE, PROVINCE, COUNTRY);
        } catch (IllegalArgumentException msg) {
            fail(msg.getMessage());
        }
        assertEquals(true, exists);
    }

    @Test
    public void testUpdateAddressSuccessful() {
        assertEquals(1, addressService.getAllAddresses().size());
        Address updatedAddress = null;
        try {
            updatedAddress = addressService.updateAddress(ADDRESS_ID, "1000", "Souvenir",
                    "Laval", "LOL LOL", "QC", "Canada");
        } catch (IllegalArgumentException msg) {
            fail(msg.getMessage());
        }
        assertNotNull(updatedAddress);
        assertEquals(ADDRESS_ID, updatedAddress.getAddressID());
        assertEquals("1000", updatedAddress.getCivicNumber());
        assertEquals("Souvenir", updatedAddress.getStreet());
        assertEquals("Laval", updatedAddress.getCity());
        assertEquals("LOL LOL", updatedAddress.getPostalCode());
        assertEquals("QC", updatedAddress.getProvince());
        assertEquals("Canada", updatedAddress.getCountry());
    }
}

































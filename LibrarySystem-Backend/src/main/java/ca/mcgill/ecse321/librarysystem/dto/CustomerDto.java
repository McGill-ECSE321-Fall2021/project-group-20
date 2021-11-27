package ca.mcgill.ecse321.librarysystem.dto;


import ca.mcgill.ecse321.librarysystem.model.Booking;

import java.util.List;

public class CustomerDto extends UserDto {

    public CustomerDto() {
    }

    public CustomerDto(int libraryCardID, boolean aIsOnlineAcc, boolean isLoggedIn, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, int outstandingBalance, List<Booking> bookings) {
        super(libraryCardID, aIsOnlineAcc, isLoggedIn, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress, outstandingBalance, bookings);
    }

    public CustomerDto(int libraryCardID, boolean aIsOnlineAcc, boolean isLoggedIn, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, String username, String email, int outstandingBalance, List<Booking> bookings) {
        super(libraryCardID, aIsOnlineAcc, isLoggedIn, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress, username, email, outstandingBalance, bookings);
    }
}
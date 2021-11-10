package ca.mcgill.ecse321.librarysystem.dto;


public class CustomerDto extends UserDto {

    public CustomerDto() {
    }

    public CustomerDto(int libraryCardID, boolean aIsOnlineAcc, boolean isLoggedIn, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, int outstandingBalance) {
        super(libraryCardID, aIsOnlineAcc, isLoggedIn, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress, outstandingBalance);
    }

    public CustomerDto(int libraryCardID, boolean aIsOnlineAcc, boolean isLoggedIn, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, String username, String email, int outstandingBalance) {
        super(libraryCardID, aIsOnlineAcc, isLoggedIn, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress, username, email, outstandingBalance);
    }
}
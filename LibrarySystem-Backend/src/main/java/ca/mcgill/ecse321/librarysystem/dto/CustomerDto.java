package ca.mcgill.ecse321.librarysystem.dto;


public class CustomerDto extends UserDto {

    public CustomerDto() {
    }

    public CustomerDto(int libraryCardID, boolean aIsOnlineAcc, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress) {
        super(libraryCardID, aIsOnlineAcc, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress);
    }

    public CustomerDto(int libraryCardID, boolean aIsOnlineAcc, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, String username, String email, String password) {
        super(libraryCardID, aIsOnlineAcc, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress);
    }
}
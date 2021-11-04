package ca.mcgill.ecse321.librarysystem.dto;

public class UserDto {

    private AddressDto address;
    private int libraryCardID;
    private boolean isOnlineAcc;
    private boolean isVerified;
    private int demeritPts;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private List<BookingDto> bookings;

    public UserDto() {}

    public UserDto(int libraryCardID, boolean aIsOnlineAcc, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress) {
        this.libraryCardID = libraryCardID;
        isOnlineAcc = aIsOnlineAcc;
        firstName = aFirstName;
        lastName = aLastName;
        isVerified = aIsVerified;
        demeritPts = aDemeritPts;
        address = aAddress;
    }

    public UserDto(int libraryCardID, boolean aIsOnlineAcc, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, String username, String email, String password) {
        this.libraryCardID = libraryCardID;
        isOnlineAcc = aIsOnlineAcc;
        firstName = aFirstName;
        lastName = aLastName;
        isVerified = aIsVerified;
        demeritPts = aDemeritPts;
        address = aAddress;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public AddressDto getAddress() {
        return address;
    }

    public int getLibraryCardID() {
        return libraryCardID;
    }

    public boolean getIsOnlineAcc() {
        return isOnlineAcc;
    }

    public boolean getIsVerified() {
        return isVerified;
    }

    public int getDemeritPts() {
        return demeritPts;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public List<BookingDto> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDto> bookings) {
        this.bookings = bookings;
    }
}

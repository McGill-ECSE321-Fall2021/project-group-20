package ca.mcgill.ecse321.librarysystem.dto;

import java.util.ArrayList;
import java.util.List;

public class UserDto {

    private AddressDto address;
    private int libraryCardID;
    private boolean isLoggedIn;
    private int outstandingBalance;
    private boolean isOnlineAcc;
    private boolean isVerified;
    private int demeritPts;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private List<String> bookings;

    public UserDto() {}

    public UserDto(int libraryCardID, boolean aIsOnlineAcc, boolean aIsLoggedIn, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, int outstandingBalance) {
        this.libraryCardID = libraryCardID;
        isLoggedIn = aIsLoggedIn;
        isOnlineAcc = aIsOnlineAcc;
        firstName = aFirstName;
        lastName = aLastName;
        isVerified = aIsVerified;
        demeritPts = aDemeritPts;
        address = aAddress;
        this.outstandingBalance = outstandingBalance;
        bookings = new ArrayList<>();
    }

    public UserDto(int libraryCardID, boolean aIsOnlineAcc, boolean aIsLoggedIn, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, String username, String email, int outstandingBalance) {
        this.libraryCardID = libraryCardID;
        isOnlineAcc = aIsOnlineAcc;
        isLoggedIn = aIsLoggedIn;
        firstName = aFirstName;
        lastName = aLastName;
        isVerified = aIsVerified;
        demeritPts = aDemeritPts;
        address = aAddress;
        this.username = username;
        this.email = email;
        this.outstandingBalance = outstandingBalance;
        bookings = new ArrayList<>();
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

    public boolean getIsLoggedIn() {
        return isLoggedIn;
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

    public String getEmail() {
        return email;
    }

    public int getOutstandingBalance() {
        return  outstandingBalance;
    }

    public List<String> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDto> bookings) {
        this.bookings = new ArrayList<>();
        for (BookingDto b: bookings) {
            this.bookings.add(b.getBookingID());
        }
    }

    public int numberOfUserbooking()
    {
        int number = bookings.size();
        return number;
    }

    public static int maximumNumberOfUserbooking()
    {
        return 5;
    }

    public boolean addUserbooking(BookingDto aUserbooking)
    {
        boolean wasAdded = false;
        UserDto existingUser = aUserbooking.getUser();
        boolean isNewUser = existingUser != null && !this.equals(existingUser);
        if (isNewUser)
        {
            aUserbooking.setUser(this);
        }
        else
        {
            bookings.add(aUserbooking.getBookingID());
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeUserbooking(BookingDto aUserbooking)
    {
        boolean wasRemoved = false;
        //Unable to remove aUserbooking, as it must always have a user
        if (!this.equals(aUserbooking.getUser()))
        {
            bookings.remove(aUserbooking.getBookingID());
            wasRemoved = true;
        }
        return wasRemoved;
    }
}

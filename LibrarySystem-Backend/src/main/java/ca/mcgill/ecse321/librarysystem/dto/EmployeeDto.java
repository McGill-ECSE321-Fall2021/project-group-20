package ca.mcgill.ecse321.librarysystem.dto;

public class EmployeeDto extends UserDto {

    public enum Role { Librarian, HeadLibrarian }

    private Role role;
    private List<HourDto> hours;

    public EmployeeDto() {}

    public EmployeeDto(int libraryCardID, boolean aIsOnlineAcc, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, Role role) {
        super(libraryCardID, aIsOnlineAcc, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress);
        this.role = role;
    }

    public EmployeeDto(int libraryCardID, boolean aIsOnlineAcc, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, String username, String email, String password, Role role) {
        super(libraryCardID, aIsOnlineAcc, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public List<HourDto> getHours() {
        return hours;
    }

    public void setHours(List<HourDto> hours) {
        this.hours = hours;
    }
}

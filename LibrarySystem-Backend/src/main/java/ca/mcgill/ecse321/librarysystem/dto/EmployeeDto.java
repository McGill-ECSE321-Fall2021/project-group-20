package ca.mcgill.ecse321.librarysystem.dto;

import java.util.List;

public class EmployeeDto extends UserDto {

    public enum Role { Librarian, HeadLibrarian }

    private Role role;
    private List<HourDto> hours;

    public EmployeeDto() {}

    public EmployeeDto(int libraryCardID, boolean isLoggedIn, boolean aIsOnlineAcc, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, Role role, int outstandingBalance) {
        super(libraryCardID, aIsOnlineAcc, isLoggedIn, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress, outstandingBalance);
        this.role = role;
    }

    public EmployeeDto(int libraryCardID, boolean aIsOnlineAcc, boolean aIsLoggedIn, String aFirstName, String aLastName, boolean aIsVerified, int aDemeritPts, AddressDto aAddress, String username, String email, int outstandingBalance, Role role) {
        super(libraryCardID, aIsOnlineAcc, aIsLoggedIn, aFirstName, aLastName, aIsVerified, aDemeritPts, aAddress, username, email, outstandingBalance);
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

    public boolean addEmployeehour(HourDto aEmployeehour)
    {
        boolean wasAdded = false;
        if (hours.contains(aEmployeehour)) { return false; }
        EmployeeDto existingEmployee = aEmployeehour.getEmployee();
        boolean isNewEmployee = existingEmployee != null && !this.equals(existingEmployee);
        if (isNewEmployee)
        {
            aEmployeehour.setEmployee(this);
        }
        else
        {
            hours.add(aEmployeehour);
        }
        wasAdded = true;
        return wasAdded;
    }

    public boolean removeEmployeehour(HourDto aEmployeehour)
    {
        boolean wasRemoved = false;
        //Unable to remove aEmployeehour, as it must always have a employee
        if (!this.equals(aEmployeehour.getEmployee()))
        {
            hours.remove(aEmployeehour);
            wasRemoved = true;
        }
        return wasRemoved;
    }
}

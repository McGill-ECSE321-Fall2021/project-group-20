package ca.mcgill.ecse321.librarysystem.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LibrarySystemDto {
    private String systemID;
    private List<UserDto> users;
    private List<ItemDto> items;
    private AddressDto businessaddress;
    private CalendarDto calendar;


    public LibrarySystemDto() {
        users = new ArrayList<UserDto>();
        items = new ArrayList<ItemDto>();
    }

    public LibrarySystemDto(AddressDto aBusinessaddress, CalendarDto aCalendar) {
        boolean didAddBusinessaddress = setBusinessaddress(aBusinessaddress);
        if (!didAddBusinessaddress) {
            throw new RuntimeException("Unable to create librarySystem due to businessaddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        boolean didAddCalendar = setCalendar(aCalendar);
        if (!didAddCalendar) {
            throw new RuntimeException("Unable to create librarySystem due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        users = new ArrayList<UserDto>();
        items = new ArrayList<ItemDto>();
    }

    public LibrarySystemDto(String aSystemID, AddressDto aBusinessaddress, CalendarDto aCalendar) {
        systemID = aSystemID;
        boolean didAddBusinessaddress = setBusinessaddress(aBusinessaddress);
        if (!didAddBusinessaddress) {
            throw new RuntimeException("Unable to create librarySystem due to businessaddress. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        boolean didAddCalendar = setCalendar(aCalendar);
        if (!didAddCalendar) {
            throw new RuntimeException("Unable to create librarySystem due to calendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
        }
        users = new ArrayList<UserDto>();
        items = new ArrayList<ItemDto>();
    }

    public boolean setSystemID(String aSystemID) {
        boolean wasSet = false;
        systemID = aSystemID;
        wasSet = true;
        return wasSet;
    }

    public String getSystemID()
    {
        return systemID;
    }

    public AddressDto getBusinessaddress()
    {
        return businessaddress;
    }

    public CalendarDto getCalendar()
    {
        return calendar;
    }

    public UserDto getUser(int index) {
        UserDto aUser = users.get(index);
        return aUser;
    }

    public List<UserDto> getUsers() {
        List<UserDto> newUsers = Collections.unmodifiableList(users);
        return newUsers;
    }

    public List<ItemDto> getItems() {
        List<ItemDto> newItems = Collections.unmodifiableList(items);
        return newItems;
    }

    public boolean setBusinessaddress(AddressDto aNewBusinessaddress) {
        this.businessaddress=aNewBusinessaddress;
        return true;
    }

    public boolean setCalendar(CalendarDto aNewCalendar) {
        boolean wasSet = false;

        CalendarDto anOldCalendar = calendar;
        calendar = aNewCalendar;
        wasSet = true;
        return wasSet;
    }

    public static int minimumNumberOfItems()
    {
        return 0;
    }

    public ItemDto addItem(ItemDto.Status aStatus, long aItemBarcode, TitleDto aTitle) {
        return new ItemDto(aStatus, aItemBarcode, aTitle);
    }
}
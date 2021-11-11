/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.dto;


import java.util.*;

// line 11 "../../../../../librarysystem.ump"

public class AuthorDto
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, AuthorDto> authorsByAuthorID = new HashMap<String, AuthorDto>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Author Attributes
  private String authorID;
  private String firstName;
  private String lastName;

  //Author Associations
  private List<String> titles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AuthorDto(){}

  public AuthorDto(String aFirstName, String aLastName)
  {
    firstName = aFirstName;
    lastName = aLastName;
    titles = new ArrayList<String>();
  }
  
  public AuthorDto(String aAuthorID, String aFirstName, String aLastName)
  {
    firstName = aFirstName;
    lastName = aLastName;
    setAuthorID(aAuthorID);
    titles = new ArrayList<String>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAuthorID(String aAuthorID)
  {

    authorID = aAuthorID;
    return true;
  }

  public boolean setFirstName(String aFirstName)
  {
    boolean wasSet = false;
    firstName = aFirstName;
    wasSet = true;
    return wasSet;
  }

  public boolean setLastName(String aLastName)
  {
    boolean wasSet = false;
    lastName = aLastName;
    wasSet = true;
    return wasSet;
  }

  public String getAuthorID()
  {
    return authorID;
  }
  /* Code from template attribute_GetUnique */
  public static AuthorDto getWithAuthorID(String aAuthorID)
  {
    return authorsByAuthorID.get(aAuthorID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithAuthorID(String aAuthorID)
  {
    return getWithAuthorID(aAuthorID) != null;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public String getLastName()
  {
    return lastName;
  }
  /* Code from template association_GetMany */
  public String getTitleID(int index)
  {
    String aTitle = titles.get(index);
    return aTitle;
  }

  public List<String> getTitles()
  {
    List<String> newTitles = Collections.unmodifiableList(titles);
    return newTitles;
  }

  public int numberOfTitles()
  {
    int number = titles.size();
    return number;
  }

  public boolean hasTitles()
  {
    boolean has = titles.size() > 0;
    return has;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTitles()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTitle(TitleDto title)
  {
    String aTitle = title.getTitleID();
    boolean wasAdded = false;
    if (titles.contains(aTitle)) { return false; }
    titles.add(aTitle);
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTitle(TitleDto title) {
    boolean wasRemoved = false;
    String aTitle = title.getTitleID();
    if (!titles.contains(aTitle)) {
      return wasRemoved;
    }

    int oldIndex = titles.indexOf(aTitle);
    titles.remove(oldIndex);
    return wasRemoved;
  }

  public String toString()
  {
    return super.toString() + "["+
            "authorID" + ":" + getAuthorID()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "]";
  }
}
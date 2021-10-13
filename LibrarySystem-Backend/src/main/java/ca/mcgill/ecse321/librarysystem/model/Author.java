/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

// line 11 "../../../../../librarysystem.ump"
public class Author
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Author> authorsByAuthorID = new HashMap<String, Author>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Author Attributes
  private String authorID;
  private String firstName;
  private String lastName;

  //Author Associations
  private List<Title> titles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Author(String aAuthorID, String aFirstName, String aLastName)
  {
    firstName = aFirstName;
    lastName = aLastName;
    if (!setAuthorID(aAuthorID))
    {
      throw new RuntimeException("Cannot create due to duplicate authorID. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    titles = new ArrayList<Title>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAuthorID(String aAuthorID)
  {
    boolean wasSet = false;
    String anOldAuthorID = getAuthorID();
    if (anOldAuthorID != null && anOldAuthorID.equals(aAuthorID)) {
      return true;
    }
    if (hasWithAuthorID(aAuthorID)) {
      return wasSet;
    }
    authorID = aAuthorID;
    wasSet = true;
    if (anOldAuthorID != null) {
      authorsByAuthorID.remove(anOldAuthorID);
    }
    authorsByAuthorID.put(aAuthorID, this);
    return wasSet;
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
  public static Author getWithAuthorID(String aAuthorID)
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
  public Title getTitle(int index)
  {
    Title aTitle = titles.get(index);
    return aTitle;
  }

  public List<Title> getTitles()
  {
    List<Title> newTitles = Collections.unmodifiableList(titles);
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

  public int indexOfTitle(Title aTitle)
  {
    int index = titles.indexOf(aTitle);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTitles()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addTitle(Title aTitle)
  {
    boolean wasAdded = false;
    if (titles.contains(aTitle)) { return false; }
    titles.add(aTitle);
    if (aTitle.indexOfAuthor(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aTitle.addAuthor(this);
      if (!wasAdded)
      {
        titles.remove(aTitle);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeTitle(Title aTitle)
  {
    boolean wasRemoved = false;
    if (!titles.contains(aTitle))
    {
      return wasRemoved;
    }

    int oldIndex = titles.indexOf(aTitle);
    titles.remove(oldIndex);
    if (aTitle.indexOfAuthor(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aTitle.removeAuthor(this);
      if (!wasRemoved)
      {
        titles.add(oldIndex,aTitle);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTitleAt(Title aTitle, int index)
  {  
    boolean wasAdded = false;
    if(addTitle(aTitle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTitles()) { index = numberOfTitles() - 1; }
      titles.remove(aTitle);
      titles.add(index, aTitle);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTitleAt(Title aTitle, int index)
  {
    boolean wasAdded = false;
    if(titles.contains(aTitle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTitles()) { index = numberOfTitles() - 1; }
      titles.remove(aTitle);
      titles.add(index, aTitle);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTitleAt(aTitle, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    authorsByAuthorID.remove(getAuthorID());
    ArrayList<Title> copyOfTitles = new ArrayList<Title>(titles);
    titles.clear();
    for(Title aTitle : copyOfTitles)
    {
      if (aTitle.numberOfAuthor() <= Title.minimumNumberOfAuthor())
      {
        aTitle.delete();
      }
      else
      {
        aTitle.removeAuthor(this);
      }
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "authorID" + ":" + getAuthorID()+ "," +
            "firstName" + ":" + getFirstName()+ "," +
            "lastName" + ":" + getLastName()+ "]";
  }
}
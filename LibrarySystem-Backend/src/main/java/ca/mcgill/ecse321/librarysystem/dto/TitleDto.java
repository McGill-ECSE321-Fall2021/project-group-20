/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.dto;
import java.util.*;

import ca.mcgill.ecse321.librarysystem.model.Item;

// line 19 "../../../../../librarysystem.ump"
public class TitleDto
{
private static Map<String, TitleDto> titlesByTitleID = new HashMap<String, TitleDto>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Title Attributes
	private String titleID;
  private String name;
  private String pubDate;

  //Title Associations
  private List<Long> items;
  private List<AuthorDto> author;
  //------------------------
  // CONSTRUCTOR
  //------------------------
  public TitleDto() {
	  items = new ArrayList<Long>();
	  author = new ArrayList<AuthorDto>();
  }
  public TitleDto(String aName, String aPubDate, AuthorDto... allAuthor)
  {
    name = aName;
    pubDate = aPubDate;
    items = new ArrayList<Long>();
    author = new ArrayList<AuthorDto>();
    boolean didAddAuthor = setAuthor(allAuthor);
    if (!didAddAuthor)
    {
      throw new RuntimeException("Unable to create Title, must have at least 1 author. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }

  }

  public TitleDto(String aTitleID, String aName, String aPubDate, AuthorDto... allAuthor)
  {
    titleID = aTitleID;
    name = aName;
    pubDate = aPubDate;
    items = new ArrayList<Long>();
    author = new ArrayList<AuthorDto>();
    boolean didAddAuthor = setAuthor(allAuthor);
    if (!didAddAuthor)
    {
      throw new RuntimeException("Unable to create Title, must have at least 1 author. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }

  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTitleID(String aTitleID)
  {
    boolean wasSet = false;
    String anOldTitleID = getTitleID();
    if (anOldTitleID != null && anOldTitleID.equals(aTitleID)) {
      return true;
    }
    if (hasWithTitleID(aTitleID)) {
      return wasSet;
    }
    titleID = aTitleID;
    wasSet = true;
    if (anOldTitleID != null) {
      titlesByTitleID.remove(anOldTitleID);
    }
    titlesByTitleID.put(aTitleID, this);
    return wasSet;
  }
  
  public String getTitleID()
  {
    return titleID;
  }
  /* Code from template attribute_GetUnique */
  public static TitleDto getWithTitleID(String aTitleID)
  {
    return titlesByTitleID.get(aTitleID);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithTitleID(String aTitleID)
  {
    return getWithTitleID(aTitleID) != null;
  }
  
  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setPubDate(String aPubDate)
  {
    boolean wasSet = false;
    pubDate = aPubDate;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getPubDate()
  {
    return pubDate;
  }

  public int numberOfItem()
  {
    int number = items.size();
    return number;
  }

  public boolean hasItem()
  {
    boolean has = items.size() > 0;
    return has;
  }

  public boolean setItem(ItemDto item) {
    items.add(item.getItemBarcode());
    return true;
  }

  public int indexOfItem(ItemDto aItem)
  {
    int index = items.indexOf(aItem);
    return index;
  }
  /* Code from template association_GetMany */
  public AuthorDto getAuthor(int index)
  {
    AuthorDto aAuthor = author.get(index);
    return aAuthor;
  }

  public List<AuthorDto> getAuthor()
  {
    List<AuthorDto> newAuthor = Collections.unmodifiableList(author);
    return newAuthor;
  }

  public int numberOfAuthor()
  {
    int number = author.size();
    return number;
  }

  public boolean hasAuthor()
  {
    boolean has = author.size() > 0;
    return has;
  }

  public int indexOfAuthor(AuthorDto aAuthor)
  {
    int index = author.indexOf(aAuthor);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfItemValid()
  {
    boolean isValid = numberOfItem() >= minimumNumberOfItem();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfItem()
  {
    return 1;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public ItemDto addItem(Item.Status aStatus, long aItemBarcode)
  {
    ItemDto aNewItem = new ItemDto(aStatus, aItemBarcode, this);
    return aNewItem;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfAuthorValid()
  {
    boolean isValid = numberOfAuthor() >= minimumNumberOfAuthor();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAuthor()
  {
    return 1;
  }

  /* Code from template association_SetMStarToMany */
  public boolean setAuthor(AuthorDto... newAuthor) {
    boolean wasSet = false;
    ArrayList<AuthorDto> verifiedAuthor = new ArrayList<AuthorDto>();
    for (AuthorDto aAuthor : newAuthor) {
      if (verifiedAuthor.contains(aAuthor)) {
        continue;
      }
      verifiedAuthor.add(aAuthor);
    }

    if (verifiedAuthor.size() != newAuthor.length || verifiedAuthor.size() < minimumNumberOfAuthor()) {
      return wasSet;
    }

    ArrayList<AuthorDto> oldAuthor = new ArrayList<AuthorDto>(author);
    author.clear();
    for (AuthorDto aNewAuthor : verifiedAuthor) {
      author.add(aNewAuthor);
      if (oldAuthor.contains(aNewAuthor)) {
        oldAuthor.remove(aNewAuthor);
      } else {
        aNewAuthor.addTitle(this);
      }
    }

    for (AuthorDto anOldAuthor : oldAuthor) {
      anOldAuthor.removeTitle(this);
    }
    wasSet = true;
    return wasSet;
  }

  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "pubDate" + ":" + getPubDate()+ "]";
  }
}
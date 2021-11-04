/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.dto;
import java.util.*;

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
  private List<ItemDto> item;
  private List<AuthorDto> author;
  //------------------------
  // CONSTRUCTOR
  //------------------------
  public TitleDto() {
	  item = new ArrayList<ItemDto>();
	  author = new ArrayList<AuthorDto>();
  }
  public TitleDto(String aName, String aPubDate, AuthorDto... allAuthor)
  {
    name = aName;
    pubDate = aPubDate;
    item = new ArrayList<ItemDto>();
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
  /* Code from template association_GetMany */
  public ItemDto getItemDto(int index)
  {
    ItemDto aItem = item.get(index);
    return aItem;
  }

  public List<ItemDto> getItem()
  {
    List<ItemDto> newItem = Collections.unmodifiableList(item);
    return newItem;
  }

  public int numberOfItem()
  {
    int number = item.size();
    return number;
  }

  public boolean hasItem()
  {
    boolean has = item.size() > 0;
    return has;
  }

  public int indexOfItem(ItemDto aItem)
  {
    int index = item.indexOf(aItem);
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
  public ItemDto addItem(ItemDto.Status aStatus, long aItemBarcode)
  {
    ItemDto aNewItem = new ItemDto(aStatus, aItemBarcode, this);
    return aNewItem;
  }

  public boolean addItem(ItemDto aItem)
  {
    boolean wasAdded = false;
    if (item.contains(aItem)) { return false; }
    TitleDto existingTitle = aItem.getTitle();
    boolean isNewTitle = existingTitle != null && !this.equals(existingTitle);

    if (isNewTitle && existingTitle.numberOfItem() <= minimumNumberOfItem())
    {
      return wasAdded;
    }
    if (isNewTitle)
    {
      aItem.setTitle(this);
    }
    else
    {
      item.add(aItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeItem(ItemDto aItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aItem, as it must always have a title
    if (this.equals(aItem.getTitle()))
    {
      return wasRemoved;
    }

    //title already at minimum (1)
    if (numberOfItem() <= minimumNumberOfItem())
    {
      return wasRemoved;
    }

    item.remove(aItem);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addItemAt(ItemDto aItem, int index)
  {  
    boolean wasAdded = false;
    if(addItem(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItem()) { index = numberOfItem() - 1; }
      item.remove(aItem);
      item.add(index, aItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveItemAt(ItemDto aItem, int index)
  {
    boolean wasAdded = false;
    if(item.contains(aItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfItem()) { index = numberOfItem() - 1; }
      item.remove(aItem);
      item.add(index, aItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addItemAt(aItem, index);
    }
    return wasAdded;
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
  /* Code from template association_AddManyToManyMethod */
  public boolean addAuthor(AuthorDto aAuthor)
  {
    boolean wasAdded = false;
    if (author.contains(aAuthor)) { return false; }
    author.add(aAuthor);
    if (aAuthor.indexOfTitle(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aAuthor.addTitle(this);
      if (!wasAdded)
      {
        author.remove(aAuthor);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMStarToMany */
  public boolean removeAuthor(AuthorDto aAuthor)
  {
    boolean wasRemoved = false;
    if (!author.contains(aAuthor))
    {
      return wasRemoved;
    }

    if (numberOfAuthor() <= minimumNumberOfAuthor())
    {
      return wasRemoved;
    }

    int oldIndex = author.indexOf(aAuthor);
    author.remove(oldIndex);
    if (aAuthor.indexOfTitle(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aAuthor.removeTitle(this);
      if (!wasRemoved)
      {
        author.add(oldIndex,aAuthor);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMStarToMany */
  public boolean setAuthor(AuthorDto... newAuthor)
  {
    boolean wasSet = false;
    ArrayList<AuthorDto> verifiedAuthor = new ArrayList<AuthorDto>();
    for (AuthorDto aAuthor : newAuthor)
    {
      if (verifiedAuthor.contains(aAuthor))
      {
        continue;
      }
      verifiedAuthor.add(aAuthor);
    }

    if (verifiedAuthor.size() != newAuthor.length || verifiedAuthor.size() < minimumNumberOfAuthor())
    {
      return wasSet;
    }

    ArrayList<AuthorDto> oldAuthor = new ArrayList<AuthorDto>(author);
    author.clear();
    for (AuthorDto aNewAuthor : verifiedAuthor)
    {
      author.add(aNewAuthor);
      if (oldAuthor.contains(aNewAuthor))
      {
        oldAuthor.remove(aNewAuthor);
      }
      else
      {
        aNewAuthor.addTitle(this);
      }
    }

    for (AuthorDto anOldAuthor : oldAuthor)
    {
      anOldAuthor.removeTitle(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAuthorAt(AuthorDto aAuthor, int index)
  {  
    boolean wasAdded = false;
    if(addAuthor(aAuthor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAuthor()) { index = numberOfAuthor() - 1; }
      author.remove(aAuthor);
      author.add(index, aAuthor);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAuthorAt(AuthorDto aAuthor, int index)
  {
    boolean wasAdded = false;
    if(author.contains(aAuthor))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAuthor()) { index = numberOfAuthor() - 1; }
      author.remove(aAuthor);
      author.add(index, aAuthor);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAuthorAt(aAuthor, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    titlesByTitleID.remove(getTitleID());
    for(int i=item.size(); i > 0; i--)
    {
      ItemDto aItem = item.get(i - 1);
      aItem.delete();
    }
    ArrayList<AuthorDto> copyOfAuthor = new ArrayList<AuthorDto>(author);
    author.clear();
    for(AuthorDto aAuthor : copyOfAuthor)
    {
      aAuthor.removeTitle(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "pubDate" + ":" + getPubDate()+ "]";
  }
}
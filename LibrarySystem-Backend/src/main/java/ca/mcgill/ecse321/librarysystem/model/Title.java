/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

// line 19 "../../../../../librarysystem.ump"
@Entity
public class Title
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Title Attributes
	@Id
  private String name;
  private String pubDate;

  //Title Associations
  @OneToMany(mappedBy="title", fetch=FetchType.EAGER)
  @Fetch(value=FetchMode.SELECT)
  private List<Item> item;
  @ManyToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)

  private List<Author> author;
  //------------------------
  // CONSTRUCTOR
  //------------------------
  public Title() {}
  public Title(String aName, String aPubDate, Author... allAuthor)
  {
    name = aName;
    pubDate = aPubDate;
    item = new ArrayList<Item>();
    author = new ArrayList<Author>();
    boolean didAddAuthor = setAuthor(allAuthor);
    if (!didAddAuthor)
    {
      throw new RuntimeException("Unable to create Title, must have at least 1 author. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

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
  public Item getItem(int index)
  {
    Item aItem = item.get(index);
    return aItem;
  }

  public List<Item> getItem()
  {
    List<Item> newItem = Collections.unmodifiableList(item);
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

  public int indexOfItem(Item aItem)
  {
    int index = item.indexOf(aItem);
    return index;
  }
  /* Code from template association_GetMany */
  public Author getAuthor(int index)
  {
    Author aAuthor = author.get(index);
    return aAuthor;
  }

  public List<Author> getAuthor()
  {
    List<Author> newAuthor = Collections.unmodifiableList(author);
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

  public int indexOfAuthor(Author aAuthor)
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
  public Item addItem(Item.Status aStatus, int aItemBarcode, LibrarySystem aLibrarySystem)
  {
    Item aNewItem = new Item(aStatus, aItemBarcode, aLibrarySystem, this);
    return aNewItem;
  }

  public boolean addItem(Item aItem)
  {
    boolean wasAdded = false;
    if (item.contains(aItem)) { return false; }
    Title existingTitle = aItem.getTitle();
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

  public boolean removeItem(Item aItem)
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
  public boolean addItemAt(Item aItem, int index)
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

  public boolean addOrMoveItemAt(Item aItem, int index)
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
  public boolean addAuthor(Author aAuthor)
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
  public boolean removeAuthor(Author aAuthor)
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
  public boolean setAuthor(Author... newAuthor)
  {
    boolean wasSet = false;
    ArrayList<Author> verifiedAuthor = new ArrayList<Author>();
    for (Author aAuthor : newAuthor)
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

    ArrayList<Author> oldAuthor = new ArrayList<Author>(author);
    author.clear();
    for (Author aNewAuthor : verifiedAuthor)
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

    for (Author anOldAuthor : oldAuthor)
    {
      anOldAuthor.removeTitle(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAuthorAt(Author aAuthor, int index)
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

  public boolean addOrMoveAuthorAt(Author aAuthor, int index)
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
    for(int i=item.size(); i > 0; i--)
    {
      Item aItem = item.get(i - 1);
      aItem.delete();
    }
    ArrayList<Author> copyOfAuthor = new ArrayList<Author>(author);
    author.clear();
    for(Author aAuthor : copyOfAuthor)
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
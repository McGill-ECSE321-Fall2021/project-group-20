/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse321.librarysystem.model;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

// line 25 "../../../../../librarysystem.ump"
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Item
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Status { Available, Reserved, Borrowed, Damaged }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Item Attributes
  private Status status;
  @Id
  @GeneratedValue
  private int itemBarcode;

  //Item Associations
  @ManyToOne(optional=false)
  private LibrarySystem librarySystem;
  @ManyToOne(optional=false)
  private Title title;
  @OneToOne(optional=true)
  private Booking booking;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Item(Status aStatus, int aItemBarcode, LibrarySystem aLibrarySystem, Title aTitle)
  {
    status = aStatus;
    itemBarcode = aItemBarcode;
    boolean didAddLibrarySystem = setLibrarySystem(aLibrarySystem);
    if (!didAddLibrarySystem)
    {
      throw new RuntimeException("Unable to create item due to librarySystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTitle = setTitle(aTitle);
    if (!didAddTitle)
    {
      throw new RuntimeException("Unable to create item due to title. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStatus(Status aStatus)
  {
    boolean wasSet = false;
    status = aStatus;
    wasSet = true;
    return wasSet;
  }

  public boolean setItemBarcode(int aItemBarcode)
  {
    boolean wasSet = false;
    itemBarcode = aItemBarcode;
    wasSet = true;
    return wasSet;
  }

  public Status getStatus()
  {
    return status;
  }

  public int getItemBarcode()
  {
    return itemBarcode;
  }
  /* Code from template association_GetOne */
  public LibrarySystem getLibrarySystem()
  {
    return librarySystem;
  }
  /* Code from template association_GetOne */
  public Title getTitle()
  {
    return title;
  }
  /* Code from template association_GetOne */
  public Booking getBooking()
  {
    return booking;
  }

  public boolean hasBooking()
  {
    boolean has = booking != null;
    return has;
  }
  /* Code from template association_SetOneToMany */
  public boolean setLibrarySystem(LibrarySystem aLibrarySystem)
  {
    boolean wasSet = false;
    if (aLibrarySystem == null)
    {
      return wasSet;
    }

    LibrarySystem existingLibrarySystem = librarySystem;
    librarySystem = aLibrarySystem;
    if (existingLibrarySystem != null && !existingLibrarySystem.equals(aLibrarySystem))
    {
      existingLibrarySystem.removeItem(this);
    }
    librarySystem.addItem(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setTitle(Title aTitle)
  {
    boolean wasSet = false;
    //Must provide title to item
    if (aTitle == null)
    {
      return wasSet;
    }

    if (title != null && title.numberOfItem() <= Title.minimumNumberOfItem())
    {
      return wasSet;
    }

    Title existingTitle = title;
    title = aTitle;
    if (existingTitle != null && !existingTitle.equals(aTitle))
    {
      boolean didRemove = existingTitle.removeItem(this);
      if (!didRemove)
      {
        title = existingTitle;
        return wasSet;
      }
    }
    title.addItem(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setBooking(Booking aNewBooking)
  {
    boolean wasSet = false;
    if (booking != null && !booking.equals(aNewBooking) && equals(booking.getItembooked()))
    {
      //Unable to setBooking, as existing booking would become an orphan
      return wasSet;
    }

    booking = aNewBooking;
    Item anOldItembooked = aNewBooking != null ? aNewBooking.getItembooked() : null;

    if (!this.equals(anOldItembooked))
    {
      if (anOldItembooked != null)
      {
        anOldItembooked.booking = null;
      }
      if (booking != null)
      {
        booking.setItembooked(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    LibrarySystem placeholderLibrarySystem = librarySystem;
    this.librarySystem = null;
    if(placeholderLibrarySystem != null)
    {
      placeholderLibrarySystem.removeItem(this);
    }
    Title placeholderTitle = title;
    this.title = null;
    if(placeholderTitle != null)
    {
      placeholderTitle.removeItem(this);
    }
    Booking existingBooking = booking;
    booking = null;
    if (existingBooking != null)
    {
      existingBooking.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "itemBarcode" + ":" + getItemBarcode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "status" + "=" + (getStatus() != null ? !getStatus().equals(this)  ? getStatus().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "librarySystem = "+(getLibrarySystem()!=null?Integer.toHexString(System.identityHashCode(getLibrarySystem())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "title = "+(getTitle()!=null?Integer.toHexString(System.identityHashCode(getTitle())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "booking = "+(getBooking()!=null?Integer.toHexString(System.identityHashCode(getBooking())):"null");
  }
}
package ca.mcgill.ecse321.librarysystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.librarysystem.dao.TitleRepository;
import ca.mcgill.ecse321.librarysystem.model.Author;
import ca.mcgill.ecse321.librarysystem.model.Item;
import ca.mcgill.ecse321.librarysystem.model.Title;


@Service
public class TitleService {

    @Autowired
    TitleRepository titleRepository;

    @Transactional
    public Title createTitle() {
        Title title = new Title();
        titleRepository.save(title);
        return title;
    }

    @Transactional
    public Title createTitle(String name, String pubDate, Author... author) {
        Title title = new Title(name,pubDate,author);
        titleRepository.save(title);
        return title;
    }

    @Transactional
    public Title getTitleByTitleID(String titleID) {
        Title title = titleRepository.findByTitleID(titleID);
        if (title == null) throw new NullPointerException("Title not found");
        return title;
    }

    @Transactional
    public List<Title> getTitlesByAuthorID(Author authorID) {
        List<Title> titles = titleRepository.findByAuthor(authorID);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        return titles;
    }

    @Transactional
    public List<Title> getTitlesByAuthorIDs(List<Author> authorID) {
        List<Title> titles = titleRepository.findByAuthorIn(authorID);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        return titles;
    }

    @Transactional
    public Title getTitlesByItemBarcode(Item itemBarcode) {
        Title title = titleRepository.findByItem(itemBarcode);
        if (title == null) throw new NullPointerException("Title not found");
        return title;
    }

    @Transactional
    public List<Title> getTitlesByItemBarcodes(List<Item> itemBarcode) {
        List<Title> titles = titleRepository.findByItemIn(itemBarcode);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        return titles;
    }

    @Transactional
    public List<Title> getTitlesByName(String name) {
        List<Title> titles = titleRepository.findByName(name);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        return titles;
    }

    @Transactional
    public List<Title> getTitlesByPubDate(String pubDate) {
        List<Title> titles = titleRepository.findByPubDate(pubDate);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        return titles;
    }

    @Transactional
    public Title getTitleByNameAndPubDate(String name, String pubDate) {
        Title title = titleRepository.findByNameAndPubDate(name, pubDate);
        if (title == null) throw new NullPointerException("Title not found");
        return title;
    }

    @Transactional
    public boolean deleteTitleByTitleID(String titleID) {
        Title title = titleRepository.findByTitleID(titleID);
        if (title == null) throw new NullPointerException("Title not found");
        title.delete();
        titleRepository.delete(title);
        Title test = titleRepository.findByTitleID(titleID);
        return (test == null);
    }

    @Transactional
    public boolean deleteTitlesByAuthorID(Author authorID) {
        List<Title> titles = titleRepository.findByAuthor(authorID);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        for (Title title : titles) {
            title.delete();
            titleRepository.delete(title);
        }
        List<Title> test = titleRepository.findByAuthor(authorID);
        return (test.size() == 0);
    }

    @Transactional
    public boolean deleteTitlesByAuthorIDs(List<Author> authorID) {
        List<Title> titles = titleRepository.findByAuthorIn(authorID);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        for (Title title : titles) {
            title.delete();
            titleRepository.delete(title);
        }
        List<Title> test = titleRepository.findByAuthorIn(authorID);
        return (test.size() == 0);
    }


    @Transactional
    public boolean deleteTitlesByItemBarcode(Item itemBarcode) {
        Title title = titleRepository.findByItem(itemBarcode);
        if (title == null) throw new NullPointerException("Title not found");
        title.delete();
        titleRepository.delete(title);
        Title test = titleRepository.findByItem(itemBarcode);
        return (test == null);
    }


    @Transactional
    public boolean deleteTitlesByItemBarcodes(List<Item> itemBarcode) {
        List<Title> titles = titleRepository.findByItemIn(itemBarcode);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        for (Title title : titles) {
            title.delete();
            titleRepository.delete(title);
        }
        List<Title> test = titleRepository.findByItemIn(itemBarcode);
        return (test.size() == 0);
    }


    @Transactional
    public boolean deleteTitlesByName(String name) {
        List<Title> titles = titleRepository.findByName(name);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        for (Title title : titles) {
            title.delete();
            titleRepository.delete(title);
        }
        List<Title> test = titleRepository.findByName(name);
        return (test.size() == 0);
    }

    @Transactional
    public boolean deleteTitlesByPubDate(String pubDate) {
        List<Title> titles = titleRepository.findByPubDate(pubDate);
        if (titles.size() == 0) throw new NullPointerException("Titles not found");
        for (Title title : titles) {
            title.delete();
            titleRepository.delete(title);
        }
        List<Title> test = titleRepository.findByPubDate(pubDate);
        return (test.size() == 0);
    }

    @Transactional
    public boolean deleteTitleByNameAndPubDate(String name, String pubDate) {
        Title title = titleRepository.findByNameAndPubDate( name, pubDate);
        if (title == null) throw new NullPointerException("Title not found");
        title.delete();
        titleRepository.delete(title);
        Title test = titleRepository.findByNameAndPubDate(name,pubDate);
        return (test == null);
    }

    @Transactional
    public boolean isTitleExistsByTitleID(String titleID) {
        return (titleRepository.existsByTitleID(titleID));
    }

    @Transactional
    public boolean isTitleExistsByItem(Item itemBarcode) {
        return (titleRepository.existsByItem(itemBarcode));
    }

    @Transactional
    public boolean isTitleExistsByNameAndPubDate(String name, String pubDate) {
        return (titleRepository.existsByNameAndPubDate(name, pubDate));
    }

}

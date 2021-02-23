package com.searc.kamil.booksearch.controller;


import java.util.*;


import com.searc.kamil.booksearch.model.AuthorRanking;
import com.searc.kamil.booksearch.model.Item;
import com.searc.kamil.booksearch.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequestMapping("/search")
public class SearchController {


    @Autowired
    private BookService bookService;


    @GetMapping("/isbn/{isbn}")
    @Cacheable(key = "#isbn", value = "Item")
    public Item getBookDetailsByIsbn(@PathVariable String isbn) {
        return bookService.getBookByIsbn(isbn);
    }


    @GetMapping("/lastviewed")
    public List<Item> getBooksLastViewed() {
        return bookService.getBooksLastViewed();
    }

    @GetMapping("/category/{category}")
    public List<Item> getBookDetailsByCategory(@PathVariable String category) {
        return bookService.getBooksByCategory(category);
    }

    @GetMapping("/bestauthorsbooks")
    public List<AuthorRanking> getBestBooksAuthor() {
        return bookService.getBestBooksAuthor();

    }

    @GetMapping("/amountofpages/{amountOfPages}")
    public Item getBookWhichNumberOfPagesIsGreaterThanPageAmount(@PathVariable int amountOfPages) {
        return bookService.getBookWhichNumberOfPagesIsGreaterThanPageAmount(amountOfPages);
    }


    @GetMapping("/best/{pagePerHour}/{hoursSpendsDuringDay}")
    public List<Item> getMostRatedBooksToRead(@PathVariable int pagePerHour, @PathVariable int hoursSpendsDuringDay) {

        return bookService.getMostRatedBooksToRead(pagePerHour, hoursSpendsDuringDay);

    }
}








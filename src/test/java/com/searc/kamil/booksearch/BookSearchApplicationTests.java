package com.searc.kamil.booksearch;


import com.searc.kamil.booksearch.model.Item;
import com.searc.kamil.booksearch.model.Library;
import com.searc.kamil.booksearch.service.BookService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.web.client.RestTemplate;

import java.util.List;


@SpringBootTest
class BookSearchApplicationTests {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BookService bookService;

    private final String URL_API_BOOKS = "https://www.googleapis.com/books/v1/volumes?q=java&maxResults=40";

    private final String URL_BOOK_BY_ISBN = "https://www.googleapis.com/books/v1/volumes?q=isbn:9788131755440";

    @Test
    public void test_should_get_book_by_isbn() {

        Item itemFromApi = restTemplate.getForObject(URL_BOOK_BY_ISBN, Library.class)
                .getItems().stream().findAny().get();
        Item itemFromApp = bookService.getBookByIsbn("9788131755440");


        Assert.assertEquals(itemFromApi.getVolumeInfo().getTitle(), itemFromApp.getVolumeInfo().getTitle());
        Assert.assertEquals(itemFromApi.getVolumeInfo().getAuthors(), itemFromApp.getVolumeInfo().getAuthors());

    }

    @Test
    public void test_should_get_books_by_category() {

        List<Item> itemsFromApi = restTemplate.getForObject(URL_API_BOOKS, Library.class)
                .getItems();
        Item itemFromApp = bookService.getBooksByCategory("Computers").stream().findAny().get();


        boolean checkExitBookByAmountPage = itemsFromApi.stream().filter(item -> item.getVolumeInfo().getCategories() != null).anyMatch(item -> item.getVolumeInfo().getTitle().contains(itemFromApp.getVolumeInfo().getTitle()));

        Assert.assertTrue(checkExitBookByAmountPage);


    }


    @Test
    public void test_get_best_books_author() {

        List<Item> itemsFromApi = restTemplate.getForObject(URL_API_BOOKS, Library.class)
                .getItems();
        String authorNameFromApp = bookService.getBestBooksAuthor().stream().findAny().get().getAuthor();

        boolean checkExitAuthor = itemsFromApi.stream().filter(item -> item.getVolumeInfo().getAuthors() != null).anyMatch(item -> item.getVolumeInfo().getAuthors().contains(authorNameFromApp));
        Assert.assertTrue(checkExitAuthor);

    }


    @Test
    public void test_should_get_book_which_number_of_pages_is_greater_than_page_amount() {
        List<Item> itemsFromApi = restTemplate.getForObject(URL_API_BOOKS, Library.class)
                .getItems();
        Item itemFromApp = bookService.getBookWhichNumberOfPagesIsGreaterThanPageAmount(500);


        boolean checkExitBookByAmountPage = itemsFromApi.stream().filter(item -> item.getVolumeInfo().getPageCount() != null).anyMatch(item -> item.getVolumeInfo().getTitle().contains(itemFromApp.getVolumeInfo().getTitle()));

        Assert.assertTrue(checkExitBookByAmountPage);


    }

    @Test
    public void test_should_get_most_rated_books_to_read() {
        List<Item> itemsFromApi = restTemplate.getForObject(URL_API_BOOKS, Library.class)
                .getItems();
        Item itemFromApp = bookService.getMostRatedBooksToRead(20, 5).stream().findAny().get();


        boolean checkExitBookByAvgTimeRead = itemsFromApi.stream().filter(item -> item.getVolumeInfo()!=null).anyMatch(item -> item.getVolumeInfo().getTitle().contains(itemFromApp.getVolumeInfo().getTitle()));

        Assert.assertTrue(checkExitBookByAvgTimeRead);


    }

    @Test
    public void test_get_books_last_viewed() {
        Item itemFromApp1 = bookService.getBookByIsbn("9788131755440");
        Item itemFromApp2 = bookService.getBookByIsbn("8324677658");
        Item itemFromApp3 = bookService.getBookByIsbn("8324677658");
        Item itemFromApp4 = bookService.getBookByIsbn("9780131002876");
        Item itemFromApp5 = bookService.getBookByIsbn("9780471721260");
        Item itemFromApp6 = bookService.getBookByIsbn("1439865590");
        Item itemFromApp7 = bookService.getBookByIsbn("9780201725933");
        Item itemFromApp8 = bookService.getBookByIsbn("9780201725933");
        Item itemFromApp9 = bookService.getBookByIsbn("9780201725933");

        List<Item> lastViewed = bookService.getBooksLastViewed();

        Assert.assertNotNull(lastViewed);
        Assert.assertEquals(lastViewed.size(), 5);

    }


}

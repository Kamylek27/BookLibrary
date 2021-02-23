package com.searc.kamil.booksearch.service;

import com.searc.kamil.booksearch.model.AuthorRanking;
import com.searc.kamil.booksearch.model.Item;
import com.searc.kamil.booksearch.model.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookService {

    private RestTemplate restTemplate;

    private final String URL_API_BOOKS = "https://www.googleapis.com/books/v1/volumes?q=java&maxResults=40";

    private final String URL_BOOK_BY_ISBN = "https://www.googleapis.com/books/v1/volumes?q=isbn:";

    List<Item> booksLastViewed = new LinkedList<>();

    public BookService() {
    }

    @Autowired
    public BookService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Item getBookByIsbn(String isbn) {
        Item item = restTemplate.getForObject(URL_BOOK_BY_ISBN + isbn, Library.class)
                .getItems().stream().findAny().get();
        if (booksLastViewed.size() == 5) {
            booksLastViewed.remove(0);
        }
        booksLastViewed.add(item);
        return item;
    }


    public List<Item> getBooksByCategory(String category) {
        return restTemplate.getForObject(URL_API_BOOKS, Library.class)
                .getItems().stream().filter(item -> item.getVolumeInfo().getCategories() != null && item.getVolumeInfo().getCategories().contains(category))
                .collect(Collectors.toList());
    }


    public List<AuthorRanking> getBestBooksAuthor() {

        List<Item> items = restTemplate.getForObject(URL_API_BOOKS, Library.class)
                .getItems();

        List<AuthorRanking> authorRankings = new ArrayList<>();
        double avgSum = 0;

        for (Item i : items) {
            if (i.getVolumeInfo().getAuthors() != null && i.getVolumeInfo().getAverageRating() != null) {
                for (String s : i.getVolumeInfo().getAuthors()) {
                    for (AuthorRanking a : authorRankings) {
                        if (a != null && a.getAuthor().equals(s)) {
                            avgSum = a.getAvg() + Double.parseDouble(i.getVolumeInfo().getAverageRating());
                            a.setAvg(avgSum / 2);
                            break;
                        }
                    }
                    AuthorRanking authorRanking = new AuthorRanking();
                    authorRanking.setAuthor(s);
                    authorRanking.setAvg(Double.parseDouble(i.getVolumeInfo().getAverageRating()));
                    authorRankings.add(authorRanking);
                }
            }
        }
        Collections.sort(authorRankings, Collections.reverseOrder());
        return authorRankings;
    }


    public Item getBookWhichNumberOfPagesIsGreaterThanPageAmount(int amountOfPages) {
        return restTemplate.getForObject(URL_API_BOOKS, Library.class)
                .getItems().stream()
                .filter(item -> item.getVolumeInfo().getPageCount() != null && Integer.parseInt(String.valueOf(item.getVolumeInfo().getPageCount())) > amountOfPages)
                .sorted(Comparator.comparing(Item -> Integer.parseInt(String.valueOf(Item.getVolumeInfo().getPageCount()))))
                .findFirst().get();
    }


    public List<Item> getMostRatedBooksToRead(int pagePerHour, int hoursSpendsDuringDay) {
        int sumOfPage = 0;
        int avgOfPage = pagePerHour * hoursSpendsDuringDay * 30;

        List<Item> items = restTemplate.getForObject(URL_API_BOOKS, Library.class)
                .getItems();

        Map<Item, Double> unSortedMap = items.stream()
                .filter(i -> i.getVolumeInfo().getAverageRating() != null && i.getVolumeInfo().getPageCount() != null)
                .collect(Collectors.toMap(item -> item, item -> Double.parseDouble(item.getVolumeInfo().getAverageRating())));


   /*     Map<Item, Double> unSortedMap = new LinkedHashMap<>();
        for (Item i : items) {
            if (i.getVolumeInfo().getAverageRating() != null && i.getVolumeInfo().getPageCount() != null) {
                unSortedMap.put(i, Double.parseDouble(i.getVolumeInfo().getAverageRating()));
            }
        }*/


        Map<Item, Double> sortedMap = new LinkedHashMap<>();

        unSortedMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));


        List<Item> newItems = new ArrayList<>();
        for (Map.Entry<Item, Double> entry : sortedMap.entrySet()) {
            sumOfPage += Integer.parseInt(String.valueOf(entry.getKey().getVolumeInfo().getPageCount()));
            if (avgOfPage > sumOfPage) {
                newItems.add(entry.getKey());
            }
        }
        if (newItems.isEmpty()) {
            throw new RuntimeException();
        }
        return newItems;
    }

    public List<Item> getBooksLastViewed() {
        return booksLastViewed;
    }

}

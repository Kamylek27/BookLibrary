package com.searc.kamil.booksearch.model;


public class AuthorRanking implements Comparable<AuthorRanking> {

    private String author;

    private double avg;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }


    public int compareTo(AuthorRanking authorRanking) {
        return Double.compare(this.getAvg(), authorRanking.getAvg());
    }
}

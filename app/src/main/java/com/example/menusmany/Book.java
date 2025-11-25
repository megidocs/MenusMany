package com.example.menusmany;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private String language;

    public Book(String title, String author, String language) {
        this.title = title;
        this.author = author;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getLanguage() {
        return language;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", language=" + language +
                '}';
    }
}

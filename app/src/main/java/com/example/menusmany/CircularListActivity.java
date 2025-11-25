package com.example.menusmany;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class CircularListActivity extends AppCompatActivity {

    private ArrayList<Book> allBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circular_list);

        // Load all book data
        loadBooksFromXml();

        CircularListView circularListView = findViewById(R.id.circularListView);
        
        // Provide only the titles to the custom view for drawing
        ArrayList<String> bookTitles = allBooks.stream()
                .map(Book::getTitle)
                .collect(Collectors.toCollection(ArrayList::new));
        circularListView.setItems(bookTitles);

        // Set the listener to handle item clicks
        circularListView.setOnItemClickListener((position, item) -> {
            // The position from the click corresponds to the position in our full list
            Book clickedBook = allBooks.get(position);

            // Show an AlertDialog with the book's details
            new AlertDialog.Builder(CircularListActivity.this)
                    .setTitle(clickedBook.getTitle())
                    .setMessage("Author: " + clickedBook.getAuthor() + "\nLanguage: " + clickedBook.getLanguage())
                    .setPositiveButton("OK", null)
                    .show();
        });
    }

    private void loadBooksFromXml() {
        allBooks = new ArrayList<>();
        XmlResourceParser parser = getResources().getXml(R.xml.books);
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "book".equals(parser.getName())) {
                    String title = parser.getAttributeValue(null, "title");
                    String author = parser.getAttributeValue(null, "author");
                    String language = parser.getAttributeValue(null, "language");
                    allBooks.add(new Book(title, author, language));
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        } finally {
            if (parser != null) {
                parser.close();
            }
        }
    }
}

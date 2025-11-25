package com.example.menusmany;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigationrail.NavigationRailView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SideNavigationBar extends AppCompatActivity {

    private Map<String, ArrayList<Book>> booksByLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_side_navigation_bar);

        loadAndGroupBooksFromXml();

        NavigationRailView navigationRailView = findViewById(R.id.navigation_rail);
        // The header is part of the NavigationRailView, so we need to get it from there
        View headerView = navigationRailView.getHeaderView();
        if (headerView != null) {
            ImageButton backButton = headerView.findViewById(R.id.fab_back_to_main);
            backButton.setOnClickListener(v -> finish()); // Go back to the previous activity
        }

        navigationRailView.setOnItemSelectedListener(item -> {
            String selectedLanguage = null;
            int itemId = item.getItemId();

            if (itemId == R.id.rail_english) {
                selectedLanguage = "English";
            } else if (itemId == R.id.rail_french) {
                selectedLanguage = "French";
            } else if (itemId == R.id.rail_german) {
                selectedLanguage = "German";
            }

            if (selectedLanguage != null) {
                loadBookListFragment(selectedLanguage);
                return true;
            }
            return false;
        });

        // Load default fragment
        if (savedInstanceState == null) {
            navigationRailView.setSelectedItemId(R.id.rail_english);
        }
    }

    private void loadBookListFragment(String language) {
        ArrayList<Book> books = booksByLanguage.get(language);
        if (books != null) {
            Fragment bookListFragment = SimpleBookListFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.side_nav_fragment_container, bookListFragment)
                    .commit();
        }
    }

    private void loadAndGroupBooksFromXml() {
        booksByLanguage = new HashMap<>();
        XmlResourceParser parser = getResources().getXml(R.xml.books);
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "book".equals(parser.getName())) {
                    String title = parser.getAttributeValue(null, "title");
                    String author = parser.getAttributeValue(null, "author");
                    String language = parser.getAttributeValue(null, "language");
                    Book book = new Book(title, author, language);

                    booksByLanguage.computeIfAbsent(language, k -> new ArrayList<>()).add(book);
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

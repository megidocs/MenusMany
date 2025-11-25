package com.example.menusmany;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BottomNavigationFragment extends Fragment {

    private Map<String, ArrayList<Book>> booksByLanguage;

    public BottomNavigationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAndGroupBooksFromXml();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom_navigation, container, false);

        BottomNavigationView bottomNav = view.findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Load the default fragment
        if (savedInstanceState == null) {
            loadFragmentForLanguage("English");
        }

        return view;
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            String selectedLanguage = null;
            int itemId = item.getItemId();

            if (itemId == R.id.bottom_nav_english) {
                selectedLanguage = "English";
            } else if (itemId == R.id.bottom_nav_french) {
                selectedLanguage = "French";
            } else if (itemId == R.id.bottom_nav_german) {
                selectedLanguage = "German";
            }

            if (selectedLanguage != null) {
                loadFragmentForLanguage(selectedLanguage);
                return true;
            }
            return false;
        }
    };

    private void loadFragmentForLanguage(String language) {
        ArrayList<Book> books = booksByLanguage.get(language);
        if (books != null) {
            Fragment bookListFragment = BookListFragment.newInstance(books);
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.bottom_nav_fragment_container, bookListFragment)
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

package com.example.menusmany;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WheelPickerSection extends AppCompatActivity {

    private Map<String, ArrayList<Book>> booksByLanguage;
    private final String[] languages = {"English", "French", "German"};
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel_picker_section);

        loadAndGroupBooksFromXml();

        NumberPicker numberPicker = findViewById(R.id.npLanguagePicker);
        listView = findViewById(R.id.lvWheelPicker);

        // Configure the NumberPicker to display strings
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(languages.length - 1);
        numberPicker.setDisplayedValues(languages);
        numberPicker.setWrapSelectorWheel(true); // Allows for circular scrolling

        // Listener for value changes
        numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            String selectedLanguage = languages[newVal];
            updateListView(selectedLanguage);
        });

        // Initial load
        updateListView(languages[0]);
    }

    private void updateListView(String language) {
        ArrayList<Book> books = booksByLanguage.get(language);
        if (books != null) {
            ArrayList<String> bookTitles = books.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toCollection(ArrayList::new));

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, bookTitles);
            listView.setAdapter(adapter);
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

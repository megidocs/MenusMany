package com.example.menusmany;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class ListWithPickersActivity extends AppCompatActivity {

    private ArrayList<Book> books = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_with_pickers);

        loadBooksFromXml();

        ListView listView = findViewById(R.id.lvWithPickers);
        BookWithPickerAdapter adapter = new BookWithPickerAdapter(this, books);
        listView.setAdapter(adapter);
    }

    private void loadBooksFromXml() {
        XmlResourceParser parser = getResources().getXml(R.xml.books);
        try {
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG && "book".equals(parser.getName())) {
                    String title = parser.getAttributeValue(null, "title");
                    String author = parser.getAttributeValue(null, "author");
                    String language = parser.getAttributeValue(null, "language");
                    books.add(new Book(title, author, language));
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

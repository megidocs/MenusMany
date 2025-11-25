package com.example.menusmany;

import static android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class ListViewSection extends AppCompatActivity {

    private ArrayList<Book> books;
    private Button bLV1, bLV2, bLV3, bLV4, bLV5, bLV6, bLV7, bLV8, bLV9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_section);

        setRequestedOrientation(SCREEN_ORIENTATION_LANDSCAPE);

        loadBooksFromXml();

        bLV1 = findViewById(R.id.bLV1);
        bLV1.setOnClickListener(v -> {
            SimpleListFragment fragment = SimpleListFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .commit();
        });

        bLV2 = findViewById(R.id.bLV2);
        bLV2.setOnClickListener(v -> {
            IconListFragment fragment = IconListFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .commit();
        });

        bLV3 = findViewById(R.id.bLV3);
        bLV3.setOnClickListener(v -> {
            ChecklistFragment fragment = ChecklistFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .commit();
        });

        bLV4 = findViewById(R.id.bLV4);
        bLV4.setOnClickListener(v -> {
            CustomListFragment fragment = CustomListFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .commit();
        });

        bLV5 = findViewById(R.id.bLV5);
        bLV5.setOnClickListener(v -> {
            LiveSearchFragment fragment = LiveSearchFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .commit();
        });

        bLV6 = findViewById(R.id.bLV6);
        bLV6.setOnClickListener(v -> {
            ContextMenuFragment fragment = ContextMenuFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .commit();
        });
        
        bLV7 = findViewById(R.id.bLV7);
        bLV7.setOnClickListener(v -> {
            ListWithButtonsFragment fragment = ListWithButtonsFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .commit();
        });

        bLV8 = findViewById(R.id.bLV8);
        bLV8.setOnClickListener(v -> {
            ExpandableListFragment fragment = ExpandableListFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .commit();
        });

        bLV9 = findViewById(R.id.bLV9);
        bLV9.setOnClickListener(v -> {
            DragDropListFragment fragment = DragDropListFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainer, fragment)
                    .commit();
        });
    }

    private void loadBooksFromXml() {
        books = new ArrayList<>();
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

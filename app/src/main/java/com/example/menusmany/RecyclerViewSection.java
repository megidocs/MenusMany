package com.example.menusmany;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class RecyclerViewSection extends AppCompatActivity {

    private ArrayList<Book> books;
    private Button bRV1, bRV2, bRV3, bRV4, bRV5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_section);

        loadBooksFromXml();

        bRV1 = findViewById(R.id.bRV1);
        bRV1.setOnClickListener(v -> {
            RecyclerViewFragment fragment = RecyclerViewFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerRV, fragment)
                    .commit();
        });

        bRV2 = findViewById(R.id.bRV2);
        bRV2.setOnClickListener(v -> {
            GridRecyclerViewFragment fragment = GridRecyclerViewFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerRV, fragment)
                    .commit();
        });

        bRV3 = findViewById(R.id.bRV3);
        bRV3.setOnClickListener(v -> {
            StaggeredGridFragment fragment = StaggeredGridFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerRV, fragment)
                    .commit();
        });

        bRV4 = findViewById(R.id.bRV4);
        bRV4.setOnClickListener(v -> {
            AnimatorFragment fragment = AnimatorFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerRV, fragment)
                    .commit();
        });
        
        bRV5 = findViewById(R.id.bRV5);
        bRV5.setOnClickListener(v -> {
            SwipeDismissFragment fragment = SwipeDismissFragment.newInstance(books);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerRV, fragment)
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

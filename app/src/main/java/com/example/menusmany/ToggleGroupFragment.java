package com.example.menusmany;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButtonToggleGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ToggleGroupFragment extends Fragment {

    private ArrayList<Book> books;
    private ToggleableFieldsAdapter adapter;
    private MaterialButtonToggleGroup toggleGroup;

    public ToggleGroupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadBooksFromXml();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_toggle_group, container, false);

        toggleGroup = view.findViewById(R.id.toggleGroupVertical);
        RecyclerView recyclerView = view.findViewById(R.id.rvToggleableList);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ToggleableFieldsAdapter(getContext(), books);
        recyclerView.setAdapter(adapter);

        // Pre-select the "Book" button
        toggleGroup.check(R.id.btnToggleBook);

        // Set listener
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> updateListVisibility());

        // Initial update
        updateListVisibility();

        return view;
    }

    private void updateListVisibility() {
        List<Integer> checkedIds = toggleGroup.getCheckedButtonIds();
        boolean showTitle = checkedIds.contains(R.id.btnToggleBook);
        boolean showAuthor = checkedIds.contains(R.id.btnToggleAuthor);
        boolean showLanguage = checkedIds.contains(R.id.btnToggleLanguage);
        adapter.setFieldVisibility(showTitle, showAuthor, showLanguage);
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

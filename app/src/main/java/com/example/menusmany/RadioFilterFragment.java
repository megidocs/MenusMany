package com.example.menusmany;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RadioFilterFragment extends Fragment {

    private Map<String, List<Book>> booksByLanguage;
    private RecyclerView recyclerView;
    private BookRecyclerAdapter adapter;

    public RadioFilterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_radio_filter, container, false);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroupLanguages);
        recyclerView = view.findViewById(R.id.rvRadioFilter);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // Initialize with an empty list, it will be populated on radio selection
        adapter = new BookRecyclerAdapter(getContext(), new ArrayList<>());
        recyclerView.setAdapter(adapter);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedLanguage = null;
            if (checkedId == R.id.radioEnglish) {
                selectedLanguage = "English";
            } else if (checkedId == R.id.radioFrench) {
                selectedLanguage = "French";
            } else if (checkedId == R.id.radioGerman) {
                selectedLanguage = "German";
            }

            if (selectedLanguage != null) {
                updateRecyclerView(selectedLanguage);
            }
        });

        return view;
    }

    private void updateRecyclerView(String language) {
        List<Book> filteredBooks = booksByLanguage.get(language);
        if (filteredBooks != null) {
            adapter = new BookRecyclerAdapter(getContext(), new ArrayList<>(filteredBooks));
            recyclerView.setAdapter(adapter);
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

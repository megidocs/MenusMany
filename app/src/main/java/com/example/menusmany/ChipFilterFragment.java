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

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ChipFilterFragment extends Fragment {

    private List<Book> allBooks;
    private BookRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private ChipGroup chipGroup;

    public ChipFilterFragment() {
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
        View view = inflater.inflate(R.layout.fragment_chip_filter, container, false);

        recyclerView = view.findViewById(R.id.rvChipFilter);
        chipGroup = view.findViewById(R.id.chipGroupFilter);

        // Setup RecyclerView with the adapter only once
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookRecyclerAdapter(getContext(), new ArrayList<>()); // Start with an empty list
        recyclerView.setAdapter(adapter);

        // Listener for all chips
        Chip chipEnglish = view.findViewById(R.id.chipFilterEnglish);
        Chip chipFrench = view.findViewById(R.id.chipFilterFrench);
        Chip chipGerman = view.findViewById(R.id.chipFilterGerman);

        View.OnClickListener filterListener = v -> filterList();
        chipEnglish.setOnClickListener(filterListener);
        chipFrench.setOnClickListener(filterListener);
        chipGerman.setOnClickListener(filterListener);

        // Initial filter
        filterList();

        return view;
    }

    private void filterList() {
        Set<String> selectedLanguages = new HashSet<>();
        List<Integer> checkedChipIds = chipGroup.getCheckedChipIds();

        for (Integer id : checkedChipIds) {
            if (id == R.id.chipFilterEnglish) {
                selectedLanguages.add("English");
            } else if (id == R.id.chipFilterFrench) {
                selectedLanguages.add("French");
            } else if (id == R.id.chipFilterGerman) {
                selectedLanguages.add("German");
            }
        }

        List<Book> filteredBooks = allBooks.stream()
                .filter(book -> selectedLanguages.contains(book.getLanguage()))
                .collect(Collectors.toList());

        // Correctly update the adapter's data instead of creating a new one
        adapter.updateList(new ArrayList<>(filteredBooks));
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

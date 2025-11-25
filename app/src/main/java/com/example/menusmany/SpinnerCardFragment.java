package com.example.menusmany;

import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.card.MaterialCardView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SpinnerCardFragment extends Fragment {

    private ArrayList<Book> books;
    private final Map<String, String> languageIcons = new HashMap<>();

    public SpinnerCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadBooksFromXml();
        languageIcons.put("English", "🇬🇧");
        languageIcons.put("French", "🇫🇷");
        languageIcons.put("German", "🇩🇪");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spinner_card, container, false);

        AutoCompleteTextView actvBookSpinner = view.findViewById(R.id.actvBookSpinner);
        MaterialCardView cardBookDetails = view.findViewById(R.id.cardBookDetails);

        // Views inside the card
        TextView tvCardTitle = view.findViewById(R.id.tvCardBookTitle);
        TextView tvCardAuthor = view.findViewById(R.id.tvCardBookAuthor);
        TextView tvCardLanguage = view.findViewById(R.id.tvCardBookLanguage);
        TextView tvCardFlag = view.findViewById(R.id.tvCardBookFlag);

        if (books != null) {
            ArrayList<String> bookTitles = (ArrayList<String>) books.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, bookTitles);
            actvBookSpinner.setAdapter(adapter);

            actvBookSpinner.setOnItemClickListener((parent, view1, position, id) -> {
                // Find the selected book
                String selectedTitle = (String) parent.getItemAtPosition(position);
                Book selectedBook = books.stream()
                        .filter(book -> book.getTitle().equals(selectedTitle))
                        .findFirst()
                        .orElse(null);

                if (selectedBook != null) {
                    // Populate the card with the book details
                    tvCardTitle.setText(selectedBook.getTitle());
                    tvCardAuthor.setText("by " + selectedBook.getAuthor());
                    tvCardLanguage.setText("Language: " + selectedBook.getLanguage());
                    tvCardFlag.setText(languageIcons.get(selectedBook.getLanguage()));

                    // Make the card visible
                    cardBookDetails.setVisibility(View.VISIBLE);
                }
            });
        }

        return view;
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

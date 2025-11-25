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

public class AutocompleteFragment extends Fragment {

    private ArrayList<Book> books;
    private final Map<String, String> languageIcons = new HashMap<>();

    public AutocompleteFragment() {
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
        View view = inflater.inflate(R.layout.fragment_autocomplete, container, false);

        AutoCompleteTextView actvAutocomplete = view.findViewById(R.id.actvAutocomplete);
        MaterialCardView cardDetails = view.findViewById(R.id.cardAutocompleteDetails);

        // Views inside the card
        TextView tvCardTitle = view.findViewById(R.id.tvCardAutocompleteTitle);
        TextView tvCardAuthor = view.findViewById(R.id.tvCardAutocompleteAuthor);
        TextView tvCardLanguage = view.findViewById(R.id.tvCardAutocompleteLanguage);
        TextView tvCardFlag = view.findViewById(R.id.tvCardAutocompleteFlag);

        if (books != null) {
            ArrayList<String> bookTitles = (ArrayList<String>) books.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, bookTitles);
            actvAutocomplete.setAdapter(adapter);

            // This listener triggers when a suggestion is clicked
            actvAutocomplete.setOnItemClickListener((parent, view1, position, id) -> {
                String selectedTitle = (String) parent.getItemAtPosition(position);
                Book selectedBook = books.stream()
                        .filter(book -> book.getTitle().equals(selectedTitle))
                        .findFirst()
                        .orElse(null);

                if (selectedBook != null) {
                    tvCardTitle.setText(selectedBook.getTitle());
                    tvCardAuthor.setText("by " + selectedBook.getAuthor());
                    tvCardLanguage.setText("Language: " + selectedBook.getLanguage());
                    tvCardFlag.setText(languageIcons.get(selectedBook.getLanguage()));

                    cardDetails.setVisibility(View.VISIBLE);
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

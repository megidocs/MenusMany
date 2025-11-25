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
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LanguageFilterFragment extends Fragment {

    private Map<String, List<Book>> booksByLanguage;
    private final Map<String, String> languageIcons = new HashMap<>();

    public LanguageFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAndGroupBooksFromXml();
        languageIcons.put("English", "🇬🇧");
        languageIcons.put("French", "🇫🇷");
        languageIcons.put("German", "🇩🇪");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_language_filter, container, false);

        ChipGroup chipGroup = view.findViewById(R.id.chipGroupLanguages);
        TextInputLayout tilLanguageFilter = view.findViewById(R.id.tilLanguageFilter);
        AutoCompleteTextView actvLanguageFilter = view.findViewById(R.id.actvLanguageFilter);
        MaterialCardView cardDetails = view.findViewById(R.id.cardLanguageFilterDetails);

        // Card Views
        TextView tvCardTitle = view.findViewById(R.id.tvCardLangFilterTitle);
        TextView tvCardAuthor = view.findViewById(R.id.tvCardLangFilterAuthor);
        TextView tvCardLanguage = view.findViewById(R.id.tvCardLangFilterLanguage);
        TextView tvCardFlag = view.findViewById(R.id.tvCardLangFilterFlag);

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            String selectedLanguage = null;
            if (checkedId == R.id.chipEnglish) {
                selectedLanguage = "English";
            } else if (checkedId == R.id.chipFrench) {
                selectedLanguage = "French";
            } else if (checkedId == R.id.chipGerman) {
                selectedLanguage = "German";
            }

            if (selectedLanguage != null) {
                // Enable the dropdown and populate it with books of the selected language
                tilLanguageFilter.setEnabled(true);
                tilLanguageFilter.setHint("Choose a " + selectedLanguage + " book");
                List<Book> books = booksByLanguage.get(selectedLanguage);
                if (books != null) {
                    List<String> bookTitles = books.stream().map(Book::getTitle).collect(Collectors.toList());
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, bookTitles);
                    actvLanguageFilter.setAdapter(adapter);
                    actvLanguageFilter.setText("", false); // Clear previous selection
                    cardDetails.setVisibility(View.GONE); // Hide card on new language selection
                }
            }
        });

        actvLanguageFilter.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedTitle = (String) parent.getItemAtPosition(position);
            // Find the selected book from all books
            Book selectedBook = findBookByTitle(selectedTitle);

            if (selectedBook != null) {
                tvCardTitle.setText(selectedBook.getTitle());
                tvCardAuthor.setText("by " + selectedBook.getAuthor());
                tvCardLanguage.setText("Language: " + selectedBook.getLanguage());
                tvCardFlag.setText(languageIcons.get(selectedBook.getLanguage()));
                cardDetails.setVisibility(View.VISIBLE);
            }
        });

        return view;
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

                    // Group books by language
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

    private Book findBookByTitle(String title) {
        for (List<Book> bookList : booksByLanguage.values()) {
            for (Book book : bookList) {
                if (book.getTitle().equals(title)) {
                    return book;
                }
            }
        }
        return null;
    }
}

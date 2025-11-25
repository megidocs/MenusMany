package com.example.menusmany;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Map;

public class LanguagePagerAdapter extends FragmentStateAdapter {

    private final Map<String, ArrayList<Book>> booksByLanguage;
    private final String[] languages;

    public LanguagePagerAdapter(@NonNull FragmentActivity fragmentActivity, Map<String, ArrayList<Book>> booksByLanguage) {
        super(fragmentActivity);
        this.booksByLanguage = booksByLanguage;
        this.languages = new String[]{"English", "French", "German"}; // Define the order of tabs
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Get the language for the current position
        String language = languages[position];
        // Get the list of books for that language
        ArrayList<Book> books = booksByLanguage.get(language);
        // Create a new BookListFragment with the filtered list
        return BookListFragment.newInstance(books != null ? books : new ArrayList<>());
    }

    @Override
    public int getItemCount() {
        // The number of tabs
        return languages.length;
    }
}

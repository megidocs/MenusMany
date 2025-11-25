package com.example.menusmany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IconListFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;
    private final Map<String, String> languageIcons = new HashMap<>();

    public IconListFragment() {
        // Required empty public constructor
    }

    public static IconListFragment newInstance(ArrayList<Book> books) {
        IconListFragment fragment = new IconListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOKS, books);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            books = (ArrayList<Book>) getArguments().getSerializable(ARG_BOOKS);
        }
        languageIcons.put("English", "🇬🇧");
        languageIcons.put("French", "🇫🇷");
        languageIcons.put("German", "🇩🇪");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_icon_list, container, false);
        ListView listView = view.findViewById(R.id.lvWithIcons);

        if (books != null) {
            IconAuthorAdapter adapter = new IconAuthorAdapter(getContext(), books);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view1, position, id) -> {
                Book selectedBook = books.get(position);
                String icon = languageIcons.get(selectedBook.getLanguage());
                new AlertDialog.Builder(getContext())
                        .setTitle(icon + " " + selectedBook.getTitle())
                        .setMessage("Author: " + selectedBook.getAuthor() + "\nLanguage: " + selectedBook.getLanguage())
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            });
        }

        return view;
    }
}

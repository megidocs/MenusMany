package com.example.menusmany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SimpleListFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;

    public SimpleListFragment() {
        // Required empty public constructor
    }

    public static SimpleListFragment newInstance(ArrayList<Book> books) {
        SimpleListFragment fragment = new SimpleListFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_list, container, false);
        ListView listView = view.findViewById(R.id.lvSimple);

        if (books != null) {
            // We only want to display the titles in the list
            ArrayList<String> bookTitles = (ArrayList<String>) books.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bookTitles);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((parent, view1, position, id) -> {
                Book selectedBook = books.get(position);
                new AlertDialog.Builder(getContext())
                        .setTitle(selectedBook.getTitle())
                        .setMessage("Author: " + selectedBook.getAuthor() + "\nLanguage: " + selectedBook.getLanguage())
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            });
        }

        return view;
    }
}

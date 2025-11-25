package com.example.menusmany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SimpleBookListFragment extends Fragment {

    private static final String ARG_BOOKS_LIST = "arg_books_list";

    public static SimpleBookListFragment newInstance(ArrayList<Book> books) {
        SimpleBookListFragment fragment = new SimpleBookListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOKS_LIST, books);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simple_book_list, container, false);

        ListView listView = view.findViewById(R.id.lvSimpleBookList);

        if (getArguments() != null) {
            ArrayList<Book> books = (ArrayList<Book>) getArguments().getSerializable(ARG_BOOKS_LIST);
            if (books != null) {
                ArrayList<String> bookTitles = (ArrayList<String>) books.stream()
                        .map(Book::getTitle)
                        .collect(Collectors.toList());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bookTitles);
                listView.setAdapter(adapter);
            }
        }
        return view;
    }
}

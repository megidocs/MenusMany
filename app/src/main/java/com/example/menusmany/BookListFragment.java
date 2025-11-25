package com.example.menusmany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookListFragment extends Fragment {

    private static final String ARG_BOOKS_LIST = "arg_books_list";

    public static BookListFragment newInstance(ArrayList<Book> books) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOKS_LIST, books);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvBookList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            ArrayList<Book> books = (ArrayList<Book>) getArguments().getSerializable(ARG_BOOKS_LIST);
            if (books != null) {
                BookRecyclerAdapter adapter = new BookRecyclerAdapter(getContext(), books);
                recyclerView.setAdapter(adapter);
            }
        }

        return view;
    }
}

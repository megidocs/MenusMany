package com.example.menusmany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ListWithButtonsFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;

    public ListWithButtonsFragment() {
        // Required empty public constructor
    }

    public static ListWithButtonsFragment newInstance(ArrayList<Book> books) {
        ListWithButtonsFragment fragment = new ListWithButtonsFragment();
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_with_buttons, container, false);

        ListView listView = view.findViewById(R.id.lvWithButtons);

        if (books != null) {
            BookWithButtonsAdapter adapter = new BookWithButtonsAdapter(getContext(), books);
            listView.setAdapter(adapter);
        }

        return view;
    }
}

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

public class ExpandableListFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;

    public ExpandableListFragment() {
        // Required empty public constructor
    }

    public static ExpandableListFragment newInstance(ArrayList<Book> books) {
        ExpandableListFragment fragment = new ExpandableListFragment();
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
        View view = inflater.inflate(R.layout.fragment_expandable_list, container, false);

        ListView listView = view.findViewById(R.id.lvExpandable);

        if (books != null) {
            ExpandableBookAdapter adapter = new ExpandableBookAdapter(getContext(), books);
            listView.setAdapter(adapter);
        }

        return view;
    }
}

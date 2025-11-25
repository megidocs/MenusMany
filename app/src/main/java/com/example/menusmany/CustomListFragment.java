package com.example.menusmany;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class CustomListFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;

    public CustomListFragment() {
        // Required empty public constructor
    }

    public static CustomListFragment newInstance(ArrayList<Book> books) {
        CustomListFragment fragment = new CustomListFragment();
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
        View view = inflater.inflate(R.layout.fragment_custom_list, container, false);
        ListView listView = view.findViewById(R.id.lvCustom);

        if (books != null) {
            CustomBookAdapter adapter = new CustomBookAdapter(getContext(), books);
            listView.setAdapter(adapter);

            // Set divider
            Drawable divider = ContextCompat.getDrawable(getContext(), R.drawable.green_divider);
            listView.setDivider(divider);
            listView.setDividerHeight(10);
        }

        return view;
    }
}

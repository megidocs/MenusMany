package com.example.menusmany;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LiveSearchFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;
    private ArrayAdapter<String> adapter;

    public LiveSearchFragment() {
        // Required empty public constructor
    }

    public static LiveSearchFragment newInstance(ArrayList<Book> books) {
        LiveSearchFragment fragment = new LiveSearchFragment();
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
        View view = inflater.inflate(R.layout.fragment_live_search, container, false);

        EditText etSearch = view.findViewById(R.id.etSearch);
        ListView listView = view.findViewById(R.id.lvLiveSearch);
        TextView tvFilterCount = view.findViewById(R.id.tvFilterCount);

        if (books != null) {
            ArrayList<String> bookTitles = (ArrayList<String>) books.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());

            adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bookTitles);
            listView.setAdapter(adapter);

            // Set initial count
            tvFilterCount.setText("Found: " + adapter.getCount());

            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // No action needed
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s, count1 -> {
                        // This is the callback from the FilterListener
                        tvFilterCount.setText("Found: " + count1);
                    });
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // No action needed
                }
            });
        }

        return view;
    }
}

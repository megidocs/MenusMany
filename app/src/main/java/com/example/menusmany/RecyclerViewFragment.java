package com.example.menusmany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;

    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    public static RecyclerViewFragment newInstance(ArrayList<Book> books) {
        RecyclerViewFragment fragment = new RecyclerViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        recyclerView = view.findViewById(R.id.rvBooks);
        RadioGroup rgOrientation = view.findViewById(R.id.rgOrientation);
        CheckBox cbReverse = view.findViewById(R.id.cbReverse);

        // Set up the RecyclerView
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        BookRecyclerAdapter adapter = new BookRecyclerAdapter(getContext(), books);
        recyclerView.setAdapter(adapter);

        // Listener for orientation change
        rgOrientation.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbVertical) {
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            } else if (checkedId == R.id.rbHorizontal) {
                layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            }
        });

        // Listener for reverse layout change
        cbReverse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            layoutManager.setReverseLayout(isChecked);
        });

        return view;
    }
}

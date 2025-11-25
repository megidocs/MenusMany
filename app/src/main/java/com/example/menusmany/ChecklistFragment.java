package com.example.menusmany;

import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ChecklistFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;

    public ChecklistFragment() {
        // Required empty public constructor
    }

    public static ChecklistFragment newInstance(ArrayList<Book> books) {
        ChecklistFragment fragment = new ChecklistFragment();
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
        View view = inflater.inflate(R.layout.fragment_checklist, container, false);

        ListView listView = view.findViewById(R.id.lvChecklist);
        Button viewButton = view.findViewById(R.id.bViewChecked);

        if (books != null) {
            ArrayList<String> bookTitles = (ArrayList<String>) books.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_multiple_choice, bookTitles);
            listView.setAdapter(adapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            viewButton.setOnClickListener(v -> {
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                StringBuilder selectedItems = new StringBuilder();
                for (int i = 0; i < checked.size(); i++) {
                    int position = checked.keyAt(i);
                    if (checked.valueAt(i)) {
                        selectedItems.append(books.get(position).getTitle()).append("\n");
                    }
                }

                new AlertDialog.Builder(getContext())
                        .setTitle("Selected Books")
                        .setMessage(selectedItems.toString())
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            });
        }

        return view;
    }
}

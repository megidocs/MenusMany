package com.example.menusmany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class AnimatorFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;
    private BookRecyclerAdapter adapter;

    public AnimatorFragment() {
        // Required empty public constructor
    }

    public static AnimatorFragment newInstance(ArrayList<Book> books) {
        AnimatorFragment fragment = new AnimatorFragment();
        Bundle args = new Bundle();
        // Create a copy of the list to avoid modifying the original list
        args.putSerializable(ARG_BOOKS, new ArrayList<>(books));
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
        View view = inflater.inflate(R.layout.fragment_animator, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvAnimator);
        Button bAdd = view.findViewById(R.id.bAdd);
        Button bRemove = view.findViewById(R.id.bRemove);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookRecyclerAdapter(getContext(), books);
        recyclerView.setAdapter(adapter);

        // Set a custom animator with slower speeds
        DefaultItemAnimator animator = new DefaultItemAnimator();
        animator.setAddDuration(120 * 4);
        animator.setRemoveDuration(120 * 4);
        animator.setMoveDuration(250 * 4);
        animator.setChangeDuration(250 * 4);
        recyclerView.setItemAnimator(animator);

        // Listener for the Add button
        bAdd.setOnClickListener(v -> {
            int newBookNumber = new Random().nextInt(1000);
            Book newBook = new Book("New Book " + newBookNumber, "Random Author", "Random Language");
            adapter.addItem(newBook);
            recyclerView.scrollToPosition(0); // Scroll to the top to see the new item
        });

        // Listener for the Remove button
        bRemove.setOnClickListener(v -> {
            if (adapter.getItemCount() > 0) {
                adapter.removeItem(0);
            }
        });

        return view;
    }
}

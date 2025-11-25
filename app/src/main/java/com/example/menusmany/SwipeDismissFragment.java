package com.example.menusmany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SwipeDismissFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;
    private BookRecyclerAdapter adapter;

    public SwipeDismissFragment() {
        // Required empty public constructor
    }

    public static SwipeDismissFragment newInstance(ArrayList<Book> books) {
        SwipeDismissFragment fragment = new SwipeDismissFragment();
        Bundle args = new Bundle();
        // Create a copy to avoid modifying the original list used by other fragments
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
        View view = inflater.inflate(R.layout.fragment_swipe_dismiss, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvSwipeDismiss);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookRecyclerAdapter(getContext(), books);
        recyclerView.setAdapter(adapter);

        // Create and attach the ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                // We are not handling move actions, so we return false
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                // This is where the action happens
                int position = viewHolder.getAdapterPosition();
                adapter.removeItem(position);
            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }
}

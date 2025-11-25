package com.example.menusmany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;

public class StaggeredGridFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int spanCount = 2;

    public StaggeredGridFragment() {
        // Required empty public constructor
    }

    public static StaggeredGridFragment newInstance(ArrayList<Book> books) {
        StaggeredGridFragment fragment = new StaggeredGridFragment();
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
        View view = inflater.inflate(R.layout.fragment_staggered_grid, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvStaggeredGrid);
        SeekBar sbColumns = view.findViewById(R.id.sbColumnsStaggered);
        TextView tvColumnCount = view.findViewById(R.id.tvColumnCountStaggered);

        // Set up the RecyclerView with StaggeredGridLayoutManager
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        BookStaggeredGridAdapter adapter = new BookStaggeredGridAdapter(getContext(), books);
        recyclerView.setAdapter(adapter);

        // Listener for the SeekBar to change span count
        sbColumns.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                spanCount = progress + 2; // progress is 0-2, so spanCount will be 2-4
                tvColumnCount.setText(String.valueOf(spanCount));
                staggeredGridLayoutManager.setSpanCount(spanCount);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return view;
    }
}

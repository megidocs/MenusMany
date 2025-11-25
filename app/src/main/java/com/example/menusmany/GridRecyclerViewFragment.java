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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GridRecyclerViewFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;
    private GridLayoutManager gridLayoutManager;
    private int spanCount = 2;

    public GridRecyclerViewFragment() {
        // Required empty public constructor
    }

    public static GridRecyclerViewFragment newInstance(ArrayList<Book> books) {
        GridRecyclerViewFragment fragment = new GridRecyclerViewFragment();
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
        View view = inflater.inflate(R.layout.fragment_grid_recycler_view, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.rvGrid);
        SeekBar sbColumns = view.findViewById(R.id.sbColumns);
        TextView tvColumnCount = view.findViewById(R.id.tvColumnCount);

        // Set up the RecyclerView with GridLayoutManager
        gridLayoutManager = new GridLayoutManager(getContext(), spanCount);
        recyclerView.setLayoutManager(gridLayoutManager);

        BookGridAdapter adapter = new BookGridAdapter(getContext(), books);
        recyclerView.setAdapter(adapter);

        // SpanSizeLookup to make every 5th item span the full width
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                // Make every 5th item (0-indexed) span all columns
                if ((position + 1) % 5 == 0) {
                    return spanCount;
                } else {
                    return 1;
                }
            }
        });

        // Listener for the SeekBar to change span count
        sbColumns.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                spanCount = progress + 2; // progress is 0-2, so spanCount will be 2-4
                tvColumnCount.setText(String.valueOf(spanCount));
                gridLayoutManager.setSpanCount(spanCount);
                recyclerView.getAdapter().notifyItemRangeChanged(0, books.size());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        return view;
    }
}

package com.example.menusmany;

import android.content.ClipData;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DragDropListFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;
    private DragDropAdapter adapter;

    public DragDropListFragment() {
        // Required empty public constructor
    }

    public static DragDropListFragment newInstance(ArrayList<Book> books) {
        DragDropListFragment fragment = new DragDropListFragment();
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
        View view = inflater.inflate(R.layout.fragment_drag_drop_list, container, false);

        ListView listView = view.findViewById(R.id.lvDragDrop);

        if (books != null) {
            adapter = new DragDropAdapter(getContext(), books);
            listView.setAdapter(adapter);

            listView.setOnDragListener(new View.OnDragListener() {
                private int fromPosition = -1;

                @Override
                public boolean onDrag(View v, DragEvent event) {
                    final int action = event.getAction();

                    switch (action) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            // Get the position of the item being dragged
                            View startView = (View) event.getLocalState();
                            fromPosition = listView.getPositionForView(startView);
                            return true;

                        case DragEvent.ACTION_DRAG_ENTERED:
                        case DragEvent.ACTION_DRAG_LOCATION:
                            return true;

                        case DragEvent.ACTION_DROP:
                            // Get the position where the item was dropped
                            int toPosition = listView.pointToPosition((int) event.getX(), (int) event.getY());

                            if (toPosition != AdapterView.INVALID_POSITION && fromPosition != -1 && fromPosition != toPosition) {
                                // Reorder the data
                                Book movedItem = books.remove(fromPosition);
                                books.add(toPosition, movedItem);
                                adapter.notifyDataSetChanged();
                            }
                            return true;

                        case DragEvent.ACTION_DRAG_ENDED:
                        case DragEvent.ACTION_DRAG_EXITED:
                            fromPosition = -1;
                            return true;

                        default:
                            return false;
                    }
                }
            });
        }

        return view;
    }
}

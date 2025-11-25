package com.example.menusmany;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ContextMenuFragment extends Fragment {

    private static final String ARG_BOOKS = "books";
    private ArrayList<Book> books;

    public ContextMenuFragment() {
        // Required empty public constructor
    }

    public static ContextMenuFragment newInstance(ArrayList<Book> books) {
        ContextMenuFragment fragment = new ContextMenuFragment();
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
        View view = inflater.inflate(R.layout.fragment_context_menu, container, false);

        ListView listView = view.findViewById(R.id.lvContextMenu);

        if (books != null) {
            ArrayList<String> bookTitles = (ArrayList<String>) books.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, bookTitles);
            listView.setAdapter(adapter);

            // Register the ListView for the context menu
            registerForContextMenu(listView);
        }

        return view;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.book_context_menu, menu);

        // Set the menu header to the title of the long-pressed book
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        if (info != null) {
            String bookTitle = books.get(info.position).getTitle();
            menu.setHeaderTitle(bookTitle);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if (info == null) {
            return super.onContextItemSelected(item);
        }

        String selectedOption = item.getTitle().toString();
        String bookTitle = books.get(info.position).getTitle();
        String message = "Selected '" + selectedOption + "' for book: " + bookTitle;

        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

        int itemId = item.getItemId();
        if (itemId == R.id.menu_delete || itemId == R.id.menu_update || itemId == R.id.menu_send || itemId == R.id.menu_search || itemId == R.id.menu_close) {
            return true; // We have handled the event
        } else {
            return super.onContextItemSelected(item);
        }
    }
}

package com.example.menusmany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookWithPickerAdapter extends ArrayAdapter<Book> {

    // This map is crucial to store the value for each book
    private final Map<Book, Integer> bookQuantities = new HashMap<>();

    public BookWithPickerAdapter(@NonNull Context context, ArrayList<Book> books) {
        super(context, 0, books);
        // Initialize quantities to 0 for all books
        for (Book book : books) {
            bookQuantities.put(book, 0);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_with_number_picker, parent, false);
        }

        Book currentBook = getItem(position);

        TextView tvTitle = listItemView.findViewById(R.id.tvTitleWithPicker);
        NumberPicker numberPicker = listItemView.findViewById(R.id.numberPickerItem);

        if (currentBook != null) {
            tvTitle.setText(currentBook.getTitle());

            numberPicker.setMinValue(0);
            numberPicker.setMaxValue(10);

            // Restore the value from our map
            numberPicker.setValue(bookQuantities.getOrDefault(currentBook, 0));

            // Remove previous listener to avoid multiple listeners firing
            numberPicker.setOnValueChangedListener(null);
            
            // Set a new listener to save the value when it changes
            numberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
                bookQuantities.put(currentBook, newVal);
            });
        }

        return listItemView;
    }
}

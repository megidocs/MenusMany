package com.example.menusmany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BookWithButtonsAdapter extends ArrayAdapter<Book> {

    public BookWithButtonsAdapter(@NonNull Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_with_buttons, parent, false);
        }

        Book book = getItem(position);

        TextView tvTitle = convertView.findViewById(R.id.tvBookTitleWithButtons);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);
        Button btnSend = convertView.findViewById(R.id.btnSend);
        Button btnUpdate = convertView.findViewById(R.id.btnUpdate);
        Button btnRemind = convertView.findViewById(R.id.btnRemind);

        if (book != null) {
            tvTitle.setText(book.getTitle());

            // Set listeners for each button
            btnDelete.setOnClickListener(v -> showToast("Delete clicked for: " + book.getTitle()));
            btnSend.setOnClickListener(v -> showToast("Send clicked for: " + book.getTitle()));
            btnUpdate.setOnClickListener(v -> showToast("Update clicked for: " + book.getTitle()));
            btnRemind.setOnClickListener(v -> showToast("Remind clicked for: " + book.getTitle()));
        }

        return convertView;
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}

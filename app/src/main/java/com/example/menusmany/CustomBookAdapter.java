package com.example.menusmany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CustomBookAdapter extends ArrayAdapter<Book> {

    private final Map<String, String> languageIcons = new HashMap<>();

    public CustomBookAdapter(@NonNull Context context, ArrayList<Book> books) {
        super(context, 0, books);
        languageIcons.put("English", "🇬🇧");
        languageIcons.put("French", "🇫🇷");
        languageIcons.put("German", "🇩🇪");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_item, parent, false);
        }

        Book book = getItem(position);

        TextView tvAuthor = convertView.findViewById(R.id.tvAuthor);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvLanguage = convertView.findViewById(R.id.tvLanguage);
        TextView tvFlag = convertView.findViewById(R.id.tvFlag);

        if (book != null) {
            tvAuthor.setText(book.getAuthor());
            tvTitle.setText(book.getTitle());
            tvLanguage.setText(book.getLanguage());
            tvFlag.setText(languageIcons.get(book.getLanguage()));

            tvTitle.setOnClickListener(v -> {
                new AlertDialog.Builder(getContext())
                        .setTitle(book.getTitle())
                        .setMessage("Author: " + book.getAuthor() + "\nLanguage: " + book.getLanguage())
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .show();
            });
        }

        return convertView;
    }
}

package com.example.menusmany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IconAuthorAdapter extends ArrayAdapter<Book> {

    private final Map<String, String> languageIcons = new HashMap<>();

    public IconAuthorAdapter(@NonNull Context context, ArrayList<Book> books) {
        super(context, 0, books);
        languageIcons.put("English", "🇬🇧");
        languageIcons.put("French", "🇫🇷");
        languageIcons.put("German", "🇩🇪");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_with_icon, parent, false);
        }

        Book book = getItem(position);

        TextView icon = convertView.findViewById(R.id.icon);
        TextView text = convertView.findViewById(R.id.text);

        if (book != null) {
            icon.setText(languageIcons.get(book.getLanguage()));
            text.setText(book.getAuthor());
        }

        return convertView;
    }
}

package com.example.menusmany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ExpandableBookAdapter extends ArrayAdapter<Book> {

    private final Set<Integer> expandedPositions = new HashSet<>();

    public ExpandableBookAdapter(@NonNull Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_expandable, parent, false);
        }

        Book book = getItem(position);

        TextView tvTitle = listItemView.findViewById(R.id.tvBookTitleExpandable);
        LinearLayout detailsLayout = listItemView.findViewById(R.id.llExpandableDetails);
        TextView tvAuthor = listItemView.findViewById(R.id.tvAuthorExpandable);
        TextView tvLanguage = listItemView.findViewById(R.id.tvLanguageExpandable);

        if (book != null) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText("Author: " + book.getAuthor());
            tvLanguage.setText("Language: " + book.getLanguage());

            // This part is crucial for ListView's view recycling
            final boolean isExpanded = expandedPositions.contains(position);
            detailsLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

            // The click listener is on the entire item view for better user experience
            listItemView.setOnClickListener(v -> {
                if (expandedPositions.contains(position)) {
                    expandedPositions.remove(position);
                } else {
                    expandedPositions.add(position);
                }
                // This tells the adapter to redraw the view, which will handle visibility
                notifyDataSetChanged(); 
            });
        }

        return listItemView;
    }
}

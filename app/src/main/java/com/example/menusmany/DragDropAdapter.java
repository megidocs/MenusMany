package com.example.menusmany;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DragDropAdapter extends ArrayAdapter<Book> {

    public DragDropAdapter(@NonNull Context context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_drag_handle, parent, false);
        }

        Book book = getItem(position);
        TextView tvTitle = listItemView.findViewById(R.id.tvDragItemTitle);
        ImageView ivDragHandle = listItemView.findViewById(R.id.ivDragHandle);

        if (book != null) {
            tvTitle.setText(book.getTitle());
        }

        // Create a final reference to the view for use in the lambda
        final View finalListItemView = listItemView;
        ivDragHandle.setOnLongClickListener(v -> {
            ClipData.Item item = new ClipData.Item(String.valueOf(position));
            ClipData dragData = new ClipData("drag_item", new String[]{"text/plain"}, item);
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(finalListItemView);

            // Start the drag
            v.startDragAndDrop(dragData, myShadow, finalListItemView, 0);
            return true;
        });

        return listItemView;
    }
}

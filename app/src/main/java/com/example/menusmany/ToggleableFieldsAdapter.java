package com.example.menusmany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToggleableFieldsAdapter extends RecyclerView.Adapter<ToggleableFieldsAdapter.ViewHolder> {

    private final Context context;
    private final List<Book> books;
    private boolean showTitle = false;
    private boolean showAuthor = false;
    private boolean showLanguage = false;

    public ToggleableFieldsAdapter(Context context, List<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_toggleable_fields, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Book book = books.get(position);

        holder.tvTitle.setText(book.getTitle());
        holder.tvAuthor.setText("by " + book.getAuthor());
        holder.tvLanguage.setText("Language: " + book.getLanguage());

        holder.tvTitle.setVisibility(showTitle ? View.VISIBLE : View.GONE);
        holder.tvAuthor.setVisibility(showAuthor ? View.VISIBLE : View.GONE);
        holder.tvLanguage.setVisibility(showLanguage ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setFieldVisibility(boolean showTitle, boolean showAuthor, boolean showLanguage) {
        this.showTitle = showTitle;
        this.showAuthor = showAuthor;
        this.showLanguage = showLanguage;
        notifyDataSetChanged(); // Redraw the whole list with new visibility settings
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvAuthor, tvLanguage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvToggleTitle);
            tvAuthor = itemView.findViewById(R.id.tvToggleAuthor);
            tvLanguage = itemView.findViewById(R.id.tvToggleLanguage);
        }
    }
}

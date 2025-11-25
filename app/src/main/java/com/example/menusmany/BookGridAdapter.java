package com.example.menusmany;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookGridAdapter extends RecyclerView.Adapter<BookGridAdapter.BookGridViewHolder> {

    private final Context context;
    private final ArrayList<Book> books;
    private final Map<String, String> languageIcons = new HashMap<>();

    public BookGridAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
        languageIcons.put("English", "🇬🇧");
        languageIcons.put("French", "🇫🇷");
        languageIcons.put("German", "🇩🇪");
    }

    @NonNull
    @Override
    public BookGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_list_item, parent, false);
        return new BookGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookGridViewHolder holder, int position) {
        Book book = books.get(position);

        holder.tvAuthor.setText(book.getAuthor());
        holder.tvTitle.setText(book.getTitle());
        holder.tvLanguage.setText(book.getLanguage());
        holder.tvFlag.setText(languageIcons.get(book.getLanguage()));

        holder.itemView.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle(book.getTitle())
                    .setMessage("Author: " + book.getAuthor() + "\nLanguage: " + book.getLanguage())
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class BookGridViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor;
        TextView tvTitle;
        TextView tvLanguage;
        TextView tvFlag;

        public BookGridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthorGrid);
            tvTitle = itemView.findViewById(R.id.tvTitleGrid);
            tvLanguage = itemView.findViewById(R.id.tvLanguageGrid);
            tvFlag = itemView.findViewById(R.id.tvFlagGrid);
        }
    }
}

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

public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerAdapter.BookViewHolder> {

    private final Context context;
    private final ArrayList<Book> books;
    private final Map<String, String> languageIcons = new HashMap<>();

    public BookRecyclerAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
        languageIcons.put("English", "🇬🇧");
        languageIcons.put("French", "🇫🇷");
        languageIcons.put("German", "🇩🇪");
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_list_item, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);

        holder.tvAuthor.setText(book.getAuthor());
        holder.tvTitle.setText(book.getTitle());
        holder.tvLanguage.setText(book.getLanguage());
        holder.tvFlag.setText(languageIcons.get(book.getLanguage()));

        holder.tvTitle.setOnClickListener(v -> {
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

    public void addItem(Book book) {
        books.add(0, book);
        notifyItemInserted(0);
    }

    public void removeItem(int position) {
        if (position >= 0 && position < books.size()) {
            books.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void updateList(ArrayList<Book> newBooks) {
        books.clear();
        books.addAll(newBooks);
        notifyDataSetChanged();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor;
        TextView tvTitle;
        TextView tvLanguage;
        TextView tvFlag;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvLanguage = itemView.findViewById(R.id.tvLanguage);
            tvFlag = itemView.findViewById(R.id.tvFlag);
        }
    }
}

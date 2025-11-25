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
import java.util.Random;

public class BookStaggeredGridAdapter extends RecyclerView.Adapter<BookStaggeredGridAdapter.BookStaggeredGridViewHolder> {

    private final Context context;
    private final ArrayList<Book> books;
    private final Map<String, String> languageIcons = new HashMap<>();
    private final Random random = new Random();

    public BookStaggeredGridAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
        languageIcons.put("English", "🇬🇧");
        languageIcons.put("French", "🇫🇷");
        languageIcons.put("German", "🇩🇪");
    }

    @NonNull
    @Override
    public BookStaggeredGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_list_item, parent, false);
        return new BookStaggeredGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookStaggeredGridViewHolder holder, int position) {
        Book book = books.get(position);

        holder.tvAuthor.setText(book.getAuthor());
        holder.tvTitle.setText(book.getTitle());
        holder.tvLanguage.setText(book.getLanguage());
        holder.tvFlag.setText(languageIcons.get(book.getLanguage()));

        // Staggered effect: randomly change the height of the item
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        // Give a base height and add a random value
        int baseHeight = 350; // in pixels
        layoutParams.height = baseHeight + random.nextInt(200);
        holder.itemView.setLayoutParams(layoutParams);

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

    public static class BookStaggeredGridViewHolder extends RecyclerView.ViewHolder {
        TextView tvAuthor;
        TextView tvTitle;
        TextView tvLanguage;
        TextView tvFlag;

        public BookStaggeredGridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthorGrid);
            tvTitle = itemView.findViewById(R.id.tvTitleGrid);
            tvLanguage = itemView.findViewById(R.id.tvLanguageGrid);
            tvFlag = itemView.findViewById(R.id.tvFlagGrid);
        }
    }
}

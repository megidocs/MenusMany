package com.example.menusmany;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DrawerPlaceholderFragment extends Fragment {

    private static final String ARG_TEXT = "arg_text";

    public static DrawerPlaceholderFragment newInstance(String text) {
        DrawerPlaceholderFragment fragment = new DrawerPlaceholderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_placeholder, container, false);
        TextView textView = view.findViewById(R.id.tvPlaceholder);
        if (getArguments() != null) {
            textView.setText(getArguments().getString(ARG_TEXT));
        }
        return view;
    }
}

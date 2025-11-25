package com.example.menusmany;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ChooseComponentsSection extends AppCompatActivity {

    private Button bSpinnerCard, bAutocomplete, bLanguageFilter, bToggleGroup, bChipFilter, bRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_components_section);

        bSpinnerCard = findViewById(R.id.bSpinnerCard);
        bSpinnerCard.setOnClickListener(v -> {
            SpinnerCardFragment fragment = new SpinnerCardFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerChooseComponents, fragment)
                    .commit();
        });

        bAutocomplete = findViewById(R.id.bAutocomplete);
        bAutocomplete.setOnClickListener(v -> {
            AutocompleteFragment fragment = new AutocompleteFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerChooseComponents, fragment)
                    .commit();
        });

        bLanguageFilter = findViewById(R.id.bLanguageFilter);
        bLanguageFilter.setOnClickListener(v -> {
            LanguageFilterFragment fragment = new LanguageFilterFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerChooseComponents, fragment)
                    .commit();
        });
        
        bToggleGroup = findViewById(R.id.bToggleGroup);
        bToggleGroup.setOnClickListener(v -> {
            ToggleGroupFragment fragment = new ToggleGroupFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerChooseComponents, fragment)
                    .commit();
        });

        bChipFilter = findViewById(R.id.bChipFilter);
        bChipFilter.setOnClickListener(v -> {
            ChipFilterFragment fragment = new ChipFilterFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerChooseComponents, fragment)
                    .commit();
        });

        bRadioGroup = findViewById(R.id.bRadioGroup);
        bRadioGroup.setOnClickListener(v -> {
            RadioFilterFragment fragment = new RadioFilterFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flContainerChooseComponents, fragment)
                    .commit();
        });
    }
}

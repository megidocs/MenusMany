package com.example.menusmany;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bLVmain, bRVmain, bCCmain, bNavMain, bSideNavMain, bViewPagerMain, bWheelPickerMain, bListWithPickersMain, bCircularListMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bLVmain = findViewById(R.id.bLVmain);
        bRVmain = findViewById(R.id.bRVmain);
        bCCmain = findViewById(R.id.bCCmain);
        bNavMain = findViewById(R.id.bNavMain);
        bSideNavMain = findViewById(R.id.bSideNavMain);
        bViewPagerMain = findViewById(R.id.bViewPagerMain);
        bWheelPickerMain = findViewById(R.id.bWheelPickerMain);
        bListWithPickersMain = findViewById(R.id.bListWithPickersMain);
        bCircularListMain = findViewById(R.id.bCircularListMain);

        bLVmain.setOnClickListener(this);
        bRVmain.setOnClickListener(this);
        bCCmain.setOnClickListener(this);
        bNavMain.setOnClickListener(this);
        bSideNavMain.setOnClickListener(this);
        bViewPagerMain.setOnClickListener(this);
        bWheelPickerMain.setOnClickListener(this);
        bListWithPickersMain.setOnClickListener(this);
        bCircularListMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        int id = v.getId();
        if (id == R.id.bLVmain) {
            intent = new Intent(this, ListViewSection.class);
        } else if (id == R.id.bRVmain) {
            intent = new Intent(this, RecyclerViewSection.class);
        } else if (id == R.id.bCCmain) {
            intent = new Intent(this, ChooseComponentsSection.class);
        } else if (id == R.id.bNavMain) {
            intent = new Intent(this, NavigationSection.class);
        } else if (id == R.id.bSideNavMain) {
            intent = new Intent(this, SideNavigationBar.class);
        } else if (id == R.id.bViewPagerMain) {
            intent = new Intent(this, ViewPagerActivity.class);
        } else if (id == R.id.bWheelPickerMain) {
            intent = new Intent(this, WheelPickerSection.class);
        } else if (id == R.id.bListWithPickersMain) {
            intent = new Intent(this, ListWithPickersActivity.class);
        } else if (id == R.id.bCircularListMain) {
            intent = new Intent(this, CircularListActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
        }
    }
}

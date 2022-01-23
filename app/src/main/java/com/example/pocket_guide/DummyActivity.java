package com.example.pocket_guide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class DummyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dummy);
        // time stamp = 5.21.31
        // read the data to other fragment [5.23]
        Bundle intent = getIntent().getExtras();
        if(intent !=null){
            String locationId = intent.getString("LocationId");
            getSharedPreferences("LOCATION", MODE_PRIVATE).edit().putString("LocationID", locationId).apply();
            Toast.makeText(this, locationId, Toast.LENGTH_SHORT).show();
        }
    }

}
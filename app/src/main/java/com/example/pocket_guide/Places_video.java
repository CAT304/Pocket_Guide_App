package com.example.pocket_guide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Places_video extends AppCompatActivity {

    ViewPager2 viewPager2;
    videoadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_video);

        viewPager2=(ViewPager2)findViewById(R.id.vpager);

        FirebaseRecyclerOptions<videomodel> options=
                new FirebaseRecyclerOptions.Builder<videomodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Places").child("Escape Penang").child("videos"),videomodel.class)
                        .build();

        adapter = new videoadapter(options);
        viewPager2.setAdapter(adapter);

    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.startListening();
    }
}
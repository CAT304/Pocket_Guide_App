package com.example.pocket_guide;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Places_video extends AppCompatActivity {

    ViewPager2 viewPager2;
    videoadapter adapter;
    String video_title , title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_video);
        Bundle intent = getIntent().getExtras();
        if(intent !=null){
            video_title = intent.getString("Vtitle");
            getSharedPreferences("Vtitle", MODE_PRIVATE).edit().putString("Vtitle",video_title).apply();
        }

        title = video_title;
        viewPager2=(ViewPager2)findViewById(R.id.vpager);

        FirebaseRecyclerOptions<videomodel> options=
                new FirebaseRecyclerOptions.Builder<videomodel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Places").child(title).child("videos"),videomodel.class)
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

package com.example.pocket_guide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Places_info extends AppCompatActivity {
        TextView a,b , name;
        EditText review;
        FloatingActionButton btn , loc;
        Button etr;
        ImageView img;
        DatabaseReference reff , rew , lrew , locref;
        StorageReference storageReference;
        Uri imageuri;
        ListView listView;
        String placename, place_name , pllocation;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_places_info);

                Bundle intent = getIntent().getExtras();
                if(intent !=null){
                        place_name = intent.getString("Name");
                        getSharedPreferences("Name", MODE_PRIVATE).edit().putString("Name", place_name).apply();
                }
//                placename = "Bukit Bendera";
                placename = place_name;
                a=(TextView) findViewById(R.id.Ratings);
                b=(TextView) findViewById(R.id.Details);
                name=(TextView) findViewById(R.id.place_name) ;
                btn=(FloatingActionButton)findViewById(R.id.acptbtn);
                loc = (FloatingActionButton)findViewById(R.id.locbtn);
                img=(ImageView)findViewById(R.id.picplace);

                review = findViewById(R.id.etReview);
                etr = findViewById(R.id.Enter);

                listView = findViewById(R.id.List_reviews);

                rew = FirebaseDatabase.getInstance().getReference().child("Places").child(placename).child("Reviews");

                ArrayList<String> list = new ArrayList<>();
                ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.reviews_list,list);
                listView.setAdapter(adapter);

                btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                Intent i = new Intent(Places_info.this,Places_video.class);
                                i.putExtra("Vtitle",placename);
                                startActivity(i);
                        }
                });

                loc.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                Intent intent1 = new Intent(Intent.ACTION_VIEW,
                                        Uri.parse("google.navigation:q="+ pllocation));
                                intent1.setPackage("com.google.android.apps.maps");
                                startActivity(intent1);
                        }
                });

                etr.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                                insertReviews();
                        }
                });



                storageReference = FirebaseStorage.getInstance().getReference("Dest Pitcure/"+placename+".jpg");
                try {
                        File localfile = File.createTempFile("tempfile",".jpg");
                        storageReference.getFile(localfile)
                                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                                Bitmap bitmap = BitmapFactory.decodeFile(localfile.getAbsolutePath());
                                                img.setImageBitmap(bitmap);
                                        }
                                }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Places_info.this,"Failed to retrive",Toast.LENGTH_SHORT).show();
                                }
                        });
                } catch (IOException e) {
                        e.printStackTrace();
                }

                rew.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                list.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                                        String temp_review = snapshot.getValue().toString();
                                        String temp_review1 = temp_review.substring(8);
                                        temp_review1 = temp_review1.substring(0, temp_review1.length() - 1);
                                        list.add(Objects.requireNonNull(temp_review1));
                 //                       Toast.makeText(Places_info.this,"Data"+ snapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
 //                                       Toast.makeText(Places_info.this,"Data"+ temp_review1,Toast.LENGTH_SHORT).show();
                                }
                                adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                });



                reff= FirebaseDatabase.getInstance().getReference().child("Places").child(placename);
                reff.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String details = Objects.requireNonNull(snapshot.child("Details").getValue()).toString();
                                String ratings = Objects.requireNonNull(snapshot.child("Ratings").getValue()).toString();
                                String loc = Objects.requireNonNull(snapshot.child("Location").getValue()).toString();
                                a.setText(ratings);
                                b.setText(details);
                                name.setText(placename);
                                pllocation = loc;
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                });


        }

        private void insertReviews(){
                String reviews = review.getText().toString();

                ReviewData rd = new ReviewData(reviews);

                rew.push().setValue(rd);// To insert the new record
                Toast.makeText(Places_info.this,"Data inserted" ,Toast.LENGTH_SHORT).show();
        }

}
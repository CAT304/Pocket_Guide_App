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

public class Places_info extends AppCompatActivity {
        TextView a,b;
        EditText review;
        Button btn , etr;
        ImageView img;
        DatabaseReference reff , rew , lrew;
        StorageReference storageReference;
        Uri imageuri;
        ListView listView;
        String placename;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_places_info);
                placename = "Tanjung Bungah";
                a=(TextView) findViewById(R.id.Ratings);
                b=(TextView) findViewById(R.id.Details);
                btn=(Button) findViewById(R.id.acptbtn);
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
                                        Toast.makeText(Places_info.this,"Failed to retrive",Toast.LENGTH_SHORT);
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
                                        list.add(snapshot.getValue().toString());
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
                                String details = snapshot.child("Details").getValue().toString();
                                String ratings = snapshot.child("Ratings").getValue().toString();
                                a.setText(ratings);
                                b.setText(details);
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
                Toast.makeText(Places_info.this,"Data inserted",Toast.LENGTH_SHORT).show();
        }




}
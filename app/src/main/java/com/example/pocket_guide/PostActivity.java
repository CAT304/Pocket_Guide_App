package com.example.pocket_guide;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hendraanggrian.appcompat.socialview.Hashtag;
import com.hendraanggrian.appcompat.widget.HashtagArrayAdapter;
import com.hendraanggrian.appcompat.widget.SocialAutoCompleteTextView;
import com.theartofdev.edmodo.cropper.CropImage;
import java.util.HashMap;
import java.util.Objects;
public class PostActivity extends AppCompatActivity {
    private Uri imageUri;
    private String imageUrl;
    private ImageView imageAdded;
    SocialAutoCompleteTextView description;
    private DatabaseReference databaseReference;
    private StorageReference filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        ImageView close = findViewById(R.id.close);
        imageAdded = findViewById(R.id.image_added);
        TextView post = findViewById(R.id.post);
        description = findViewById(R.id.description);
        close.setOnClickListener(v -> {
            startActivity(new Intent(PostActivity.this , DummyActivity.class));
            finish();
        });
        post.setOnClickListener(v -> upload());
        CropImage.activity().start(PostActivity.this);
    }

    private void upload() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading the image");
        pd.show();
        if (imageUri != null){
            filePath = FirebaseStorage.getInstance().getReference("Posted Pics").child(System.currentTimeMillis() + ".png");
            UploadTask uploadtask = filePath.putFile(imageUri);
            Task<Uri> urlTask = uploadtask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                }
                return filePath.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Uri downloadUri = task.getResult();
                    imageUrl = downloadUri != null ? downloadUri.toString() : null;
                    databaseReference = FirebaseDatabase.getInstance().getReference("Posts");
                    String Post_ID = databaseReference.push().getKey();

                    Toast.makeText(this, Post_ID, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, imageUrl, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, description.getText().toString(), Toast.LENGTH_SHORT).show();

                    String Test_ID = "testid3"; // set the user id
                    HashMap<String , Object> map = new HashMap<>();
                    map.put("postid" , Post_ID);
                    map.put("imageurl" , imageUrl);
                    map.put("description" , description.getText().toString());
                    map.put("publisher" , Test_ID);
                    //map.put("publisher" , FirebaseAuth.getInstance().getCurrentUser().getUid()); ----------------------> gotta change back this line

                    if (Post_ID != null) {
                        databaseReference.child(Post_ID).setValue(map);
                    }
                    Toast.makeText(this, "Data inserted", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
                startActivity(new Intent(PostActivity.this , MainActivity.class));
                finish();
            }).addOnFailureListener(e -> Toast.makeText(PostActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show());
        } else {
            Toast.makeText(this, "No image was selected!", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result != null ? result.getUri() : null;
            imageAdded.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PostActivity.this , MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        final ArrayAdapter<Hashtag> hashtagAdapter = new HashtagArrayAdapter<>(getApplicationContext());
        FirebaseDatabase.getInstance().getReference().child("HashTags").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    hashtagAdapter.add(new Hashtag(Objects.requireNonNull(snapshot.getKey()), (int) snapshot.getChildrenCount()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        description.setHashtagAdapter(hashtagAdapter);
    }
}
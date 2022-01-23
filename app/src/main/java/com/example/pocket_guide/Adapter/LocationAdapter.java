package com.example.pocket_guide.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pocket_guide.DummyActivity;
import com.example.pocket_guide.Model.Location;
import com.example.pocket_guide.R;
import com.squareup.picasso.Picasso;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder>{
    private Context mContext;
    private List<Location> mLocations;
    private boolean isFragment;

    public LocationAdapter(Context mContext, List<Location> mLocations, boolean isFragment){
        this.mContext = mContext;
        this.mLocations = mLocations;
        this.isFragment = isFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(mContext).inflate(R.layout.location_item, parent, false);
        return new LocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position){
        Location location = mLocations.get(position);
        holder.location_name.setText(location.getName());
        Picasso.get().load(location.getImageUri()).placeholder(R.mipmap.ic_launcher).into(holder.location_image);
        // when we click the location name the function below is executed.
        holder.location_name.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               Intent intent = new Intent(mContext, DummyActivity.class);
               intent.putExtra("Name", location.getName());
               mContext.startActivity(intent);
            }
        });
        // when we click the location image the function below is executed.
        holder.location_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(mContext, DummyActivity.class);
                intent.putExtra("Name", location.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView location_image;
        public TextView location_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            location_image = itemView.findViewById(R.id.location_image);
            location_name = itemView.findViewById(R.id.location_name);
        }
    }
}


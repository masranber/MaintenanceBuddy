package com.example.maintenancebuddy.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenancebuddy.R;
import com.example.maintenancebuddy.data.model.Garage;
import com.example.maintenancebuddy.databinding.FragmentDashboardBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

public class DashboardFragment extends Fragment {

    private DashboardViewModel       dashboardViewModel;
    private FragmentDashboardBinding binding;
    private RecyclerView vehicleList;
    FirebaseFirestore                db;
    FirestoreRecyclerOptions<Garage> options;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState
    ) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        db = FirebaseFirestore.getInstance();
        vehicleList = binding.recycleView;
        vehicleList.setHasFixedSize(true);
        vehicleList.setLayoutManager(new LinearLayoutManager(getContext()));
        Query query = db.collection("Garage");
        options = new FirestoreRecyclerOptions.Builder<Garage>()
                .setQuery(query, Garage.class)
                .build();
        return root;
    }
    @Override
    public void onStart() {
        super.onStart();
        FirestoreRecyclerAdapter<Garage,GarageViewHolder> adapter = new FirestoreRecyclerAdapter<Garage, GarageViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull GarageViewHolder holder, int position, @NonNull Garage model) {
                holder.setTitle(model.getName());
                holder.setSubHeading(model.getDescription());
                holder.setImage(getContext(),model.getImage());
            }

            @NonNull
            @Override
            public GarageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        };
        vehicleList.setAdapter(adapter);
        }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
};


class GarageViewHolder extends RecyclerView.ViewHolder {
    private View view;

     GarageViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

   void setTitle(String title){
         TextView post_title = (TextView)view.findViewById(R.id.heading_post);
         post_title.setText(title);
    }
     void setSubHeading(String title){
         TextView post_title = (TextView)view.findViewById(R.id.description_post);
         post_title.setText(title);
     }
     void setImage(Context ctx, String image){
         ImageView post_image = (ImageView) view.findViewById(R.id.image_post);
         Picasso.with(ctx).load(image).into(post_image);
     }

}
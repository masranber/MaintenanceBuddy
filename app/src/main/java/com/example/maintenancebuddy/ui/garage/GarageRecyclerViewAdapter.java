package com.example.maintenancebuddy.ui.garage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenancebuddy.R;
import com.example.maintenancebuddy.data.model.MaintenanceEvent;
import com.example.maintenancebuddy.data.model.Vehicle;
import com.example.maintenancebuddy.ui.recyclerview.ModelViewHolder;
import com.example.maintenancebuddy.ui.recyclerview.OnItemClickListener;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class GarageRecyclerViewAdapter extends FirestoreRecyclerAdapter<Vehicle, GarageRecyclerViewAdapter.VehicleViewHolder> {

    private OnItemClickListener<Vehicle> onItemClickListener;

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public GarageRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<Vehicle> options) {
        super(options);
    }

    public void setOnItemClickListener(OnItemClickListener<Vehicle> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    protected void onBindViewHolder(@NonNull VehicleViewHolder holder, int position, @NonNull Vehicle model) {
        holder.onBind(model, onItemClickListener);
    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vehicle, parent, false);
        return new VehicleViewHolder(view);
    }

    public static class VehicleViewHolder extends ModelViewHolder<Vehicle> {

        private final TextView makeModelText, yearText;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            makeModelText = itemView.findViewById(R.id.vehicle_makemodel);
            yearText = itemView.findViewById(R.id.vehicle_year);
        }

        @Override
        public void onBindModel(Vehicle model) {
            makeModelText.setText(model.getMake() + " " + model.getModel());
            yearText.setText(String.valueOf(model.getYear()));
        }
    }
}

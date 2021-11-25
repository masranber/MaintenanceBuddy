package com.example.maintenancebuddy.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maintenancebuddy.R;
import com.example.maintenancebuddy.data.model.MaintenanceEvent;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MaintenanceHistoryRecyclerViewAdapter extends FirestoreRecyclerAdapter<MaintenanceEvent, MaintenanceHistoryRecyclerViewAdapter.MaintenanceEventViewHolder> {


    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MaintenanceHistoryRecyclerViewAdapter(@NonNull FirestoreRecyclerOptions<MaintenanceEvent> options) {
        super(options);
    }

    public void updateOptions(@NonNull FirestoreRecyclerOptions<MaintenanceEvent> options) {
        updateOptions(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MaintenanceEventViewHolder holder, int position, @NonNull MaintenanceEvent model) {
        holder.bindMaintenanceEvent(model);
    }

    @NonNull
    @Override
    public MaintenanceEventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_maintenance_history_item, parent, false);
        return new MaintenanceEventViewHolder(view);
    }

    public static class MaintenanceEventViewHolder extends RecyclerView.ViewHolder {

        private final TextView nameTextView, odometerTextView, dateTextView, costTextView;

        public MaintenanceEventViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_name);
            odometerTextView = itemView.findViewById(R.id.item_timestamp_value);
            dateTextView = itemView.findViewById(R.id.item_date_value);
            costTextView = itemView.findViewById(R.id.item_total_value);
        }

        public void bindMaintenanceEvent(MaintenanceEvent maintenanceEvent) {
            nameTextView.setText(maintenanceEvent.getDescription());
            odometerTextView.setText(String.valueOf(maintenanceEvent.getVehicleOdometer()));
            dateTextView.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date(maintenanceEvent.getDatePerformed())));
            costTextView.setText(String.format("$ %.2f", maintenanceEvent.getTotalCost()));
        }
    }
}

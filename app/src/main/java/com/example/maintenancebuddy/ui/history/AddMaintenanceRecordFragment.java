package com.example.maintenancebuddy.ui.history;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.maintenancebuddy.R;
import com.example.maintenancebuddy.data.DatabaseKeys;
import com.example.maintenancebuddy.data.MaintenanceType;
import com.example.maintenancebuddy.data.TextInputValidator;
import com.example.maintenancebuddy.data.model.MaintenanceEvent;
import com.example.maintenancebuddy.data.repository.VehicleRepository;
import com.example.maintenancebuddy.databinding.FragmentAddMaintenanceRecordBinding;
import com.example.maintenancebuddy.databinding.FragmentMaintenanceHistoryBinding;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddMaintenanceRecordFragment extends Fragment {

    private FragmentAddMaintenanceRecordBinding binding;
    private AddMaintenanceRecordViewModel mViewModel;

    private Date performedOnDate;
    private MaintenanceType maintenanceType;

    @Inject
    protected VehicleRepository vehicleRepository;

    public static AddMaintenanceRecordFragment newInstance() {
        return new AddMaintenanceRecordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState
    ) {
        mViewModel = new ViewModelProvider(this).get(AddMaintenanceRecordViewModel.class);
        binding = FragmentAddMaintenanceRecordBinding.inflate(inflater, container, false);
        final View root = binding.getRoot();

        AutoCompleteTextView serviceTypeTextView = (AutoCompleteTextView) binding.serviceTypeInput.getEditText();
        serviceTypeTextView.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, MaintenanceType.values()));

        serviceTypeTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                maintenanceType = (MaintenanceType) adapterView.getItemAtPosition(i);
            }
        });

        binding.datePerformedInput.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(view.isInTouchMode() && b) {
                    view.performClick();
                }
            }
        });

        binding.datePerformedInput.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        binding.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean serviceType = TextInputValidator.validateRequiredField(binding.serviceTypeInput.getEditText().getText().toString());
                boolean serviceName = TextInputValidator.validateRequiredField(binding.serviceNameInput.getEditText().getText().toString());
                boolean datePerformed = TextInputValidator.validateRequiredField(binding.datePerformedInput.getEditText().getText().toString());
                boolean odometer = TextInputValidator.validateRequiredField(binding.odometerInput.getEditText().getText().toString());
                boolean reason = TextInputValidator.validateRequiredField(binding.serviceReasonInput.getEditText().getText().toString());
                boolean cost = TextInputValidator.validateRequiredField(binding.costInput.getEditText().getText().toString());
                if(serviceType && serviceName && datePerformed && odometer && reason && cost) {
                    MaintenanceEvent event = new MaintenanceEvent();
                    event.setVehicleUID(vehicleRepository.getCurrentVehicle().getUID());
                    event.setMaintenanceType(maintenanceType.ordinal());
                    event.setDescription(binding.serviceNameInput.getEditText().getText().toString());
                    event.setDatePerformed(performedOnDate.getTime());
                    event.setVehicleOdometer(Double.parseDouble(binding.odometerInput.getEditText().getText().toString()));
                    event.setReason(binding.serviceReasonInput.getEditText().getText().toString());
                    event.setTotalCost(Double.parseDouble(binding.costInput.getEditText().getText().toString()));
                    DocumentReference doc = FirebaseFirestore.getInstance().collection(DatabaseKeys.COLLECTION_RECORDS).document();
                    event.setUID(doc.getId());
                    doc.set(event);

                }
            }
        });

        return root;
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Service Performed Date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(new CalendarConstraints.Builder().setEnd(MaterialDatePicker.todayInUtcMilliseconds()).setValidator(DateValidatorPointBackward.now()).build())
                .build();
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                performedOnDate = new Date(selection+1);
                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                binding.datePerformedInput.getEditText().setText(df.format(performedOnDate));
            }
        });
        datePicker.show(getParentFragmentManager(), "NewRecordDatePicker");
    }

}
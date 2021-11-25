package com.example.maintenancebuddy.ui.addVehicle;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.maintenancebuddy.R;
import com.example.maintenancebuddy.data.UserRepository;
import com.example.maintenancebuddy.data.model.Vehicle;
import com.example.maintenancebuddy.databinding.AddVehicleFragmentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class addVehicleFragment extends Fragment {

    private AddVehicleViewModel addVehicleViewModel;
    private AddVehicleFragmentBinding binding;
    FirebaseFirestore db;
    String userID;
    private Uri filePath;

    @Inject
    public UserRepository userRepository;

    public static addVehicleFragment newInstance() {
        return new addVehicleFragment();
    }
    public static final int PICK_IMAGE = 200;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        userID = userRepository.getCurrentUser().uid;
        AddVehicleViewModel addVehicleViewModel = new ViewModelProvider(this ).get(AddVehicleViewModel.class);

        binding = AddVehicleFragmentBinding.inflate(inflater,container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        Button selectImage = binding.selectImageButton;
        Button addVehicle = binding.addVehicleButton;
        Button addEvent = binding.addEventButton; // STUB to be done in iteration 2
        EditText make = binding.editTextMake;
        EditText model = binding.editTextModel;
        EditText color = binding.editTextColor;
        EditText miles = binding.editTextMiles;
        EditText year = binding.editTextYear;
        EditText vin = binding.editTextplateNumber;
        ImageView preview = binding.imageView;
        TextView errorText = binding.ErrorText;
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFileChooser();
                if(filePath!=null){
                    ProgressDialog pd = new ProgressDialog(getActivity());
                    pd.setMessage("Loading");
                    pd.show();
                    StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("images/pic.jpg");
                    imageRef.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            pd.dismiss();
                            Toast.makeText(getContext(),"FileUploaded",Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                            double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                            pd.setMessage(Double.toString(progress));
                        }
                    });
                }
            }
        });
        addVehicle.setOnClickListener(view -> {
              if(!(isEmpty(make) || isEmpty(model) || isEmpty(year))){
                  Vehicle newVehicle = new Vehicle(userID,make.getText().toString(),model.getText().toString(),Integer.parseInt(year.getText().toString()));
                  newVehicle.setGarageUID(userID);
                  if(!isEmpty(color)){
                      newVehicle.setColor(color.getText().toString());
                  }
                  if(!isEmpty(miles)){
                      newVehicle.setMiles(Integer.parseInt(miles.getText().toString()));
                  }
                  if(!isEmpty(vin)){
                      newVehicle.setVin(vin.getText().toString());
                  }
                  DocumentReference doc = db.collection("vehicles").document();
                  newVehicle.setUID(doc.getId());
                  doc.set(newVehicle);
                  Navigation.findNavController(binding.getRoot()).popBackStack(R.id.navigation_add_vehicle, true);
              }
              else{
                errorText.setText("Please make sure you have the make model and year");
              }
          });
        return root;
    }


    private void startFileChooser(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        someActivityResultLauncher.launch(intent);
    }
    private boolean isEmpty(EditText text) {
        return text.getText().toString().trim().length() == 0;
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        filePath = data.getData();

                    }
                }
            });


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
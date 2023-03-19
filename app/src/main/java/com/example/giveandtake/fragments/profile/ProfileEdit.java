package com.example.giveandtake.fragments.profile;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import com.example.giveandtake.R;
import com.example.giveandtake.databinding.FragmentProfileEditBinding;
import com.example.giveandtake.model.User;
import com.example.giveandtake.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileEdit extends Fragment {

    EditText ageET, addressET, workPlaceET, volunteerET, nameET;
    Button updateBtn;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private FirebaseAuth auth;
    ProgressBar progressBar;
    FragmentProfileEditBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isProfileSelected = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.editProfileImage.setImageBitmap(result);
                    isProfileSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.editProfileImage.setImageURI(result);
                    isProfileSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileEditBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        binding.profileSaveBtn.setOnClickListener(view1->{
            String age = ageET.getText().toString();
            String work = workPlaceET.getText().toString();
            String address = addressET.getText().toString();
            String volunteer = volunteerET.getText().toString();
            String name = nameET.getText().toString();
            progressBar.setVisibility(View.VISIBLE);

            if (isProfileSelected){
                binding.editProfileImage.setDrawingCacheEnabled(true);
                binding.editProfileImage.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.editProfileImage.getDrawable()).getBitmap();
                UserModel.instance().uploadImage(user.getUid(), bitmap, url->{
                    DocumentReference docRef = FirebaseFirestore.getInstance().collection("User").document(user.getUid());
                    docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User userEdit = documentSnapshot.toObject(User.class);
                            if (url != null){
                                userEdit.setProfileImage(url);
                                updateAccount(age, work, address, volunteer,name,url);
                            }
                        }
                    });
                });
            }else
                updateAccount(age, work, address, volunteer,name,"");
            Navigation.findNavController(view1).popBackStack(R.id.profileFragment,false);
        });
        binding.cancellBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.profileFragment,false));

        binding.cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        NavController navController = NavHostFragment.findNavController(this);
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        ageET = view.findViewById(R.id.birthdayTv_edit);
        addressET = view.findViewById(R.id.addressTv_edit);
        workPlaceET = view.findViewById(R.id.workTv_edit);
        volunteerET = view.findViewById(R.id.profile_volunteerTv_edit);
        updateBtn = view.findViewById(R.id.profile_saveBtn);
        auth = FirebaseAuth.getInstance();
        nameET = view.findViewById(R.id.edit_nameTv);
        progressBar = view.findViewById(R.id.update_progressBar);
    }

    private void updateAccount( String age, String work, String address, String volunteer, String name, String profileImage)
    {
        auth.updateCurrentUser(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(getContext(), "Profile Updated successfully", Toast.LENGTH_SHORT).show();
                }
                uploadUpdatedUser(user,age, work, address, volunteer, name, profileImage);

            }
        });
    }

    private void uploadUpdatedUser(FirebaseUser user,String age,String work, String address, String volunteer , String name, String profileImage) {
        Map<String, Object> map = new HashMap<>();
        if(!age.equals(""))
            map.put("age", age);
        if(!work.equals(""))
            map.put("workAt", work);
        if(!address.equals(""))
            map.put("address", address);
        if(!volunteer.equals(""))
            map.put("volunteerStatus", volunteer);
        if(!name.equals(""))
            map.put("name", name);
        if(!profileImage.equals(""))
            map.put("profileImage", profileImage);


        FirebaseFirestore.getInstance().collection("User").document(user.getUid()).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    String exception = task.getException().getMessage();
                    Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

package com.example.giveandtake.fragments;

import static com.example.giveandtake.utils.ImagesContent.loadSavedImages;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
//import com.example.giveandtake.Manifest;
//import com.canhub.cropper.CropImage;
import com.example.giveandtake.R;
import com.example.giveandtake.adapter.GalleryAdapter;
import com.example.giveandtake.model.GalleryImages;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PrimitiveIterator;

public class Add extends Fragment {

    private EditText descET;
    private ImageView imageView;
    private RecyclerView recyclerView;

    private ImageButton backBtn, nextBtn;

    private List<GalleryImages> list;
    private GalleryAdapter adapter;
    private FirebaseUser user;

    Uri imageUri;
    String imageURL;

    public Add() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();
        adapter =  new GalleryAdapter(list);

        recyclerView.setAdapter(adapter);

        clickListener();

    }

    public void clickListener(){
        adapter.SendImage(new GalleryAdapter.SendImage() {
            @Override
            public void onSend(Uri picUri) {
                imageUri = picUri;
                Glide.with(getContext())
                                .load(picUri)
                                        .into(imageView);
                imageView.setVisibility(View.VISIBLE);
                nextBtn.setVisibility(View.VISIBLE);

                //CropImage.ActivityResult(picUri)

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();

            }
        });
    }

    private void uploadData(){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("Post Images/"+System.currentTimeMillis());
        storageReference.putFile(imageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    imageURL = uri.toString();
                                    uploadData(uri.toString());
                                }
                            });
                        }
                    }
                });
    }

    private void uploadData(String imageURL)
    {

        CollectionReference reference = FirebaseFirestore
                .getInstance()
                .collection("Users")
                .document(user.getUid())
                .collection("Post Images");

        String id =reference.document().getId();
        String description = descET.getText().toString();
        Map<String,Object> map = new HashMap<>();
        map.put("id", id);
        map.put("description", description);
        map.put("imageUrl", imageURL);
        map.put("timestamp", FieldValue.serverTimestamp());

        map.put("userName", String.valueOf(user.getPhotoUrl()));
        map.put("profileImage", user.getDisplayName());
        map.put("likeCount", 0);
        map.put("comments", "");
        map.put("uid", user.getUid());





        reference.document(id).set(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            System.out.println();
                        }
                        else
                            Toast.makeText(getContext(),"Error: "+ task.getException().getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                    }
                });

    }
    private void init(View view)
    {
        descET = view.findViewById(R.id.descriptionET);
        imageView = view.findViewById(R.id.imageView);
        recyclerView = view.findViewById(R.id.recyclerView);
        backBtn = view.findViewById(R.id.backBtn);
        nextBtn = view.findViewById(R.id.nextBtn);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }


    @Override
    public void onResume() {
        super.onResume();

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadSavedImages(getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS));
             adapter.notifyDataSetChanged();

                /*
                                Dexter.withContext(getContext())
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE )
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {

                                if(report.areAllPermissionsGranted()) {
                                    File file = new File(Environment.getExternalStorageDirectory().toString()+ "/Download");
                                    if(file.exists())
                                    {
                                        File[] files = file.listFiles();
                                        assert files != null;
                                        for (File f: files)
                                        {
                                            if (f.getAbsolutePath().endsWith(".jpg")||f.getAbsolutePath().endsWith(".png"))
                                             {
                                                list.add(new GalleryImages(Uri.fromFile(f)));
                                                adapter.notifyDataSetChanged();
                                            }
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

                            }
                        }).check();
                 */
            }
        });
    }
}
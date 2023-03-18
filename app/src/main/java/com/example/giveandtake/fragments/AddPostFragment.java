package com.example.giveandtake.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.giveandtake.R;
import com.example.giveandtake.databinding.FragmentAddPostBinding;
import com.example.giveandtake.model.PostModel;
import com.example.giveandtake.model.Post;
import com.example.giveandtake.model.User;
import com.example.giveandtake.fragments.user.UserListFragmentViewModel;
import com.example.giveandtake.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class AddPostFragment extends Fragment {
    FragmentAddPostBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    Boolean isPictureSelected = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.addFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        },this, Lifecycle.State.RESUMED);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.postImage.setImageBitmap(result);
                    isPictureSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.postImage.setImageURI(result);
                    isPictureSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddPostBinding.inflate(inflater,container,false);

        View view = binding.getRoot();

        List<User> list = new LinkedList<>();
        FirebaseFirestore.getInstance().collection("User").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot jsonsList = task.getResult();
                    for (DocumentSnapshot json : jsonsList) {
                        User user = User.fromJson(json.getData());
                        list.add(user);
                    }
                }
            }
        });

        binding.addpostSaveBtn.setOnClickListener(view1 -> {
            String postText = binding.addpostPostText.getText().toString();
            String postId = "id_"+postText;;
            FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
            User postUser = new User();
            for(int i=0; i<list.size(); i++){
                if(list.get(i).getId().equals(fireUser.getUid())) {
                    postUser = list.get(i);
                }
            }
            String name = postUser.getName();
            String profileImage = postUser.profileImageURL;

            Post post = new Post(name,profileImage,"", 0L,postText,postId,"");

            if (isPictureSelected){
                binding.postImage.setDrawingCacheEnabled(true);
                binding.postImage.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.postImage.getDrawable()).getBitmap();
                PostModel.instance().uploadImage(postId, bitmap, url->{
                    if (url != null){
                        post.setPostImage(url);
                    }
                    PostModel.instance().addPost(post, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                    });
                });
            }else {
                PostModel.instance().addPost(post, (unused) -> {
                    Navigation.findNavController(view1).popBackStack();
                });
            }
        });
        binding.cancellBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.homeListFragment,false));

        binding.cameraButton.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1->{
            galleryLauncher.launch("images/*");
        });
            return view;
    }
}
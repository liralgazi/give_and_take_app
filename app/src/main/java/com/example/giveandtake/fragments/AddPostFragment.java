package com.example.giveandtake.fragments;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LiveData;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class AddPostFragment extends Fragment {
    FragmentAddPostBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    UserListFragmentViewModel viewModel;

    Boolean isPictureSelected = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.addPostFragment);
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
                    binding.avatarImg.setImageBitmap(result);
                    isPictureSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.avatarImg.setImageURI(result);
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

        binding.addpostSaveBtn.setOnClickListener(view1 -> {
            String postText = binding.addpostPostText.getText().toString();
            String postId = "id_"+postText;;
            FirebaseUser fireUser = FirebaseAuth.getInstance().getCurrentUser();
           LiveData<User> user = UserModel.instance().getUserById(fireUser.getUid());
            List<User> list = UserModel.instance().getAllUsers().getValue();
            Post post = new Post("userName","","", 0L,postText,postId,"");

            if (isPictureSelected){
                binding.avatarImg.setDrawingCacheEnabled(true);
                binding.avatarImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.avatarImg.getDrawable()).getBitmap();
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
            galleryLauncher.launch("image/*");
        });
            return view;
    }
}
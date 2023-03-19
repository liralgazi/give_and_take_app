package com.example.giveandtake.fragments.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.navigation.NavDirections;

import com.example.giveandtake.R;
import com.example.giveandtake.databinding.FragmentHomeListBinding;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.giveandtake.model.FireBaseModel;
import com.example.giveandtake.model.PostModel;
import com.example.giveandtake.model.Post;
import com.example.giveandtake.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HomeListFragment extends Fragment {
    FragmentHomeListBinding binding;
   HomeRecyclerAdapter adapter;
    DatabaseReference reference;

    HomeListFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentHomeListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HomeRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new HomeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Post post = viewModel.getData().getValue().get(pos);
                post.setLikeCount(post.getLikeCount()+1);
                setLikeCountToDb(post);
            }
        });

        binding.progressBar.setVisibility(View.GONE);


        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            if(list.size() != 0){
               adapter.setData(list);
            }
        });

        PostModel.instance().EventPostListLoadingState.observe(getViewLifecycleOwner(), status->{
            binding.swipeRefresh.setRefreshing(status == PostModel.LoadingState.LOADING);
        });

       binding.swipeRefresh.setOnRefreshListener(()->{
           reloadData();
       });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(HomeListFragmentViewModel.class);
    }

    void reloadData(){
       // binding.progressBar.setVisibility(View.VISIBLE);
        PostModel.instance().refreshAllPosts();
    }

    public void setLikeCountToDb(Post post){
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", post.likeCount);
        FirebaseFirestore.getInstance().collection("posts").document(post.getPostId()).update(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    assert getActivity() != null;
                } else {
                    String exception = task.getException().getMessage();
                    Toast.makeText(getContext(), "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        reloadData();
    }
}
package com.example.giveandtake.fragments.user;

import android.content.Context;
import android.os.Bundle;

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
import android.widget.SearchView;

import com.example.giveandtake.databinding.FragmentUserListBinding;
import com.example.giveandtake.model.User;
import com.example.giveandtake.model.UserModel;

import java.util.List;
import java.util.function.Predicate;

public class UserListFragment extends Fragment {
    FragmentUserListBinding binding;
    UserRecyclerAdapter adapter;

    UserListFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new UserRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new UserRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                User user = viewModel.getData().getValue().get(pos);
            }
        });

        binding.progressBar.setVisibility(View.GONE);

        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            if(list.size() != 0)
                adapter.setData(list);
        });

        UserModel.instance().EventUserListLoadingState.observe(getViewLifecycleOwner(), status->{
            binding.swipeRefresh.setRefreshing(status == UserModel.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(()->{
            reloadUserData();
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserListFragmentViewModel.class);
    }

    void reloadUserData(){
        UserModel.instance().refreshAllUsers();
    }
}

package com.example.giveandtake.fragments.home;

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

import com.example.giveandtake.model.PostModel;
import com.example.giveandtake.model.Post;

public class HomeListFragment extends Fragment {
    FragmentHomeListBinding binding;
   HomeRecyclerAdapter adapter;

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
                //TODO: add other directions to another fragment
//                HomeListFragmentDirections.ActionStudentsListFragmentToBlueFragment action = HomeListFragmentDirections.actionStudentsListFragmentToBlueFragment(st.name);
//                Navigation.findNavController(view).navigate(action);
            }
        });
       // adapter = new HomeRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
//        binding.recyclerView.setAdapter(adapter);
//

//        adapter.setOnItemClickListener(new HomeRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int pos) {
//                Log.d("TAG", "Row was clicked " + pos);
//                Post post = viewModel.getData().getValue().get(pos);
//                //TODO: add other directions to another fragment
////                HomeListFragmentDirections.ActionStudentsListFragmentToBlueFragment action = HomeListFragmentDirections.actionStudentsListFragmentToBlueFragment(st.name);
////                Navigation.findNavController(view).navigate(action);
//            }
//        });

        binding.progressBar.setVisibility(View.GONE);


        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            if(list.size() != 0)
                adapter.setData(list);
        });

        PostModel.instance().EventPostListLoadingState.observe(getViewLifecycleOwner(), status->{
            binding.swipeRefresh.setRefreshing(status == PostModel.LoadingState.LOADING);
        });

       binding.swipeRefresh.setOnRefreshListener(()->{
           reloadData();
       });

//        LiveData<List<Movie>> data = MovieModel.instance.searchMoviesByTitle("avatar");
//        data.observe(getViewLifecycleOwner(),list->{
//            list.forEach(item->{
//                Log.d("TAG","got movie: " + item.getTitle() + " " + item.getPoster());
//            });
//        });

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
}
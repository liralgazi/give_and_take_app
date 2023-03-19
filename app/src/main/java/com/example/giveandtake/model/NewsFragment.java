package com.example.giveandtake.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.giveandtake.R;
import com.example.giveandtake.adapter.NewsAdapter;

import java.util.ArrayList;

import javax.annotation.Nullable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends Fragment {

    String api= "dd16b736845c4011a2b69b986bbc1a32";
    ArrayList<ModelNews> modelNewsArrayList;
    NewsAdapter newsAdapter;
    String country = "us";
    String category = "health";
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news, null);
        recyclerView = v.findViewById(R.id.recyclerViewNews);

        modelNewsArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        newsAdapter = new NewsAdapter(getContext(), modelNewsArrayList);
        recyclerView.setAdapter(newsAdapter);

        findNews();
        return v;
    }

    private void findNews()
    {
        ApiUtilities.getNewsApiInterface().getCategoryNews(country, category,10, api ).enqueue(new Callback<mainNews>() {
            @Override
            public void onResponse(Call<mainNews> call, Response<mainNews> response) {
                if (response.isSuccessful())
                {
                    modelNewsArrayList.addAll(response.body().getArticles());
                    newsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<mainNews> call, Throwable t) {

            }
        });

    }

}

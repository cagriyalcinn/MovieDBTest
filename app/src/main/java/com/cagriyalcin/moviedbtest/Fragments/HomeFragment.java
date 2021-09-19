package com.cagriyalcin.moviedbtest.Fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.cagriyalcin.moviedbtest.Activities.MainActivity;
import com.cagriyalcin.moviedbtest.Adapters.UpComingMoviesAdapter;
import com.cagriyalcin.moviedbtest.Adapters.ViewPagerAdapter;
import com.cagriyalcin.moviedbtest.DataModels.NowPlayingModel;
import com.cagriyalcin.moviedbtest.DataModels.Result;
import com.cagriyalcin.moviedbtest.DataModels.UpComingMovieModel;
import com.cagriyalcin.moviedbtest.R;
import com.cagriyalcin.moviedbtest.Retrofit.RetrofitClient;
import com.cagriyalcin.moviedbtest.Retrofit.ServiceCalls;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ViewPager viewPager;
    ScrollingPagerIndicator indicator;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rvMovies;

    UpComingMoviesAdapter upComingMoviesAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = view.findViewById(R.id.viewPager);
        indicator = view.findViewById(R.id.indicator);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        rvMovies = view.findViewById(R.id.rvMovies);

        swipeRefreshLayout.setOnRefreshListener(this);

        fetchDatas();

        return view;
    }

    private void fetchDatas() {
        fetchNowPlayingMovies();
        fetchUpComingMovies();
    }

    private void fetchNowPlayingMovies() {
        ServiceCalls serviceCalls = RetrofitClient.getAPIService();
        Map<String,String> hmap = new HashMap<>();
        hmap.put("language", MainActivity.appLanguage);
        hmap.put("api_key", MainActivity.apiKey);
        serviceCalls.getNowPlayingMovies(hmap).enqueue(new Callback<NowPlayingModel>() {
            @Override
            public void onResponse(Call<NowPlayingModel> call, Response<NowPlayingModel> response) {
                if(response.body() != null) {
                    ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity(), response.body().getResults());
                    viewPager.setAdapter(viewPagerAdapter);
                    indicator.attachToPager(viewPager);

                    viewPagerAdapter.setOnItemClickListener(new ViewPagerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int movie_id) {
                            goToDetails(movie_id);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<NowPlayingModel> call, Throwable t) {

            }
        });
    }

    private void fetchUpComingMovies() {
        ServiceCalls serviceCalls = RetrofitClient.getAPIService();
        Map<String,String> hmap = new HashMap<>();
        hmap.put("language", MainActivity.appLanguage);
        hmap.put("api_key", MainActivity.apiKey);
        serviceCalls.getUpComingMovies(hmap).enqueue(new Callback<UpComingMovieModel>() {
            @Override
            public void onResponse(Call<UpComingMovieModel> call, Response<UpComingMovieModel> response) {
                if(response.body() != null) {
                    upComingMoviesAdapter = new UpComingMoviesAdapter(getActivity(), response.body().getResults());
                    rvMovies.setHasFixedSize(true);
                    rvMovies.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
                    rvMovies.setAdapter(upComingMoviesAdapter);

                    upComingMoviesAdapter.UpComingMoviesItemSelectListener(new UpComingMoviesAdapter.UpComingMoviesItemSelectListener() {
                        @Override
                        public void onItemClick(Result result) {
                            goToDetails(result.getId());
                        }
                    });

                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<UpComingMovieModel> call, Throwable t) {

            }
        });
    }

    private void goToDetails(int movie_id) {
        Fragment homeFragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt("id", movie_id);
        homeFragment.setArguments(args);
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment, homeFragment).addToBackStack("detail");
        fragmentTransaction.commit();
    }

    @Override
    public void onRefresh() {
        fetchDatas();
    }
}
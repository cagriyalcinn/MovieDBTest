package com.cagriyalcin.moviedbtest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.cagriyalcin.moviedbtest.DataModels.Result;
import com.cagriyalcin.moviedbtest.R;
import java.util.List;

public class UpComingMoviesAdapter extends RecyclerView.Adapter<UpComingMoviesAdapter.ViewHolder> {
    private List<Result> resultList;
    private Context context;

    public interface UpComingMoviesItemSelectListener {
        void onItemClick(Result result);
    }

    public void UpComingMoviesItemSelectListener(UpComingMoviesItemSelectListener listener) {
        this.listener = listener;
    }

    public UpComingMoviesItemSelectListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMovieTitle, tvMovieDesc, tvMovieDate;
        public ImageView imgMovie;
        public LinearLayout llMovie;

        public ViewHolder(View view) {
            super(view);
            tvMovieTitle = view.findViewById(R.id.tvMovieTitle);
            tvMovieDesc = view.findViewById(R.id.tvMovieDesc);
            tvMovieDate = view.findViewById(R.id.tvMovieDate);
            llMovie = view.findViewById(R.id.llMovie);
            imgMovie = view.findViewById(R.id.imgMovie);
        }
    }

    public UpComingMoviesAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    @Override
    public UpComingMoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_upcoming, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvMovieTitle.setText(resultList.get(position).getTitle());
        holder.tvMovieDesc.setText(resultList.get(position).getOverview());
        holder.tvMovieDate.setText(resultList.get(position).getReleaseDate());

        Glide.with(context).load("https://image.tmdb.org/t/p/original/" + resultList.get(position).getPosterPath()).into(holder.imgMovie);

        holder.llMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(resultList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }
}

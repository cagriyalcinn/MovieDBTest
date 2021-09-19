package com.cagriyalcin.moviedbtest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.cagriyalcin.moviedbtest.DataModels.Result;
import com.cagriyalcin.moviedbtest.R;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<Result> results;

    public ViewPagerAdapter(Context context, List<Result> results) {
        this.context = context;
        this.results = results;
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int movie_id);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.row_now_playing, null);

        ImageView imgMovie = (ImageView) view.findViewById(R.id.imgMovie);
        TextView tvMovieTitle = (TextView) view.findViewById(R.id.tvMovieTitle);
        TextView tvMovieDesc = (TextView) view.findViewById(R.id.tvMovieDesc);

        Glide.with(context).load("https://image.tmdb.org/t/p/original/" + results.get(position).getPosterPath()).into(imgMovie);
        tvMovieTitle.setText(results.get(position).getOriginalTitle());
        tvMovieDesc.setText(results.get(position).getOverview());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(results.get(position).getId());
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}

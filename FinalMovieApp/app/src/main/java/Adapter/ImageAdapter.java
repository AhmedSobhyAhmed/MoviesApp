package Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.ahmed.finalmovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Bean.Movie;

public class ImageAdapter extends BaseAdapter {


    Context mContext;
    LayoutInflater mInflater;
    List<Movie> mObjects = new ArrayList<>();

    public ImageAdapter(Context context, List<Movie> objects) {
        mContext = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mObjects = objects;
    }

    @Override
    public int getCount() {
        return mObjects.size();
    }

    @Override
    public Object getItem(int position) {

        return mObjects.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = mInflater.inflate(R.layout.movie_view_grid, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        Movie movie = (Movie) getItem(position);

        String image_url = "http://image.tmdb.org/t/p/w185" + movie.getPoster();

        viewHolder = (ViewHolder) view.getTag();

        Picasso.with(mContext).load(image_url).into(viewHolder.imageView);

        return view;
    }

    public static class ViewHolder {
        public final ImageView imageView;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.grid_item_image);
        }
    }

}


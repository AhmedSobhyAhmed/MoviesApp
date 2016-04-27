package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ahmed.finalmovieapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Bean.Trailer;

/**
 * Created by AhMed on 4/25/2016.
 */
public class TrailerAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    List<Trailer> mObjects = new ArrayList<>();

    public TrailerAdapter(Context context, List<Trailer> objects) {
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
            view = mInflater.inflate(R.layout.trailer_view_grid, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        Trailer trailer = (Trailer) getItem(position);

        String yt_thumbnail_url = "http://img.youtube.com/vi/" + trailer.getKey() + "/0.jpg";

        viewHolder = (ViewHolder) view.getTag();

        Picasso.with(mContext).load(yt_thumbnail_url).into(viewHolder.imageView);
        viewHolder.trailername.setText("Name :"+trailer.getName());
        viewHolder.trailertype.setText("Type :"+trailer.getType());

        return view;
    }

    public static class ViewHolder {
        public final ImageView imageView;
        public final TextView trailername;
        public final TextView trailertype;

        public ViewHolder(View view) {
            imageView = (ImageView) view.findViewById(R.id.trailer_item_image);
            trailername = (TextView) view.findViewById(R.id.name);
            trailertype = (TextView) view.findViewById(R.id.type);

        }
    }

}

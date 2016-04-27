package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ahmed.finalmovieapp.R;

import java.util.ArrayList;
import java.util.List;

import Bean.Review;

/**
 * Created by AhMed on 4/25/2016.
 */
public class ReviewAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    List<Review> mObjects = new ArrayList<>();

    public ReviewAdapter(Context context, List<Review> objects) {
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
            view = mInflater.inflate(R.layout.review_view_grid, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }

        Review review = (Review) getItem(position);

        viewHolder = (ViewHolder) view.getTag();

        viewHolder.author.setText(review.getAuthor());
        viewHolder.content.setText(review.getContent());
        return view;
    }

    public static class ViewHolder {
        public final TextView author;
        public final TextView content;

        public ViewHolder(View view) {
            author = (TextView) view.findViewById(R.id.author);
            content = (TextView) view.findViewById(R.id.content);
        }
    }

}

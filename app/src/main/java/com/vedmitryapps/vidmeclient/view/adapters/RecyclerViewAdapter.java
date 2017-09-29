package com.vedmitryapps.vidmeclient.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vedmitryapps.vidmeclient.R;
import com.vedmitryapps.vidmeclient.model.objects.Video;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private DisplayMetrics displayMetrics;
    private List<Video> videos;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Video> videos) {
        this.context = context;
        this.videos = videos;
        displayMetrics = context.getResources().getDisplayMetrics();
        Log.i("TAG22", "RecyclerViewAdapter constr... " + videos.size());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        Log.i("TAG22", "onCreateViewHolder ");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.i("TAG22", "onBind " + position);
        holder.title.setText(videos.get(position).getTitle());
        holder.likesCount.setText(String.valueOf(videos.get(position).getLikesCount()));
        double w = videos.get(position).getWidth();
        double h = videos.get(position).getHeight();
        double k = w/h;

        Picasso.with(context)
                .load(videos.get(position).getThumbnailUrl())
                .resize(displayMetrics.widthPixels, (int) (displayMetrics.widthPixels/k))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.likesQuantity)
        TextView likesCount;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.i("TAG22", "ViewHolder constr .... ");
            ButterKnife.bind(this, itemView);
        }
    }

    public void update(List<Video> videos){
        this.videos = videos;
        notifyDataSetChanged();
    }
}

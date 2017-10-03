package com.vedmitryapps.vidmeclient.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        double w = videos.get(position).getWidth();
        double h = videos.get(position).getHeight();
        double k = w/h;

        Glide
                .with(context)
                .load(videos.get(position).getThumbnailUrl())
                .placeholder(R.mipmap.ic_launcher)
                .override(displayMetrics.widthPixels, (int) (displayMetrics.widthPixels/k))
                .into(holder.imageView);

        holder.title.setText(videos.get(position).getTitle());
        //holder.title.setText(String.valueOf(position));
        holder.likesCount.setText(String.valueOf(videos.get(position).getLikesCount()));
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
            ButterKnife.bind(this, itemView);
        }
    }

    public void update(List<Video> videos){
        this.videos = videos;
        notifyDataSetChanged();
    }
}

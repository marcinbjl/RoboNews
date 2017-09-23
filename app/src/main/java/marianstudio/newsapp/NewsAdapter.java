package marianstudio.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends
        RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private static List<News> mNews;

    public NewsAdapter(List<News> news) {
        mNews = news;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View newsView = inflater.inflate(R.layout.list_item, parent, false);

        return new MyViewHolder(newsView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        News news = mNews.get(position);

        viewHolder.headlineTextView.setText(news.getHeadLine());
        viewHolder.sectionTextView.setText(news.getSection());
        viewHolder.dateTextView.setText(news.getElapsedTime());

        if (news.getThumbnail() == null) {
            viewHolder.thumbnailImageView.setImageBitmap(MainActivity.noThumbnail);
            viewHolder.thumbnailImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        } else {
            viewHolder.thumbnailImageView.setImageBitmap(news.getThumbnail());
            viewHolder.thumbnailImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    public void clear() {
        mNews.clear();
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView headlineTextView;
        public TextView sectionTextView;
        public TextView dateTextView;
        public ImageView thumbnailImageView;

        public MyViewHolder(final View itemView) {
            super(itemView);
            headlineTextView = (TextView) itemView.findViewById(R.id.headline);
            sectionTextView = (TextView) itemView.findViewById(R.id.section);
            dateTextView = (TextView) itemView.findViewById(R.id.date);
            thumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    News currentNews = mNews.get(pos);

                    Uri newsUri = Uri.parse(currentNews.getUrl());

                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                    itemView.getContext().startActivity(websiteIntent);
                }
            });
        }
    }
}
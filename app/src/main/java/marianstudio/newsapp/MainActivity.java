package marianstudio.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?order-by=newest&show-fields=thumbnail&page-size=15&q=robotics&api-key=test";
    private static final int NEWS_LOADER_ID = 1;
    public static Bitmap noThumbnail;
    public RecyclerView mNewsRvList;
    ArrayList<News> mNews;
    private NewsAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private SwipeRefreshLayout mSwipeContainer;
    private View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingIndicator = findViewById(R.id.loading_indicator);
        mNews = new ArrayList<News>();
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mNewsRvList = (RecyclerView) findViewById(R.id.rv_news);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        mSwipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                restartLoader();
            }
        });

        noThumbnail = BitmapFactory.decodeResource(getResources(), R.drawable.no_thumbnail);

        mNewsRvList = (RecyclerView) findViewById(R.id.rv_news);

        mAdapter = new NewsAdapter(mNews);
        mNewsRvList.setAdapter(mAdapter);

        mNewsRvList.setLayoutManager(new LinearLayoutManager(this));

        if (isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        return new NewsLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mNews.clear();
        mAdapter.notifyDataSetChanged();

        if (news != null && !news.isEmpty()) {
            mNews.addAll(news);
            mNewsRvList.setVisibility(View.VISIBLE);
        }
        loadingIndicator.setVisibility(View.GONE);
        mSwipeContainer.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mNews.clear();
        mAdapter.notifyDataSetChanged();
    }

    public void restartLoader() {

        if (isConnected()) {
            mAdapter.clear();
            mEmptyStateTextView.setVisibility(View.GONE);
            getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
        } else {
            mNewsRvList.setVisibility(View.GONE);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            mSwipeContainer.setRefreshing(false);
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}

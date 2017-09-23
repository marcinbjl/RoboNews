package marianstudio.newsapp;

import android.graphics.Bitmap;

public class News {

    private String mHeadLine;
    private String mSection;
    private String mElapsedTime;
    private Bitmap mThumbnail;
    private String mUrl;

    public News(String headLine, String section, String elapsedTime, Bitmap thumbnail, String url) {
        mHeadLine = headLine;
        mSection = section;
        mElapsedTime = elapsedTime;
        mThumbnail = thumbnail;
        mUrl = url;
    }


    public String getHeadLine() {
        return mHeadLine;
    }

    public String getSection() {
        return mSection;
    }

    public String getElapsedTime() {
        return mElapsedTime;
    }

    public Bitmap getThumbnail() {
        return mThumbnail;
    }

    public String getUrl() {
        return mUrl;
    }
}

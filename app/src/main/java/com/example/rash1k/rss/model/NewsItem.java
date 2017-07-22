package com.example.rash1k.rss.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsItem implements Parcelable{

    private  String title;
    private  String authorId;
    private  String authorName;
    private  String datePublished;
    private  String urlPhotos;


    public NewsItem() {
    }

    public NewsItem(String title, String urlPhotos, String datePublished, String authorName, String authorId) {
        this.title = title;
        this.urlPhotos = urlPhotos;
        this.datePublished = datePublished;
        this.authorName = authorName;
        this.authorId = authorId;
    }

    protected NewsItem(Parcel in) {
        title = in.readString();
        urlPhotos = in.readString();
        datePublished = in.readString();
        authorName = in.readString();
        authorId = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getUrlPhotos() {
        return urlPhotos;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrlPhotos(String urlPhotos) {
        this.urlPhotos = urlPhotos;
    }

    public void setDatePublished(String datePublished) {
        this.datePublished = datePublished;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    @Override
    public String toString() {
        return title;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(urlPhotos);
        dest.writeString(datePublished);
        dest.writeString(authorName);
        dest.writeString(authorId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<NewsItem> CREATOR = new Parcelable.Creator<NewsItem>() {
        @Override
        public NewsItem createFromParcel(Parcel in) {
            return new NewsItem(in);
        }

        @Override
        public NewsItem[] newArray(int size) {
            return new NewsItem[size];
        }
    };
}

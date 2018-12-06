package com.hi.boardify;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageModel implements Parcelable{

    String name, url,time,prof,id;
    public ImageModel(String url, String name){
        this.name = name;
        this.url = url;
        this.time = time;
        this.prof = prof;
        this.id = id;
    }
    public ImageModel() {
    }
    // Getters & Setters here
    protected ImageModel(Parcel in) {
        name = in.readString();
        url = in.readString();
        time = in.readString();
        prof = in.readString();
        id = in.readString();
    }

    public static final Creator<ImageModel> CREATOR = new Creator<ImageModel>() {
        @Override
        public ImageModel createFromParcel(Parcel in) {
            return new ImageModel(in);
        }

        @Override
        public ImageModel[] newArray(int size) {
            return new ImageModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(time);
        dest.writeString(prof);
        dest.writeString(id);
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getTime(){return time;}

    public String getProf(){return prof;}

    public String getId(){return id;}



    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTime(String time){this.time = time;}
    public void setProf(String prof){this.prof=prof;}

    public void setId(String id){this.id=id;}
}
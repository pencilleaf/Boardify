package com.hi.boardify;

import android.content.Context;
import android.media.Image;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DataHolder {
    private static DataHolder data_holder = null;
    private ArrayList<ImageModel> storeddata = new ArrayList<>();

    private DataHolder(){

    }
    public static DataHolder getInstance() {
        if (data_holder == null) {
            data_holder = new DataHolder();
            return data_holder;
        } else {
            return data_holder;
        }
    }

    public ArrayList<ImageModel> getAllData(){
        return this.storeddata;
    }

    public ImageModel getData(int i){
        return storeddata.get(i);
    }

    public void addData(ImageModel m){
        storeddata.add(m);
    }
    public static <E> void SaveArrayListToSD(Context mContext, String filename, ArrayList<E> list){
        try {

            FileOutputStream fos = mContext.openFileOutput(filename + ".dat", mContext.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

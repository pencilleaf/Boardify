package com.hi.boardify;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class GalleryPagerAdapter extends FragmentPagerAdapter {
    public ArrayList<ImageModel> images;

    public GalleryPagerAdapter(FragmentManager fm, ArrayList<ImageModel> data) {
        super(fm);
        this.images = data;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        ImageModel image = images.get(position);
        return ImageDetailFragment.newInstance(image);
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
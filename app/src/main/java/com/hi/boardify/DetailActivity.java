package com.hi.boardify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    private GalleryPagerAdapter mSectionsPagerAdapter;
    public ArrayList<ImageModel> data = new ArrayList<>();
    int pos;
    Toolbar toolbar;
    private ViewPager mViewPager;
    DataHolder dataHolder;
    DatabaseReference mRootDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dataHolder = DataHolder.getInstance();
        setContentView(R.layout.activity_detail);
        databaseReference= mRootDatabaseRef.child(dataHolder.getUserID());

        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        data = getIntent().getParcelableArrayListExtra("data");
        pos = getIntent().getIntExtra("pos",0);
        setTitle(data.get(pos).getName());


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new GalleryPagerAdapter(getSupportFragmentManager(), data);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setPageTransformer(true, new DepthPageTransformer());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(pos);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                setTitle(data.get(i).getName());
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (getIntent().getStringExtra("Uniqid").equals("Boards")){
            menu.findItem(R.id.action_delete).setVisible(false);
            menu.findItem(R.id.action_download).setVisible(true);
        }else if (getIntent().getStringExtra("Uniqid").equals("DownloadBoards")){
            menu.findItem(R.id.action_download).setVisible(false);
            menu.findItem(R.id.action_delete).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_download){
            databaseReference.child(data.get(pos).getName()).setValue(data.get(pos));
            Toast.makeText(this,"Whiteboard downloaded",Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_delete){
            databaseReference.child(data.get(pos).getName()).removeValue();
            Intent intent = new Intent(DetailActivity.this, DownloadBoards.class);
            startActivity(intent);
            Toast.makeText(this,"Whiteboard deleted",Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}


package com.hi.boardify;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;

import java.util.ArrayList;

public class DownloadBoards extends AppCompatActivity {
    ArrayList<ImageModel> data;
    JSONArray request;
    GalleryAdapter mAdapter;
    DatabaseReference mRootDatabaseRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        databaseReference= mRootDatabaseRef.child("users");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        data = new ArrayList<>();
        final RecyclerView mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setHasFixedSize(true);

        databaseReference.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    //listen for changes, the entire set of data is passed to data snapshot
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        data.clear();
                        for(DataSnapshot datasnapshot: dataSnapshot.getChildren()){
                            ImageModel imageModel = datasnapshot.getValue(ImageModel.class);
                            data.add(imageModel);
                            Log.i("THE DATA FETCHED IS", imageModel.getUrl());
                        }
                        mAdapter = new GalleryAdapter(DownloadBoards.this, data);
                        mRecyclerView.setAdapter(mAdapter);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                }
        );
       // data = dataHolder.getAllData();




        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(DownloadBoards.this, DetailActivity.class);
                        intent.putParcelableArrayListExtra("data", data);
                        intent.putExtra("pos", position);
                        startActivity(intent);

                    }
                }));
    }
}

package com.hi.boardify;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Boards extends AppCompatActivity {

    String server_url = "http://boardify.ml";
    ArrayList<ImageModel> data = new ArrayList<>();
    JSONArray request;
    GalleryAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageModel test = new ImageModel();
        test.setUrl("https://storage.googleapis.com/boardify-whiteboards/7455604.jpg");
        test.setName("test");
        data.add(test);
        getJson();

        RecyclerView mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new GalleryAdapter(Boards.this, data);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(Boards.this, DetailActivity.class);
                        intent.putParcelableArrayListExtra("data", data);
                        intent.putExtra("pos", position);
                        intent.putExtra("Uniqid","Boards");
                        startActivity(intent);

                    }
                }));
    }

    private void getJson(){
        final RequestQueue requestQueue  = Volley.newRequestQueue(Boards.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, server_url, null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            request = response.getJSONArray("boards");

                            for (int i = 0; i < request.length(); i++) {
                                try {
                                    JSONObject jsonObject = request.getJSONObject(i);
                                    ImageModel imageModel = new ImageModel();
                                    imageModel.setName(jsonObject.getString("title"));
                                    imageModel.setUrl(jsonObject.getString("url").replace("\\",""));
                                    data.add(imageModel);
                                    Log.i("LOGCAT", imageModel.getUrl());
                                } catch (JSONException ex) {
                                    ex.printStackTrace();
                                    Log.i("LOGCAT", "Something went wrong");
                                }
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Log.i("LOGCAT", "Something went wrong");
                        }
                        mAdapter.notifyDataSetChanged();
                        requestQueue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
                requestQueue.stop();
            }

        });
        requestQueue.add(jsonObjectRequest);
    }




}

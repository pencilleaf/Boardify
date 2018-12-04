package com.hi.boardify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class ImagesResult extends AppCompatActivity {
    ArrayList<ImageModel> data = new ArrayList<>();
    GalleryAdapter mAdapter;
    String server_url = "http://boardify.ml/search";
    String query;
    JSONArray request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_result);

        query = getIntent().getStringExtra("query");
        query = query.replaceAll(" ", "+");
        ImageModel test = new ImageModel();
        test.setUrl("https://storage.googleapis.com/boardify-whiteboards/7455604.jpg");
        test.setName("test");
        data.add(test);
        getJson();

        RecyclerView mRecyclerView = findViewById(R.id.imageList);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new GalleryAdapter(ImagesResult.this, data);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(ImagesResult.this, DetailActivity.class);
                        intent.putParcelableArrayListExtra("data", data);
                        intent.putExtra("pos", position);
                        intent.putExtra("Uniqid","Boards");
                        startActivity(intent);

                    }
                }));
    }

    private void getJson() {
        Log.i("LOGCAT", server_url + "/" + query);

        final RequestQueue queue  = Volley.newRequestQueue(ImagesResult.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, server_url + "/" + query, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("LOGCAT", response.toString());
                        try {
                            request = response.getJSONArray("boards");
                            Log.i("LOGCAT", request.toString());
                            for (int i = 0; i < request.length(); i++) {
                                try {
                                    JSONObject jsonObject = request.getJSONObject(i);
                                    ImageModel imageModel = new ImageModel();
                                    imageModel.setName("test");
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
                        queue.stop();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error){
                error.printStackTrace();
                queue.stop();
            }

        });

        queue.add(jsonObjectRequest);
    }
}

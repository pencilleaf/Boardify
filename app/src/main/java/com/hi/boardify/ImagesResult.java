package com.hi.boardify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ImagesResult extends AppCompatActivity {
    ArrayList<ImageModel> data = new ArrayList<>();
    GalleryAdapter mAdapter;
    String server_url ;
    String query;
    JSONArray request;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_result);

        // add an if statement here to check the previous intent.
        server_url = getIntent().getStringExtra("server");
        query = getIntent().getStringExtra("query");
        query = query.replaceAll(" ", "+");
        getJson();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Search Results");
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
                                    imageModel.setName(jsonObject.getString("title"));
                                    imageModel.setUrl(jsonObject.getString("url").replace("\\",""));
                                    imageModel.setProf(jsonObject.getString("professor"));
                                    imageModel.setTime(jsonObject.getString("time_photo_taken"));
                                    imageModel.setId(jsonObject.getJSONObject("_id").getString("$oid"));
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
                     //   mRecyclerView.setAdapter(mAdapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(ImagesResult.this, ImagesResult.class);
                intent.putExtra("server", server_url);
                intent.putExtra("query" , query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

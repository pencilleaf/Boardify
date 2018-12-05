package com.hi.boardify;
import android.app.SearchManager;
import android.support.v7.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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


public class Boards extends AppCompatActivity{

    String server_url;
    ArrayList<ImageModel> data = new ArrayList<>();
    JSONArray request;
    GalleryAdapter mAdapter;
    SearchView searchView;
    String searchurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (getIntent().getStringExtra("prev").equals("infosys")){
            server_url = "http://boardify.ml/module/1";
            searchurl = "http://boardify.ml/search/1";
            getSupportActionBar().setTitle(getString(R.string.infosys));
        }else if(getIntent().getStringExtra("prev").equals("compstruct")){
            server_url = "http://boardify.ml/module/2";
            searchurl = "http://boardify.ml/search/2";
            getSupportActionBar().setTitle(getString(R.string.compstruct));
        }else if(getIntent().getStringExtra("prev").equals("algo")){
            server_url = "http://boardify.ml/module/3";
            searchurl = "http://boardify.ml/search/3";
            getSupportActionBar().setTitle(getString(R.string.algo));
        }else if(getIntent().getStringExtra("prev").equals("physics")){
            server_url = "http://boardify.ml/module/4";
            searchurl = "http://boardify.ml/search/4";
            getSupportActionBar().setTitle(getString(R.string.physics));
        }else if(getIntent().getStringExtra("prev").equals("math")){
            server_url = "http://boardify.ml/module/5";
            searchurl = "http://boardify.ml/search/5";
            getSupportActionBar().setTitle(getString(R.string.math));
        }else if(getIntent().getStringExtra("prev").equals("chemistry")){
            server_url = "http://boardify.ml/module/6";
            searchurl = "http://boardify.ml/search/6";
            getSupportActionBar().setTitle(getString(R.string.chem));
        }else if(getIntent().getStringExtra("prev").equals("biology")){
            server_url = "http://boardify.ml/module/7";
            searchurl = "http://boardify.ml/search/7";
            getSupportActionBar().setTitle(getString(R.string.bio));
        }else if(getIntent().getStringExtra("prev").equals("hass")){
            server_url = "http://boardify.ml/module/8";
            searchurl = "http://boardify.ml/search/8";
            getSupportActionBar().setTitle(getString(R.string.hass));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getJson();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();

        RecyclerView mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
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
                                    imageModel.setProf(jsonObject.getString("professor"));
                                    imageModel.setTime(jsonObject.getString("time_photo_taken"));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(Boards.this, ImagesResult.class);
                intent.putExtra("server", searchurl);
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

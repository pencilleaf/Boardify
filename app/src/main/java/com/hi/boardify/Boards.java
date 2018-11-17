package com.hi.boardify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class Boards extends AppCompatActivity {

    ArrayList<ImageModel> data = new ArrayList<>();
    public static String IMGS[] = {"https://cdn.vox-cdn.com/thumbor/15ya4Dy0W11mQ6EQWksZw6hN0qs=/0x0:800x450/920x613/filters:focal(239x138:367x266):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/62348289/800px-Serena_Eevee.0.0.png", "https://vignette.wikia.nocookie.net/deltarune/images/0/03/Susie_face.png/revision/latest?cb=20181104052309",
            "https://vignette.wikia.nocookie.net/deltarune/images/7/7c/Ralsei_face.png/revision/latest?cb=20181102004128",
            "https://vignette.wikia.nocookie.net/undertale/images/6/67/Dogamy.png/revision/latest?cb=20160227205510&path-prefix=ru",
            "https://m.gjcdn.net/screenshot-thumbnail/300x300/419140-v3.jpg","https://vignette.wikia.nocookie.net/deltarune/images/0/03/Susie_face.png/revision/latest?cb=20181104052309",
            "https://vignette.wikia.nocookie.net/deltarune/images/7/7c/Ralsei_face.png/revision/latest?cb=20181102004128",
            "https://vignette.wikia.nocookie.net/undertale/images/6/67/Dogamy.png/revision/latest?cb=20160227205510&path-prefix=ru",
            "https://m.gjcdn.net/screenshot-thumbnail/300x300/419140-v3.jpg","https://vignette.wikia.nocookie.net/deltarune/images/0/03/Susie_face.png/revision/latest?cb=20181104052309",
            "https://vignette.wikia.nocookie.net/deltarune/images/7/7c/Ralsei_face.png/revision/latest?cb=20181102004128",
            "https://vignette.wikia.nocookie.net/undertale/images/6/67/Dogamy.png/revision/latest?cb=20160227205510&path-prefix=ru",
            "https://m.gjcdn.net/screenshot-thumbnail/300x300/419140-v3.jpg","https://vignette.wikia.nocookie.net/deltarune/images/0/03/Susie_face.png/revision/latest?cb=20181104052309",
            "https://vignette.wikia.nocookie.net/deltarune/images/7/7c/Ralsei_face.png/revision/latest?cb=20181102004128",
            "https://vignette.wikia.nocookie.net/undertale/images/6/67/Dogamy.png/revision/latest?cb=20160227205510&path-prefix=ru",
            "https://m.gjcdn.net/screenshot-thumbnail/300x300/419140-v3.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        for (int i = 0; i < IMGS.length; i++) {
            //	Adding images & title to POJO class and storing in Array (our data)
            ImageModel imageModel = new ImageModel();
            imageModel.setName("Image " + i);
            imageModel.setUrl(IMGS[i]);
            data.add(imageModel);
        }
        RecyclerView mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,3));
        mRecyclerView.setHasFixedSize(true);
        GalleryAdapter mAdapter = new GalleryAdapter(Boards.this, data);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(Boards.this, DetailActivity.class);
                        intent.putParcelableArrayListExtra("data", data);
                        intent.putExtra("pos", position);
                        startActivity(intent);

                    }
                }));
    }

}

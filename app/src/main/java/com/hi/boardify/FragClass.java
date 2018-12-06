package com.hi.boardify;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import java.util.List;


public class FragClass extends Fragment {
    //String server_url = "http://boardify.ml";
    //String json;

    public FragClass() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frag_class, container, false);

        //pass extras in intent to determine which mod is being clicked
        CardView cardView1 = view.findViewById(R.id.card_view1);
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Boards.class);
                intent.putExtra("prev", "infosys");
                startActivity(intent);
            }
        });

        CardView cardView2 = view.findViewById(R.id.card_view2);
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Boards.class);
                intent.putExtra("prev", "compstruct");
                startActivity(intent);
            }
        });

        CardView cardView3 = view.findViewById(R.id.card_view3);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Boards.class);
                intent.putExtra("prev", "algo");
                startActivity(intent);
            }
        });
        CardView cardView4 = view.findViewById(R.id.card_view4);
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Boards.class);
                intent.putExtra("prev", "physics");
                startActivity(intent);
            }
        });
        CardView cardView5 = view.findViewById(R.id.card_view5);
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Boards.class);
                intent.putExtra("prev", "math");
                startActivity(intent);
            }
        });
        CardView cardView6 = view.findViewById(R.id.card_view6);
        cardView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Boards.class);
                intent.putExtra("prev", "chemistry");
                startActivity(intent);
            }
        });
        CardView cardView7 = view.findViewById(R.id.card_view7);
        cardView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Boards.class);
                intent.putExtra("prev", "biology");
                startActivity(intent);
            }
        });
        CardView cardView8 = view.findViewById(R.id.card_view8);
        cardView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Boards.class);
                intent.putExtra("prev", "hass");
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}

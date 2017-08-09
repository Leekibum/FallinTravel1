package com.probum.fallintravel;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

/**
 * Created by alfo6-25 on 2017-07-25.
 */

public class FestivalFragment extends Fragment {

    ArrayList<Item> items=new ArrayList<>();
    RecyclerView recyclerView;
    FestivalAdapter adapter;
    RequestQueue requestQueue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue= Volley.newRequestQueue(getActivity());

        items.add(new Item("title","noimage","time"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_festival,container,false);
        recyclerView=(RecyclerView)view.findViewById(R.id.layout_recycler);

        RecyclerView.LayoutManager manager=new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter=new FestivalAdapter(getActivity(),items);
        recyclerView.setAdapter(adapter);

        return view;
    }
}

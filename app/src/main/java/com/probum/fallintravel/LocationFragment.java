package com.probum.fallintravel;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

public class LocationFragment extends Fragment {

    ArrayList<Item> items=new ArrayList<>();
    RecyclerView recyclerView;
    LocationAdapter adapter;
    RequestQueue requestQueue;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue= Volley.newRequestQueue(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_location, container, false);

        recyclerView=(RecyclerView)view.findViewById(R.id.layout_recycler);

        RecyclerView.LayoutManager manager= new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);

        adapter=new LocationAdapter(getActivity(),items);
        recyclerView.setAdapter(adapter);

        readLocation();

        return view;
    }

    void readLocation(){
        String url="http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey="+ G.serviceKey+"&contentTypeId=&areaCode="+G.citycode+"&sigunguCode="+G.sigunguCode+"&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=B&numOfRows=12&pageNo=1&_type=json";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject obj=response.getJSONObject("response");
                    obj=obj.getJSONObject("body");
                    obj=obj.getJSONObject("items");
                    JSONArray jsonArray=obj.getJSONArray("item");

                    for (int i=0;i<jsonArray.length();i++){
                        obj=jsonArray.getJSONObject(i);

                        String title=obj.getString("title");
                        String firstimage="noimage";
                        if (obj.has("firstimage")) firstimage=obj.getString("firstimage");
                        items.add(new Item(title,firstimage));
                        adapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) { }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {}
        });
        requestQueue.add(jsonObjectRequest);
    }


}

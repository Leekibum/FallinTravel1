package com.probum.fallintravel;

import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

/**
 * Created by alfo6-25 on 2017-07-25.
 */

public class FestivalFragment extends Fragment {

    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Item> times=new ArrayList<>();
    RecyclerView recyclerView;
    FestivalAdapter adapter;
    RequestQueue requestQueue;
    int numOfRows;
    int pageNo;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());
        numOfRows = 12;
        pageNo = 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_festival, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.layout_recycler);

        RecyclerView.LayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        adapter = new FestivalAdapter(getActivity(), items,times);
        recyclerView.setAdapter(adapter);

        readfestival();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPostion = ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();
                int itemTotalCount = recyclerView.getAdapter().getItemCount() - 1;
                if (lastVisibleItemPostion == itemTotalCount) {
                    pageNo += 1;
                    readfestival();
                }
            }
        });

        return view;
    }

    void readfestival() {
        String url = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?ServiceKey=" + G.serviceKey + "&contentTypeId=15&areaCode=" + G.citycode + "&sigunguCode=" + G.sigunguCode + "&cat1=&cat2=&cat3=&listYN=Y&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&arrange=C&numOfRows=" + numOfRows + "&pageNo=" + pageNo + "&_type=json";

        Log.i("url Festival URl",url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject obj = response.getJSONObject("response");
                    obj = obj.getJSONObject("body");
                    obj = obj.getJSONObject("items");
                    JSONArray jsonArray = obj.getJSONArray("item");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        obj = jsonArray.getJSONObject(i);

                        String title = obj.getString("title");
                        String contentid = obj.getString("contentid");
                        readtime(contentid);
                        String contenttypeid=obj.getString("contenttypeid");
                        String firstimage = "noimage";
                        if (obj.has("firstimage")) firstimage = obj.getString("firstimage");

                        items.add(new Item(title, firstimage,contentid,contenttypeid));
                        adapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void setValue() {
        pageNo=1;
    }

    void readtime(final String contentid){
        String url="http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailIntro?ServiceKey="+G.serviceKey+"&contentTypeId=15&contentId="+contentid+"&MobileOS=ETC&MobileApp=TourAPI3.0_Guide&introYN=Y&_type=json";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object=response.getJSONObject("response");
                    object=object.getJSONObject("body");
                    object=object.getJSONObject("items");
                    object=object.getJSONObject("item");

                    String eventstartdate=object.getString("eventstartdate");
                    String eventenddate=object.getString("eventenddate");
                    times.add(new Item(eventstartdate+eventenddate,contentid));
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}


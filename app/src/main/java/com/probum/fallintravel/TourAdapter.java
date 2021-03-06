package com.probum.fallintravel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class TourAdapter extends RecyclerView.Adapter {

    ArrayList<Item> items;
    Context context;

    public TourAdapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder myholder = (ViewHolder) holder;



        myholder.tv_title.setText(items.get(position).title);
        if (items.get(position).time==null) myholder.tv_time.setVisibility(View.GONE);

        if (items.get(position).firstimage == null) {    //firstimage가 int 면 0
            myholder.img.setVisibility(View.INVISIBLE);//  나중에 이미지가 없어요 같은 이미지로 변경하기
        } else {
            Glide.with(context).load(items.get(position).firstimage).into(myholder.img);
        }





    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title, tv_time;
        ImageView img;

        public ViewHolder(final View itemView) {
            super(itemView);

            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_time=(TextView)itemView.findViewById(R.id.tv_time);
            img = (ImageView) itemView.findViewById(R.id.img);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "gg", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

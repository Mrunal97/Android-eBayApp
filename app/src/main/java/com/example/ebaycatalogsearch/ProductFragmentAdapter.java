package com.example.ebaycatalogsearch;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

public class ProductFragmentAdapter extends  RecyclerView.Adapter<ProductFragmentAdapter.ViewHolder> {

    private Context mContext;
    JSONArray pictureUrl;

    public ProductFragmentAdapter(Context applicationContext, JSONArray e_pictureURL) {
        this.mContext=applicationContext;
        this.pictureUrl=e_pictureURL;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_recycler,parent,false);
        //ViewHolder view=new ViewHolder();
        Log.d("pictureurl", String.valueOf(pictureUrl));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("pictureurl", String.valueOf(pictureUrl));
        Log.d("picturllen", String.valueOf(pictureUrl.length()));
        for(int i=0;i<pictureUrl.length();i++)
        {
            try {
                Glide.with(mContext).load( pictureUrl.get(position)).into(holder.scroll);
                Log.d("ith value", String.valueOf(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

//        for(int i=0;i<pictureUrl.length();i++) {
//
//        }

    }

    @Override
    public int getItemCount() {
        return pictureUrl.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView scroll;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scroll=itemView.findViewById(R.id.scrollable_image);

        }
    }
}

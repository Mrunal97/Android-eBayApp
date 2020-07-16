package com.example.ebaycatalogsearch;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.bumptech.glide.Glide;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    Context applicationContext;
     List<Ebay> ebay;
    RecyclerView recyclerView;
    LayoutInflater inflater;
    String dynamicUrl;

    public RecyclerViewAdapter(Context applicationContext, List<Ebay> ebay, String dynamicUrl) {
        this.applicationContext=applicationContext;
        this.inflater=LayoutInflater.from(applicationContext);
        this.ebay=ebay;
        this.dynamicUrl=dynamicUrl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= inflater.inflate(R.layout.layout2,parent,false);
        //recyclerView=view.findViewById(R.id.recycler_id);
        final ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        try{
        holder.ebayTitle.setText(ebay.get(position).getE_title());
        holder.ebayTitle. setText(ebay.get(position).getE_title().toUpperCase());
            holder.ebayCondition.setText(ebay.get(position).getE_condition());
            holder.ebayCurrentPrice.setText("$"+ebay.get(position).getE_price());

        }
        catch(Exception e){
            e.printStackTrace();
        }

        String start = "<b>";
        String end = "</b>";

        try{
            if((ebay.get(position).getE_shipPrice()).equals("0.0")){
                holder.ebayShippingPrice.setText(Html.fromHtml(start+"FREE "+end+ "Shipping", HtmlCompat.FROM_HTML_MODE_LEGACY));
            }
            else {
                holder.ebayShippingPrice.setText(Html.fromHtml("Ships for " +start+"$"+end+start+ebay.get(position).getE_shipPrice()+end,HtmlCompat.FROM_HTML_MODE_LEGACY));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }

        try {
            if (ebay.get(position).getE_topRated().equals("true")) {
                holder.topRated.setText("Top Rated Listing");
            }
        }
        catch(Exception e)
        {e.printStackTrace();}


try {
    if ((ebay.get(position).getE_image()).equals("https://thumbs1.ebaystatic.com/pict/04040_0.jpg")) {
        Glide.with(applicationContext).load(R.drawable.ebay_default).into(holder.ebayImage);
    } else {
        Glide.with(applicationContext).load(ebay.get(position).getE_image()).into(holder.ebayImage);
    }
}
catch (Exception e){
    e.printStackTrace();
}



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentDetailed = new Intent(applicationContext,DetailedActivity.class);
                intentDetailed.putExtra("productId",ebay.get(position).getE_productId());
                intentDetailed.putExtra("title",ebay.get(position).getE_title());
                intentDetailed.putExtra("currentPrice",ebay.get(position).getE_price());
                intentDetailed.putExtra("shippingServiceCost",ebay.get(position).getE_shipPrice());
                intentDetailed.putExtra("shippingInfo",ebay.get(position).getE_shippingInfo().toString());
       //         intentDetailed.putExtra("shippingInfo", ebay.get(position).getE_shippingInfo());
                intentDetailed.putExtra("viewItemURL", ebay.get(position).getE_itemUrl());

                applicationContext.startActivity(intentDetailed);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.d("ebaysize", String.valueOf(ebay.size()));
        return Math.min(50,ebay.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ScrollView scrollView;
        TextView ebayTitle, ebayCondition, ebayCurrentPrice, ebayShippingPrice, displayCount, topRated;
        ImageView ebayImage;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card_id);
            ebayTitle=itemView.findViewById(R.id.title_id);
            ebayShippingPrice=itemView.findViewById(R.id.shipPrice_id);
            ebayCurrentPrice=itemView.findViewById(R.id.price_id);
            ebayCondition=itemView.findViewById(R.id.condition_id);
            ebayImage=itemView.findViewById(R.id.image_id);
            scrollView=itemView.findViewById(R.id.scroll_id);
            topRated=itemView.findViewById(R.id.topRated_id);
            //displayCount=itemView.findViewById(R.id.search_results_id);
        }
    }


}

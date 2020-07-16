package com.example.ebaycatalogsearch;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class SecondActivity extends AppCompatActivity {
    Toolbar toolbar;
    List<Ebay> ebay;
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    String dynamicUrl;
    TextView noRecords;
    TextView progressText;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;
    TextView displayCount;
    String keyword;
    final String hardcodedUrl="http://10.0.2.2:8080/ebay?keyword=iphone&priceFrom=500&priceTo=2000&condition=1000";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler);
        swipeRefreshLayout=findViewById(R.id.swiperefresh_items);
        noRecords=findViewById(R.id.noRecords_id);
        recyclerView=findViewById(R.id.recycler_id);
        progressBar=findViewById(R.id.progress_id);
        progressText=findViewById(R.id.progressText_id);
        displayCount=findViewById(R.id.search_results_id);
        toolbar=findViewById(R.id.myAppBar);



        this.setSupportActionBar(toolbar);
        //this.set
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("Search Results");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//        getSupportActionBar().setTitle("Search Results");
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        dynamicUrl=intent.getStringExtra("dynamicUrl");
        keyword=intent.getStringExtra("keyword");

        Log.d("secondActivity",dynamicUrl);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(swipeRefreshLayout.isRefreshing()){
                            swipeRefreshLayout.setRefreshing(false);
                            progressBar.setVisibility(View.VISIBLE);
                            progressText.setVisibility(View.VISIBLE);
                        }
                    }
                },1000);
                LoadJSON();

            }
        });


        LoadJSON();

        ebay=new ArrayList<>();



    }

    private void LoadJSON() {
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        RequestQueue queue= Volley.newRequestQueue(SecondActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, dynamicUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
              //  Log.d("responselen", String.valueOf(response.length()));
                if(response.length()==2)
                {
                    progressBar.setVisibility(View.GONE);
                    progressText.setVisibility(View.GONE);
                    noRecords.setVisibility(View.VISIBLE);
                    Log.d("response",response);
                    Toast.makeText(SecondActivity.this,"No Records",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        progressBar.setVisibility(View.GONE);
                        progressText.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);
                        recyclerView.setVisibility(View.VISIBLE);
                        JSONArray jsonArray = new JSONArray(response);
                        Log.d("jsonlen", String.valueOf(jsonArray.length()));
                        for (int i = 0; i < jsonArray.length(); i++) {

                            Log.d("response", response);
                            Ebay ebay_obj = new Ebay();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.d("jsonObj", String.valueOf(jsonObject));
                            ebay_obj.setE_image(jsonObject.getString("galleryURL"));
                            ebay_obj.setE_title(jsonObject.getString("title"));
                            try{
                            ebay_obj.setE_shipPrice(jsonObject.getString("shippingServiceCost"));}
                            catch(Exception e){
                                e.printStackTrace();
                            }

                            ebay_obj.setE_topRated(jsonObject.getString("topRatedListing"));
                            ebay_obj.setE_condition(jsonObject.getString("condition"));
                            ebay_obj.setE_price(jsonObject.getString("currentPrice"));
                            ebay_obj.setE_productId(jsonObject.getString("productId"));
                            ebay_obj.setE_shippingInfo(jsonObject.getJSONObject("shippingInfo"));
                            ebay_obj.setE_itemUrl(jsonObject.getString("viewItemURL"));
                            ebay_obj.setE_count(jsonObject.getString("count"));

//                        JSONObject shippingInfo = jsonObject.getJSONObject("shippingInfo");
//                        ebay_obj.setE_shippingInfo(shippingInfo.getJSONObject("shippingType"));
//                        ebay_obj.setE_shippingInfo(policy.getString("shipToLocations"));
//                        ebay_obj.setE_shippingInfo(policy.getString("expeditedShipping"));
//                        ebay_obj.setE_shippingInfo(policy.getString("shippingType"));

                            ebay.add(ebay_obj);
                            Log.d("ebayObj", ebay.toString());
                        }

//                    AdapterDetailed adapterDetailed= new AdapterDetailed(getSupportFragmentManager());
//                    adapterDetailed.AddFragment(new ProductFragment(ebay),"PRODUCT");
//                    adapterDetailed.AddFragment(new ShippingFragment(ebay),"SHIPPING");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    String startString;
                    String next="<font color='#0063d1'>";
                    String fontend="</font>";
//                    Log.d("int", String.valueOf(Integer.parseInt(ebay.get(0).getE_count())));
                    if(Integer.parseInt(ebay.get(0).getE_count())<=50) {
                        displayCount.setText(Html.fromHtml("Showing "+next+ebay.get(0).getE_count()+fontend+" results for "+next+keyword+fontend, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    }
                    else
                    {
                        displayCount.setText(Html.fromHtml("Showing "+next+"50"+fontend+" results for "+next+keyword+fontend, HtmlCompat.FROM_HTML_MODE_LEGACY));
                    }

                    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
                    recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL));
                    recyclerView.setLayoutManager(new GridLayoutManager(SecondActivity.this, 2));
                    adapter = new RecyclerViewAdapter(SecondActivity.this, ebay, dynamicUrl);
                    recyclerView.setAdapter(adapter);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //  textView.setText("That didn't work!");
                Log.d("tag", "onErrorResponse:" + error.getMessage());
            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                // app icon in action bar clicked; goto parent activity.
//                this.finish();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
@Override
public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            Log.d("options","menu");
            return true;
    }

    return super.onOptionsItemSelected(item);
}



//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId()== android.R.id.home)
//        {
//
//                this.finish();
//                return true;
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        return true;
//    }
}

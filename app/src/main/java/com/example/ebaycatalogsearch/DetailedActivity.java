package com.example.ebaycatalogsearch;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailedActivity extends AppCompatActivity {
    TabLayout tabLayout;
    AppBarLayout appBarLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    List<Ebay> ebay;
    String shippingInfo, itemUrl, title, currentPrice, shippingServiceCost;
    ProgressBar progressBar;
    TextView progressText;
    // private OnGenerateStringListener onGenerateStringListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_activity);
       // onGenerateStringListener = (OnGenerateStringListener) this;

       // onStartGenerateStringFragment();
        tabLayout = findViewById(R.id.menuBar_id);
        viewPager = findViewById(R.id.viewPager_id);
        progressBar=findViewById(R.id.progress_id);
        progressText=findViewById(R.id.progressText_id);
        toolbar=findViewById(R.id.myAppBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ebay = new ArrayList<>();

        //productId from SecondActivity
        Intent intent=getIntent();
        String productId=intent.getStringExtra("productId");
        //dynamicUrl=intent.getStringExtra("dynamicUrl");
        shippingInfo=intent.getStringExtra("shippingInfo");
        title=intent.getStringExtra("title");
        currentPrice=intent.getStringExtra("currentPrice");
        shippingServiceCost=intent.getStringExtra("shippingServiceCost");
        itemUrl=intent.getStringExtra("viewItemURL");
        getSupportActionBar().setTitle(title);
        apiCall(productId);

    }
    private void apiCall(String productId)
    {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .encodedAuthority("webtechassign9.uk.r.appspot.com")
                .appendEncodedPath("ebay")
                .appendEncodedPath("productId")
                .appendQueryParameter("productId", productId);
        String productUrl = builder.build().toString();
        Log.d("productUrl",productUrl);
        RequestQueue queue = Volley.newRequestQueue(DetailedActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, productUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        try {
                            progressBar.setVisibility(View.GONE);
                            progressText.setVisibility(View.GONE);
                            JSONArray jsonArray = new JSONArray(response);

                            Ebay ebay_obj = new Ebay();
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                ebay_obj.setE_user_id(jsonObject.getString("User ID"));
                                ebay_obj.setE_feedback_rate(jsonObject.getString("Feedback Rating Star"));
                                ebay_obj.setE_user_id(jsonObject.getString("Feedback Score"));
                                ebay_obj.setE_user_id(jsonObject.getString("Positive Feedback Percent"));
//                                JSONObject policy = jsonObject.getJSONObject("Return Policy");
//                                ebay_obj.setE_return_policy(policy.getJSONObject("Refund"));
//                                ebay_obj.setE_return_policy(policy.getString("Returns Within"));
                                ebay_obj.setE_seller(jsonObject.getJSONObject("Seller"));
                                ebay_obj.setE_return_policy(jsonObject.getJSONObject("Return Policy"));
                                ebay_obj.setE_nameValueList1(jsonObject.getJSONArray("NameValueList1"));
                               ebay_obj.setE_pictureURL(jsonObject.getJSONArray("PictureURL"));
                               ebay_obj.setE_brandValue(jsonObject.getString("BrandValue"));
                               ebay_obj.setE_subTitle(jsonObject.getString("Subtitle"));

//                                Log.d("userID:",jsonObject.getString("User ID"));
//                                Log.d("feedback Rate:",jsonObject.getString("Feedback Rating Star"));
                                ebay.add(ebay_obj);
                                Log.d("ebay", ebay.toString());
                            }

                            AdapterDetailed adapterDetailed= new AdapterDetailed(getSupportFragmentManager());
                            adapterDetailed.AddFragment(new ProductFragment(ebay,title,currentPrice,shippingServiceCost),"PRODUCT");
                            adapterDetailed.AddFragment(new SellerFragment(ebay),"SELLER INFO");
                            adapterDetailed.AddFragment(new ShippingFragment(ebay, shippingInfo),"SHIPPING");
                            viewPager.setAdapter(adapterDetailed);
                            tabLayout.setupWithViewPager(viewPager);
                            setUpTabIcons();



                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
         stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                 DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                 DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }


    private void setUpTabIcons() {
//        Drawable mIcon= ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_seller);
//        mIcon.setColorFilter(ContextCompat.getColor(getApplicationContext(),R.color.app_blue_eBay), PorterDuff.Mode.MULTIPLY);

        int[] tabIcons={
                R.drawable.information_variant_selected,
                R.drawable.shop,
                R.drawable.truck_delivery_selected

        };
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home)
        {

            finish();

        }
        else if(item.getItemId()== R.id.redirect_id)
        {
            Intent openURL = new Intent(Intent.ACTION_VIEW, Uri.parse(itemUrl));

            startActivity(openURL);

        }
        else
            return super.onOptionsItemSelected(item);
        return true;
    }


}

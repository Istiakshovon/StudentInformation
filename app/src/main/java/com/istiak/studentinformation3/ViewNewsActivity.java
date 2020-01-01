package com.istiak.studentinformation3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewNewsActivity extends AppCompatActivity {

    ListView listView;
    private ProgressDialog loading;
    int MAX_SIZE=999;
    public String newsId[]=new String[MAX_SIZE];
    public String userTitle[]=new String[MAX_SIZE];
    public String userDescription[]=new String[MAX_SIZE];
    public String userDate[]=new String[MAX_SIZE];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_news);

        listView=findViewById(R.id.list_view);
        newsData();
    }

    private void newsData(){
        //for showing progress dialog
        loading = new ProgressDialog(ViewNewsActivity.this);
        loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Loading");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.VIEW_NEWS;

        StringRequest stringRequest = new StringRequest(URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
                Log.d("data",response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        loading.dismiss();
                        Toast.makeText(ViewNewsActivity.this, "Network Error!", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(ViewNewsActivity.this);
        requestQueue.add(stringRequest);
    }



    private void showJSON(String response) {

        //Create json object for receiving jason data
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);


            if (result.length()==0)
            {
                Toast.makeText(ViewNewsActivity.this, "No Data Available!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ViewNewsActivity.this, HomeActivity.class);

                startActivity(intent);
                //imgNoData.setImageResource(R.drawable.nodata);
            }

            else {

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    String id = jo.getString(Constant.KEY_ID);
                    String title = jo.getString(Constant.KEY_TITLE);
                    String description = jo.getString(Constant.KEY_DESCRIPTION);
                    String date = jo.getString(Constant.KEY_DATE);
                    //insert data into array for put extra
                    newsId[i] = id;
                    userTitle[i] = title;
                    userDescription[i] = description;
                    userDate[i] = date;

                    //put value into Hashmap
                    HashMap<String, String> user_data = new HashMap<>();
                    user_data.put(Constant.KEY_TITLE, title);
                    user_data.put(Constant.KEY_DESCRIPTION, description);
                    user_data.put(Constant.KEY_DATE, date);

                    list.add(user_data);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ViewNewsActivity.this, list, R.layout.news_list_items,
                new String[]{Constant.KEY_TITLE, Constant.KEY_DATE},
                new int[]{R.id.txt_title, R.id.txt_date});

        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {


                Intent intent=new Intent(ViewNewsActivity.this,NewsDetailsActivity.class);
                intent.putExtra("title",userTitle[position]);
                intent.putExtra("description",userDescription[position]);
                intent.putExtra("date",userDate[position]);
                intent.putExtra("newsId",newsId[position]);

                startActivity(intent);



            }
        });



    }

    //for back button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

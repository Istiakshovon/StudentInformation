package com.istiak.studentinformation3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NewsDetailsActivity extends AppCompatActivity {

    TextView txtTitle,txtDescription,txtDate;
    Button btnDeleteNews;
    private ProgressDialog loading;
    String getUserRoll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);

        txtTitle = findViewById(R.id.txt_title_details);
        txtDescription = findViewById(R.id.txt_description_details);
        txtDate = findViewById(R.id.txt_date_details);
        btnDeleteNews = findViewById(R.id.btn_delete_news);

        String title = getIntent().getExtras().getString("title");
        String description = getIntent().getExtras().getString("description");
        String date = getIntent().getExtras().getString("date");


        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getUserRoll = sharedPreferences.getString(Constant.ROLL_SHARED_PREF, "");

        txtTitle.setText(title);
        txtDescription.setText(description);
        txtDate.setText(date);

        btnDeleteNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewsDetailsActivity.this);
                builder.setIcon(R.drawable.loading)
                        .setMessage("Want to Delete Contact?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                // Perform Your Task Here--When Yes Is Pressed.
                                delete();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Perform Your Task Here--When No is pressed
                                dialog.cancel();
                            }
                        }).show();
            }
        });
    }

    private void delete(){
        loading = new ProgressDialog(this);
        // loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Delete");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.DELETE_NEWS_URL;


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //for track response in logcat
                        Log.d("RESPONSE", response);
                        // Log.d("RESPONSE", userCell);


                        //If we are getting success from server
                        if (response.equals("success")) {

                            loading.dismiss();
                            //Starting profile activity

                            Intent intent = new Intent(NewsDetailsActivity.this, HomeActivity.class);
                            Toast.makeText(NewsDetailsActivity.this, " Successfully Deleted!", Toast.LENGTH_SHORT).show();
                            startActivity(intent);

                        }


                        //If we are getting success from server
                        else if (response.equals("failure")) {

                            loading.dismiss();
                            //Starting profile activity

                            Intent intent = new Intent(NewsDetailsActivity.this, HomeActivity.class);
                            Toast.makeText(NewsDetailsActivity.this, "You can't delete the post!", Toast.LENGTH_SHORT).show();
                            //startActivity(intent);

                        } else {

                            loading.dismiss();
                            Toast.makeText(NewsDetailsActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        Toast.makeText(NewsDetailsActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                String newsId = getIntent().getExtras().getString("newsId");

                params.put(Constant.KEY_ID, newsId);
                params.put(Constant.KEY_USER_ROLL, getUserRoll);

                //Log.d("ID", userId + " "+ getUserRoll);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(NewsDetailsActivity.this);
        requestQueue.add(stringRequest);
    }
}

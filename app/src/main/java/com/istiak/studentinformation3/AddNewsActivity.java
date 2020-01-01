package com.istiak.studentinformation3;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class AddNewsActivity extends AppCompatActivity {

    EditText etxtTitle,etxtDescription,etxtDate;
    Button btnAdd;
    ProgressDialog loading;
    String getUserRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_news);

        etxtDate = findViewById(R.id.etxt_date);
        etxtDescription = findViewById(R.id.etxt_description);
        etxtTitle = findViewById(R.id.etxt_title);
        btnAdd=findViewById(R.id.btn_add);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(true); //for back button
        actionBar.setDisplayHomeAsUpEnabled(true);//for back button
        actionBar.setTitle("Add news");//for actionbar title

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getUserRoll = sharedPreferences.getString(Constant.ROLL_SHARED_PREF, "");
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });
    }

    //Save contact method
    public void  add()
    {

        final String title=etxtTitle.getText().toString();
        final String description=etxtDescription.getText().toString();
        final String date=etxtDate.getText().toString();


        if (title.isEmpty())
        {
            Toast.makeText(this, "Title Can't be Empty", Toast.LENGTH_SHORT).show();
        }
        else if (description.isEmpty())
        {
            Toast.makeText(this, "Description Can't be Empty", Toast.LENGTH_SHORT).show();
        }

        else if(date.isEmpty())
        {
            Toast.makeText(this, "Date Can't be Empty", Toast.LENGTH_SHORT).show();
        }

        else {
            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Adding");
            loading.setMessage("Please wait....");
            loading.show();

            String URL = Constant.SAVE_URL;


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

                                Intent intent = new Intent(AddNewsActivity.this, HomeActivity.class);
                                Toast.makeText(AddNewsActivity.this, " Successfully Saved!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }


                            //If we are getting success from server
                            else if (response.equals("failure")) {

                                loading.dismiss();
                                //Starting profile activity

                                //Intent intent = new Intent(AddNewsActivity.this, HomeActivity.class);
                                Toast.makeText(AddNewsActivity.this, " Save fail!", Toast.LENGTH_SHORT).show();
                                //startActivity(intent);

                            } else {

                                loading.dismiss();
                                Toast.makeText(AddNewsActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want

                            Toast.makeText(AddNewsActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request


                    params.put(Constant.KEY_USER_ROLL, getUserRoll);
                    params.put(Constant.KEY_TITLE, title);
                    params.put(Constant.KEY_DESCRIPTION, description);
                    params.put(Constant.KEY_DATE, date);



                    // Log.d("ID", getID);

                    //returning parameter
                    return params;
                }
            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(AddNewsActivity.this);
            requestQueue.add(stringRequest);
        }

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

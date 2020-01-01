package com.istiak.studentinformation3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class profileActivity extends AppCompatActivity {

    EditText etxtName,etxtClass,etxtRoll,etxtFatherName,etxtMotherName,etxtContact,etxtGender,etxtSection,etxtPassword;

    Button btnEdit,btnUpdate,btnDelete;

    String getUserRoll;

    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etxtName = findViewById(R.id.etxt_name);
        etxtClass = findViewById(R.id.etxt_class);
        etxtRoll = findViewById(R.id.etxt_roll);
        etxtFatherName = findViewById(R.id.etxt_father_name);
        etxtMotherName = findViewById(R.id.etxt_mother_name);
        etxtContact = findViewById(R.id.etxt_contact);
        etxtGender = findViewById(R.id.etxt_gender);
        etxtSection = findViewById(R.id.etxt_section);
        etxtPassword = findViewById(R.id.etxt_password);

        btnEdit = findViewById(R.id.btn_edit);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        //Fetching cell from shared preferences
        SharedPreferences sharedPreferences;
        sharedPreferences =getSharedPreferences(Constant.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        getUserRoll = sharedPreferences.getString(Constant.ROLL_SHARED_PREF, "");

        etxtName.setEnabled(false);
        etxtClass.setEnabled(false);
        etxtRoll.setEnabled(false);
        etxtFatherName.setEnabled(false);
        etxtMotherName.setEnabled(false);
        etxtContact.setEnabled(false);
        etxtGender.setEnabled(false);
        etxtSection.setEnabled(false);

        //call data
        data();

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etxtName.setEnabled(true);
                etxtClass.setEnabled(true);
               // etxtRoll.setEnabled(true);
                etxtFatherName.setEnabled(true);
                etxtMotherName.setEnabled(true);
                etxtContact.setEnabled(true);
                etxtGender.setEnabled(true);
                etxtSection.setEnabled(true);

                etxtName.setTextColor(Color.WHITE);
                etxtClass.setTextColor(Color.WHITE);
                //etxtRoll.setTextColor(Color.WHITE);
                etxtFatherName.setTextColor(Color.WHITE);
                etxtMotherName.setTextColor(Color.WHITE);
                etxtContact.setTextColor(Color.WHITE);
                etxtGender.setTextColor(Color.WHITE);
                etxtSection.setTextColor(Color.WHITE);
                etxtPassword.setTextColor(Color.WHITE);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             UpdateProfile();


            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new AlertDialog.Builder(profileActivity.this);
                builder.setIcon(R.drawable.loading)
                        .setMessage("Want to Delete your profile?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                // Perform Your Task Here--When Yes Is Pressed.
                                DeleteProfile();
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






    //update contact method
    private void UpdateProfile() {

        final String name = etxtName.getText().toString();
        final String Class = etxtClass.getText().toString();
        final String roll = etxtRoll.getText().toString();
        final String motherName = etxtMotherName.getText().toString();
        final String fatherName = etxtFatherName.getText().toString();
        final String contact = etxtContact.getText().toString();
        final String gender = etxtGender.getText().toString();
        final String section = etxtSection.getText().toString();
        final String password = etxtPassword.getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(this, "Name Can't Empty", Toast.LENGTH_SHORT).show();
        } else if (Class.isEmpty()) {
            Toast.makeText(this, "Class Can't Empty", Toast.LENGTH_SHORT).show();
        } else if (roll.isEmpty()) {
            Toast.makeText(this, "Roll Can't Empty", Toast.LENGTH_SHORT).show();
        } else if (motherName.isEmpty()) {
            Toast.makeText(this, "Mother name Can't Empty", Toast.LENGTH_SHORT).show();
        }else if (fatherName.isEmpty()) {
            Toast.makeText(this, "father name Can't Empty", Toast.LENGTH_SHORT).show();
        }else if (contact.isEmpty()) {
            Toast.makeText(this, "Contact Can't Empty", Toast.LENGTH_SHORT).show();
        }else if (gender.isEmpty()) {
            Toast.makeText(this, "Gender Can't Empty", Toast.LENGTH_SHORT).show();
        }else if (section.isEmpty()) {
            Toast.makeText(this, "Section Can't Empty", Toast.LENGTH_SHORT).show();
        }else if (password.isEmpty()) {
            Toast.makeText(this, "Password Can't Empty", Toast.LENGTH_SHORT).show();
        }else {
            loading = new ProgressDialog(this);
            // loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Update");
            loading.setMessage("Please wait....");
            loading.show();

            String URL = Constant.UPDATE_PROFILE_URL;


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

                                Intent intent = new Intent(profileActivity.this, HomeActivity.class);
                                Toast.makeText(profileActivity.this, " Successfully Updated!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);

                            }


                            //If we are getting success from server
                            else if (response.equals("failure")) {

                                loading.dismiss();
                                //Starting profile activity

                                Intent intent = new Intent(profileActivity.this, HomeActivity.class);
                                Toast.makeText(profileActivity.this, " Update fail!", Toast.LENGTH_SHORT).show();
                                //startActivity(intent);

                            } else {

                                loading.dismiss();
                                Toast.makeText(profileActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                            }

                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //You can handle error here if you want

                            Toast.makeText(profileActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constant.KEY_NAME, name);
                    params.put(Constant.KEY_CLASS, Class);
                    params.put(Constant.KEY_ROLL, roll);
                    params.put(Constant.KEY_MOTHER_NAME, fatherName);
                    params.put(Constant.KEY_FATHER_NAME, motherName);
                    params.put(Constant.KEY_CONTACT, contact);
                    params.put(Constant.KEY_GENDER, gender);
                    params.put(Constant.KEY_SECTION, section);
                    params.put(Constant.KEY_PASSWORD, password);



                    //returning parameter
                    return params;
                }
            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(profileActivity.this);
            requestQueue.add(stringRequest);
        }

    }






    //Delete method for deleting contacts
    public void DeleteProfile() {
        loading = new ProgressDialog(this);
        // loading.setIcon(R.drawable.wait_icon);
        loading.setTitle("Delete");
        loading.setMessage("Please wait....");
        loading.show();

        String URL = Constant.DELETE__POFILE_URL;


        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        //for track response in logcat
                        Log.d("DELETE_RESPONSE", response);
                        // Log.d("RESPONSE", userCell);


                        //If we are getting success from server
                        if (response.equals("success")) {

                            loading.dismiss();
                            //Starting profile activity

                            Intent intent = new Intent(profileActivity.this, LoginActivity.class);
                            Toast.makeText(profileActivity.this, " Successfully Deleted!", Toast.LENGTH_SHORT).show();
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }


                        //If we are getting success from server
                        else if (response.equals("failure")) {

                            loading.dismiss();
                            //Starting profile activity

                            Toast.makeText(profileActivity.this, " Delete fail!", Toast.LENGTH_SHORT).show();

                        } else {

                            loading.dismiss();
                            Toast.makeText(profileActivity.this, "Network Error", Toast.LENGTH_SHORT).show();

                        }

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want

                        Toast.makeText(profileActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                        loading.dismiss();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                params.put(Constant.KEY_USER_ROLL, getUserRoll);

                Log.d("ID", getUserRoll);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(profileActivity.this);
        requestQueue.add(stringRequest);

    }















    private void data() {
            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Sign up");
            loading.setMessage("Please wait....");
            loading.show();


            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.GET_PROFILE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    showJSON(response);
                    Log.d("profile",response);
                    loading.dismiss();
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(profileActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constant.KEY_USER_ROLL, getUserRoll);


                    //returning parameter
                    return params;
                }
            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }

        private void showJSON(String response){
            JSONObject jsonObject=null;
            ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
            try{
                jsonObject = new JSONObject(response);
                JSONArray result = jsonObject.getJSONArray(Constant.JSON_ARRAY);

                if(result.length()==0){
                    Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
                }else{
                    JSONObject jo = result.getJSONObject(0);
                    String name = jo.getString(Constant.KEY_NAME);
                    String roll = jo.getString(Constant.KEY_ROLL);
                    String Class = jo.getString(Constant.KEY_CLASS);
                    String fatherName = jo.getString(Constant.KEY_FATHER_NAME);
                    String motherName = jo.getString(Constant.KEY_MOTHER_NAME);
                    String gender = jo.getString(Constant.KEY_GENDER);
                    String contact = jo.getString(Constant.KEY_CONTACT);
                    String section = jo.getString(Constant.KEY_SECTION);


                    etxtName.setText(name);
                    etxtClass.setText(Class);
                    etxtRoll.setText(roll);
                    etxtFatherName.setText(fatherName);
                    etxtMotherName.setText(motherName);
                    etxtContact.setText(contact);
                    etxtGender.setText(gender);
                    etxtSection.setText(section);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



        }


}


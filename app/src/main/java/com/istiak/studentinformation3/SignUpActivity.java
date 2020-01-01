package com.istiak.studentinformation3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
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

public class SignUpActivity extends AppCompatActivity {

    EditText etxtName,etxtClass,etxtRoll,etxtContact,etxtfatherNameName,etxtmotherNameName,etxtPassword;
    RadioButton rbMale,rbFemale,rbOther;
    RadioButton rbA,rbB;
    Button btnSign;
    ProgressDialog loading;
     String gender = "";
     String  section = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etxtName = findViewById(R.id.etxt_name);
        etxtClass = findViewById(R.id.etxt_class);
        etxtContact = findViewById(R.id.etxt_contact);
        etxtfatherNameName = findViewById(R.id.etxt_father_name);
        etxtmotherNameName = findViewById(R.id.etxt_mother_name);
        etxtRoll = findViewById(R.id.etxt_roll);
        etxtPassword = findViewById(R.id.etxt_password);

        rbA = findViewById(R.id.rb_a);
        rbB = findViewById(R.id.rb_b);

        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        rbOther = findViewById(R.id.rb_other);
        btnSign = findViewById(R.id.btn_sign);

        btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sign_up();
            }
        });
    }







    private void sign_up() {
        if (rbMale.isChecked()){
            gender ="Male";
        }else if(rbFemale.isChecked()){
             gender = "Female";
        }else if (rbOther.isChecked()){
            gender = "other";
        }
        if (rbA.isChecked()){
           section = "A";
        }else if (rbB.isChecked()){
           section = "B";
        }

        //Getting values from edit texts
        final String name = etxtName.getText().toString().trim();
        final String roll = etxtRoll.getText().toString().trim();
        final String Class = etxtClass.getText().toString().trim();
        final String fatherName = etxtfatherNameName.getText().toString().trim();
        final String motherName = etxtmotherNameName.getText().toString().trim();
        final String password = etxtPassword.getText().toString().trim();
        final String contact = etxtContact.getText().toString().trim();



        //Checking  field/validation
        if (name.isEmpty()) {
            etxtName.setError("Please enter name !");
            etxtName.requestFocus();
        }
        else if (Class.isEmpty()) {

            etxtClass.setError("Please enter class !");
            etxtClass.requestFocus();

        }
        else if (roll.isEmpty()) {

            etxtRoll.setError("Please enter roll !");
            etxtRoll.requestFocus();

        }else if (fatherName.isEmpty()) {

            etxtfatherNameName.setError("Please enter your fatherName's name !");
            etxtfatherNameName.requestFocus();

        }else if (motherName.isEmpty()) {

            etxtmotherNameName.setError("Please enter your motherName's name !");
            etxtmotherNameName.requestFocus();

        }else if (contact.isEmpty()) {

            etxtContact.setError("Please enter your contact number !");
            etxtContact.requestFocus();

        }

        else if (password.isEmpty()) {

            etxtPassword.setError("Please enter password !");
            etxtPassword.requestFocus();

        }else
        {
            loading = new ProgressDialog(this);
            loading.setIcon(R.drawable.wait_icon);
            loading.setTitle("Sign up");
            loading.setMessage("Please wait....");
            loading.show();


            StringRequest stringRequest=new StringRequest(Request.Method.POST, Constant.SIGNUP_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    //for track response in logcat
                    Log.d("RESPONSE", response);
                    if (response.equals("success")) {
                        loading.dismiss();
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else if (response.equals("exists")) {

                        Toast.makeText(SignUpActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }

                    else if (response.equals("failure")) {

                        Toast.makeText(SignUpActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();

                    }
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            Toast.makeText(SignUpActivity.this, "No Internet Connection or \nThere is an error !!!", Toast.LENGTH_LONG).show();
                            loading.dismiss();
                        }
                    }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    //Adding parameters to request

                    params.put(Constant.KEY_NAME, name);
                    params.put(Constant.KEY_ROLL, roll);
                    params.put(Constant.KEY_CLASS, Class);
                    params.put(Constant.KEY_FATHER_NAME, fatherName);
                    params.put(Constant.KEY_MOTHER_NAME, motherName);
                    params.put(Constant.KEY_GENDER, gender);
                    params.put(Constant.KEY_CONTACT, contact);
                    params.put(Constant.KEY_SECTION, section);
                    params.put(Constant.KEY_PASSWORD, password);

                    //Show data in logcat operation
                    //Log.d("info",""+name+" "+roll+" "+Class+" "+fatherName+" "+motherName+" "+gender+" "+section);

                    //returning parameter
                    return params;
                }
            };


            //Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }








}

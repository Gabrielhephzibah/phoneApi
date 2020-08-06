package com.cherish.phoneapi.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.cherish.phoneapi.R;
import com.cherish.phoneapi.Utils.utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton fab;
    Button submitBtn;
    TextInputEditText name;
    TextInputEditText descriptionp;
    TextInputEditText pricep;
    TextInputEditText imagep;


    public void submit(View view){
        final ProgressDialog dialog = utils.showProgressDialog(MainActivity.this,"LOADING......");
        dialog.show();

        String product = name.getText().toString();
        final String description = descriptionp.getText().toString();
        String price = pricep.getText().toString();
        String image = imagep.getText().toString();

        Log.i("message","button clicked");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("product",product);
            jsonObject.put("description",description);
            jsonObject.put("price",price);
            jsonObject.put("image",image);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://guarded-tor-02806.herokuapp.com/api/post/add")
                .addJSONObjectBody(jsonObject) // posting json
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.dismiss();

                        Toast.makeText(MainActivity.this,"A new product has been added successfully", Toast.LENGTH_LONG).show();
                        name.setText("");
                        descriptionp.setText("");
                        pricep.setText("");
                        imagep.setText("");




                        // do anything with response
//                        boolean status = response.getBoolean("status");
//                            String error = response.getString("error");
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        error.printStackTrace();
                        dialog.dismiss();
                    }
                });

//        AndroidNetworking.post("https://guarded-tor-02806.herokuapp.com/api/post/add")
//                .addBodyParameter("product",product)
//                .addBodyParameter("description",description)
//                .addBodyParameter("price",price)
//                .addBodyParameter("image",image)
//                .setTag("test")
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // do anything with response
//                        try {
//                            boolean status = response.getBoolean("status");
//                            String error = response.getString("error");
//
//
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
//
//                    }
//                    @Override
//                    public void onError(ANError error) {
//                        error.printStackTrace();
//                    }
//                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        submitBtn = findViewById(R.id.submitBtn);
        name = findViewById(R.id.name);
        descriptionp = findViewById(R.id.descriptionp);
        pricep =  findViewById(R.id.pricep);
        imagep = findViewById(R.id.imagep);

        fab = findViewById(R.id.fab1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(),Product_display_page.class);
                startActivity(next);

            }
        });
    }
}

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
import com.cherish.phoneapi.activities.Product_display_page;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class Edit_Page extends AppCompatActivity {
    FloatingActionButton fab;
    Button submitBtn;
    TextInputEditText name;
    TextInputEditText descriptionp;
    TextInputEditText pricep;
    TextInputEditText imagep;
    String id;

    public void save(View view){
        final ProgressDialog dialog = com.cherish.phoneapi.Utils.utils.showProgressDialog(Edit_Page.this,"LOADING......");
        dialog.show();



        Log.i("message","you just saved ");

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

        AndroidNetworking.put("https://guarded-tor-02806.herokuapp.com/api/post/update/"+id)
                .addJSONObjectBody(jsonObject) // posting json
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Toast.makeText(Edit_Page.this,"A New Product has been Edited Successfully", Toast.LENGTH_LONG).show();
                        dialog.dismiss();

                        Intent productPage = new Intent(getApplicationContext(), Product_display_page.class);
                        startActivity(productPage);

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        error.printStackTrace();
                        dialog.dismiss();
                    }
                });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__page);

        id = getIntent().getStringExtra("_id");
        submitBtn = findViewById(R.id.submitBtn);
        name = findViewById(R.id.name);
        descriptionp = findViewById(R.id.descriptionp);
        pricep =  findViewById(R.id.pricep);
        imagep = findViewById(R.id.imagep);

        fab = findViewById(R.id.fab1);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent next = new Intent(getApplicationContext(),Product_display_page.class);
//                startActivity(next);
//
//            }
//        });
    }
}

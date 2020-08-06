package com.cherish.phoneapi.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.cherish.phoneapi.R;
import com.cherish.phoneapi.Utils.utils;
import com.cherish.phoneapi.adapter.ProductAdapter;
import com.cherish.phoneapi.adapter.ProductList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Product_display_page extends AppCompatActivity {
    ProductAdapter productAdapter;
    FloatingActionButton fab2;
    Button next;
    ListView listView;
    ArrayList<ProductList>productList = new ArrayList<>();
    ProductList product;
    ImageView deleteBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_display_page);
        listView = findViewById(R.id.listView);
//        deleteBtn = (ImageView)findViewById(R.id.deleteBtn);
        fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(back);
            }
        });
        display();
    }


    public void display(){
            final ProgressDialog dialog = utils.showProgressDialog(Product_display_page.this,"LOADING......");
            dialog.show();
        AndroidNetworking.get("https://guarded-tor-02806.herokuapp.com/api/post/all")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                        @Override
                        public void onResponse(JSONArray response) {
                            // do anything with response
                           try {

                               JSONArray jsonArray = (JSONArray) response;
                               for (int i = 0; i<response.length(); i++){
                                            ProductList product = new ProductList();
                                   JSONObject object = jsonArray.getJSONObject(i);
                                   product.setName(object.getString("product"));
                                    product.setDescription(object.getString("description"));
                                    product.setPrice(object.getString("price"));
                                    product.setImage(object.getString("image"));
                                    product.setId(object.getString("_id"));
                                productList.add(product);
                                dialog.dismiss();

                                   Log.i("my message","this is the response"+response);
                               }
                           }catch (Exception e){
                               e.printStackTrace();
                               dialog.dismiss();
                           }

                        productAdapter = new ProductAdapter(Product_display_page.this,productList);



                       listView.setAdapter(productAdapter);
                       Log.i("my message","my response"+productAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                                System.out.println("ITEM_AT_POSITION "+parent.getAdapter().getItem(position).toString());
                                String name = ((TextView) view.findViewById(R.id.name)).getText().toString();
                                String description = ((TextView) view.findViewById(R.id.description)).getText().toString();
                                String price = ((TextView) view.findViewById(R.id.price)).getText().toString();
                                final String idd = ((TextView)view.findViewById(R.id.id)).getText().toString();
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Product_display_page.this);
                                alertDialogBuilder.setTitle("Delete Message");
                                alertDialogBuilder
                                        .setMessage( "Are you sure you want to delete this Product")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        final ProgressDialog spinner = utils.showProgressDialog(Product_display_page.this,"LOADING......");
                                                        spinner.show();

                                                        AndroidNetworking.delete("https://guarded-tor-02806.herokuapp.com/api/post/" + idd)
                                                                .setPriority(Priority.MEDIUM)
                                                                .build()
                                                                .getAsJSONObject(new JSONObjectRequestListener() {
                                                                    @Override
                                                                    public void onResponse(JSONObject response) {
                                                                        // do anything with response
                                                                        spinner.dismiss();
                                                                        Toast.makeText(Product_display_page.this,"Product has been successfully deleted",Toast.LENGTH_LONG).show();
                                                                        Intent samePage = new Intent(getApplicationContext(),Product_display_page.class);
                                                                        startActivity(samePage);
                                                                    }
                                                                    @Override
                                                                    public void onError(ANError error) {
                                                                        // handle error
                                                                        error.printStackTrace();
                                                                    }
                                                                });
                                                    }
                                                });
                                            alertDialogBuilder
                                               .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                   @Override
                                                   public void onClick(DialogInterface dialog, int which) {
                                                       dialog.cancel();

                                                   }
                                               });
                                            alertDialogBuilder
                                                    .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                           Intent edit = new Intent(getApplicationContext(), Edit_Page.class);
                                                           edit.putExtra("_id", String.valueOf(idd));
                                                             startActivity(edit);
                                                        }
                                                    });

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                            }
                        });
                    }



                    @Override
                    public void onError(ANError error) {
                        // handle error
                        error.printStackTrace();

                        Toast.makeText(Product_display_page.this,"Product not successfully deleted",Toast.LENGTH_LONG).show();

                    }
                });
    }



}

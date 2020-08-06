package com.cherish.phoneapi.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.cherish.phoneapi.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends ArrayAdapter {
    private Context mcontext;

    private List<ProductList> productList;
    String id;


    public ProductAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<ProductList> list) {
        super(context, 0 , list);
        mcontext = context;
        productList = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mcontext).inflate(R.layout.product_layout,parent,false);
        ProductList product = productList.get(position);
        TextView name  = listItem.findViewById(R.id.name);
        name.setText(product.getName());

        TextView description = listItem.findViewById(R.id.description);
        description.setText(product.getDescription());

        TextView price  = listItem.findViewById(R.id.price);
        price.setText("Price: " +product.getPrice());


        ImageView image = listItem.findViewById(R.id.image);
        String base64String = product.getImage();
        String base64Image = base64String.split(",")[1];
        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        image.setImageBitmap(Bitmap.createScaledBitmap(decodedByte, 400, 400, false));

        TextView id  = listItem.findViewById(R.id.id);
        id.setText(product.getId());




//        Picasso.get().load(product.getImage()).resize(400, 400).into(image);




//
//        if(Transaction.getResponseCode().equals("00")){
//            amount.setText(Transaction.getTransactionAmount());
//            amount.setTextColor(Color.parseColor("#00B653"));
//        }else{
//            amount.setText(Transaction.getTransactionAmount());
//            amount.setTextColor(Color.parseColor("#B6042F"));
//        }
//        TextView datee = (TextView) listItem.findViewById(R.id.transactionDate);
//        datee.setText(Transaction.getTransactionDate());
//
//        TextView status = (TextView) listItem.findViewById(R.id.transactionStatus);
//        if(Transaction.getResponseCode().equals("00")){
//            status.setText(Transaction.getTransactionStatus());
//            status.setTextColor(Color.parseColor("#00B653"));
//        }else{
//            status.setText(Transaction.getTransactionStatus());
//            status.setTextColor(Color.parseColor("#B6042F"));
//        }

        return listItem;
    }

    @Override
    public int getCount() {
        if(productList.size() > 10){
            return 10;
        }else{
            return productList.size();
        }
    }

}

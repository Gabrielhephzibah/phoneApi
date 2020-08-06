package com.cherish.phoneapi.adapter;

public class ProductList {
    private String name;
    private  String description;
    private  String price;
    private  String image;
    private String id;

    public ProductList(String productName, String productDescription, String productPrice, String productImage,String productId){
        name = productName;
        description = productDescription;
        price = productPrice;
        image = productImage;
        id=productId;

    }

    public ProductList() {

    }

    public void setName(String productName){
        name = productName;
    }
    public  String getName(){
        return name;
    }

    public  void setDescription(String productDescription){
        description = productDescription;
    }
    public String getDescription(){
        return description;
    }

    public void setPrice(String productPrice){
        price= productPrice;
    }

    public String getPrice(){
        return price;
    }

    public void setImage(String productImage) {

        this.image = productImage;
    }

    public String getImage() {

        return image;
    }

    public  void setId(String productId){
        id=productId;
    }

    public String getId(){
        return id;
    }


}

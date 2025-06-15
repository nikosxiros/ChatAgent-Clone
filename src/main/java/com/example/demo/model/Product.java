package com.example.demo.model;

public class Product {

    private Long id;
    private  String productName;
    private Double productPrice;
    private int productStock;

    public Product(Long id, int productStock, Double productPrice, String productName) {
        this.id = id;
        this.productStock = productStock;

        this.productPrice = productPrice;
        this.productName = productName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }



    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }
}

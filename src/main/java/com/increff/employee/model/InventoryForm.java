package com.increff.employee.model;

public class InventoryForm {
    private String barcode;
    private int quantity;
    public String getInvBarcode(){
        return barcode;
    }
    public void setInvBarcode(String barcode){
        this.barcode = barcode;
    }

    public int getInvQuantity(){
        return  quantity;
    }
    public void setInvQuantity(int quantity){
        this.quantity = quantity;
    }

}

package com.increff.employee.model.form;

import java.util.List;

public class OrderForm{

   private List<OrderItemForm> orderItemFormList;

   public List<OrderItemForm> getOrderItemFormList() {
      return orderItemFormList;
   }

   public void setOrderItemFormList(List<OrderItemForm> orderItemFormvector) {
      this.orderItemFormList = orderItemFormvector;
   }
}

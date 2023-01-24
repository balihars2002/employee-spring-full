package com.increff.employee.model;
import com.sun.istack.NotNull;

import java.util.List;
import java.util.Vector;

public class OrderForm{

   private List<OrderItemForm> orderItemFormList;

   public List<OrderItemForm> getOrderItemFormList() {
      return orderItemFormList;
   }

   public void setOrderItemFormList(List<OrderItemForm> orderItemFormvector) {
      this.orderItemFormList = orderItemFormvector;
   }
}

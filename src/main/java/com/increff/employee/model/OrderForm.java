package com.increff.employee.model;
import com.sun.istack.NotNull;

import java.util.Vector;

public class OrderForm{

   private Vector<OrderItemForm> orderItemFormvector;

   public Vector<OrderItemForm> getOrderItemFormvector() {
      return orderItemFormvector;
   }

   public void setOrderItemFormvector(Vector<OrderItemForm> orderItemFormvector) {
      this.orderItemFormvector = orderItemFormvector;
   }

}

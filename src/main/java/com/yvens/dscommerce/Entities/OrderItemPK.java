package com.yvens.dscommerce.Entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Embeddable
@Table(name = "tb_order_item")
public class OrderItemPK {

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

   

    public OrderItemPK() {
    }



    public OrderItemPK(Order order, Product product) {
        this.order = order;
        this.product = product;
    }



    public Order getOrder() {
        return order;
    }



    public void setOrder(Order order) {
        this.order = order;
    }



    public Product getProduct() {
        return product;
    }



    public void setProduct(Product product) {
        this.product = product;
    }

    

}

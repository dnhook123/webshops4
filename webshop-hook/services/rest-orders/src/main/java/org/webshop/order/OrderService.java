package org.webshop.order;


import org.hibernate.criterion.Order;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class OrderService {

    @Transactional(SUPPORTS)
    public List<Orders> findAllOrders() {
        return Orders.listAll();
    }

    @Transactional(SUPPORTS)
    public Orders findOrderById(Long id) {
        return Orders.findById(id);
    }

    @Transactional(SUPPORTS)
    public Orders findMostRecentOrder() {
        long allOrdersCount = Orders.count();
       return  findOrderById(allOrdersCount);
    }


    public Orders persistOrder(@Valid Orders order) {
        Orders.persist(order);
        return order;
    }

    public Orders updateOrder(long id, String status) {
        Orders entity = Orders.findById(id);
        entity.orderStatus = status;
        Orders.persist(entity);

        return entity;
    }


    public void deleteOrder(Long id) {
        Orders order = Orders.findById(id);
        order.delete();
    }

}
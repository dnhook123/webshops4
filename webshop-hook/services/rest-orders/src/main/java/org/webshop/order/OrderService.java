package org.webshop.order;


import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
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


    public Orders persistOrder(@Valid Orders order) {
        Orders.persist(order);
        return order;
    }


    public void deleteOrder(Long id) {
        Orders order = Orders.findById(id);
        order.delete();
    }

}
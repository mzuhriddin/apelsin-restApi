package com.example.apelsinrestapi.repository;

import com.example.apelsinrestapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByActiveTrue();

    List<Order> findAllByCustomer_Id(Integer id);

    @Query(value = "select * from orders where id not in (select order_id from details) and date<'2023-01-01'", nativeQuery = true)
    List<Order> ordersWithoutDetails();

    @Query(value = "select * from orders o inner join customer c on c.id = o.customer_id where o.date in (select date from orders where c.id = orders.customer_id order by date desc limit 1)", nativeQuery = true)
    List<Order> customerLastOrder();
}

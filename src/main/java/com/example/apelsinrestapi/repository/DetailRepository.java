package com.example.apelsinrestapi.repository;

import com.example.apelsinrestapi.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DetailRepository extends JpaRepository<Detail, Integer> {
    List<Detail> findAllByActiveTrue();

    Optional<Detail> findByOrder_Id(Integer id);

    List<Detail> findAllByProduct_Id(Integer id);

    @Query(value = "select * from details d join orders o on o.id = d.order_id join product p on p.id = d.product_id", nativeQuery = true)
    List<Detail> GeneralOrders();

}

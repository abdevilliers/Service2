package com.christy.Service2.repositiories;

import com.christy.Service2.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {

    Optional<Order> findById(Long id);
    List<Order> findAll();
    void deleteById(Long id);
}

package com.example.demo.Repository;

import com.example.demo.Model.Product;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
   
    public List<Product> findByDesgination(String desgination);
    @Query("select p from Product p where p.desgination like :x")
    public Page<Product> SearchProduct(@Param("x") String mc, Pageable pageable);

}

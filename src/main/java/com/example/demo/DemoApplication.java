package com.example.demo;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;



import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class DemoApplication  {
    public static void main(String[] args) {
//       
    	ApplicationContext cxt=  SpringApplication.run(DemoApplication.class, args);
        ProductRepository  productRepository=cxt.getBean(ProductRepository.class);
//        productRepository.save(new Product("AAA",150));
//        productRepository.save(new Product("BBB",200));
//        productRepository.save(new Product("CCC",250));

        System.out.println("*********************List Product************************");
        
        List<Product> ListProduct=productRepository.findAll();
        for(Product p:ListProduct){
            System.out.println(" |DESGINATION : "+p.getDesgination()+" |PRIX : "+p.getPrix());
        }
        System.out.println("*********************Find  Product By ID************************");
        
        Scanner sc =new Scanner(System.in);
        System.out.println("Give Me Id :");
        Long id=sc.nextLong();
        System.out.println(" |DESGINATION : "+productRepository.findById(id).get().getDesgination()+"\n"+" |PRIX :"+productRepository.findById(id).get().getPrix());
        
        System.out.println("*********************Search Product************************"); 
        
        Page<Product> pageProduct=productRepository.SearchProduct("%A%",PageRequest.of(0,1));
        System.out.println("Page numéro:"+pageProduct.getNumber());
        System.out.println("Nombre de produits:"+pageProduct.getNumberOfElements());
        System.out.println("Page numéro:"+pageProduct.getTotalPages());
        System.out.println("Total produits:"+pageProduct.getTotalElements());
        for(Product pr:pageProduct){
            System.out.println(" |Desgination : "+pr.getDesgination());
        }
    }
    
   

    
}

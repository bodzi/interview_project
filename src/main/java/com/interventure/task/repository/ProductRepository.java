
package com.interventure.task.repository;

import com.interventure.task.entitiy.Product;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bojana
 */
public interface ProductRepository extends JpaRepository<Product,Long> {
    
   Optional<Product> findByName(String name);
  
}

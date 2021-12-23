package com.mockproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mockproject.entity.Products;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long>{
	
	List<Products> findByIsDeletedAndQuantityGreaterThan(Boolean isDeleted, Integer quantity);
	
	/* select * from products p inner join product_types t on p.typeId = t.id
			where t.slug = ? and p.isDeleted = ? and p.quantity > ?
	 */
	List<Products> findByProductType_SlugAndIsDeletedAndQuantityGreaterThan(String slug, Boolean isDeleted, Integer quantity);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "UPDATE products SET quantity = ?1 WHERE id = ?2", nativeQuery = true)
	void updateQuantity(Integer quantity, Long productId);
}

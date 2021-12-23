package com.mockproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mockproject.entity.Orders;

@Repository
public interface StatsRepository extends JpaRepository<Orders, Long> {
	
	@Query(value = "{CALL sp_getTotalPriceOneMonth(:month, :year)}", nativeQuery = true)
	String getTotalPriceOneMonth(@Param("month") String month, 
			@Param("year") String year);
}

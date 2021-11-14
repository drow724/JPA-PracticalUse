package jpabook.jpashop.domain.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Order;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

	private final EntityManager em;
	
	public void save(Order order) {
		em.persist(order);
	}
	
	public Order findOne(Long id) {
		return em.find(Order.class, id);
	}
	
//	private List<Order> findAll(OrderSearch orderSearch) {
//		// TODO Auto-generated method stub
//	}
	
	
}

package jpabook.jpashop.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Delivery;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import jpabook.jpashop.domain.repository.MemberRepository;
import jpabook.jpashop.domain.repository.OrderRepository;
import jpabook.jpashop.domain.repository.OrderSearch;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final MemberRepository memberRepository;
	private final ItemRepository itemRepository;
	
	/*
	 * 주문
	 */
	@Transactional
	public Long order(Long memberId, Long itemId, int count) {
		
		//엔티티 조회
		Member member = memberRepository.findOne(memberId);
		Item item = itemRepository.findOne(itemId);
		
		//배송정보 생성
		Delivery delivery = new Delivery();
		delivery.setAddress(member.getAddress());
		
		//주문상품 생성
		OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);		
		
		//기본 생성자를 protected로 만들어서 생성 방지(createOrderItem 메소드로만 객체 생성 가능)
//		OrderItem orderItem = new OrderItem();
		
		//주문 생성
		Order order = Order.createOrder(member, delivery, orderItem);
		
		//기본 생성자를 protected로 만들어서 생성 방지(createOrder 메소드로만 객체 생성 가능)
//		Order order = new Order();
		
		//주문 저장
		//cascade.all 덕분에 한번에 save 만으로 모든 연관엔티티 persist
		orderRepository.save(order);
		
		return order.getId();
	}
	
	/*
	 * 취소
	 */
	public void cancelOrder(Long orderId) {
		
		//주문 엔티티 조회
		Order order = orderRepository.findOne(orderId);
		
		//주문 취소
		order.cancel();
		
		
	}
	
	//검색
	public List<Order> findOrders(OrderSearch orderSearch) {
		return orderRepository.findAllByString(orderSearch);
	}
	
}

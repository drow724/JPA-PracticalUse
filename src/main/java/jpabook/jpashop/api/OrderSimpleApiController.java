package jpabook.jpashop.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;

/*
 * xToOne(ManyToOne, OneToOne)
Order
Order -> Member
Order -> Delivery
*/
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

	private final OrderRepository orderRepository;
	
	//무한루프에 빠짐 (@JsonIgnore 사용)
	//지연로딩으로 인한 proxy 객체기 때문에 ByteBuddyInterceptor 에러가 난다.
	@GetMapping("/api/v1/simple-orders")
	public List<Order> ordersV1() {
		List<Order> all = orderRepository.findAllByString(new OrderSearch());
		for (Order order : all) {
			order.getMember().getName();
			order.getDelivery().getAddress();
		}
		return all;
	}
	
}

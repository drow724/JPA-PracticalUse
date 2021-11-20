package jpabook.jpashop.domain.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.OrderSearch;
import jpabook.jpashop.domain.service.ItemService;
import jpabook.jpashop.domain.service.MemberService;
import jpabook.jpashop.domain.service.OrderService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;
 	private final MemberService memberSerivce;
 	private final ItemService itemService;
 	
 	@GetMapping("/order")
 	public String createForm(Model model) {
 		
 		List<Member> members = memberSerivce.findMembers();
 		List<Item> items = itemService.findItems();
 		
 		model.addAttribute("members",members);
 		model.addAttribute("items", items);
 		
 		return "order/orderForm";
 		
 	}
	
 	@PostMapping("/order")
 	public String order(@RequestParam("memberId") Long memberId,
 						@RequestParam("itemId") Long itemId,
 						@RequestParam("count") int count) {
 		
 		orderService.order(memberId, itemId, count);
 		
 		return "redirect:/orders";

 	}
 	
	@GetMapping(value = "/orders")
	public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
		
		List<Order> orders = orderService.findOrders(orderSearch);
		model.addAttribute("orders", orders);
		
		return "order/orderList";
		
	}
 	
	@PostMapping(value = "/orders/{orderId}/cancel")
	public String cancelOrder(@PathVariable("orderId") Long orderId) {
		
		orderService.cancelOrder(orderId);
		
		return "redirect:/orders";
		
	}
	
}

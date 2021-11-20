package jpabook.jpashop.domain.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.item.Item;
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
 	
}

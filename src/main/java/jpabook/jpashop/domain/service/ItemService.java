package jpabook.jpashop.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.domain.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;
	
	@Transactional
	public void saveItem(Item item) {
		itemRepository.save(item);
	}
	
	@Transactional
	public void updateItem(Long itemId, String name, int price, int stockQunatity) { // itemParam: 파리미터로 넘어온 준영속 상태의 엔티티
		Item findItem = itemRepository.findOne(itemId); // 같은 엔티티를 조회한다.
		//findItem.change(name, price, stockQunatity); //의미 있는 메소드를 만들어서 변경하자
		findItem.setName(name);// 데이터를 수정한다.
		findItem.setPrice(price); 
		findItem.setStockQuantity(stockQunatity); 
		//itemRepository.save(findItem); //변경 감지 기능 덕분에 실행할 필요 X
	}
	
//	@Transactional
//	void update(Item itemParam) { //itemParam: 파리미터로 넘어온 준영속 상태의 엔티티
//		Item mergeItem = em.merge(itemParam); //null값이 올 위험이 있어서 실무에서 쓰지 않음!!
//	}
	
	public List<Item> findItems() {
		return itemRepository.findAll();
	}
	
	public Item findOne(Long itemId) {
		return itemRepository.findOne(itemId);
	}
}

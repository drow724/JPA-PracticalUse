package jpabook.jpashop.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public List<Item> saveItem() {
		return itemRepository.findAll();
	}
	
	public Item findOne(Long itemId) {
		return itemRepository.findOne(itemId);
	}
}

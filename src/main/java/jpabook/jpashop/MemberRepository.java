package jpabook.jpashop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

	@PersistenceContext
	private EntityManager em;
	
	//커맨드와 커리를 분리해라 (Member Return X)
	//저장 후 사이드 이펙트를 방지하기 위해 return X
	//Id 정도만 조회를 위해 return
	public Long save(Member member) {
		em.persist(member);
		return member.getId();
	}
	
	public Member find(Long id) {
		return em.find(Member.class, id);
	}
	
}

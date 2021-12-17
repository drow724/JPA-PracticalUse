package jpabook.jpashop.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.service.MemberService;

@SpringBootTest
@Transactional //기본적으로 rollback
class MemberServiceTest {

	@Autowired MemberService memberService;
	@Autowired MemberRepository memberRepository;
	//@Autowired EntityManager em;
	
	@Test
	@Rollback(false)
	void 회원가입() throws Exception {
		
		// given
		Member member = new Member();
		member.setName("kim");
		
		// when
		Long savedId = memberService.join(member);
		
		// then
		//em.flush();
		assertEquals(member, memberRepository.findById(savedId).get());
		
	}

	@Test//(expected = IllegalStateException.class) junit4
	void 중복_회원_예외() throws Exception {
		
		// given
		Member member1 = new Member();
		member1.setName("kim");
		
		Member member2 = new Member();
		member2.setName("kim");
		
		// when
		memberService.join(member1);
		
//		try {
//			memberService.join(member2); //예외가 발생해야 한다.
//		} catch (IllegalStateException e) {
//			return;
//		}
//		
		// then
		IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
		assertEquals("이미 존재하는 회원입니다.", thrown.getMessage());
		
		//fail("예외가 발생해야 한다."); junit4
	}
	
}

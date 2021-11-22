package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
//@AllArgsConstructor 생성자 생성
@RequiredArgsConstructor //final이 붙은 생성자 생성
public class MemberService {

//	@Autowired 테스트 코드 작성시 Mock 주입 불가능 등 엑세스나 변경 하기 힘듬
//	final을 붙이면 생성자 파라미터 주입이 안됬을때 에러가 발생함
	private final MemberRepository memberRepository;
	
//	@Autowired //setter 인젝션을 걸어서 해결 (단, 누군가가 객체 변경 가능),(실제로 객체를 바꿀일은 많지 않다.)
//	public void setMemberRepository(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
	
//	@Autowired 생성자가 하나밖에 없는 경우에 스프링에서 자동으로 @Autowired해줌 (@AllArgsConstructor)
//	public MemberService(MemberRepository memberRepository) {
//		this.memberRepository = memberRepository;
//	}
//	public static void main(String[] args) {
//		MemberService memberService = new MemberService(Mock());
//	}
	
	//회원가입
	@Transactional
	public Long join(Member member) {
		
		validateDuplicateMember(member);//중복 회원 검증
		
		memberRepository.save(member);
		
		return member.getId();
		
	}

	//EXCEPTION
	//멤버 a에 대해 동시에 insert될 경우는 보장못함(멀티쓰레드 세이프 하지 않다)
	//member.getName()에 대해 유니크 제약조건을 건다.(DB에서)
	private void validateDuplicateMember(Member member) {
		
		List<Member> findMembers = memberRepository.findByName(member.getName());
		
		if(!findMembers.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
		
	}
	
	//회원 전체 조회
	public List<Member> findMembers() {
		return memberRepository.findAll();
	}
	
	//회원 단건 조회
	public Member findOne(Long memberId) {
		return memberRepository.findOne(memberId);
	}
	
}

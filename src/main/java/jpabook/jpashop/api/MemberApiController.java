package jpabook.jpashop.api;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

//@Controller @ResponseBody
//@ResponseBody 태그란 데이터를 자체를 바로 JSON 이나 XML로 바로 보내자 할때 사용하는 어노테이션
@RestController //위에 두개 태그를 합친 어노테이션
@RequiredArgsConstructor
public class MemberApiController {
	
	private final MemberService memberService;
	
	@PostMapping("/api/v1/members")
	public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) { // @RequestBody는 JSON 타입으로 온 body를 Member에 그대로 매핑한다.
		
		Long id = memberService.join(member);
		
		return new CreateMemberResponse(id);
		
	}
	
	@PostMapping("/api/v2/members")
	public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) { // 별도에 DTO를 만든다.(entity가 변해도 상관없다.) api 스펙이 바뀌지 않음.
		
		Member member = new Member();
		member.setName(request.getName());
		
		Long id = memberService.join(member);
		
		return new CreateMemberResponse(id);
		
	}
	
	@PutMapping("/api/v2/members/{id}")
	public UpdateMemberResponse updateMemberV2(
			@PathVariable("id")Long id,
			@RequestBody @Valid UpdateMemberRequest request) { // 별도에 DTO를 만든다.(entity가 변해도 상관없다.) api 스펙이 바뀌지 않음.
		
		memberService.update(id, request.getName());
		Member findMember = memberService.findOne(id);
		return new UpdateMemberResponse(findMember.getId(), findMember.getName());
	}
	
	@Data
	static class UpdateMemberRequest {
		
		private String name;	
		
	}
	
	@Data
	@AllArgsConstructor //entity에는 @getter 정도만 사용하고 DTO에는 막 쓴다
	static class UpdateMemberResponse {
		
		private Long	id;
		
		private String name;
		
	}
	
	@Data
	static class CreateMemberRequest {
		
		@NotEmpty
		private String name;	
		
	}
	
	@Data
	static class CreateMemberResponse {
		
		private Long id;

		public CreateMemberResponse(Long id) {
			
			this.id = id;
			
		}
		
	}
	
}

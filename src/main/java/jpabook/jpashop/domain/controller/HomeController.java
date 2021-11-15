package jpabook.jpashop.domain.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class HomeController {

	//@Slf4j로 대체
	//Logger log = LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/")
	public String home() {
		log.info("home controller");
		return "home";
	}
	
}

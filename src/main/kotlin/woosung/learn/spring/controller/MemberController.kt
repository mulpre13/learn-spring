package woosung.learn.spring.controller

import org.springframework.stereotype.Controller
import woosung.learn.spring.service.MemberService

@Controller
class MemberController(
    private val memberService: MemberService
) {
}
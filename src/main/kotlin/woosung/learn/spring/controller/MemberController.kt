package woosung.learn.spring.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import woosung.learn.spring.domain.Member
import woosung.learn.spring.service.MemberService

@Controller
class MemberController(
    private val memberService: MemberService
) {
    @GetMapping("/members/new")
    fun createForm(): String = "/members/createMemberForm"

    @PostMapping("/members/new")
    fun create(form: MemberForm): String {
        memberService.join(form.name)
        return "redirect:/"
    }

    @GetMapping("/members")
    fun list(model: Model): String {
        val members: List<Member> = memberService.findMembers()
        model.addAttribute("members", members)
        return "members/memberList"
    }
}
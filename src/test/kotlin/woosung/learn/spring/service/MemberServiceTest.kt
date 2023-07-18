package woosung.learn.spring.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import woosung.learn.spring.repository.MemoryMemberRepository

class MemberServiceTest : FunSpec({
    lateinit var memberService: MemberService

    beforeEach {
        val memberRepository = MemoryMemberRepository()
        memberService = MemberService(memberRepository)
    }

    test("join") {
        // given
        val name = "hello"

        // when
        val id = memberService.join(name)

        // then
        memberService.findMember(id)!!.name shouldBe name
    }

    test("duplicate join") {
        // given
        val name = "spring"

        // when
        memberService.join(name)

        // then
        val exception = shouldThrow<IllegalStateException> {
            memberService.join(name)
        }
        exception.message shouldBe "User already exists"
    }

    test("findMembers") {
        // given
        val name1 = "member1"
        val name2 = "member2"

        // when
        val member1 = memberService.join(name1)
        val member2 = memberService.join(name2)

        // then
        val members = memberService.findMembers()
        members.size shouldBe 2
        members.map { it.id } shouldContainExactlyInAnyOrder listOf(member1, member2)
    }

    test("findOne") {
        // given
        val name1 = "member1"
        val name2 = "member2"

        // when
        val id1 = memberService.join(name1)
        val id2 = memberService.join(name2)

        // then
        val member1 = memberService.findMember(id1)!!
        val member2 = memberService.findMember(id2)!!
        member1.id shouldBe id1
        member2.id shouldBe id2
    }
})

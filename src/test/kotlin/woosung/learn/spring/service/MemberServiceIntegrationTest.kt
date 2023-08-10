package woosung.learn.spring.service

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.spring.SpringAutowireConstructorExtension
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import woosung.learn.spring.repository.MemberRepository

class ProjectConfig : AbstractProjectConfig() {
    override fun extensions(): List<Extension> = listOf(SpringAutowireConstructorExtension)
    override val isolationMode = IsolationMode.InstancePerTest
}

// TODO transactional is not applying;
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest(
    private val memberService: MemberService,
    private val memberRepository: MemberRepository
) : FunSpec() {
    init {
        test("join") {
            // given
            val name = "hello"

            // when
            val id = memberService.join(name)

            // then
            memberService.findMember(id)!!.name shouldBe memberRepository.findById(id)!!.name
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
    }
}
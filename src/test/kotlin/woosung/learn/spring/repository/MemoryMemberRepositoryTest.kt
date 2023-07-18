package woosung.learn.spring.repository

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import woosung.learn.spring.domain.Member

class MemoryMemberRepositoryTest() : FunSpec() {
    private lateinit var repository: MemberRepository

    init {
        beforeEach() {
            repository = MemoryMemberRepository()
        }

        test("save") {
            val member: Member = repository.save("spring")
            val result: Member = repository.findById(member.id)!!

            result shouldBe member
        }

        test("findByName") {
            val member1: Member = repository.save("spring1")
            val member2: Member = repository.save("spring2")

            val result1: Member = repository.findByName("spring1")!!
            val result2: Member = repository.findByName("spring2")!!

            result1 shouldBe member1
            result2 shouldBe member2
        }

        test("findAll") {
            val member1: Member = repository.save("spring1")
            val member2: Member = repository.save("spring2")

            val result: List<Member> = repository.findAll()
            result.size shouldBe 2
            result.map{it.id} shouldContainAll listOf(member1.id, member2.id)
        }
    }
}
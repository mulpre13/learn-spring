package woosung.learn.spring.service

import woosung.learn.spring.domain.Member
import woosung.learn.spring.repository.MemoryMemberRepository

class MemberService(
    private val memberRepository: MemoryMemberRepository
) {
    fun join(name: String): Long {
        memberRepository.findByName(name) ?: { throw IllegalStateException("User already exists") }
        return memberRepository.save(name).id
    }

    fun findMembers(): List<Member> {
        return memberRepository.findAll()
    }

    fun findMember(id: Long): Member? {
        return memberRepository.findById(id)
    }
}
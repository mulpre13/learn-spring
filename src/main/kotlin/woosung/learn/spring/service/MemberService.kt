package woosung.learn.spring.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import woosung.learn.spring.domain.Member
import woosung.learn.spring.repository.MemberRepository

@Service
@Transactional
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun join(name: String): Long {
        memberRepository.findByName(name)?.let { throw IllegalStateException("User already exists") }
        val member = Member(name = name)
        return memberRepository.save(member).id!!
    }

    fun findMembers(): List<Member> {
        return memberRepository.findAll()
    }

    fun findMember(id: Long): Member? {
        return memberRepository.findById(id)
    }
}
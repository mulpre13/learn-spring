package woosung.learn.spring.service

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import woosung.learn.spring.domain.Member
import woosung.learn.spring.repository.MemberRepository

@Service
class MemberService(
    @Qualifier("jdbcMemberRepository") private val memberRepository: MemberRepository
) {
    fun join(name: String): Long {
        memberRepository.findByName(name)?.let { throw IllegalStateException("User already exists") }
        return memberRepository.save(name).id
    }

    fun findMembers(): List<Member> {
        return memberRepository.findAll()
    }

    fun findMember(id: Long): Member? {
        return memberRepository.findById(id)
    }
}
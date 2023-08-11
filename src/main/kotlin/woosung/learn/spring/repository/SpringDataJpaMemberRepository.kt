package woosung.learn.spring.repository

import org.springframework.data.jpa.repository.JpaRepository
import woosung.learn.spring.domain.Member

interface SpringDataJpaMemberRepository : MemberRepository, JpaRepository<Member, Long> {
    override fun findByName(name: String): Member?
}
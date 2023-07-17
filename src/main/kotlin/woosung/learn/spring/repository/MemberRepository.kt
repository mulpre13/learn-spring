package woosung.learn.spring.repository

import woosung.learn.spring.domain.Member

interface MemberRepository {
    fun save(name: String): Member
    fun findById(id: Long): Member?
    fun findByName(name: String): Member?
    fun findAll(): List<Member>
}
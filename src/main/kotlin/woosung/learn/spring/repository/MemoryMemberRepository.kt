package woosung.learn.spring.repository

import org.springframework.stereotype.Repository
import woosung.learn.spring.domain.Member

@Repository
class MemoryMemberRepository : MemberRepository {
    private val store = HashMap<Long, Member>()
    private var sequence = 0L

    override fun save(name: String): Member {
        val member = Member(++sequence, name)
        store[member.id!!] = member
        return member
    }

    override fun findById(id: Long): Member? {
        return store[id]
    }

    override fun findByName(name: String): Member? {
        return store.entries.find { it.value.name == name }?.value
    }

    override fun findAll(): List<Member> {
        return store.values.toList()
    }
}
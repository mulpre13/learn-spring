package woosung.learn.spring.repository

import woosung.learn.spring.domain.Member

private val store = HashMap<Long, Member>()
private var sequence = 0L

class MemoryMemberRepository : MemberRepository {
    override fun save(member: Member): Member {
        member.id = ++sequence
        store[member.id] = member
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
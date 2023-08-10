package woosung.learn.spring.repository

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Repository
import woosung.learn.spring.domain.Member
import java.sql.ResultSet

@Repository
class JdbcTemplateMemberRepository(private val jdbcTemplate: JdbcTemplate) : MemberRepository {
    override fun save(name: String): Member {
        val jdbcInsert = SimpleJdbcInsert(jdbcTemplate)
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id")

        val parameters: Map<String, String> = mapOf("name" to name)

        val key: Number = jdbcInsert.executeAndReturnKey(MapSqlParameterSource(parameters))
        return Member(id = key.toLong(), name = name)
    }

    override fun findById(id: Long): Member? {
        val result: List<Member> = jdbcTemplate.query("select * from member where id = ?", memberRowMapper, id)
        return result.firstOrNull()
    }

    override fun findByName(name: String): Member? {
        val result: List<Member> = jdbcTemplate.query("select * from member where name = ?", memberRowMapper, name)
        return result.firstOrNull()
    }

    override fun findAll(): List<Member> {
        return jdbcTemplate.query("select * from member", memberRowMapper)
    }

    private val memberRowMapper: (ResultSet, Int) -> Member =
        { rs, rowNum -> Member(id = rs.getLong("id"), name = rs.getString("name")) }
}

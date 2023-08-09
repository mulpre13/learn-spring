package woosung.learn.spring.repository

import org.springframework.stereotype.Repository
import woosung.learn.spring.domain.Member
import java.sql.SQLException
import java.sql.Statement
import javax.sql.DataSource

@Repository
class JdbcMemberRepository(
    private val dataSource: DataSource
) : MemberRepository {
    override fun save(name: String): Member {
        val sql = "insert into member(name) values(?)"

        dataSource.connection.use { conn ->
            conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { pstmt ->
                pstmt.setString(1, name)
                pstmt.executeUpdate()

                pstmt.generatedKeys.use { rs ->
                    if (rs.next()) {
                        return Member(rs.getLong(1), name)
                    } else {
                        throw SQLException("Failed on ID lookup")
                    }
                }
            }
        }
    }

    override fun findById(id: Long): Member? {
        val sql = "select * from member where id = ?"

        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setLong(1, id)

                pstmt.executeQuery().use { rs ->
                    return if (rs.next()) {
                        Member(rs.getLong("id"), rs.getString("name"))
                    } else {
                        null
                    }
                }
            }
        }
    }

    override fun findByName(name: String): Member? {
        val sql = "select * from member where name = ?"

        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.setString(1, name)

                pstmt.executeQuery().use { rs ->
                    return if (rs.next()) {
                        Member(rs.getLong("id"), rs.getString("name"))
                    } else {
                        null
                    }
                }
            }
        }
    }

    override fun findAll(): List<Member> {
        val sql = "select * from member"

        dataSource.connection.use { conn ->
            conn.prepareStatement(sql).use { pstmt ->
                pstmt.executeQuery().use { rs ->
                    val members = ArrayList<Member>()

                    while (rs.next()) {
                        members.add(Member(rs.getLong("id"), rs.getString("name")))
                    }
                    return members
                }
            }
        }
    }
}
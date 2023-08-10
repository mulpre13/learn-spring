package woosung.learn.spring.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Member(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null, val name: String
)

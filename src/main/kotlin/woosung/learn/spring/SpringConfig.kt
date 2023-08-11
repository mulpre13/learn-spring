package woosung.learn.spring

import jakarta.persistence.EntityManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import woosung.learn.spring.repository.JpaMemberRepository
import woosung.learn.spring.repository.MemberRepository

@Configuration
class SpringConfig

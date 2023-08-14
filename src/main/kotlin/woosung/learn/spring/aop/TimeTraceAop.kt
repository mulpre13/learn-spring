package woosung.learn.spring.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class TimeTraceAop {
    @Around("execution(* woosung.learn.spring..*(..))")
    fun execute(joinPoint: ProceedingJoinPoint): Any? {
        val start = System.currentTimeMillis()
        println("START: ${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name}")
        try {
            return joinPoint.proceed()
        } finally {
            val finish = System.currentTimeMillis()
            val timeMs = finish - start
            println("END: ${joinPoint.signature.declaringTypeName}.${joinPoint.signature.name} ${timeMs}ms")
        }
    }
}

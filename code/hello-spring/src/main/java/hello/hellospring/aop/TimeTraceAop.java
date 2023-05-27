package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect // 적어줘야 필수
@Component
public class TimeTraceAop {
    @Around("execution(* hello.hellospring..*(..))") // 타게팅
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        System.out.println("Start: " + joinPoint.toString()); // 어떤 메서드를 콜하는지 읽을 수 있다.
        try {
            return joinPoint.proceed();            // 그 다음으로 진행한다.

        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms"); // 어떤 메서드를 콜하는지 읽을 수 있다.
        }
    }
}

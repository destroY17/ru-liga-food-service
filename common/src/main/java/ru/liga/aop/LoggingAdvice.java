package ru.liga.aop;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.liga.model.UserLogMessage;

@Aspect
@Component
@Slf4j
public class LoggingAdvice {
    @Pointcut(value = "@annotation(ru.liga.aop.annotation.UserLog)")
    public void userLogPointCut() {

    }

    @Around(value = "userLogPointCut()")
    public Object logger(ProceedingJoinPoint pjp) throws JsonProcessingException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        String username = principal instanceof UserDetails ?
                ((UserDetails) principal).getUsername() :
                "unknownUser";

        String logId = "user-log";
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        String args = new ObjectMapper().writeValueAsString(pjp.getArgs());

        UserLogMessage logMessage = UserLogMessage
                .builder()
                .logId(logId)
                .username(username)
                .className(className)
                .methodName(methodName)
                .args(args)
                .build();

        log.info(logMessage.toString());

        Object object = null;
        try {
            object = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return object;
    }
}

package com.lexsoft.project.beer.core.validation.aspects;

import com.lexsoft.project.beer.exception.ExceptionEnum;
import com.lexsoft.project.beer.exception.ExceptionUtils;
import com.lexsoft.project.beer.exception.model.ErrorMessage;
import com.lexsoft.project.beer.exception.types.InternalWebException;
import io.jsonwebtoken.impl.DefaultClaims;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AuthorizedAspect {

    @Around(" @annotation(com.lexsoft.project.beer.core.validation.aspects.Authorized)")
    public Object validateAspect(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();

        Authorized validateAction = method.getAnnotation(Authorized.class);
        List<String> roles = Arrays.asList(validateAction.roles());
        Boolean hasAccess = roles.contains(getRole());
        // Call your Authorization server and check if all is good
        if(hasAccess) {
            return pjp.proceed();
        }else {
            List<ErrorMessage> errorMessages = ExceptionUtils.addError(ExceptionEnum.FORBIDDEN, null, null);
            throw new InternalWebException(ExceptionEnum.FORBIDDEN.getStatusCode(), errorMessages);
        }
    }


    private String getRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        DefaultClaims claims = (DefaultClaims) authentication.getPrincipal();
        String resultRole = (String) claims.get("role");
        return resultRole;
    }

}

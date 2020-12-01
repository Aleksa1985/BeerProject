package com.lexsoft.project.beer.core.validation.aspects;

import com.lexsoft.project.beer.core.validation.Validate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
@Aspect
public class ValidateBodyAspect {

    @Autowired
    Validate validate;

    @Around(" @annotation(com.lexsoft.project.beer.core.validation.aspects.ValidateBody)")
    public Object validateAspect(ProceedingJoinPoint pjp) throws Throwable {
        validate.validate(pjp.getArgs()[0]);
        return pjp.proceed();
    }


}

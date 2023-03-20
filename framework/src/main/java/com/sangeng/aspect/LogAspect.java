package com.sangeng.aspect;

import com.alibaba.fastjson.JSON;
import com.sangeng.annotation.SystemLog;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("@annotation(com.sangeng.annotation.SystemLog)")
    public void pt(){
    }
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;

        try {
            handleBefore( joinPoint);
            ret = joinPoint.proceed();
            handleAfter(ret);
        } finally {
        // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }
        return ret;
    }

    private void handleBefore(ProceedingJoinPoint joinPoint) {
        //每一个访问请求都不一样，不能写死了。获取当前线程当中的请求对象。spring封装好的
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        //获取被增强方法上的方法对象businessName
        SystemLog systemLog=getSystemLog(joinPoint);
        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.businessName() );
        // 打印 Http method (请求方式)
        log.info("HTTP Method    : {}", request.getMethod() );
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}",joinPoint.getSignature().getDeclaringType(),joinPoint.getSignature().getName()  );
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost() );
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));

    }
    private void handleAfter(Object ret) {
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret) );
    }
    //获取被增强方法上的方法对象businessName
    //1.获取被增强方法的对象，是不是得拿到被增强方法的信息ProceedingJoinPoint joinPoint
    //joinPoint传递  printLog-> handleBefore->getSystemLog
    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
       //签名就是切面包含注解的部分，但是它是一个接口
        Signature signature = joinPoint.getSignature();
        //签名实现类
        MethodSignature methodSignature = (MethodSignature) signature;
        //签名的方法
        Method method = methodSignature.getMethod();
        //拿到注解
        SystemLog annotation = method.getAnnotation(SystemLog.class);
        return annotation;
    }


}

//package cc.openhome.gossip.aspect;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.owasp.html.PolicyFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//@Aspect
//@Component
//public class HtmlSanitizer {
//
//    private final Logger logger = Logger.getLogger(HtmlSanitizer.class.getName());
//
//    @Autowired
//    private PolicyFactory policyFactory;
//
//    @Around("execution(* cc.openhome.gossip.controller.MemberController.newMessage(..))")
//    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//        Object[] args = proceedingJoinPoint.getArgs();
//        args[0] = policyFactory.sanitize(args[0].toString());
//        logger.log(Level.INFO, "blabla = {0}", args[0]);
//        return proceedingJoinPoint.proceed(args);
//    }
//}

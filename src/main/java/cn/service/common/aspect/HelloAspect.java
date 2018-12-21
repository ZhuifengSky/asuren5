package cn.service.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

public class HelloAspect {
    public void beforeAdvice() {
        //System.out.println("前置通知执行了");
    }

    public void afterAdvice() {
        //System.out.println("后置通知执行了");
    }

    public void afterReturnAdvice(Object result) {
        if (null!=result){
           // System.out.println("返回通知执行了" + "运行业务方法返回的结果为" + result.toString());
        }else{
            //System.out.printf("无返回结果或者返回结果为空");
        }
    }

    public Object aroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object result = "";
        try {
            System.out.println("环绕通知开始执行了");
            long start = System.currentTimeMillis();
            //result是目标方法执行的结果,procedd()方法就是让目标的方法执行,简单说  环绕通知=前置通知+目标方法执行+后置通知
            result = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            System.out.println("环绕通知执行结束了");
            System.out.println("执行业务方法共计：" + (end - start) + "毫秒。");
        } catch (Throwable e) {

        }
        return result;
    }

    public void throwingAdvice(JoinPoint joinPoint, Exception e) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("异常通知执行了.");
        stringBuffer.append("方法:").append(joinPoint.getSignature().getName()).append("出现了异常.");
        stringBuffer.append("异常信息为:").append(e.getMessage());
        System.out.println(stringBuffer.toString());
    }
    
   
}
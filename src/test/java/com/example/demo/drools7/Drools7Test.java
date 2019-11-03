package com.example.demo.drools7;


import com.example.demo.DroolsTestBase;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.api.runtime.rule.FactHandle;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bushneo
 * @create 2019-11-03 12:21
 */
public class Drools7Test extends DroolsTestBase {

    @Test
    public void testDrools7Api(){
        Person person = new Person();

        person.setAge(12);
        person.setName("Test");

        KieSession kieSession = super.getKieSession();

        kieSession.insert(person);
        kieSession.fireAllRules();
        kieSession.dispose();

    }


    /**
     * 规则名称假设为:buyCar
     * 业务场景：
     *      某品牌车（car）打折，打折规则如下：
     *          1.购买人年龄超过60岁，打八折
     *          2.其他年龄打9则
     */
    @Test
    public void testBuyCar(){
        Person p1 = new Person();
        p1.setAge(28);
        p1.setName("tom");
        Car c1 = new Car();
        c1.setPerson(p1);

        Person p2 = new Person();
        p2.setAge(61);
        p2.setName("tony");
        Car c2 = new Car();
        c2.setPerson(p2);

        KieSession kieSession = getKieSession("buyCarWithAge-rules","buycar-group1");

        kieSession.insert(c1);
        kieSession.insert(c2);

        int rules = kieSession.fireAllRules();
        kieSession.dispose();

        System.out.println("命中规则："+rules);
        System.out.println("c1新的折扣："+c1.getDiscount());
        System.out.println("c2新的折扣："+c2.getDiscount());

    }
    
    @Test
    public void testFactHandler(){
        KieSession kieSession = getKieSession("fact-handle-group");

        Person p = new Person();
        p.setAge(81);
        FactHandle factHandle = kieSession.insert(p);
        System.out.println(factHandle.toExternalForm());

        int count = kieSession.fireAllRules();
        System.out.println("命中规则数 :"+count);

        p.setAge(90);
        kieSession.getAgenda().getAgendaGroup("fact-handle-group").setFocus();
        kieSession.update(factHandle,p);

        count = kieSession.fireAllRules();
        System.out.println("命中规则数 :"+count);

        kieSession.dispose();

    }

    /**
     * 测试无状态的session
     */
    @Test
    public void testStateLessSession(){
        StatelessKieSession statelessKieSession = super.getStatelessKieSession();
        Person person = new Person();
        person.setAge(35);

        List<Person> list = new ArrayList<>();
        Person person1 = new Person();
        person1.setAge(19);
        list.add(person);
        list.add(person1);

        statelessKieSession.execute(person);
        statelessKieSession.execute(list);

    }
}

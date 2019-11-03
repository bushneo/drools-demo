package com.example.demo.drools5;


import org.junit.Test;
import org.kie.api.io.ResourceType;
import org.kie.internal.KnowledgeBase;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.definition.KnowledgePackage;
import org.kie.internal.io.ResourceFactory;
import org.kie.internal.runtime.StatefulKnowledgeSession;

import java.util.Collection;


/**
 * @author bushneo
 * @create 2019-11-03 12:21
 */
public class Drools5Test {

    @Test
    public void testApi(){
        KnowledgeBuilder builder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        builder.add(ResourceFactory.newClassPathResource("com/rules/goods.drl",this.getClass()),ResourceType.DRL);

        if (builder.hasErrors()){
            System.out.println(builder.getErrors());
        }

        KnowledgeBase knowledgeBase = builder.newKnowledgeBase();
        Collection<KnowledgePackage> knowledgePackages = builder.getKnowledgePackages();

        knowledgeBase.addKnowledgePackages(knowledgePackages);

        StatefulKnowledgeSession statefulKnowledgeSession = knowledgeBase.newStatefulKnowledgeSession();

        Goods goods = new Goods();

        statefulKnowledgeSession.insert(goods);
        int rules = statefulKnowledgeSession.fireAllRules();
        statefulKnowledgeSession.dispose();

        System.out.println("命中规则："+rules);
        
        System.out.println("新的折扣："+goods.getDiscount());


    }

}

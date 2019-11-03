package com.example.demo;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;

/**
 * @author bushneo
 * @create 2019-11-03 16:37
 */
public class DroolsTestBase {


    public KieSession getKieSession(){
        KieServices kieServices = KieServices.Factory.get();
        KieContainer container = kieServices.getKieClasspathContainer();
        KieSession kieSession = container.newKieSession("all-rules");
        return kieSession;
    }

    public KieSession getKieSession(String kSessionName,String groupName){
        KieServices kieServices = KieServices.Factory.get();
        KieContainer container = kieServices.getKieClasspathContainer();
        KieSession kieSession = container.newKieSession(kSessionName);
        kieSession.getAgenda().getAgendaGroup(groupName).setFocus();
        return kieSession;
    }

    protected KieSession getKieSession(String groupName){
        KieSession kieSession = getKieSession();
        kieSession.getAgenda().getAgendaGroup(groupName).setFocus();
        return kieSession;
    }



    public StatelessKieSession getStatelessKieSession(){
        KieServices kieServices = KieServices.get();
        KieContainer container = kieServices.getKieClasspathContainer();
        StatelessKieSession statelessKieSession = container.newStatelessKieSession("stateless-rules");
        return statelessKieSession;
    }

    protected StatelessKieSession getStatelessKieSession(String groupName){
        StatelessKieSession kieSession = getStatelessKieSession();
        return kieSession;
    }
}

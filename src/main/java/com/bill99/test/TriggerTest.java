package com.bill99.test;

import com.bill99.comm.Constans;
import com.bill99.tools.CoeMulticommTrigger;
import org.testng.annotations.Test;

public class TriggerTest {

    @Test
    public void postingTodayTrigger(){
        CoeMulticommTrigger coeMulticommTrigger = new CoeMulticommTrigger();
        coeMulticommTrigger.executeTrigger(Constans.GROUP_MCS,Constans.POSTING_TODAY);
    }

    @Test
    public void autoSettleTrigger(){
        CoeMulticommTrigger coeMulticommTrigger = new CoeMulticommTrigger();
        coeMulticommTrigger.executeTrigger(Constans.GROUP_MCS,Constans.AUTO_SETTLE);
    }

    @Test
    public void refundOrderNotice(){
        CoeMulticommTrigger  coeMulticommTrigger = new CoeMulticommTrigger();
        coeMulticommTrigger.executeTrigger(Constans.GROUP_FMP,Constans.REFUNDORDERNOTICE);
    }

}


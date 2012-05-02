package org.glassfish.elasticity.config.serverbeans;

import org.glassfish.paas.tenantmanager.config.TenantManagerConfig;
import org.glassfish.paas.tenantmanager.entity.Tenant;
import org.glassfish.paas.tenantmanager.entity.TenantServices;
import org.glassfish.paas.tenantmanager.api.TenantManager;
import org.glassfish.paas.tenantmanager.impl.TenantManagerEx;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.config.ConfigSupport;
import org.jvnet.hk2.config.SingleConfigCode;
import org.jvnet.hk2.config.TransactionFailure;

public class ElasticConfigTest  extends ConfigApiTest {

    TenantManager tenantManager;

    @Before
    public void before() {
        Habitat habitat = getHabitat();
        tenantManager = habitat.getComponent(TenantManager.class);
        // change file store to point to test files
        TenantManagerConfig tenantManagerConfig = ((TenantManagerEx) tenantManager).getTenantManagerConfig();
        try {
            ConfigSupport.apply(new SingleConfigCode<TenantManagerConfig>() {
                @Override
                public Object run(TenantManagerConfig tmc) throws TransactionFailure {
                    String fileStore = ElasticConfigTest.class.getResource("/").getPath();
                    tmc.setFileStore(fileStore);
                    return tmc;
                }
            }, tenantManagerConfig);
        } catch (TransactionFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();            
        }
    }

    // Verify tenant1 and tenant2 can be retrieved.
    @Test
    public void testGet() {
        Assert.assertNotNull("tenantManager", tenantManager);
        tenantManager.setCurrentTenant("tenant1");
        Tenant tenant = tenantManager.get(Tenant.class);
        Assert.assertNotNull("Current Tenant", tenant);
        TenantServices services = tenant.getServices();
        Assert.assertNotNull("Services", services);
        try {
            ConfigSupport.apply(new SingleConfigCode<TenantServices>() {
                @Override
                public Object run(TenantServices tenantServices) throws TransactionFailure {
                    
                    Elastic es = tenantServices.createChild(Elastic.class);

                    ElasticAlerts alerts=es.createChild((ElasticAlerts.class));
//                    alerts.setName(("alert1"));
//                    es.setElasticAlerts(alerts);
                    tenantServices.getTenantServices().add(es);
                    return tenantServices;
                }
            }, services);
        } catch (TransactionFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Elastic elastic = services.getServiceByType(Elastic.class);
        Assert.assertNotNull("Elastic Service", elastic);
        ElasticAlerts alerts = elastic.getElasticAlerts();
//        Assert.assertNotNull("Elastic Alertrs", alerts);
//        Assert.assertEquals("Elastic Alert Schedule", "10s", alerts.getSchedule());
//        Assert.assertEquals("Elastic Alert Sample Interval", 5, alerts.getSampleInterval());
        
        
        
    }
    

    @Test
    public void testAlerts() {
        tenantManager.setCurrentTenant("tenant2");
        Tenant tenant = tenantManager.get(Tenant.class);
        TenantServices ts = tenant.getServices();
        Elastic elastic = (Elastic) ts.getServiceByType(Elastic.class);
        ElasticAlerts elasticAlerts = elastic.getElasticAlerts();

        try {
            //TODO There is nothing specific to elasticity to test here. This should move to CTM tests
            ConfigSupport.apply(new SingleConfigCode<ElasticAlerts>() {
                @Override
                public Object run(ElasticAlerts eAlerts) throws TransactionFailure {

                    ElasticAlert alert = eAlerts.createChild(ElasticAlert.class);
                    alert.setName(("alert1"));
                    alert.setSchedule("10s");
                    alert.setType("jvm_memory");
                    eAlerts.getElasticAlert().add(alert);
                    return eAlerts;
                }
            }, elasticAlerts);
        } catch (TransactionFailure e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Assert.assertNotNull(elasticAlerts.getElasticAlert("alert1"));
    }
}

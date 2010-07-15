/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.enterprise.config.serverbeans;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;

import java.beans.PropertyVetoException;

import com.sun.enterprise.util.LocalStringManagerImpl;
import com.sun.logging.LogDomains;

import org.glassfish.config.support.*;
import org.glassfish.quality.ToDo;

import org.jvnet.hk2.annotations.Inject;
import org.jvnet.hk2.annotations.Scoped;
import org.jvnet.hk2.annotations.Service;
import org.jvnet.hk2.component.Habitat;
import org.jvnet.hk2.component.PerLookup;
import org.jvnet.hk2.component.Injectable;
import org.jvnet.hk2.config.*;
import org.jvnet.hk2.config.types.Property;
import org.jvnet.hk2.config.types.PropertyBag;

import org.glassfish.api.Param;
import org.glassfish.api.admin.AdminCommandContext;
import org.glassfish.api.admin.config.PropertiesDesc;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;

/**
 *
 */

/* @XmlType(name = "", propOrder = {
    "clusterRefOrServerRef",
    "property"
}) */

@Configured
public interface LbConfig extends ConfigBeanProxy, Injectable, PropertyBag {

    /**
     * Gets the value of the name property.
     *
     * Name of the load balancer configuration
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute(key=true)
    @NotNull
    String getName();

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    @Param (name = "name", optional=true)
    void setName(String value) throws PropertyVetoException;

    /**
     * Gets the value of the responseTimeoutInSeconds property.
     *
     * Period within which a server must return a response or otherwise it will
     * be considered unhealthy. Default value is 60 seconds. Must be greater
     * than or equal to 0. A value of 0 effectively turns off this check
     * functionality, meaning the server will always be considered healthy
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute (defaultValue="60")
    @Min(value=0)
    String getResponseTimeoutInSeconds();

    /**
     * Sets the value of the responseTimeoutInSeconds property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    @Param(name = "responsetimeout", optional=true)
    void setResponseTimeoutInSeconds(String value) throws PropertyVetoException;

    /**
     * Gets the value of the httpsRouting property.
     *
     * Boolean flag indicating how load-balancer will route https requests.
     * If true then an https request to the load-balancer will result in an
     * https request to the server; if false then https requests to the
     * load-balancer result in http requests to the server.
     * Default is to use http (i.e. value of false)
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute (defaultValue="false",dataType=Boolean.class)
    String getHttpsRouting();

    /**
     * Sets the value of the httpsRouting property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    @Param(name = "httpsrouting", optional=true)
    void setHttpsRouting(String value) throws PropertyVetoException;

    /**
     * Gets the value of the reloadPollIntervalInSeconds property.
     *
     * Maximum period, in seconds, that a change to the load balancer
     * configuration file takes before it is detected by the load balancer and
     * the file reloaded. A value of 0 indicates that reloading is disabled.
     * Default period is 1 minute (60 sec)
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute (defaultValue="60")
    String getReloadPollIntervalInSeconds();

    /**
     * Sets the value of the reloadPollIntervalInSeconds property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    @Param(name = "reloadinterval", optional=true)
    void setReloadPollIntervalInSeconds(String value) throws PropertyVetoException;

    /**
     * Gets the value of the monitoringEnabled property.
     *
     * Boolean flag that determines whether monitoring is switched on or not.
     * Default is that monitoring is switched off (false) 
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute (defaultValue="false")
    String getMonitoringEnabled();

    /**
     * Sets the value of the monitoringEnabled property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    @Param(name = "monitor", optional=true)
    void setMonitoringEnabled(String value) throws PropertyVetoException;

    /**
     * Gets the value of the routeCookieEnabled property.
     *
     * Boolean flag that determines whether a route cookie is or is not enabled.
     * Default is enabled (true).
     * 
     * @return possible object is
     *         {@link String }
     */
    @Attribute (defaultValue="true",dataType=Boolean.class)
    String getRouteCookieEnabled();

    /**
     * Sets the value of the routeCookieEnabled property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    @Param(name = "routecookie", optional=true)
    void setRouteCookieEnabled(String value) throws PropertyVetoException;

    /**
     * Gets the value of the clusterRefOrServerRef property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clusterRefOrServerRef property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClusterRefOrServerRef().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link ClusterRef }
     * {@link ServerRef }
     */
    @Element("*")
    List<Ref> getClusterRefOrServerRef();

    /**
    	Properties as per {@link PropertyBag}
     */
    @Override
    @ToDo(priority=ToDo.Priority.IMPORTANT, details="Provide PropertyDesc for legal props" )
    @PropertiesDesc(props={})
    @Element
    List<Property> getProperty();

    @DuckTyped
    <T> List<T> getRefs(Class<T> type);

    @DuckTyped
    <T> T getRefByRef(Class<T> type, String ref);   

    public class Duck {
        public static <T> List<T> getRefs(LbConfig lc, Class<T> type) {
            List<T> refs = new ArrayList<T>();
            for (Object r : lc.getClusterRefOrServerRef()) {
                if (type.isInstance(r)) {
                    refs.add(type.cast(r));
                }
            }
            // you have to return an umodifiable list since this list
            // is not the real list of elements as maintained by this config bean
            return Collections.unmodifiableList(refs);
        }

        public static <T> T getRefByRef(LbConfig lc, Class<T> type, String ref) {
            if (ref == null) {
                return null;
            }

            for (Ref r : lc.getClusterRefOrServerRef())
                if (type.isInstance(r) && r.getRef().equals(ref))
                    return type.cast(r);

            return null;
        }
    }
    
    @Service
    @Scoped(PerLookup.class)
    class Decorator implements CreationDecorator<LbConfig> {

        @Param(primary=true, optional=true)
        String target;

        @Param (optional=true)
        String config;

        @Param (optional=true, defaultValue="60")
        String responsetimeout;

        @Param (optional=true, defaultValue="false")
        String httpsrouting;

        @Param (optional=true, defaultValue="60")
        String reloadinterval;

        @Param (optional=true, defaultValue="false")
        String monitor;

        @Param (optional=true, defaultValue="true")
        String routecookie;

        @Param(optional=true, name="property", separator=':')
        Properties properties;

        @Inject
        Habitat habitat;

        @Inject
        Domain domain;

        @Inject
        LbConfigs lbconfigs;

        /**
         * Create lb-config entries
         * tasks :
         *      - ensures that it references an existing cluster

         * @param context administration command context
         * @param instance newly created configuration element
         * @throws TransactionFailure
         * @throws PropertyVetoException
         *
         */
        @Override
        public void decorate(AdminCommandContext context, final LbConfig instance) throws TransactionFailure, PropertyVetoException {
            Logger logger = LogDomains.getLogger(LbConfig.class, LogDomains.ADMIN_LOGGER);
            LocalStringManagerImpl localStrings = new LocalStringManagerImpl(LbConfig.class);            

            if (config == null && target == null) {
                String msg = localStrings.getLocalString("RequiredTargetOrConfig", "Neither LB config name nor target specified");
                throw new TransactionFailure(msg);
            }

            // generate lb config name if not specified
            if (config == null) {
                config = target + "_LB_CONFIG";
            }

            if (lbconfigs.getLbConfig(config) != null) {
                String msg = localStrings.getLocalString("LbConfigExists", config);
                throw new TransactionFailure(msg);
            }

            instance.setName(config);
            instance.setResponseTimeoutInSeconds(responsetimeout);
            instance.setReloadPollIntervalInSeconds(reloadinterval);
            instance.setMonitoringEnabled(monitor);
            instance.setRouteCookieEnabled(routecookie);
            instance.setHttpsRouting(httpsrouting);

            // creates a reference to the target
            if (target != null) {                
                if (domain.getClusterNamed(target) != null) {
                   ClusterRef cRef = instance.createChild(ClusterRef.class);
                   cRef.setRef(target);
                   instance.getClusterRefOrServerRef().add(cRef);
                } else if (domain.isServer(target)) {
                    ServerRef sRef = instance.createChild(ServerRef.class);
                    sRef.setRef(target);
                    instance.getClusterRefOrServerRef().add(sRef);
                } else {
                    String msg = localStrings.getLocalString("InvalidTarget", target);
                    throw new TransactionFailure(msg);
                }
            }

            // add properties
            if (properties != null) {
                for (Object propname: properties.keySet()) {
                    Property newprop = instance.createChild(Property.class);
                    newprop.setName((String) propname);
                    newprop.setValue(properties.getProperty((String) propname));
                    instance.getProperty().add(newprop);
                }
            }
            logger.info(localStrings.getLocalString("http_lb_admin.LbConfigCreated", config));
        }
    }

    @Service
    @Scoped(PerLookup.class)
    class DeleteDecorator implements DeletionDecorator<LbConfigs, LbConfig> {
        @Inject
        private Domain domain;

        @Override
        public void decorate(AdminCommandContext context, LbConfigs parent, LbConfig child)
                throws PropertyVetoException, TransactionFailure {
            Logger logger = LogDomains.getLogger(LbConfig.class, LogDomains.ADMIN_LOGGER);
            LocalStringManagerImpl localStrings = new LocalStringManagerImpl(LbConfig.class);

            String lbConfigName = child.getName();
            LbConfig lbConfig = domain.getLbConfigs().getLbConfig(lbConfigName);

            //Ensure there are no refs 
            if ( (lbConfig.getClusterRefOrServerRef().size() != 0 ) ) {
                String msg = localStrings.getLocalString("LbConfigNotEmpty", lbConfigName);
                throw new TransactionFailure(msg);
            }
            logger.info(localStrings.getLocalString("http_lb_admin.LbConfigDeleted", lbConfigName));
        }
   }
}

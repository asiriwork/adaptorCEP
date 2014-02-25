/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wso2.event.adaptor.udp;

import org.apache.axis2.engine.AxisConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.event.input.adaptor.core.AbstractInputEventAdaptor;
import org.wso2.carbon.event.input.adaptor.core.InputEventAdaptorListener;
import org.wso2.carbon.event.input.adaptor.core.MessageType;
import org.wso2.carbon.event.input.adaptor.core.Property;
import org.wso2.carbon.event.input.adaptor.core.config.InputEventAdaptorConfiguration;
import org.wso2.carbon.event.input.adaptor.core.message.config.InputEventAdaptorMessageConfiguration;
import org.wso2.event.adaptor.udp.internal.util.UdpEventAdaptorConstants;
import org.wso2.event.adaptor.udp.print.netflowListener;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


public final class UdpEventAdaptorType extends AbstractInputEventAdaptor {

    private static final Log log = LogFactory.getLog(UdpEventAdaptorType.class);
    public static ConcurrentHashMap<String, InputEventAdaptorListener> inputEventAdaptorListenerMap =
            new ConcurrentHashMap<String, InputEventAdaptorListener>();
    private ResourceBundle resourceBundle;

    @Override
    protected String getName() {
        return org.wso2.event.adaptor.udp.internal.util.UdpEventAdaptorConstants.EVENT_ADAPTOR_TYPE_TESTIN;
    }

    @Override
    protected List<String> getSupportedInputMessageTypes() {

        List<String> list1 = new ArrayList<String>();
        list1.add(MessageType.MAP);

        return list1;
    }

    @Override
    protected void init() {
        this.resourceBundle = ResourceBundle.getBundle("org.wso2.event.adaptor.udp.i18n.Resources", Locale.getDefault());
    }

    @Override
    protected List<Property> getInputAdaptorProperties() {

        List<Property> propertyList = new ArrayList<Property>();

        //set netflow version
        Property netflowVersion = new Property(UdpEventAdaptorConstants.EVENT_ADAPTOR_NETFLOW_VERSION);
        netflowVersion.setRequired(true);
        netflowVersion.setDisplayName(
                resourceBundle.getString(UdpEventAdaptorConstants.EVENT_ADAPTOR_NETFLOW_VERSION));
        netflowVersion.setOptions(new String[]{"version 5", "version 6"});
        netflowVersion.setDefaultValue("version 5");
        // destinationTypeProperty.setHint(resourceBundle.getString(JMSEventAdaptorConstants.ADAPTOR_JMS_DESTINATION_TYPE_HINT));
        propertyList.add(netflowVersion);

        // set receiving netflow host
        Property host = new Property(org.wso2.event.adaptor.udp.internal.util.UdpEventAdaptorConstants.EVENT_ADAPTOR_CONF_RECEIVING_UDP_HOST);
        host.setDisplayName(
                resourceBundle.getString(org.wso2.event.adaptor.udp.internal.util.UdpEventAdaptorConstants.EVENT_ADAPTOR_CONF_RECEIVING_UDP_HOST));
        host.setRequired(true);
        //host.setHint(resourceBundle.getString(UdpEventAdaptorConstants.EVENT_ADAPTOR_CONF_RECEIVING_UDP_HOST_HINT));
        propertyList.add(host);

        // set receiving udp port
        Property port = new Property(org.wso2.event.adaptor.udp.internal.util.UdpEventAdaptorConstants.EVENT_ADAPTOR_CONF_RECEIVING_UDP_PORT);
        port.setDisplayName(
                resourceBundle.getString(org.wso2.event.adaptor.udp.internal.util.UdpEventAdaptorConstants.EVENT_ADAPTOR_CONF_RECEIVING_UDP_PORT));
        port.setRequired(true);
        //port.setHint(resourceBundle.getString(UdpEventAdaptorConstants.EVENT_ADAPTOR_CONF_RECEIVING_UDP_PORT_HINT));
        propertyList.add(port);




        return propertyList;
    }

    @Override
    protected List<Property> getInputMessageProperties() {

        List<Property> propertyList = new ArrayList<Property>();

        Property property1 = new Property(org.wso2.event.adaptor.udp.internal.util.UdpEventAdaptorConstants.EVENT_ADAPTOR_MESSAGE_TOPIC);
        property1.setDisplayName(
                resourceBundle.getString(UdpEventAdaptorConstants.EVENT_ADAPTOR_MESSAGE_TOPIC));
        property1.setRequired(true);
        property1.setHint(resourceBundle.getString(org.wso2.event.adaptor.udp.internal.util.UdpEventAdaptorConstants.EVENT_ADAPTOR_MESSAGE_TOPIC));
        propertyList.add(property1);

        return propertyList;
    }

    @Override
    public String subscribe(
            InputEventAdaptorMessageConfiguration inputEventAdaptorMessageConfiguration,
            InputEventAdaptorListener inputEventAdaptorListener,
            InputEventAdaptorConfiguration inputEventAdaptorConfiguration,
            AxisConfiguration axisConfiguration) {
        String subscriptionId = UUID.randomUUID().toString();
        inputEventAdaptorListenerMap.put(subscriptionId, inputEventAdaptorListener);
        netflowListener.listener(inputEventAdaptorConfiguration);


        return subscriptionId;
    }

    @Override
    public void unsubscribe(
            InputEventAdaptorMessageConfiguration inputEventAdaptorMessageConfiguration,
            InputEventAdaptorConfiguration inputEventAdaptorConfiguration,
            AxisConfiguration axisConfiguration, String s) {
        netflowListener.collectorInstance.destroy();

        //To change body of implemented methods use File | Settings | File Templates.
    }

}

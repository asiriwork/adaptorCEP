package org.wso2.event.adaptor.udp.print;

/**
 * Copyright (C) 2005-2007 Oliver Hitz <oliver@net-track.ch>
 *
 * $Id: V5Printer.java,v 1.2 2007-04-24 13:24:50 oli Exp $
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later
 * version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

import org.wso2.carbon.event.input.adaptor.core.InputEventAdaptorListener;
import org.wso2.carbon.event.input.adaptor.core.config.InputEventAdaptorConfiguration;
import org.wso2.event.adaptor.udp.UdpEventAdaptorType;
import org.wso2.event.adaptor.udp.internal.util.UdpEventAdaptorConstants;
import org.wso2.event.adaptor.udp.nettrack.net.netflow.*;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class netflowListener {


    public static Collector collectorInstance;


    static class PrintAccountant implements Accountant {
        public void account(Flow f) {

            String netflowStream = f.toString();


            String[] data = netflowStream.split("\\n");
            Map event = new HashMap();

            for (String value : data) {
                String[] parts = value.split(":");
                event.put(parts[0], parts[1]);

            }

            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = dateFormat.format(date);
            event.put("CurrentTime", formattedDate);


            Map<String, InputEventAdaptorListener> adaptorListenerMap = UdpEventAdaptorType.inputEventAdaptorListenerMap;
            for (InputEventAdaptorListener inputEventAdaptorListener : adaptorListenerMap.values()) {
                inputEventAdaptorListener.onEventCall(event);
            }

        }

        public void destroy() {
        }


    }

    static String k;

    public static void listener(InputEventAdaptorConfiguration inputEventAdaptorConfiguration) {

        String host = inputEventAdaptorConfiguration.getInputProperties().get(UdpEventAdaptorConstants.EVENT_ADAPTOR_CONF_RECEIVING_UDP_HOST);
        String port = inputEventAdaptorConfiguration.getInputProperties().get(UdpEventAdaptorConstants.EVENT_ADAPTOR_CONF_RECEIVING_UDP_PORT);
        String netFlowVersion = inputEventAdaptorConfiguration.getInputProperties().get(UdpEventAdaptorConstants.EVENT_ADAPTOR_NETFLOW_VERSION);


        InetAddress source;
        try {
            source = InetAddress.getByName(host.trim());
            Collector collector;
            try {
                collector = new Collector(Integer.parseInt(port.trim()));

                if(netFlowVersion.equals("version 6")){
                    V6FlowHandler handler = new V6FlowHandler(source,100);
                    handler.addAccountant(new PrintAccountant());
                    collector.addFlowHandler(handler);
                    collectorInstance = collector;
                    collector.start();
                }else if(netFlowVersion.equals("version 5")){
                    V5FlowHandler handler = new V5FlowHandler(source, 100);
                    handler.addAccountant(new PrintAccountant());
                    collector.addFlowHandler(handler);
                    collectorInstance = collector;
                    collector.start();
                }
                else{

                }




            } catch (SocketException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}

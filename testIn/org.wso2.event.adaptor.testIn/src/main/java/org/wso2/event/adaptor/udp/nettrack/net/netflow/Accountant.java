/**
 * Copyright (C) 2001-2007 Oliver Hitz <oliver@net-track.ch>
 *
 * $Id: Accountant.java,v 1.2 2007-04-24 13:24:54 oli Exp $
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

package org.wso2.event.adaptor.udp.nettrack.net.netflow;

import org.wso2.carbon.databridge.agent.thrift.exception.AgentException;
import org.wso2.carbon.databridge.commons.exception.*;

import javax.security.sasl.AuthenticationException;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.UnknownHostException;

public interface Accountant {
    /**
     * Accounts a flow.
     *
     * @param flow Flow to account
     * @throws TransportException
     * @throws org.wso2.carbon.databridge.commons.exception.AuthenticationException
     * @throws NoStreamDefinitionExistException
     * @throws DifferentStreamDefinitionAlreadyDefinedException
     * @throws StreamDefinitionException
     * @throws MalformedStreamDefinitionException
     * @throws AgentException
     * @throws UnknownHostException
     * @throws SocketException
     * @throws MalformedURLException
     * @throws AuthenticationException
     */
    public void account(Flow flow) throws AuthenticationException, MalformedURLException, SocketException, UnknownHostException, AgentException, MalformedStreamDefinitionException, StreamDefinitionException, DifferentStreamDefinitionAlreadyDefinedException, NoStreamDefinitionExistException, org.wso2.carbon.databridge.commons.exception.AuthenticationException, TransportException;

    /**
     * Destroys this accountant.
     */
    public void destroy();
}

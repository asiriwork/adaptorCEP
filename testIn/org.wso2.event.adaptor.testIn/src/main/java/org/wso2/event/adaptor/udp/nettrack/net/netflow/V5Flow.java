/**
 * Copyright (C) 2000-2007 Oliver Hitz <oliver@net-track.ch>
 *
 * $Id: V5Flow.java,v 1.2 2007-04-24 13:24:54 oli Exp $
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


import org.wso2.event.adaptor.udp.nettrack.net.IpAddr;
import org.wso2.event.adaptor.udp.nettrack.util.ByteUtil;


public class V5Flow extends Flow {
    public int getLength() {
        return 48;
    }

    public int getTime() {
        return ByteUtil.getUByte(data, offset + 28);
    }

    public int getTcpFlags() {
        return ByteUtil.getUByte(data, offset + 37);
    }

    public int getProt() {
        return ByteUtil.getUByte(data, offset + 38);
    }

    public int getTOS() {
        return ByteUtil.getUByte(data, offset + 39);
    }

    public int getSrcAS() {
        return ByteUtil.getUShort(data, offset + 40);
    }

    public int getDstAS() {
        return ByteUtil.getUShort(data, offset + 42);
    }

    public int getSrcMask() {
        return ByteUtil.getUByte(data, offset + 44);
    }

    public int getDstMask() {
        return ByteUtil.getUByte(data, offset + 45);
    }

    public String toString() {
        return super.toString() + "\n" + "TcpFlags: " + getTcpFlags() + "\n"
                + "Prot: " + getProt() + "\n" + "TOS: " + getTOS() + "\n"
                + "SrcAS: " + getSrcAS() + "\n" + "DstAS: " + getDstAS() + "\n"
                + "SrcMask: " + getSrcMask() + "\n" + "DstMask: "
                + getDstMask() + "\n\n";
    }

    public String toShortString() {
        return IpAddr.toString(getSrcAddr()) + "." + getSrcPort() + " -> "
                + IpAddr.toString(getDstAddr()) + "." + getDstPort() + " "
                + getDOctets() + " " + getProt() + " Time: " + getTime();
    }
}

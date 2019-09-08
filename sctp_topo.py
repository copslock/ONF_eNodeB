#!/usr/bin/python

from mininet.topo import Topo
from mininet.net import Mininet
from mininet.cli import CLI
from mininet.log import setLogLevel, info, debug
from mininet.node import Host, RemoteController, OVSSwitch
import os

onos = RemoteController('onos', ip='192.168.0.1', port=6633)

class Onos(Host):

    def __init__(self, name, intfDict, *args, **kwargs):
        Host.__init__(self, name, *args, **kwargs)

        self.intfDict = intfDict

    def config(self, **kwargs):
        Host.config(self, **kwargs)

        for intf, attrs in self.intfDict.items():
            self.cmd('ip addr flush dev %s' % intf)
            if 'mac' in attrs:
                self.cmd('ip link set %s down' % intf)
                self.cmd('ip link set %s address %s' % (intf, attrs['mac']))
                self.cmd('ip link set %s up ' % intf)
            for addr in attrs['ipAddrs']:
                self.cmd('ip addr add %s dev %s' % (addr, intf))
                
class SCTP_CLIENT(Host):

    def __init__(self, name, intfDict, *args, **kwargs):
        Host.__init__(self, name, *args, **kwargs)
        self.intfDict = intfDict

    def config(self, **kwargs):
        Host.config(self, **kwargs)

        for intf, attrs in self.intfDict.items():
            self.cmd('ip addr flush dev %s' % intf)
            if 'mac' in attrs:
                self.cmd('ip link set %s down' % intf)
                self.cmd('ip link set %s address %s' % (intf, attrs['mac']))
                self.cmd('ip link set %s up ' % intf)
            for addr in attrs['ipAddrs']:
                self.cmd('ip addr add %s dev %s' % (addr, intf))

        self.cmd('java -jar /home/shubham/enodeb/target/sctpclient-1.0-SNAPSHOT-jar-with-dependencies.jar &> /dev/null &')

    def terminate(self):
        Host.terminate(self)

class L2Switch(OVSSwitch):

    def start(self, controllers):
        return OVSSwitch.start(self, [])

class SCTP_TOPO(Topo):
    "SCTP topology"

    def build(self):
        name = 'h1'
        eth0 = {
            'ipAddrs': ['192.168.0.2/24']
        }
        intfs = {
            '%s-eth0' % name: eth0
        }
        h1 = self.addHost(name, cls=SCTP_CLIENT, intfDict=intfs)

        name = 'h2'
        eth0 = {
            'ipAddrs': ['192.168.0.3/24']
        }
        intfs = {
            '%s-eth0' % name: eth0
        }
        h2 = self.addHost(name, cls=SCTP_CLIENT, intfDict=intfs)

        s1 = self.addSwitch(
            's1', dpid='0000000000000001', failMode='standalone', cls=L2Switch)
        
        name = 'onos'
        eth0 = {
            'ipAddrs': ['192.168.0.1/24']
        }
        intfs = {
            '%s-eth0' % name: eth0
        }
        onos = self.addHost(name, inNamespace=False, cls=Onos, intfDict=intfs)


        self.addLink(h1, s1, port1=0, port2=1)
        self.addLink(h2, s1, port1=0, port2=2)
        self.addLink(onos, s1, port1=0, port2=3)


topos = {'sctp': SCTP_TOPO}

if __name__ == '__main__':
    setLogLevel('debug')
    topo = SCTP_TOPO()

    net = Mininet(topo=topo, build=False)
    net.addController(onos)
    net.build()
    net.start()

    CLI(net)

    net.stop()

    info("done\n")

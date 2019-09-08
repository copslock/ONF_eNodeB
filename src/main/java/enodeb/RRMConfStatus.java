package enodeb;

import codecs.api.ECGI;
import codecs.api.CRNTI;
import codecs.ber.types.BerEnum;
import codecs.ber.types.string.BerUTF8String;
import codecs.pdu.RRMConfig;
import codecs.pdu.RRMConfigStatus;
import codecs.pdu.XrancApiID;
import codecs.pdu.XrancPdu;
import codecs.pdu.XrancPduBody;
import codecs.pdu.XrancPduHdr;

import java.io.UnsupportedEncodingException;

public class RRMConfStatus {
    XrancPdu pdu1;

    public static XrancPdu constructPacket(RRMConfig rrmConfig){
        RRMConfigStatus rrmConfigStatus = new RRMConfigStatus();
        rrmConfigStatus.setEcgi(rrmConfig.getEcgi());

        if(rrmConfig.getCrnti()!= null) {
            RRMConfigStatus.Crnti crntilist = new RRMConfigStatus.Crnti();
            crntilist.getCRNTI().add(rrmConfig.getCrnti().getCRNTI().get(0));

            rrmConfigStatus.setCrnti(crntilist);
        }
        RRMConfigStatus.Status st = new RRMConfigStatus.Status();
        try {
            //TODO: add Logic to have the background process to know the status of the packet.
            //RRMConfigStatus.Status st = new RRMConfigStatus.Status();
            //st.code = "SUCCESS".getBytes();
            st.getBerEnum().add(new BerEnum(1));
      /*  System.out.println("~~~~~~~~~~ new BerEnum(success.getBytes()) = "+ new BerEnum(1).toString());
        System.out.println("~~~~~~~~~~ st = "+ st.getBerEnum().get(0).toString());
        System.out.println("~~~~~~~~~~ st 2 = "+ st.toString());*/
            rrmConfigStatus.setStatus(st);
        }
        catch(Exception e){
            st.getBerEnum().add(new BerEnum(0));
        }
        XrancPdu pdu = new XrancPdu();

        XrancPduBody pBody = new XrancPduBody();
        pBody.setRRMConfigStatus(rrmConfigStatus);
        BerUTF8String ver = null;
        try{
            ver = new BerUTF8String("5");
        }
        catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(30);
        XrancPduHdr pduHdr = new XrancPduHdr();
        pduHdr.setApiId(apiID);
        pduHdr.setVer(ver);

        pdu.setBody(pBody);
        pdu.setHdr(pduHdr);
        return pdu;
    }
}

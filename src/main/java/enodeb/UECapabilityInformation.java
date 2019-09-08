package enodeb;

import codecs.api.CACap;
import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.pdu.*;
import codecs.ber.types.BerBoolean;
import codecs.ber.types.BerEnum;
import codecs.ber.types.BerInteger;
import codecs.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class UECapabilityInformation {

    public XrancPdu setPacketProperties(XrancPdu decoder) {
        CRNTI crnti = decoder.getBody().getUECapabilityEnquiry().getCrnti();
        ECGI ecgi = decoder.getBody().getUECapabilityEnquiry().getEcgi();
        CACap caCap = new CACap();
        caCap.setBand(new BerInteger(100));
        caCap.setCaclassdl(new BerEnum('a'));
        caCap.setCaclassul(new BerEnum('b'));
        caCap.setCrossCarrierSched(new BerBoolean(true));

        UECapabilityInfo capabilityInfo = new UECapabilityInfo();
        capabilityInfo.setCrnti(crnti);
        capabilityInfo.setEcgi(ecgi);
        capabilityInfo.setCaCap(caCap);

        XrancPduBody body = new XrancPduBody();
        body.setUECapabilityInfo(capabilityInfo);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(24);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) {
        UECapabilityInfo capabilityInfo = new UECapabilityInfo();
        capabilityInfo.setCrnti(crnti);
        capabilityInfo.setEcgi(ecgi);


        XrancPduBody body = new XrancPduBody();
        body.setUECapabilityInfo(capabilityInfo);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(25);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }
}

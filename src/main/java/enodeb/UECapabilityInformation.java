package enodeb;

import codecs.api.CACap;
import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.BerBoolean;
import org.openmuc.jasn1.ber.types.BerEnum;
import org.openmuc.jasn1.ber.types.BerInteger;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

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
        XrancApiID apiID = new XrancApiID(13);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) {
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
        XrancApiID apiID = new XrancApiID(13);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }
}

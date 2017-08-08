package samplemessages;

import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class UECapabilityEnq {

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) {
        UECapabilityEnquiry capabilityEnquiry = new UECapabilityEnquiry();
        capabilityEnquiry.setCrnti(crnti);
        capabilityEnquiry.setEcgi(ecgi);

        XrancPduBody body = new XrancPduBody();
        body.setUECapabilityEnquiry(capabilityEnquiry);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(12);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }

    public XrancPdu setPacketProperties(XrancPdu mainDecoder) {
        CRNTI crnti = mainDecoder.getBody().getUECapabilityEnquiry().getCrnti();

        //TODO: ECGI Value has to be read as CELL ID.
        ECGI ecgi = mainDecoder.getBody().getUECapabilityEnquiry().getEcgi();

        UECapabilityEnquiry capabilityEnquiry = new UECapabilityEnquiry();
        capabilityEnquiry.setCrnti(crnti);
        capabilityEnquiry.setEcgi(ecgi);

        XrancPduBody body = new XrancPduBody();
        body.setUECapabilityEnquiry(capabilityEnquiry);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(12);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }
}

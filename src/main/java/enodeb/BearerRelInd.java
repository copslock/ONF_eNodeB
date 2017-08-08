package enodeb;

import codecs.api.*;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.BerInteger;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class BearerRelInd {

    public XrancPdu setPacketProperties(XrancPdu decoder) {
        CRNTI crnti = decoder.getBody().getBearerAdmissionResponse().getCrnti();
        ECGI ecgi = decoder.getBody().getBearerAdmissionResponse().getEcgi();

        BerInteger numErabs = new BerInteger(2);


        BearerReleaseInd bearerReleaseInd = new BearerReleaseInd();
        bearerReleaseInd.setCrnti(crnti);
        bearerReleaseInd.setEcgi(ecgi);
        bearerReleaseInd.setNumErabs(numErabs);

        BearerReleaseInd.ErabIds erabIds = new BearerReleaseInd.ErabIds();
        bearerReleaseInd.setErabIds(erabIds);

        XrancPduBody body = new XrancPduBody();
        body.setBearerReleaseInd(bearerReleaseInd);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(11);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) {
        BerInteger numErabs = new BerInteger(2);


        BearerReleaseInd bearerReleaseInd = new BearerReleaseInd();
        bearerReleaseInd.setCrnti(crnti);
        bearerReleaseInd.setEcgi(ecgi);
        bearerReleaseInd.setNumErabs(numErabs);

        BearerReleaseInd.ErabIds erabIds = new BearerReleaseInd.ErabIds();
        bearerReleaseInd.setErabIds(erabIds);

        XrancPduBody body = new XrancPduBody();
        body.setBearerReleaseInd(bearerReleaseInd);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(11);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }
}

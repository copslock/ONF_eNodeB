package enodeb;

import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.api.RelCause;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class UERelInd {

    public XrancPdu setPacketProperties(XrancPdu decoder) {
        CRNTI crnti = decoder.getBody().getUEAdmissionRequest().getCrnti();
        ECGI ecgi = decoder.getBody().getUEAdmissionRequest().getEcgi();

        RelCause relCause = new RelCause(0);

        UEReleaseInd ueReleaseInd = new UEReleaseInd();
        ueReleaseInd.setCrnti(crnti);
        ueReleaseInd.setEcgi(ecgi);
        ueReleaseInd.setReleaseCause(relCause);

        XrancPduBody body = new XrancPduBody();
        body.setUEReleaseInd(ueReleaseInd);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(7);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) {
        RelCause relCause = new RelCause(0);

        UEReleaseInd ueReleaseInd = new UEReleaseInd();
        ueReleaseInd.setCrnti(crnti);
        ueReleaseInd.setEcgi(ecgi);
        ueReleaseInd.setReleaseCause(relCause);

        XrancPduBody body = new XrancPduBody();
        body.setUEReleaseInd(ueReleaseInd);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(7);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }
}

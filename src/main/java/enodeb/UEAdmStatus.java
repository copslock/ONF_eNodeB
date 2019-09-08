package enodeb;

import codecs.api.AdmEstStatus;
import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.pdu.*;
import codecs.ber.BerByteArrayOutputStream;
import codecs.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class UEAdmStatus {
    XrancPdu pdu;
    BerByteArrayOutputStream os;

    public UEAdmStatus() {
        os = new BerByteArrayOutputStream(4096);
    }

    /*public String encodeStatus(XrancPdu decoder) {
        pdu = setPacketProperties(decoder);
        try {
            pdu.encode(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DatatypeConverter.printHexBinary(os.getArray());
    }
*/
    public XrancPdu setPacketProperties(XrancPdu decoder) {
        CRNTI crnti = decoder.getBody().getUEAdmissionResponse().getCrnti();
        ECGI ecgi = decoder.getBody().getUEAdmissionResponse().getEcgi();
        int rrcConnectionComplete = 1;

        AdmEstStatus status;
        if (rrcConnectionComplete == 1) {
            status = new AdmEstStatus(0);
        } else {
            status = new AdmEstStatus(1);
        }
        UEAdmissionStatus admissionStatus = new UEAdmissionStatus();
        admissionStatus.setAdmEstStatus(status);
        admissionStatus.setCrnti(crnti);
        admissionStatus.setEcgi(ecgi);

        XrancPduBody body = new XrancPduBody();
        body.setUEAdmissionStatus(admissionStatus);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(4);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) {
        int rrcConnectionComplete = 1;

        AdmEstStatus status;
        if (rrcConnectionComplete == 1) {
            status = new AdmEstStatus(0);
        } else {
            status = new AdmEstStatus(1);
        }
        UEAdmissionStatus admissionStatus = new UEAdmissionStatus();
        admissionStatus.setAdmEstStatus(status);
        admissionStatus.setCrnti(crnti);
        admissionStatus.setEcgi(ecgi);

        XrancPduBody body = new XrancPduBody();
        body.setUEAdmissionStatus(admissionStatus);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(4);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }
}

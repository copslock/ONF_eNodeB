package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class UEAdmRequest {

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) throws UnsupportedEncodingException {
        AdmEstCause admEstCause = new AdmEstCause(0);

        UEAdmissionRequest admissionRequest = new UEAdmissionRequest();

        admissionRequest.setCrnti(crnti);
        admissionRequest.setEcgi(ecgi);
        admissionRequest.setAdmEstCause(admEstCause);

        XrancPduBody body = new XrancPduBody();
        body.setUEAdmissionRequest(admissionRequest);

        BerUTF8String ver = new BerUTF8String("4");

        XrancApiID apiID = new XrancApiID(2);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }

}
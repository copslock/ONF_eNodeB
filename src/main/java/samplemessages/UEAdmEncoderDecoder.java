package samplemessages;

import codecs.api.AdmEstResponse;
import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.pdu.*;
import codecs.ber.BerByteArrayOutputStream;
import codecs.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class UEAdmEncoderDecoder {

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) {
        AdmEstResponse response = new AdmEstResponse(0);

        UEAdmissionResponse ueAdmissionResponse = new UEAdmissionResponse();
        ueAdmissionResponse.setCrnti(crnti);
        ueAdmissionResponse.setEcgi(ecgi);
        ueAdmissionResponse.setAdmEstResponse(response);

        XrancPduBody body = new XrancPduBody();
        body.setUEAdmissionResponse(ueAdmissionResponse);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(3);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);
        return pdu;
    }

}

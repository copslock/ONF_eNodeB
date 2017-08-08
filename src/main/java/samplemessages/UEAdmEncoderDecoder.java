package samplemessages;

import codecs.api.AdmEstResponse;
import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class UEAdmEncoderDecoder {

    private int flag;
    private BerByteArrayOutputStream os;
    private XrancPdu pdu;

    public UEAdmEncoderDecoder() {
        os = new BerByteArrayOutputStream(4096);
    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) {
        AdmEstResponse response = new AdmEstResponse(1);

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

    public XrancPdu setPacketProperties(UEAdmissionRequest pdu_decoded) {
        /*XrancPdu pdu_decoded = new XrancPdu();
        byte[] bytearray = DatatypeConverter.parseHexBinary(decodedString);
        InputStream inputStream = new ByteArrayInputStream(bytearray);
        try {
            pdu_decoded.decode(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        CRNTI crnti = pdu_decoded.getCrnti();
        ECGI ecgi = pdu_decoded.getEcgi();

        AdmEstResponse response = new AdmEstResponse(1);
        /*if (flag == 1) {
            response = new AdmEstResponse(0);
        } else {
            response = new AdmEstResponse(1);
        }*/

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

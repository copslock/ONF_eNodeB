package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class UEAdmRequest {

    BerByteArrayOutputStream os;

    public UEAdmRequest() {
        os = new BerByteArrayOutputStream(4096);
    }

    /*public String encodeResponse() {
        pdu = setPacketProperties();
        try {
            pdu.encode(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DatatypeConverter.printHexBinary(os.getArray());
    }*/

    public XrancPdu setPacketProperties() throws UnsupportedEncodingException {
        CRNTI crnti = new CRNTI(new byte[]{(byte) 0x44, (byte) 0x44}, 16);

        PLMNIdentity plmnIdentity = new PLMNIdentity(new byte[]{(byte) 0x22, (byte) 0x08, (byte) 0x41});
        EUTRANCellIdentifier eutranCellIdentifier = new EUTRANCellIdentifier(new byte[]{
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xF0
        }, 28);

        ECGI ecgi = new ECGI();
        ecgi.setPLMNIdentity(plmnIdentity);
        ecgi.setEUTRANcellIdentifier(eutranCellIdentifier);

        AdmEstCause admEstCause = new AdmEstCause(4);

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

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) throws UnsupportedEncodingException {
        AdmEstCause admEstCause = new AdmEstCause(4);

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
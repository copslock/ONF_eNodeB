package enodeb;

import codecs.api.*;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.BerInteger;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class BearerAdmStatus {
    public XrancPdu setPacketProperties(XrancPdu decoder) {
        CRNTI crnti = decoder.getBody().getBearerAdmissionResponse().getCrnti();
        ECGI ecgi = decoder.getBody().getBearerAdmissionResponse().getEcgi();

        //TODO: Verify mainDecoder.getBody().getBearerAdmissionRequest().getNumErabs() or
        BerInteger numErabs = new BerInteger(2);

        ERABResponseItem responseItem = new ERABResponseItem();
        responseItem.setId(new ERABID(10));
        responseItem.setDecision(new ERABDecision(0));

        ERABResponseItem responseItem1 = new ERABResponseItem();
        responseItem1.setId(new ERABID(2));
        responseItem1.setDecision(new ERABDecision(0));

        ERABResponse erabResponse = new ERABResponse();
        erabResponse.setERABResponse(responseItem);
        erabResponse.setERABResponse(responseItem1);

        BearerAdmissionResponse bearerAdmissionResponse = new BearerAdmissionResponse();
        bearerAdmissionResponse.setCrnti(crnti);
        bearerAdmissionResponse.setEcgi(ecgi);
        bearerAdmissionResponse.setErabResponse(erabResponse);
        bearerAdmissionResponse.setNumErabList(numErabs);

        XrancPduBody body = new XrancPduBody();
        body.setBearerAdmissionResponse(bearerAdmissionResponse);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(10);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;


    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) {
        //TODO: Verify mainDecoder.getBody().getBearerAdmissionRequest().getNumErabs() or
        BerInteger numErabs = new BerInteger(2);

        ERABResponseItem responseItem = new ERABResponseItem();
        responseItem.setId(new ERABID(10));
        responseItem.setDecision(new ERABDecision(0));

        ERABResponseItem responseItem1 = new ERABResponseItem();
        responseItem1.setId(new ERABID(2));
        responseItem1.setDecision(new ERABDecision(0));

        ERABResponse erabResponse = new ERABResponse();
        erabResponse.setERABResponse(responseItem);
        erabResponse.setERABResponse(responseItem1);

        BearerAdmissionResponse bearerAdmissionResponse = new BearerAdmissionResponse();
        bearerAdmissionResponse.setCrnti(crnti);
        bearerAdmissionResponse.setEcgi(ecgi);
        bearerAdmissionResponse.setErabResponse(erabResponse);
        bearerAdmissionResponse.setNumErabList(numErabs);

        XrancPduBody body = new XrancPduBody();
        body.setBearerAdmissionResponse(bearerAdmissionResponse);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("2");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(9);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;


    }
}

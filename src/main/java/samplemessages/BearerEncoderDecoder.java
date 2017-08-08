package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.BerInteger;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class BearerEncoderDecoder {

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti, ERABParams erabParams, BerInteger numParams) {
        ERABResponse erabResponse = new ERABResponse();

        for (int i = 0; i < numParams.intValue(); i++) {
            ERABParamsItem erabParamsItem = erabParams.getERABParamsItem().get(i);

            ERABResponseItem responseItem = new ERABResponseItem();
            responseItem.setId(erabParamsItem.getId());

            // FIXME: add logic
            responseItem.setDecision(new ERABDecision(0));

            erabResponse.setERABResponse(responseItem);
        }


        BearerAdmissionResponse bearerAdmissionResponse = new BearerAdmissionResponse();
        bearerAdmissionResponse.setCrnti(crnti);
        bearerAdmissionResponse.setEcgi(ecgi);
        bearerAdmissionResponse.setErabResponse(erabResponse);
        bearerAdmissionResponse.setNumErabList(numParams);

        XrancPduBody body = new XrancPduBody();
        body.setBearerAdmissionResponse(bearerAdmissionResponse);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("2.0");
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

    public XrancPdu setPacketProperties(XrancPdu mainDecoder) {
        CRNTI crnti = mainDecoder.getBody().getBearerAdmissionRequest().getCrnti();
        ECGI ecgi = mainDecoder.getBody().getBearerAdmissionRequest().getEcgi();

        //TODO: Verify mainDecoder.getBody().getBearerAdmissionRequest().getNumErabs() or
        BerInteger numErabs = new BerInteger(5);

        ERABResponseItem responseItem = new ERABResponseItem();
        responseItem.setId(new ERABID(1));
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
            ver = new BerUTF8String("2.0");
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

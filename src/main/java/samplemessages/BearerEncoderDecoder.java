package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import codecs.ber.types.BerInteger;
import codecs.ber.types.string.BerUTF8String;

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
            ver = new BerUTF8String("4");
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

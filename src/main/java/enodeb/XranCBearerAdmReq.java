package enodeb;

import codecs.pdu.*;import codecs.api.*;
import org.openmuc.jasn1.ber.types.BerInteger;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class XranCBearerAdmReq {

    public XrancPdu setPacketProperties(XrancPdu decoder) throws UnsupportedEncodingException {
        CRNTI crnti = decoder.getBody().getUEAdmissionResponse().getCrnti();
        ECGI ecgi = decoder.getBody().getUEAdmissionResponse().getEcgi();

        UEAMBR ueambr = new UEAMBR();
        ueambr.setAmbrDl(new BitRate(100));
        ueambr.setAmbrUl(new BitRate(100));

        BerInteger numerabs = new BerInteger(2);

        ERABParams erabParams = new ERABParams();
        ERABParamsItem erabParamsItem = new ERABParamsItem();
        erabParamsItem.setId(new ERABID(1));
        erabParamsItem.setDirection(new ERABDirection(0));
        erabParamsItem.setType(new ERABType(1));
        erabParamsItem.setQci(new QCI(8));
        erabParamsItem.setArp(new BerInteger(5));
        erabParamsItem.setGbrDl(new BitRate(100));
        erabParamsItem.setGbrUl(new BitRate(200));
        erabParamsItem.setMbrDl(new BitRate(300));
        erabParamsItem.setMbrUl(new BitRate(400));

        ERABParamsItem erabParamsItem1 = new ERABParamsItem();
        erabParamsItem1.setId(new ERABID(2));
        erabParamsItem1.setDirection(new ERABDirection(0));
        erabParamsItem1.setType(new ERABType(1));
        erabParamsItem1.setQci(new QCI(8));
        erabParamsItem1.setArp(new BerInteger(5));
        erabParamsItem1.setGbrDl(new BitRate(100));
        erabParamsItem1.setGbrUl(new BitRate(200));
        erabParamsItem1.setMbrDl(new BitRate(300));
        erabParamsItem1.setMbrUl(new BitRate(400));

        erabParams.addERABParamsItem(erabParamsItem);
        erabParams.addERABParamsItem(erabParamsItem1);

        BearerAdmissionRequest bearerAdmissionRequest = new BearerAdmissionRequest();
        bearerAdmissionRequest.setCrnti(crnti);
        bearerAdmissionRequest.setEcgi(ecgi);
        bearerAdmissionRequest.setErabParams(erabParams);
        bearerAdmissionRequest.setNumErabs(numerabs);
        bearerAdmissionRequest.setUeAmbr(ueambr);

        XrancPduBody body = new XrancPduBody();
        body.setBearerAdmissionRequest(bearerAdmissionRequest);

        BerUTF8String ver = new BerUTF8String("2.0");

        XrancApiID apiID = new XrancApiID(8);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;

    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) throws UnsupportedEncodingException {
        UEAMBR ueambr = new UEAMBR();
        ueambr.setAmbrDl(new BitRate(100));
        ueambr.setAmbrUl(new BitRate(100));

        BerInteger numerabs = new BerInteger(2);

        ERABParams erabParams = new ERABParams();
        ERABParamsItem erabParamsItem = new ERABParamsItem();
        erabParamsItem.setId(new ERABID(1));
        erabParamsItem.setDirection(new ERABDirection(0));
        erabParamsItem.setType(new ERABType(1));
        erabParamsItem.setQci(new QCI(8));
        erabParamsItem.setArp(new BerInteger(5));
        erabParamsItem.setGbrDl(new BitRate(100));
        erabParamsItem.setGbrUl(new BitRate(200));
        erabParamsItem.setMbrDl(new BitRate(300));
        erabParamsItem.setMbrUl(new BitRate(400));

        ERABParamsItem erabParamsItem1 = new ERABParamsItem();
        erabParamsItem1.setId(new ERABID(2));
        erabParamsItem1.setDirection(new ERABDirection(0));
        erabParamsItem1.setType(new ERABType(1));
        erabParamsItem1.setQci(new QCI(8));
        erabParamsItem1.setArp(new BerInteger(5));
        erabParamsItem1.setGbrDl(new BitRate(100));
        erabParamsItem1.setGbrUl(new BitRate(200));
        erabParamsItem1.setMbrDl(new BitRate(300));
        erabParamsItem1.setMbrUl(new BitRate(400));

        erabParams.addERABParamsItem(erabParamsItem);
        erabParams.addERABParamsItem(erabParamsItem1);

        BearerAdmissionRequest bearerAdmissionRequest = new BearerAdmissionRequest();
        bearerAdmissionRequest.setCrnti(crnti);
        bearerAdmissionRequest.setEcgi(ecgi);
        bearerAdmissionRequest.setErabParams(erabParams);
        bearerAdmissionRequest.setNumErabs(numerabs);
        bearerAdmissionRequest.setUeAmbr(ueambr);

        XrancPduBody body = new XrancPduBody();
        body.setBearerAdmissionRequest(bearerAdmissionRequest);

        BerUTF8String ver = new BerUTF8String("2.0");

        XrancApiID apiID = new XrancApiID(8);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;

    }

}

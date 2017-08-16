package enodeb;

import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.api.ENBUES1APID;
import codecs.api.MMEUES1APID;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class UECtxUpdate {

    public XrancPdu setPacketProperties(XrancPdu decoder) throws UnsupportedEncodingException {
        CRNTI crnti = decoder.getBody().getUEAdmissionResponse().getCrnti();
        ECGI ecgi = decoder.getBody().getUEAdmissionResponse().getEcgi();

        MMEUES1APID mmeues1APID = new MMEUES1APID(304549940);
        ENBUES1APID enbues1APID = new ENBUES1APID(264145);

        UEContextUpdate attachComplete = new UEContextUpdate();
        attachComplete.setCrnti(crnti);
        attachComplete.setEcgi(ecgi);
        attachComplete.setMMEUES1APID(mmeues1APID);
        attachComplete.setENBUES1APID(enbues1APID);

        XrancPduBody body = new XrancPduBody();
        body.setUEContextUpdate(attachComplete);

        BerUTF8String ver = new BerUTF8String("4");

        XrancApiID apiID = new XrancApiID(5);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;

    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti, MMEUES1APID mmeues1APID, ENBUES1APID enbues1APID) throws UnsupportedEncodingException {
        UEContextUpdate ueContextUpdate = new UEContextUpdate();

        ueContextUpdate.setCrnti(crnti);
        ueContextUpdate.setEcgi(ecgi);
        ueContextUpdate.setMMEUES1APID(mmeues1APID);
        ueContextUpdate.setENBUES1APID(enbues1APID);

        XrancPduBody body = new XrancPduBody();
        body.setUEContextUpdate(ueContextUpdate);

        BerUTF8String ver = new BerUTF8String("4");

        XrancApiID apiID = new XrancApiID(5);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;

    }
}

package enodeb;

import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.api.ReconfIndReason;
import codecs.pdu.*;
import codecs.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class UEReconfInd {
    public XrancPdu setPacketProperties(XrancPdu decoder) throws UnsupportedEncodingException {
        CRNTI crntiOld = decoder.getBody().getUEAdmissionResponse().getCrnti();
        ECGI ecgi = decoder.getBody().getUEAdmissionResponse().getEcgi();
        CRNTI crntiNew = new CRNTI(new byte[]{(byte) 0x44, (byte) 0x43}, 16);

        UEReconfigInd ueReconfigInd = new UEReconfigInd();
        ueReconfigInd.setCrntiOld(crntiOld);
        ueReconfigInd.setEcgi(ecgi);
        ueReconfigInd.setCrntiNew(crntiNew);

        //TODO: need to set cause here
        ReconfIndReason reason = new ReconfIndReason(1);
        ueReconfigInd.setReconfigCause(reason);

        XrancPduBody body = new XrancPduBody();
        body.setUEReconfigInd(ueReconfigInd);

        BerUTF8String ver = new BerUTF8String("4");

        XrancApiID apiID = new XrancApiID(6);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;

    }

}

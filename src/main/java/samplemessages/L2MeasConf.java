package samplemessages;

import codecs.api.ECGI;
import codecs.api.EUTRANCellIdentifier;
import codecs.api.PLMNIdentity;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.BerInteger;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class L2MeasConf {


    public static XrancPdu constructPacket(ECGI ecgi, int l2MeasInterval) {

        BerInteger reportIntervalMS = new BerInteger(l2MeasInterval);
        L2MeasConfig measConfig = new L2MeasConfig();
        measConfig.setEcgi(ecgi);
        measConfig.setReportIntervalMs(reportIntervalMS);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("2a");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        XrancApiID apiID = new XrancApiID(19);
        XrancPduBody body = new XrancPduBody();
        body.setL2MeasConfig(measConfig);

        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setBody(body);
        pdu.setHdr(hdr);

        return pdu;

    }

    public XrancPdu setPacketProperties() {
        PLMNIdentity plmnIdentity = new PLMNIdentity(new byte[]{(byte) 0x22, (byte) 0x08, (byte) 0x41});
        EUTRANCellIdentifier eutranCellIdentifier = new EUTRANCellIdentifier(new byte[]{
                (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xF0
        }, 28);

        ECGI ecgi = new ECGI();
        ecgi.setPLMNIdentity(plmnIdentity);
        ecgi.setEUTRANcellIdentifier(eutranCellIdentifier);

        BerInteger reportIntervalMS = new BerInteger(10);
        L2MeasConfig measConfig = new L2MeasConfig();
        measConfig.setEcgi(ecgi);
        measConfig.setReportIntervalMs(reportIntervalMS);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("2a");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        XrancApiID apiID = new XrancApiID(19);
        XrancPduBody body = new XrancPduBody();
        body.setL2MeasConfig(measConfig);

        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setBody(body);
        pdu.setHdr(hdr);

        return pdu;
    }
}

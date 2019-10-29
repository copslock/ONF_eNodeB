package samplemessages;

import codecs.api.ECGI;
import codecs.api.EUTRANCellIdentifier;
import codecs.api.PLMNIdentity;
import codecs.pdu.*;
import codecs.ber.types.BerInteger;
import codecs.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class L2MeasConf {


    public static XrancPdu constructPacket(ECGI ecgi, int l2MeasInterval) {

        BerInteger reportIntervalMS = new BerInteger(l2MeasInterval);
        L2MeasConfig measConfig = new L2MeasConfig();
        measConfig.setEcgi(ecgi);
        measConfig.setReportIntervalMs(reportIntervalMS);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
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

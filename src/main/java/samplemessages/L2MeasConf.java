package samplemessages;

import codecs.api.ECGI;
import codecs.api.EUTRANCellIdentifier;
import codecs.api.PLMNIdentity;
import codecs.api.L2ReportInterval;
import codecs.api.L2MeasReportInterval;
import codecs.pdu.*;
import codecs.ber.types.BerInteger;
import codecs.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class L2MeasConf {


    public static XrancPdu constructPacket(ECGI ecgi, int l2MeasInterval) {

        BerInteger reportIntervalMS = new BerInteger(l2MeasInterval);
        L2MeasConfig measConfig = new L2MeasConfig();
        measConfig.setEcgi(ecgi);

        L2ReportInterval l2ReportInterval = new L2ReportInterval();
        L2MeasReportInterval l2MeasReportInterval = new L2MeasReportInterval(l2MeasInterval);
        l2ReportInterval.setTPdcpMeasReportPerUe(l2MeasReportInterval);
        l2ReportInterval.setTRadioMeasReportPerCell(l2MeasReportInterval);
        l2ReportInterval.setTRadioMeasReportPerUe(l2MeasReportInterval);
        l2ReportInterval.setTRadioSchedReportPerUe(l2MeasReportInterval);
        l2ReportInterval.setTRadioSchedReportPerCell(l2MeasReportInterval);

        measConfig.setReportIntervals(l2ReportInterval);

        // Shubham: removing this to set the reportintervals
        // measConfig.setReportIntervalMs(reportIntervalMS);

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

package samplemessages;

import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.api.QCI;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.BerInteger;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class PDCPReportPerUE {

    public XrancPdu setPacketProperties(XrancPdu recv_pdu) {
        ECGI ecgi = recv_pdu.getBody().getL2MeasConfig().getEcgi();

        //Need to get this from UE.
        CRNTI crnti = new CRNTI(new byte[]{(byte) 0x44, (byte) 0x44}, 16);

        PDCPMeasReportPerUe.QciVals qciVals = new PDCPMeasReportPerUe.QciVals();
        qciVals.setQCI(new QCI(1));
        qciVals.setQCI(new QCI(2));

        PDCPMeasReportPerUe.DataVolDl dataVolDl = new PDCPMeasReportPerUe.DataVolDl();
        dataVolDl.setBerInteger(new BerInteger(500));
        dataVolDl.setBerInteger(new BerInteger(500));

        PDCPMeasReportPerUe.DataVolUl dataVolUl = new PDCPMeasReportPerUe.DataVolUl();
        dataVolUl.setBerInteger(new BerInteger(500));
        dataVolUl.setBerInteger(new BerInteger(500));

        PDCPMeasReportPerUe.PktDelayDl pktDelayDl = new PDCPMeasReportPerUe.PktDelayDl();
        pktDelayDl.setBerInteger(new BerInteger(314));
        pktDelayDl.setBerInteger(new BerInteger(314));

        PDCPMeasReportPerUe.PktDelayUl pktDelayUl = new PDCPMeasReportPerUe.PktDelayUl();
        pktDelayUl.setBerInteger(new BerInteger(314));
        pktDelayUl.setBerInteger(new BerInteger(314));

        PDCPMeasReportPerUe.PktDiscardRateDl pktDiscardRateDl = new PDCPMeasReportPerUe.PktDiscardRateDl();
        pktDiscardRateDl.setBerInteger(new BerInteger(10));
        pktDiscardRateDl.setBerInteger(new BerInteger(5));

        PDCPMeasReportPerUe.PktLossRateDl pktLossRateDl = new PDCPMeasReportPerUe.PktLossRateDl();
        pktLossRateDl.setBerInteger(new BerInteger(5));
        pktLossRateDl.setBerInteger(new BerInteger(10));

        PDCPMeasReportPerUe.PktLossRateUl pktLossRateUl = new PDCPMeasReportPerUe.PktLossRateUl();
        pktLossRateUl.setBerInteger(new BerInteger(8));
        pktLossRateUl.setBerInteger(new BerInteger(2));

        PDCPMeasReportPerUe.ThroughputDl throughputDl = new PDCPMeasReportPerUe.ThroughputDl();
        throughputDl.setBerInteger(new BerInteger(500));
        throughputDl.setBerInteger(new BerInteger(500));

        PDCPMeasReportPerUe.ThroughputUl throughputUl = new PDCPMeasReportPerUe.ThroughputUl();
        throughputUl.setBerInteger(new BerInteger(500));
        throughputUl.setBerInteger(new BerInteger(500));

        PDCPMeasReportPerUe pdcpMeasReportPerUe = new PDCPMeasReportPerUe();
        pdcpMeasReportPerUe.setCrnti(crnti);
        pdcpMeasReportPerUe.setEcgi(ecgi);
        pdcpMeasReportPerUe.setQciVals(qciVals);
        pdcpMeasReportPerUe.setDataVolDl(dataVolDl);
        pdcpMeasReportPerUe.setDataVolUl(dataVolUl);
        pdcpMeasReportPerUe.setPktDelayDl(pktDelayDl);
        pdcpMeasReportPerUe.setPktDelayUl(pktDelayUl);
        pdcpMeasReportPerUe.setPktDiscardRateDl(pktDiscardRateDl);
        pdcpMeasReportPerUe.setPktLossRateDl(pktLossRateDl);
        pdcpMeasReportPerUe.setPktLossRateUl(pktLossRateUl);
        pdcpMeasReportPerUe.setThroughputDl(throughputDl);
        pdcpMeasReportPerUe.setThroughputUl(throughputUl);

        XrancPduBody body = new XrancPduBody();
        body.setPDCPMeasReportPerUe(pdcpMeasReportPerUe);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        XrancApiID apiID = new XrancApiID(24);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;

    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti) {

        PDCPMeasReportPerUe.QciVals qciVals = new PDCPMeasReportPerUe.QciVals();
        qciVals.setQCI(new QCI(1));
        qciVals.setQCI(new QCI(2));

        PDCPMeasReportPerUe.DataVolDl dataVolDl = new PDCPMeasReportPerUe.DataVolDl();
        dataVolDl.setBerInteger(new BerInteger(500));
        dataVolDl.setBerInteger(new BerInteger(500));

        PDCPMeasReportPerUe.DataVolUl dataVolUl = new PDCPMeasReportPerUe.DataVolUl();
        dataVolUl.setBerInteger(new BerInteger(500));
        dataVolUl.setBerInteger(new BerInteger(500));

        PDCPMeasReportPerUe.PktDelayDl pktDelayDl = new PDCPMeasReportPerUe.PktDelayDl();
        pktDelayDl.setBerInteger(new BerInteger(314));
        pktDelayDl.setBerInteger(new BerInteger(314));

        PDCPMeasReportPerUe.PktDelayUl pktDelayUl = new PDCPMeasReportPerUe.PktDelayUl();
        pktDelayUl.setBerInteger(new BerInteger(314));
        pktDelayUl.setBerInteger(new BerInteger(314));

        PDCPMeasReportPerUe.PktDiscardRateDl pktDiscardRateDl = new PDCPMeasReportPerUe.PktDiscardRateDl();
        pktDiscardRateDl.setBerInteger(new BerInteger(10));
        pktDiscardRateDl.setBerInteger(new BerInteger(5));

        PDCPMeasReportPerUe.PktLossRateDl pktLossRateDl = new PDCPMeasReportPerUe.PktLossRateDl();
        pktLossRateDl.setBerInteger(new BerInteger(5));
        pktLossRateDl.setBerInteger(new BerInteger(10));

        PDCPMeasReportPerUe.PktLossRateUl pktLossRateUl = new PDCPMeasReportPerUe.PktLossRateUl();
        pktLossRateUl.setBerInteger(new BerInteger(8));
        pktLossRateUl.setBerInteger(new BerInteger(2));

        PDCPMeasReportPerUe.ThroughputDl throughputDl = new PDCPMeasReportPerUe.ThroughputDl();
        throughputDl.setBerInteger(new BerInteger(500));
        throughputDl.setBerInteger(new BerInteger(500));

        PDCPMeasReportPerUe.ThroughputUl throughputUl = new PDCPMeasReportPerUe.ThroughputUl();
        throughputUl.setBerInteger(new BerInteger(500));
        throughputUl.setBerInteger(new BerInteger(500));

        PDCPMeasReportPerUe pdcpMeasReportPerUe = new PDCPMeasReportPerUe();
        pdcpMeasReportPerUe.setCrnti(crnti);
        pdcpMeasReportPerUe.setEcgi(ecgi);
        pdcpMeasReportPerUe.setQciVals(qciVals);
        pdcpMeasReportPerUe.setDataVolDl(dataVolDl);
        pdcpMeasReportPerUe.setDataVolUl(dataVolUl);
        pdcpMeasReportPerUe.setPktDelayDl(pktDelayDl);
        pdcpMeasReportPerUe.setPktDelayUl(pktDelayUl);
        pdcpMeasReportPerUe.setPktDiscardRateDl(pktDiscardRateDl);
        pdcpMeasReportPerUe.setPktLossRateDl(pktLossRateDl);
        pdcpMeasReportPerUe.setPktLossRateUl(pktLossRateUl);
        pdcpMeasReportPerUe.setThroughputDl(throughputDl);
        pdcpMeasReportPerUe.setThroughputUl(throughputUl);

        XrancPduBody body = new XrancPduBody();
        body.setPDCPMeasReportPerUe(pdcpMeasReportPerUe);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        XrancApiID apiID = new XrancApiID(24);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;

    }
}

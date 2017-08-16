package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RXSigMeasRep {

    public XrancPdu setPacketProperties(XrancPdu decoder) {
        CRNTI crnti = decoder.getBody().getRXSigMeasConfig().getCrnti();
        ECGI ecgi = decoder.getBody().getRXSigMeasConfig().getEcgi();

        RXSigMeasReport.CellMeasReports cellMeasReports = new RXSigMeasReport.CellMeasReports();
        RXSigReport rxSigReport = new RXSigReport();
        PCIARFCN pciarfcn = new PCIARFCN();
        pciarfcn.setPci(new PhysCellId(500));
        pciarfcn.setEarfcnDl(new ARFCNValue(2100));
        rxSigReport.setPciArfcn(pciarfcn);
        rxSigReport.setRsrp(new RSRPRange(4));
        rxSigReport.setRsrq(new RSRQRange(5));
        cellMeasReports.setRXSigReport(rxSigReport);

        RXSigReport rxSigReport1 = new RXSigReport();
        PCIARFCN pciarfcn1 = new PCIARFCN();
        pciarfcn1.setPci(new PhysCellId(500));
        pciarfcn1.setEarfcnDl(new ARFCNValue(2100));
        rxSigReport1.setPciArfcn(pciarfcn);
        rxSigReport1.setRsrp(new RSRPRange(4));
        rxSigReport1.setRsrq(new RSRQRange(5));
        cellMeasReports.setRXSigReport(rxSigReport1);

        RXSigMeasReport rxSigMeasReport = new RXSigMeasReport();
        rxSigMeasReport.setCrnti(crnti);
        rxSigMeasReport.setEcgi(ecgi);
        rxSigMeasReport.setCellMeasReports(cellMeasReports);

        XrancPduBody body = new XrancPduBody();
        body.setRXSigMeasReport(rxSigMeasReport);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        XrancApiID apiID = new XrancApiID(18);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;

    }

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti, RXSigMeasConfig.MeasCells cellMeas) {

        RXSigMeasReport.CellMeasReports cellMeasReports = new RXSigMeasReport.CellMeasReports();

        cellMeas.getPCIARFCN().forEach(pciafrcn -> {
            RXSigReport rxSigReport = new RXSigReport();
            rxSigReport.setPciArfcn(pciafrcn);
            rxSigReport.setRsrp(new RSRPRange(100));
            rxSigReport.setRsrq(new RSRQRange(100));

            cellMeasReports.setRXSigReport(rxSigReport);
        });

        RXSigMeasReport rxSigMeasReport = new RXSigMeasReport();
        rxSigMeasReport.setCrnti(crnti);
        rxSigMeasReport.setEcgi(ecgi);
        rxSigMeasReport.setCellMeasReports(cellMeasReports);

        XrancPduBody body = new XrancPduBody();
        body.setRXSigMeasReport(rxSigMeasReport);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        XrancApiID apiID = new XrancApiID(16);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;

    }
}

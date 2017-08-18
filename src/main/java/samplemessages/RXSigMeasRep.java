package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class RXSigMeasRep {

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

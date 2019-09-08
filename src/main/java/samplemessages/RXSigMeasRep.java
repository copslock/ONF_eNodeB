package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import codecs.ber.types.string.BerUTF8String;
import codecs.pdu.RRCMeasConfig.Crnti;
import codecs.api.MeasObject.MeasCells;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class RXSigMeasRep {

    public static XrancPdu constructPacket(ECGI ecgi, Crnti crnti,
                                           RRCMeasConfig.MeasObjects measObjects) {


        RXSigMeasReport.CellMeasReports cellMeasReports = new RXSigMeasReport.CellMeasReports();
        //cellMeas.getMeasCells().getPCIARFCN()//getPCIARFCN
        //cellMeas.getPCIARFCN().forEach(pciafrcn -> {
        RXSigMeasReport.CellMeasReports.SEQUENCEOF sequenceof = new RXSigMeasReport.CellMeasReports.SEQUENCEOF();
        measObjects.getMeasObject().forEach(measObject -> {

            PCIARFCN pciarfcn = new PCIARFCN();
            pciarfcn.setEarfcnDl(measObject.getDlFreq());
            pciarfcn.setPci(measObject.getMeasCells().getPci());

            RXSigReport rxSigReport = new RXSigReport();
            //rxSigReport.setEcgi(ecgi);
            rxSigReport.setPciArfcn(pciarfcn);
            rxSigReport.setRsrp(new RSRPRange(100));
            rxSigReport.setRsrq(new RSRQRange(100));
            // :FIX Me setMeasId is not present here which has to be set

            //setCellMeasReports
            sequenceof.getRXSigReport().add(rxSigReport);
            cellMeasReports.getSEQUENCEOF().add(sequenceof);//.getRXSigReport().add(rxSigReport);

            //cellMeasReports.setRXSigReport(rxSigReport);
            });

        RXSigMeasReport rxSigMeasReport = new RXSigMeasReport();

        RXSigMeasReport.Crnti crntilist = new RXSigMeasReport.Crnti();
        crntilist.getCRNTI().add(crnti.getCRNTI().get(0));

        rxSigMeasReport.setCrnti(crntilist);
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

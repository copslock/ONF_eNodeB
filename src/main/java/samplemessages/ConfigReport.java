package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import codecs.ber.BerByteArrayOutputStream;
import codecs.ber.types.BerBoolean;
import codecs.ber.types.BerInteger;
import codecs.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class ConfigReport {

    BerByteArrayOutputStream os;

    public ConfigReport() {
        os = new BerByteArrayOutputStream(4096);
    }

    /*public String encodeResponse(XrancPdu decoder) {
        pdu = setPacketProperties(decoder);
        try {
            pdu.encode(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DatatypeConverter.printHexBinary(os.getArray());
    }*/

    public static XrancPdu constructPacket(ECGI ecgi, PCIARFCN pciarfcn) throws UnsupportedEncodingException {

        CandScell candScell = new CandScell();
        candScell.setPci(pciarfcn.getPci());
        candScell.setEarfcnDl(pciarfcn.getEarfcnDl());

        CellConfigReport.CandScells candScells = new CellConfigReport.CandScells();
        candScells.setCandScells(candScell);

        ARFCNValue earfcn_ul = new ARFCNValue(1900);

        BerInteger rbs_per_tti_dl = new BerInteger(40);
        BerInteger rbs_per_tti_ul = new BerInteger(30);
        BerInteger num_tx_antenna = new BerInteger(2);
        DuplexMode duplexMode = new DuplexMode(1);
        BerInteger max_num_connected_ues = new BerInteger(1000);
        BerInteger max_num_connected_bearers = new BerInteger(2000);
        BerInteger max_num_ues_sched_per_tti_dl = new BerInteger(10);
        BerInteger max_num_ues_sched_per_tti_ul = new BerInteger(10);
        BerBoolean dlfs_sched_enable = new BerBoolean(true);

        CellConfigReport cellConfigReport = new CellConfigReport();
        cellConfigReport.setEcgi(ecgi);
        cellConfigReport.setPci(pciarfcn.getPci());
        cellConfigReport.setCandScells(candScells);
        cellConfigReport.setEarfcnDl(pciarfcn.getEarfcnDl());
        cellConfigReport.setEarfcnUl(earfcn_ul);
        cellConfigReport.setRbsPerTtiDl(rbs_per_tti_dl);
        cellConfigReport.setRbsPerTtiUl(rbs_per_tti_ul);
        cellConfigReport.setNumTxAntenna(num_tx_antenna);
        cellConfigReport.setDuplexMode(duplexMode);
        cellConfigReport.setMaxNumConnectedUes(max_num_connected_ues);
        cellConfigReport.setMaxNumConnectedBearers(max_num_connected_bearers);
        cellConfigReport.setMaxNumUesSchedPerTtiDl(max_num_ues_sched_per_tti_dl);
        cellConfigReport.setMaxNumUesSchedPerTtiUl(max_num_ues_sched_per_tti_ul);
        cellConfigReport.setDlfsSchedEnable(dlfs_sched_enable);

        BerUTF8String ver = new BerUTF8String("2");

        XrancApiID apiID = new XrancApiID(1);
        XrancPduBody body = new XrancPduBody();
        body.setCellConfigReport(cellConfigReport);

        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setBody(body);
        pdu.setHdr(hdr);

        return pdu;
    }
}

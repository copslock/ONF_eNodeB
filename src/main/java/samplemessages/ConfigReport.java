package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import org.openmuc.jasn1.ber.types.BerBoolean;
import org.openmuc.jasn1.ber.types.BerInteger;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

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

    public XrancPdu setPacketProperties(XrancPdu decoder) throws UnsupportedEncodingException {

        ECGI ecgi = decoder.getBody().getCellConfigRequest().getEcgi();

        PhysCellId physCellId = new PhysCellId(420);

        PhysCellId physCellId1 = new PhysCellId(500);
        ARFCNValue earfcn_dl = new ARFCNValue(2100);
        CandScell candScell = new CandScell();
        candScell.setPci(physCellId1);
        candScell.setEarfcnDl(earfcn_dl);

        PhysCellId physCellId2 = new PhysCellId(400);
        ARFCNValue earfcn_dl1 = new ARFCNValue(2300);
        CandScell candScell1 = new CandScell();
        candScell1.setPci(physCellId2);
        candScell1.setEarfcnDl(earfcn_dl1);

        CellConfigReport.CandScells candScells = new CellConfigReport.CandScells();
        candScells.setCandScells(candScell);
        candScells.setCandScells(candScell1);

        ARFCNValue earfcn_dl2 = new ARFCNValue(2000);

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
        cellConfigReport.setPci(physCellId);
        cellConfigReport.setCandScells(candScells);
        cellConfigReport.setEarfcnDl(earfcn_dl2);
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

        BerUTF8String ver = new BerUTF8String("2a");

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

    public static XrancPdu constructPacket(ECGI ecgi, PCIARFCN pciarfcn) throws UnsupportedEncodingException {

        PhysCellId physCellId1 = new PhysCellId(500);
        ARFCNValue earfcn_dl = new ARFCNValue(2100);
        CandScell candScell = new CandScell();
        candScell.setPci(physCellId1);
        candScell.setEarfcnDl(earfcn_dl);

        PhysCellId physCellId2 = new PhysCellId(400);
        ARFCNValue earfcn_dl1 = new ARFCNValue(2300);
        CandScell candScell1 = new CandScell();
        candScell1.setPci(physCellId2);
        candScell1.setEarfcnDl(earfcn_dl1);

        CellConfigReport.CandScells candScells = new CellConfigReport.CandScells();
        candScells.setCandScells(candScell);
        candScells.setCandScells(candScell1);

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

        BerUTF8String ver = new BerUTF8String("2a");

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

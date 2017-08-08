package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class SignalMeasConfig {
    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti, RXSigMeasConfig.MeasCells measCells, int interval) {
        RXSigRepQty rxSigRepQty = new RXSigRepQty(2);
        RXSigMeasRepInterval repInterval = new RXSigMeasRepInterval(interval);

        RXSigMeasConfig sigMeasConfig = new RXSigMeasConfig();
        sigMeasConfig.setCrnti(crnti);
        sigMeasConfig.setEcgi(ecgi);
        sigMeasConfig.setReportQty(rxSigRepQty);
        sigMeasConfig.setMeasCells(measCells);
        sigMeasConfig.setReportInterval(repInterval);

        XrancPduBody body = new XrancPduBody();
        body.setRXSigMeasConfig(sigMeasConfig);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(17);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }

    public XrancPdu setPacketProperties() {
        CRNTI crnti = new CRNTI(new byte[]{(byte) 0x44, (byte) 0x44});

        PLMNIdentity plmnIdentity = new PLMNIdentity(new byte[]{(byte) 0xFC, (byte) 0xFF, (byte) 0x3F});
        EUTRANCellIdentifier eutranCellIdentifier = new EUTRANCellIdentifier(new byte[]{
                (byte) 0xCF, (byte) 0xFE, (byte) 0x7C, (byte) 0xF0
        }, 28);

        ECGI ecgi = new ECGI();
        ecgi.setPLMNIdentity(plmnIdentity);
        ecgi.setEUTRANcellIdentifier(eutranCellIdentifier);

        RXSigRepQty rxSigRepQty = new RXSigRepQty(2);
        PCIARFCN pciarfcn = new PCIARFCN();
        pciarfcn.setPci(new PhysCellId(500));
        pciarfcn.setEarfcnDl(new ARFCNValue(2100));
        RXSigMeasConfig.MeasCells measCells = new RXSigMeasConfig.MeasCells();
        measCells.setPCIARFCN(pciarfcn);
        RXSigMeasRepInterval repInterval = new RXSigMeasRepInterval(1);


        RXSigMeasConfig sigMeasConfig = new RXSigMeasConfig();
        sigMeasConfig.setCrnti(crnti);
        sigMeasConfig.setEcgi(ecgi);
        sigMeasConfig.setReportQty(rxSigRepQty);
        sigMeasConfig.setMeasCells(measCells);
        sigMeasConfig.setReportInterval(repInterval);


        XrancPduBody body = new XrancPduBody();
        body.setRXSigMeasConfig(sigMeasConfig);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        XrancApiID apiID = new XrancApiID(17);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }


}

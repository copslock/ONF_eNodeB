package samplemessages;

import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.api.PCIARFCN;
import codecs.api.RadioRepPerServCell;
import codecs.ber.types.BerInteger;
import codecs.ber.types.string.BerUTF8String;
import codecs.pdu.RadioMeasReportPerUE;
import codecs.pdu.XrancApiID;
import codecs.pdu.XrancPdu;
import codecs.pdu.XrancPduBody;
import codecs.pdu.XrancPduHdr;
import enodeb.SctpClientHandler;

import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class RadioReportPerUE1 {
        public static XrancPdu constructPacket(ECGI priECGI, CRNTI crnti, PCIARFCN pciarfcn, int lastInt) {
        // Shubham: Removing this as it was not present in V5
        // radioRepPerServCell.setPciArfcn(pciarfcn);
        XrancPduBody body = new XrancPduBody();
        RadioMeasReportPerUE radioMeasReportPerUE = new RadioMeasReportPerUE();
        RadioMeasReportPerUE.RadioReportServCells radioReportServCells = new RadioMeasReportPerUE.RadioReportServCells();

        /*ecgiSet.forEach( ecgi -> {*/
            RadioRepPerServCell radioRepPerServCell = new RadioRepPerServCell();
            RadioRepPerServCell.CqiHist cqiHist = new RadioRepPerServCell.CqiHist();
            RadioRepPerServCell.PuschSinrHist puschSinrHist = new RadioRepPerServCell.PuschSinrHist();
            RadioRepPerServCell.RiHist riHist = new RadioRepPerServCell.RiHist();
            RadioRepPerServCell.PucchSinrHist pucchSinrHist = new RadioRepPerServCell.PucchSinrHist();
            radioRepPerServCell.setEcgi(priECGI);

        cqiHist.setBerInteger(new BerInteger(lastInt));

        radioRepPerServCell.setCqiHist(cqiHist);

        riHist.setBerInteger(new BerInteger(1));
        riHist.setBerInteger(new BerInteger(1));
        radioRepPerServCell.setRiHist(riHist);

        for(int i = 0;i<14;i++){
              puschSinrHist.setBerInteger(new BerInteger(ThreadLocalRandom.current().nextInt(1, 15)));
        }
        radioRepPerServCell.setPuschSinrHist(puschSinrHist);
        for(int i = 0;i<14;i++){
              pucchSinrHist.setBerInteger(new BerInteger(ThreadLocalRandom.current().nextInt(1, 15)));
        }
        radioRepPerServCell.setPucchSinrHist(pucchSinrHist);
        radioReportServCells.setRadioRepPerServCell(radioRepPerServCell);
        radioMeasReportPerUE.setRadioReportServCells(radioReportServCells);
        radioMeasReportPerUE.setCrnti(crnti);
        radioMeasReportPerUE.setEcgi(priECGI);
        body.setRadioMeasReportPerUE(radioMeasReportPerUE);

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
}
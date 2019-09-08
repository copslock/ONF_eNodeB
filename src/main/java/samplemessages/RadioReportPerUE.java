package samplemessages;

//import codecs.api.*;
import codecs.api.CRNTI;
import codecs.api.ECGI;
import codecs.api.PCIARFCN;
import codecs.api.RadioRepPerServCell;
import codecs.pdu.*;
import codecs.ber.types.BerInteger;
import codecs.ber.types.string.BerUTF8String;
//import codecs.api.ECGI;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RadioReportPerUE {

    protected  static RadioRepPerServCell.CqiHist cqiHist = new RadioRepPerServCell.CqiHist();

    public static XrancPdu  constructPacket(ECGI ecgi, CRNTI crnti, PCIARFCN pciarfcn) {
        RadioMeasReportPerUE.RadioReportServCells radioReportServCells = new RadioMeasReportPerUE.RadioReportServCells();
                    // This is the first RadioRepPerServCell
        RadioRepPerServCell radioRepPerServCell = new RadioRepPerServCell();
            // Shubham: Removing this as it was not present in V5
            //radioRepPerServCell.setPciArfcn(pciarfcn);
            radioRepPerServCell.setEcgi(ecgi);

            LinkedList<BerInteger> list = new LinkedList<>();
            int value = 0;
            int position = 0;
            // Adding randomizer here to set random cqi values from 1 to 30

            RadioRepPerServCell.CqiHist cqiHist1 = new RadioRepPerServCell.CqiHist();
            for (int freq = 0; freq < 1; freq++) {
                value = ThreadLocalRandom.current().nextInt(1, 31);
                //System.out.println(" value = " + value);
                cqiHist1.setBerInteger(new BerInteger(10));
            }
            radioRepPerServCell.setCqiHist(cqiHist1);


            RadioRepPerServCell.RiHist riHist = new RadioRepPerServCell.RiHist();
            riHist.setBerInteger(new BerInteger(1));
            riHist.setBerInteger(new BerInteger(2));
            radioRepPerServCell.setRiHist(riHist);

            RadioRepPerServCell.PuschSinrHist puschSinrHist = new RadioRepPerServCell.PuschSinrHist();
            for(int i = 1 ; i< 15; i++) {
                puschSinrHist.setBerInteger(new BerInteger(i));
                /*puschSinrHist.setBerInteger(new BerInteger(2));
                puschSinrHist.setBerInteger(new BerInteger(3));
                puschSinrHist.setBerInteger(new BerInteger(4));
                puschSinrHist.setBerInteger(new BerInteger(5));
                puschSinrHist.setBerInteger(new BerInteger(6));
                puschSinrHist.setBerInteger(new BerInteger(7));*/
            }
            radioRepPerServCell.setPuschSinrHist(puschSinrHist);

            RadioRepPerServCell.PucchSinrHist pucchSinrHist = new RadioRepPerServCell.PucchSinrHist();
            for(int i = 1 ; i< 15; i++) {
                pucchSinrHist.setBerInteger(new BerInteger(i));
             /*   pucchSinrHist.setBerInteger(new BerInteger(2));
                pucchSinrHist.setBerInteger(new BerInteger(3));
                pucchSinrHist.setBerInteger(new BerInteger(4));
                pucchSinrHist.setBerInteger(new BerInteger(5));
                pucchSinrHist.setBerInteger(new BerInteger(6));
                pucchSinrHist.setBerInteger(new BerInteger(7)); */
            }
            radioRepPerServCell.setPucchSinrHist(pucchSinrHist);

        radioReportServCells.setRadioRepPerServCell(radioRepPerServCell);
        RadioMeasReportPerUE radioMeasReportPerUE = new RadioMeasReportPerUE();
        radioMeasReportPerUE.setRadioReportServCells(radioReportServCells);
        radioMeasReportPerUE.setCrnti(crnti);
        radioMeasReportPerUE.setEcgi(ecgi);

        XrancPduBody body = new XrancPduBody();
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
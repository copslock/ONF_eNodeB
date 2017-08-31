package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import codecs.ber.types.BerInteger;
import codecs.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class RadioReportPerUE {

    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti, PCIARFCN pciarfcn) {
        //Need to get this from UE.

        RadioRepPerServCell radioRepPerServCell = new RadioRepPerServCell();

        radioRepPerServCell.setPciArfcn(pciarfcn);

        RadioRepPerServCell.CqiHist cqiHist = new RadioRepPerServCell.CqiHist();
        cqiHist.setBerInteger(new BerInteger(1));
        cqiHist.setBerInteger(new BerInteger(2));
        cqiHist.setBerInteger(new BerInteger(3));
        cqiHist.setBerInteger(new BerInteger(4));
        cqiHist.setBerInteger(new BerInteger(5));
        cqiHist.setBerInteger(new BerInteger(6));
        cqiHist.setBerInteger(new BerInteger(7));
        cqiHist.setBerInteger(new BerInteger(8));
        cqiHist.setBerInteger(new BerInteger(9));
        cqiHist.setBerInteger(new BerInteger(10));
        cqiHist.setBerInteger(new BerInteger(11));
        cqiHist.setBerInteger(new BerInteger(12));
        cqiHist.setBerInteger(new BerInteger(13));
        cqiHist.setBerInteger(new BerInteger(14));
        cqiHist.setBerInteger(new BerInteger(15));
        cqiHist.setBerInteger(new BerInteger(16));
        radioRepPerServCell.setCqiHist(cqiHist);

        RadioRepPerServCell.RiHist riHist = new RadioRepPerServCell.RiHist();
        riHist.setBerInteger(new BerInteger(1));
        riHist.setBerInteger(new BerInteger(1));
        radioRepPerServCell.setRiHist(riHist);

        RadioRepPerServCell.PuschSinrHist puschSinrHist = new RadioRepPerServCell.PuschSinrHist();
        puschSinrHist.setBerInteger(new BerInteger(1));
        puschSinrHist.setBerInteger(new BerInteger(2));
        puschSinrHist.setBerInteger(new BerInteger(3));
        puschSinrHist.setBerInteger(new BerInteger(4));
        puschSinrHist.setBerInteger(new BerInteger(5));
        puschSinrHist.setBerInteger(new BerInteger(6));
        puschSinrHist.setBerInteger(new BerInteger(7));
        puschSinrHist.setBerInteger(new BerInteger(8));
        puschSinrHist.setBerInteger(new BerInteger(9));
        puschSinrHist.setBerInteger(new BerInteger(10));
        puschSinrHist.setBerInteger(new BerInteger(11));
        puschSinrHist.setBerInteger(new BerInteger(12));
        puschSinrHist.setBerInteger(new BerInteger(13));
        puschSinrHist.setBerInteger(new BerInteger(14));
        radioRepPerServCell.setPuschSinrHist(puschSinrHist);

        RadioRepPerServCell.PucchSinrHist pucchSinrHist = new RadioRepPerServCell.PucchSinrHist();
        pucchSinrHist.setBerInteger(new BerInteger(1));
        pucchSinrHist.setBerInteger(new BerInteger(2));
        pucchSinrHist.setBerInteger(new BerInteger(3));
        pucchSinrHist.setBerInteger(new BerInteger(4));
        pucchSinrHist.setBerInteger(new BerInteger(5));
        pucchSinrHist.setBerInteger(new BerInteger(6));
        pucchSinrHist.setBerInteger(new BerInteger(7));
        pucchSinrHist.setBerInteger(new BerInteger(8));
        pucchSinrHist.setBerInteger(new BerInteger(9));
        pucchSinrHist.setBerInteger(new BerInteger(10));
        pucchSinrHist.setBerInteger(new BerInteger(11));
        pucchSinrHist.setBerInteger(new BerInteger(12));
        pucchSinrHist.setBerInteger(new BerInteger(13));
        pucchSinrHist.setBerInteger(new BerInteger(14));
        radioRepPerServCell.setPucchSinrHist(pucchSinrHist);


        RadioMeasReportPerUE.RadioReportServCells radioReportServCells = new RadioMeasReportPerUE.RadioReportServCells();
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

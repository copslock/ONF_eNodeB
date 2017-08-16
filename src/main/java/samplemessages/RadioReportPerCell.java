package samplemessages;

import codecs.api.ECGI;
import codecs.pdu.*;
import org.openmuc.jasn1.ber.types.BerInteger;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class RadioReportPerCell {
    public XrancPdu setPacketProperties(XrancPdu recv_pdu) {

        ECGI ecgi = recv_pdu.getBody().getL2MeasConfig().getEcgi();
        RadioMeasReportPerCell.PuschIntfPowerHist puschIntfPowerHist = new RadioMeasReportPerCell.PuschIntfPowerHist();
        puschIntfPowerHist.setBerInteger(new BerInteger(1));
        puschIntfPowerHist.setBerInteger(new BerInteger(2));
        puschIntfPowerHist.setBerInteger(new BerInteger(3));
        puschIntfPowerHist.setBerInteger(new BerInteger(4));
        puschIntfPowerHist.setBerInteger(new BerInteger(5));
        puschIntfPowerHist.setBerInteger(new BerInteger(6));
        puschIntfPowerHist.setBerInteger(new BerInteger(7));
        puschIntfPowerHist.setBerInteger(new BerInteger(8));
        puschIntfPowerHist.setBerInteger(new BerInteger(9));
        puschIntfPowerHist.setBerInteger(new BerInteger(10));
        puschIntfPowerHist.setBerInteger(new BerInteger(11));
        puschIntfPowerHist.setBerInteger(new BerInteger(12));
        puschIntfPowerHist.setBerInteger(new BerInteger(13));
        puschIntfPowerHist.setBerInteger(new BerInteger(14));
        puschIntfPowerHist.setBerInteger(new BerInteger(15));
        puschIntfPowerHist.setBerInteger(new BerInteger(16));
        puschIntfPowerHist.setBerInteger(new BerInteger(17));

        RadioMeasReportPerCell.PucchIntfPowerHist pucchIntfPowerHist = new RadioMeasReportPerCell.PucchIntfPowerHist();
        pucchIntfPowerHist.setBerInteger(new BerInteger(1));
        pucchIntfPowerHist.setBerInteger(new BerInteger(2));
        pucchIntfPowerHist.setBerInteger(new BerInteger(3));
        pucchIntfPowerHist.setBerInteger(new BerInteger(4));
        pucchIntfPowerHist.setBerInteger(new BerInteger(5));
        pucchIntfPowerHist.setBerInteger(new BerInteger(6));
        pucchIntfPowerHist.setBerInteger(new BerInteger(7));
        pucchIntfPowerHist.setBerInteger(new BerInteger(8));
        pucchIntfPowerHist.setBerInteger(new BerInteger(9));
        pucchIntfPowerHist.setBerInteger(new BerInteger(10));
        pucchIntfPowerHist.setBerInteger(new BerInteger(11));
        pucchIntfPowerHist.setBerInteger(new BerInteger(12));
        pucchIntfPowerHist.setBerInteger(new BerInteger(13));
        pucchIntfPowerHist.setBerInteger(new BerInteger(14));
        pucchIntfPowerHist.setBerInteger(new BerInteger(15));
        pucchIntfPowerHist.setBerInteger(new BerInteger(16));
        pucchIntfPowerHist.setBerInteger(new BerInteger(17));

        RadioMeasReportPerCell radioMeasReportPerCell = new RadioMeasReportPerCell();
        radioMeasReportPerCell.setEcgi(ecgi);
        radioMeasReportPerCell.setPuschIntfPowerHist(puschIntfPowerHist);
        radioMeasReportPerCell.setPucchIntfPowerHist(pucchIntfPowerHist);

        XrancPduBody body = new XrancPduBody();
        body.setRadioMeasReportPerCell(radioMeasReportPerCell);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        XrancApiID apiID = new XrancApiID(21);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }

    public static XrancPdu constructPacket(ECGI ecgi) {
        RadioMeasReportPerCell.PuschIntfPowerHist puschIntfPowerHist = new RadioMeasReportPerCell.PuschIntfPowerHist();
        puschIntfPowerHist.setBerInteger(new BerInteger(1));
        puschIntfPowerHist.setBerInteger(new BerInteger(2));
        puschIntfPowerHist.setBerInteger(new BerInteger(3));
        puschIntfPowerHist.setBerInteger(new BerInteger(4));
        puschIntfPowerHist.setBerInteger(new BerInteger(5));
        puschIntfPowerHist.setBerInteger(new BerInteger(6));
        puschIntfPowerHist.setBerInteger(new BerInteger(7));
        puschIntfPowerHist.setBerInteger(new BerInteger(8));
        puschIntfPowerHist.setBerInteger(new BerInteger(9));
        puschIntfPowerHist.setBerInteger(new BerInteger(10));
        puschIntfPowerHist.setBerInteger(new BerInteger(11));
        puschIntfPowerHist.setBerInteger(new BerInteger(12));
        puschIntfPowerHist.setBerInteger(new BerInteger(13));
        puschIntfPowerHist.setBerInteger(new BerInteger(14));
        puschIntfPowerHist.setBerInteger(new BerInteger(15));
        puschIntfPowerHist.setBerInteger(new BerInteger(16));
        puschIntfPowerHist.setBerInteger(new BerInteger(17));

        RadioMeasReportPerCell.PucchIntfPowerHist pucchIntfPowerHist = new RadioMeasReportPerCell.PucchIntfPowerHist();
        pucchIntfPowerHist.setBerInteger(new BerInteger(1));
        pucchIntfPowerHist.setBerInteger(new BerInteger(2));
        pucchIntfPowerHist.setBerInteger(new BerInteger(3));
        pucchIntfPowerHist.setBerInteger(new BerInteger(4));
        pucchIntfPowerHist.setBerInteger(new BerInteger(5));
        pucchIntfPowerHist.setBerInteger(new BerInteger(6));
        pucchIntfPowerHist.setBerInteger(new BerInteger(7));
        pucchIntfPowerHist.setBerInteger(new BerInteger(8));
        pucchIntfPowerHist.setBerInteger(new BerInteger(9));
        pucchIntfPowerHist.setBerInteger(new BerInteger(10));
        pucchIntfPowerHist.setBerInteger(new BerInteger(11));
        pucchIntfPowerHist.setBerInteger(new BerInteger(12));
        pucchIntfPowerHist.setBerInteger(new BerInteger(13));
        pucchIntfPowerHist.setBerInteger(new BerInteger(14));
        pucchIntfPowerHist.setBerInteger(new BerInteger(15));
        pucchIntfPowerHist.setBerInteger(new BerInteger(16));
        pucchIntfPowerHist.setBerInteger(new BerInteger(17));

        RadioMeasReportPerCell radioMeasReportPerCell = new RadioMeasReportPerCell();
        radioMeasReportPerCell.setEcgi(ecgi);
        radioMeasReportPerCell.setPuschIntfPowerHist(puschIntfPowerHist);
        radioMeasReportPerCell.setPucchIntfPowerHist(pucchIntfPowerHist);

        XrancPduBody body = new XrancPduBody();
        body.setRadioMeasReportPerCell(radioMeasReportPerCell);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        XrancApiID apiID = new XrancApiID(19);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;
    }
}

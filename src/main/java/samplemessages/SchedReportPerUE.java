package samplemessages;

import codecs.api.*;
import codecs.pdu.*;
import codecs.ber.types.BerInteger;
import codecs.ber.types.string.BerUTF8String;

import java.io.UnsupportedEncodingException;

public class SchedReportPerUE {


    public static XrancPdu constructPacket(ECGI ecgi, CRNTI crnti, PCIARFCN pciarfcn) {
        SchedMeasRepPerServCell schedMeasRepPerServCell = new SchedMeasRepPerServCell();

        SchedMeasRepPerServCell.QciVals qciVals = new SchedMeasRepPerServCell.QciVals();
        qciVals.setQCI(new QCI(1));
        qciVals.setQCI(new QCI(2));

        PRBUsage prbUsage = new PRBUsage();
        PRBUsage.PrbUsageDl prbUsageDl = new PRBUsage.PrbUsageDl();
        prbUsageDl.setBerInteger(new BerInteger(50));
        prbUsageDl.setBerInteger(new BerInteger(100));
        prbUsage.setPrbUsageDl(prbUsageDl);

        PRBUsage.PrbUsageUl prbUsageUl = new PRBUsage.PrbUsageUl();
        prbUsageUl.setBerInteger(new BerInteger(50));
        prbUsageUl.setBerInteger(new BerInteger(100));
        prbUsage.setPrbUsageUl(prbUsageUl);

        SchedMeasRepPerServCell.McsDl mcsDl = new SchedMeasRepPerServCell.McsDl();
        mcsDl.setBerInteger(new BerInteger(1));
        mcsDl.setBerInteger(new BerInteger(4));

        SchedMeasRepPerServCell.McsUl mcsUl = new SchedMeasRepPerServCell.McsUl();
        mcsUl.setBerInteger(new BerInteger(5));
        mcsUl.setBerInteger(new BerInteger(6));

        SchedMeasRepPerServCell.NumSchedTtisDl numSchedTtisDl = new SchedMeasRepPerServCell.NumSchedTtisDl();
        numSchedTtisDl.setBerInteger(new BerInteger(1000));
        numSchedTtisDl.setBerInteger(new BerInteger(1000));

        SchedMeasRepPerServCell.NumSchedTtisUl numSchedTtisUl = new SchedMeasRepPerServCell.NumSchedTtisUl();
        numSchedTtisUl.setBerInteger(new BerInteger(1000));
        numSchedTtisUl.setBerInteger(new BerInteger(1000));

        SchedMeasRepPerServCell.RankDl1 rankDl1 = new SchedMeasRepPerServCell.RankDl1();
        rankDl1.setBerInteger(new BerInteger(1));
        rankDl1.setBerInteger(new BerInteger(1));

        SchedMeasRepPerServCell.RankDl2 rankDl2 = new SchedMeasRepPerServCell.RankDl2();
        rankDl2.setBerInteger(new BerInteger(1));
        rankDl2.setBerInteger(new BerInteger(1));

        schedMeasRepPerServCell.setPciArfcn(pciarfcn);
        schedMeasRepPerServCell.setQciVals(qciVals);
        schedMeasRepPerServCell.setPrbUsage(prbUsage);
        schedMeasRepPerServCell.setMcsDl(mcsDl);
        schedMeasRepPerServCell.setMcsUl(mcsUl);
        schedMeasRepPerServCell.setNumSchedTtisDl(numSchedTtisDl);
        schedMeasRepPerServCell.setNumSchedTtisUl(numSchedTtisUl);
        schedMeasRepPerServCell.setRankDl1(rankDl1);
        schedMeasRepPerServCell.setRankDl2(rankDl2);

        SchedMeasReportPerUE.SchedReportServCells schedReportServCells = new SchedMeasReportPerUE.SchedReportServCells();
        schedReportServCells.setSchedMeasRepPerServCell(schedMeasRepPerServCell);

        SchedMeasReportPerUE schedMeasReportPerUE = new SchedMeasReportPerUE();
        schedMeasReportPerUE.setCrnti(crnti);
        schedMeasReportPerUE.setEcgi(ecgi);
        schedMeasReportPerUE.setSchedReportServCells(schedReportServCells);

        XrancPduBody body = new XrancPduBody();
        body.setSchedMeasReportPerUE(schedMeasReportPerUE);

        BerUTF8String ver = null;
        try {
            ver = new BerUTF8String("4");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        XrancApiID apiID = new XrancApiID(20);
        XrancPduHdr hdr = new XrancPduHdr();
        hdr.setVer(ver);
        hdr.setApiId(apiID);

        XrancPdu pdu = new XrancPdu();
        pdu.setHdr(hdr);
        pdu.setBody(body);

        return pdu;


    }
}

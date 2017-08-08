package enodeb;

import codecs.api.*;
import codecs.pdu.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.sctp.SctpMessage;
import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import samplemessages.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class SctpClientHandler extends ChannelInboundHandlerAdapter {

    private CRNTI crnti;
    private MMEUES1APID mmeues1APID;
    private ENBUES1APID enbues1APID;

    private PCIARFCN pciarfcn;

//    private static boolean doIt = true;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws IOException {
        Random rand = new Random();
        byte[] bytes = new byte[2];
        new Random().nextBytes(bytes);
        crnti = new CRNTI(bytes, 16);

        mmeues1APID = new MMEUES1APID(rand.nextInt(99999) * 10);
        enbues1APID = new ENBUES1APID(rand.nextInt(99999) * 10);

        pciarfcn = new PCIARFCN();
        pciarfcn.setPci(new PhysCellId(rand.nextInt(999) * 10));
        pciarfcn.setEarfcnDl(new ARFCNValue(rand.nextInt(999) * 10));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws IOException {
        SctpMessage sctpMessage = (SctpMessage) msg;
        ByteBuf byteBuf = sctpMessage.content();

        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        XrancPdu recv_pdu = new XrancPdu();

        InputStream inputStream = new ByteArrayInputStream(bytes);

        recv_pdu.decode(inputStream);
        int apiID = recv_pdu.getHdr().getApiId().intValue();
        System.out.println("Received message: " + recv_pdu.toString());
        switch (apiID) {
            case 0: {
                // Decoded CellConfigRequest.
                CellConfigRequest cellConfigRequest = recv_pdu.getBody().getCellConfigRequest();
                // Send encoded CellConfigResponse.

                XrancPdu xrancPdu = ConfigReport.constructPacket(cellConfigRequest.getEcgi(), pciarfcn);
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                //FIXME: REMOVE THIS TEST
                xrancPdu = UEAdmRequest.constructPacket(cellConfigRequest.getEcgi(), crnti);
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                break;
            }
//            case 2: {
//                // We don't know what will trigger the sending of this packet.
//                // Encode UE Admission Request.
//
//                UEAdmRequest ueAdmRequest = new UEAdmRequest();
//
//                send_pdu = ueAdmRequest.setPacketProperties();
//                ctx.writeAndFlush(getSctpMessage(send_pdu));
//                break;
//            }
            case 3: {
                // Decode UE Response

                // Encode UE Admission Status - apiID 4
                UEAdmissionResponse ueAdmissionResponse = recv_pdu.getBody().getUEAdmissionResponse();
                XrancPdu xrancPdu = UEAdmStatus.constructPacket(ueAdmissionResponse.getEcgi(), ueAdmissionResponse.getCrnti());
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                // Encode UE Admission Context Update/UE Attach Complete = apiID 5
                xrancPdu = UECtxUpdate.constructPacket(ueAdmissionResponse.getEcgi(), ueAdmissionResponse.getCrnti(), mmeues1APID, enbues1APID);
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                // TODO: uncomment
//                int rrcConnComplete = 1;
//                if (rrcConnComplete == 1) {
//                    UEReconfInd ueReconfInd = new UEReconfInd();
//                    send_pdu = ueReconfInd.setPacketProperties(recv_pdu);
//                    // Encode UE Reconfig Ind = apiID 6
//                    ctx.writeAndFlush(getSctpMessage(send_pdu));
//                }

                // if UE goes from RRC_ACTIVE to RRC_IDLE, send UE_RELEASE_IND from eNB to xRANc
                // FIXME: REMOVE THIS TEST
//                if (doIt) {
//                    xrancPdu = UERelInd.constructPacket(ueAdmissionResponse.getEcgi(), ueAdmissionResponse.getCrnti());
//                    ctx.writeAndFlush(getSctpMessage(xrancPdu));
//                }

                // Encode and send Bearer Admission Request.
                xrancPdu = XranCBearerAdmReq.constructPacket(ueAdmissionResponse.getEcgi(), ueAdmissionResponse.getCrnti());
                ctx.writeAndFlush(getSctpMessage(xrancPdu));
                break;
            }
            case 7: {
                UEReleaseInd ueReleaseInd = recv_pdu.getBody().getUEReleaseInd();
                break;
            }
            case 9: {
                // Decode Bearer Admission Response
                BearerAdmissionResponse bearerAdmissionResponse = recv_pdu.getBody().getBearerAdmissionResponse();

                XrancPdu xrancPdu = BearerAdmStatus.constructPacket(bearerAdmissionResponse.getEcgi(), bearerAdmissionResponse.getCrnti());
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                xrancPdu = BearerRelInd.constructPacket(bearerAdmissionResponse.getEcgi(), bearerAdmissionResponse.getCrnti());
                ctx.writeAndFlush(getSctpMessage(xrancPdu));
                break;
            }
            case 12: {
                // Decode UECapabilityEnquiry
                UECapabilityEnquiry capabilityEnquiry = recv_pdu.getBody().getUECapabilityEnquiry();

                // Encode and send UE Capability Info
                XrancPdu xrancPdu = UECapabilityInformation.constructPacket(capabilityEnquiry.getEcgi(), capabilityEnquiry.getCrnti());
                ctx.writeAndFlush(getSctpMessage(xrancPdu));
                break;
            }
            case 17: {
                // Decode RX Signal Meas Config
                RXSigMeasConfig rxSigMeasConfig = recv_pdu.getBody().getRXSigMeasConfig();

                // Encode and send RX Sig Meas Report
                XrancPdu xrancPdu = RXSigMeasRep.constructPacket(rxSigMeasConfig.getEcgi(), rxSigMeasConfig.getCrnti(), rxSigMeasConfig.getMeasCells());
                ctx.writeAndFlush(getSctpMessage(xrancPdu));
                break;
            }
            case 19:
                // periodically send these packets out.
                L2MeasConfig l2MeasConfig = recv_pdu.getBody().getL2MeasConfig();

                XrancPdu xrancPdu = RadioReportPerUE.constructPacket(l2MeasConfig.getEcgi(), crnti);
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                xrancPdu = RadioReportPerCell.constructPacket(l2MeasConfig.getEcgi());
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                xrancPdu = SchedReportPerUE.constructPacket(l2MeasConfig.getEcgi(), crnti, pciarfcn);
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                xrancPdu = SchedReportPerCell.constructPacket(l2MeasConfig.getEcgi());
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                xrancPdu = PDCPReportPerUE.constructPacket(l2MeasConfig.getEcgi(), crnti);
                ctx.writeAndFlush(getSctpMessage(xrancPdu));
                break;
        }

    }

    private SctpMessage getSctpMessage(XrancPdu pdu) throws IOException {
        BerByteArrayOutputStream os = new BerByteArrayOutputStream(4096);

        pdu.encode(os);

        System.out.println("Sending message: " + pdu);
        final ByteBuf buf = Unpooled.buffer(os.getArray().length);
        for (int i = 0; i < buf.capacity(); i++) {
            buf.writeByte(os.getArray()[i]);
        }
        return new SctpMessage(0, 0, buf);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

}
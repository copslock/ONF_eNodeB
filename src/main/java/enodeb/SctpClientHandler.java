package enodeb;

import codecs.api.*;
import codecs.pdu.*;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.sctp.SctpMessage;
import org.openmuc.jasn1.ber.BerByteArrayOutputStream;
import org.openmuc.jasn1.ber.types.string.BerUTF8String;
import samplemessages.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SctpClientHandler extends ChannelInboundHandlerAdapter {

    private CRNTI crnti;
    private MMEUES1APID mmeues1APID;
    private ENBUES1APID enbues1APID;

    private PCIARFCN pciarfcn;

    private ECGI ecgi;

    public SctpClientHandler(String host_l) {
        char lastChar = host_l.charAt(host_l.length() - 1);
        byte[] bytes = new byte[]{(byte) 0xFF, (byte) 0xFF};
        crnti = new CRNTI(bytes, 16);

        mmeues1APID = new MMEUES1APID(Integer.valueOf(lastChar));
        enbues1APID = new ENBUES1APID(Integer.valueOf(lastChar));

        pciarfcn = new PCIARFCN();
        pciarfcn.setPci(new PhysCellId(Integer.valueOf(lastChar)));
        pciarfcn.setEarfcnDl(new ARFCNValue(Integer.valueOf(lastChar)));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws IOException {

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

                this.ecgi = cellConfigRequest.getEcgi();

                XrancPdu xrancPdu = ConfigReport.constructPacket(cellConfigRequest.getEcgi(), pciarfcn);
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                xrancPdu = UEAdmRequest.constructPacket(cellConfigRequest.getEcgi(), crnti);
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                break;
            }
            case 3: {
                // Decode UE Response

                // Encode UE Admission Status - apiID 4
                UEAdmissionResponse ueAdmissionResponse = recv_pdu.getBody().getUEAdmissionResponse();
                XrancPdu xrancPdu = UEAdmStatus.constructPacket(ueAdmissionResponse.getEcgi(), ueAdmissionResponse.getCrnti());
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

                // Encode UE Admission Context Update/UE Attach Complete = apiID 5
                xrancPdu = UECtxUpdate.constructPacket(ueAdmissionResponse.getEcgi(), ueAdmissionResponse.getCrnti(), mmeues1APID, enbues1APID);
                ctx.writeAndFlush(getSctpMessage(xrancPdu));

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
            case 25: {
                // Decode UECapabilityEnquiry
                UECapabilityEnquiry capabilityEnquiry = recv_pdu.getBody().getUECapabilityEnquiry();

                // Encode and send UE Capability Info
                XrancPdu xrancPdu = UECapabilityInformation.constructPacket(capabilityEnquiry.getEcgi(), capabilityEnquiry.getCrnti());
                ctx.writeAndFlush(getSctpMessage(xrancPdu));
                break;
            }
            case 15: {
                // Decode RX Signal Meas Config
                RXSigMeasConfig rxSigMeasConfig = recv_pdu.getBody().getRXSigMeasConfig();

                // Encode and send RX Sig Meas Report

                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            XrancPdu xrancPdu = RXSigMeasRep.constructPacket(rxSigMeasConfig.getEcgi(), rxSigMeasConfig.getCrnti(), rxSigMeasConfig.getMeasCells());
                            ctx.writeAndFlush(getSctpMessage(xrancPdu));
                        } catch (IOException ignored) {
                        }
                    }
                }, rxSigMeasConfig.getReportInterval().intValue(), rxSigMeasConfig.getReportInterval().intValue());
                break;
            }
            case 17:
                // periodically send these packets out.
                L2MeasConfig l2MeasConfig = recv_pdu.getBody().getL2MeasConfig();

                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            XrancPdu xrancPdu = RadioReportPerUE.constructPacket(l2MeasConfig.getEcgi(), crnti, pciarfcn);
                            ctx.writeAndFlush(getSctpMessage(xrancPdu));

                            xrancPdu = RadioReportPerCell.constructPacket(l2MeasConfig.getEcgi());
                            ctx.writeAndFlush(getSctpMessage(xrancPdu));

                            xrancPdu = SchedReportPerUE.constructPacket(l2MeasConfig.getEcgi(), crnti, pciarfcn);
                            ctx.writeAndFlush(getSctpMessage(xrancPdu));

                            xrancPdu = SchedReportPerCell.constructPacket(l2MeasConfig.getEcgi());
                            ctx.writeAndFlush(getSctpMessage(xrancPdu));

                            xrancPdu = PDCPReportPerUE.constructPacket(l2MeasConfig.getEcgi(), crnti);
                            ctx.writeAndFlush(getSctpMessage(xrancPdu));
                        } catch (Exception ignored) {
                        }
                    }
                }, l2MeasConfig.getReportIntervalMs().intValue(), l2MeasConfig.getReportIntervalMs().intValue());

                break;

            case 12: {
                HORequest hoRequest = recv_pdu.getBody().getHORequest();

                if (hoRequest.getEcgiT().equals(this.ecgi)) {
                    HOComplete hoComplete = new HOComplete();

                    hoComplete.setEcgiS(hoRequest.getEcgiS());
                    hoComplete.setEcgiT(this.ecgi);

                    Random random = new Random();
                    crnti.code[0] = (byte) random.nextInt();

                    hoComplete.setCrntiNew(crnti);

                    XrancPduBody body = new XrancPduBody();
                    body.setHOComplete(hoComplete);

                    BerUTF8String ver = null;
                    try {
                        ver = new BerUTF8String("4");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    XrancApiID sentapiID = new XrancApiID(14);
                    XrancPduHdr hdr = new XrancPduHdr();
                    hdr.setVer(ver);
                    hdr.setApiId(sentapiID);

                    XrancPdu pdu = new XrancPdu();
                    pdu.setHdr(hdr);
                    pdu.setBody(body);

                    ctx.writeAndFlush(pdu);
                } else {
                    UEReleaseInd ueReleaseInd = new UEReleaseInd();
                    ueReleaseInd.setCrnti(hoRequest.getCrnti());
                    ueReleaseInd.setEcgi(hoRequest.getEcgiS());

                    RelCause relCause = new RelCause(BigInteger.valueOf(0));
                    ueReleaseInd.setReleaseCause(relCause);

                    XrancPduBody body = new XrancPduBody();
                    body.setUEReleaseInd(ueReleaseInd);

                    BerUTF8String ver = null;
                    try {
                        ver = new BerUTF8String("4");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    XrancApiID sentapiID = new XrancApiID(7);
                    XrancPduHdr hdr = new XrancPduHdr();
                    hdr.setVer(ver);
                    hdr.setApiId(sentapiID);

                    XrancPdu pdu = new XrancPdu();
                    pdu.setHdr(hdr);
                    pdu.setBody(body);

                    ctx.writeAndFlush(pdu);
                }

                break;
            }
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

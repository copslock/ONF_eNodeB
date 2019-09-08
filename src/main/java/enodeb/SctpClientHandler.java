package enodeb;

import codecs.api.*;
import codecs.ber.BerByteArrayOutputStream;
import codecs.ber.types.string.BerUTF8String;
import codecs.pdu.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.google.common.primitives.Bytes;
import com.opencsv.CSVReader;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.sctp.SctpMessage;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import samplemessages.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;

import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SctpClientHandler extends ChannelInboundHandlerAdapter {

	private Socket socket = null;

	private CRNTI crnti;
	private MMEUES1APID mmeues1APID;
	private ENBUES1APID enbues1APID;

	private PCIARFCN pciarfcn;

	private ECGI ecgi;
	private ConcurrentMap<CRNTI, Set<ECGI>> crECGIMap = new ConcurrentHashMap<>();
	private Set<ECGI> ecgiSet = new HashSet();

	private ConcurrentMap<ECGI, Set<CRNTI>> ecCRNTIMap = new ConcurrentHashMap<>();
	private Set<CRNTI> crntiSet = new HashSet();
	private ECGI ecgiForSet;
	public int lastInt;
	public static boolean flag = false;

	public StringBuilder sb = new StringBuilder();
	public String plmnString = "00000";
	public String ecgiString = "000000";
	public static List<Integer> cqiList = new ArrayList<>();
	public static List<Double> xCordList = new ArrayList<>();
	public static List<ECGI> ecgiList = new ArrayList<>();
	public static Set<CRNTI> crList = new HashSet<>();

	public SctpClientHandler(String host_l) {

		char lastChar = host_l.charAt(host_l.length() - 1);
		byte[] bytes = new byte[]{(byte) (0xFF), (byte) (0xFF - Integer.valueOf(lastChar))};
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
		ExecutorService service = Executors.newCachedThreadPool();

		service.submit(()->{
			try {
				multiThreadedRead(ctx, msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}


	public void multiThreadedRead(ChannelHandlerContext ctx, Object msg ) throws IOException {
		SctpMessage sctpMessage = (SctpMessage) msg;
		ByteBuf byteBuf = sctpMessage.content();

		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);

		XrancPdu recv_pdu = new XrancPdu();

		InputStream inputStream = new ByteArrayInputStream(bytes);

		try {
			recv_pdu.decode(inputStream);
		} catch (Exception e) {
			System.out.println("Exception = " + e.getStackTrace().toString());
		}
		int apiID = recv_pdu.getHdr().getApiId().intValue();

		switch (apiID) {
			case 0: {
					// Decoded CellConfigRequest.
					CellConfigRequest cellConfigRequest = recv_pdu.getBody().getCellConfigRequest();
					// Send encoded CellConfigResponse.

					this.ecgi = cellConfigRequest.getEcgi();

					XrancPdu xrancPdu = ConfigReport.constructPacket(cellConfigRequest.getEcgi(), pciarfcn);
					ctx.writeAndFlush(getSctpMessage(xrancPdu));

					crList.add(crnti);
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
			case 12: {
					 HORequest hoRequest = recv_pdu.getBody().getHORequest();

					 if (hoRequest.getEcgiT().equals(this.ecgi)) {
						 HOComplete hoComplete = new HOComplete();
						 flag = true;
						 hoComplete.setEcgiS(hoRequest.getEcgiS());
						 hoComplete.setEcgiT(this.ecgi);

						 byte[] newbytes = new byte[]{(byte) 0xAF, (byte) 0xFA};
						 //crnti = new CRNTI(newbytes, 16);

						 hoComplete.setCrntiNew(hoRequest.getCrnti());

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

						 ctx.writeAndFlush(getSctpMessage(pdu));

						 pdu = UECtxUpdate.constructPacket(hoRequest.getEcgiT(), hoComplete.getCrntiNew(), mmeues1APID, enbues1APID);
						 ctx.writeAndFlush(getSctpMessage(pdu));
						 crList.add(hoRequest.getCrnti());
					 }
					 else  if (hoRequest.getEcgiS().equals(this.ecgi)) {
						 if(crList.contains(hoRequest.getCrnti())){
							 crList.remove(hoRequest.getCrnti());
						 }
					 }

					 break;
			}
			case 15: {
					 // Decode RX Signal Meas Config
					 RXSigMeasConfig rxSigMeasConfig = recv_pdu.getBody().getRXSigMeasConfig();
					 RRCMeasConfig rrcMeasConfig = recv_pdu.getBody().getRRCMeasConfig();
					 XrancPdu xrancPdu = RXSigMeasRep.constructPacket(rrcMeasConfig.getEcgi(), rrcMeasConfig.getCrnti(),
							 rrcMeasConfig.getMeasObjects());

					 ctx.writeAndFlush(getSctpMessage(xrancPdu));
					 break;
			}
			case 16:
				 System.out.println("\n*** RXSigMeasReport ***\n");

			case 17: {

					 L2MeasConfig l2MeasConfig = recv_pdu.getBody().getL2MeasConfig();

					 String [] arr;
					 for(int i=1; i< SctpClient.allData.size(); i++){
						 arr = SctpClient.allData.get(i);
						 xCordList.add(Double.valueOf(arr[1]));
						 cqiList.add(Integer.valueOf(arr[2]));

						 if (Integer.valueOf(arr[3])%10 == 0) {
							 ecgiList.add(ECGI.hextoECGI(plmnString + "A", ecgiString + "A" + "0"));
						 }
						 else {
							 ecgiList.add(ECGI.hextoECGI(plmnString + arr[3], ecgiString + arr[3] + "0"));
						 }
					 }

					 RadioReportPerUE_Sender continousRadioReport = new RadioReportPerUE_Sender(ctx, ecgi, pciarfcn, crnti);

					 XrancPdu xrancPdu = RadioReportPerCell.constructPacket(l2MeasConfig.getEcgi());
					 ctx.writeAndFlush(getSctpMessage(xrancPdu));

					 xrancPdu = SchedReportPerUE.constructPacket(l2MeasConfig.getEcgi(), crnti, pciarfcn);
					 ctx.writeAndFlush(getSctpMessage(xrancPdu));

					 xrancPdu = SchedReportPerCell.constructPacket(l2MeasConfig.getEcgi());
					 ctx.writeAndFlush(getSctpMessage(xrancPdu));

					 xrancPdu = PDCPReportPerUE.constructPacket(l2MeasConfig.getEcgi(), crnti);
					 ctx.writeAndFlush(getSctpMessage(xrancPdu));

					 continousRadioReport.start();
					 break;
			}

			case 24: {
					 // Decode UECapabilityEnquiry
					 UECapabilityEnquiry capabilityEnquiry = recv_pdu.getBody().getUECapabilityEnquiry();
					 XrancPdu xrancPdu = UECapabilityInformation.constructPacket(capabilityEnquiry.getEcgi(), capabilityEnquiry.getCrnti());
					 ctx.writeAndFlush(getSctpMessage(xrancPdu));
					 break;
			}
			case 26: {
					 System.out.println("\n**** Scelladd ******\n");
			}
			case 27: {
					 System.out.println("\n*** Scelladd status logic to be implemented **\n");
					 //Send scelladdstatus with apiid 25
					 break;
			}
			case 29: {
					 RRMConfig rrmConfig = recv_pdu.getBody().getRRMConfig();
					 System.out.println(rrmConfig);
					 XrancPdu xrancPdu = RRMConfStatus.constructPacket(rrmConfig);
					 ctx.writeAndFlush(getSctpMessage(xrancPdu));
					 break;
			}
			case 36: {
					 RRCMeasConfig rrcMeasConfig = recv_pdu.getBody().getRRCMeasConfig();
					 break;

			}
			default:
				 System.out.println("In Deafult");
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
		System.out.println(cause.toString());
		cause.printStackTrace();
		ctx.close();
	}

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}
}

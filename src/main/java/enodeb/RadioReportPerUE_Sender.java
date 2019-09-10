/*
	Author: Shubham
 */
package enodeb;

import codecs.ber.BerByteArrayOutputStream;
import codecs.pdu.XrancPdu;
import codecs.api.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.sctp.SctpMessage;
import samplemessages.RadioReportPerUE;
import samplemessages.RadioReportPerUE1;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


public class RadioReportPerUE_Sender extends Thread{
	private PCIARFCN pciarfcn;
	private ECGI instanceEcgi;
	private CRNTI   crnti;
	private byte[] bytes = new byte[]{(byte) 0xFF, (byte) 0xCD};
	private ChannelHandlerContext ctx;
	private CRNTI checkcrnti = new CRNTI(bytes, 16);
	public static int value;
	public int lastInt,i = 0,j;
	public String arr [];
	public int index;
	private XrancPdu xrancPdu;

	RadioReportPerUE_Sender( ChannelHandlerContext context, ECGI ecgi, PCIARFCN pciarfcn, CRNTI crnti) {
		this.ctx = context;
		this.instanceEcgi = ecgi;
		this.pciarfcn = pciarfcn;
		this.crnti = crnti;
	}

	private int getIndex(){
		int index = 0;
		String usernameColonPassword = "onos:rocks";
		String basicAuthPayload = "Basic " + Base64.getEncoder().encodeToString(usernameColonPassword.getBytes());
		JsonNode node = null;
		try{
			URL url = new URL("http://localhost:8181/onos/xran/cell/rrpscindex");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", basicAuthPayload);
			ObjectMapper mapper = new ObjectMapper();
			node = mapper.readTree(con.getInputStream());
		}
		catch(IOException io){
			System.out.println(io.getStackTrace());
		}
		node = node.path("data");
		Iterator<JsonNode> it = node.elements();
		while (it.hasNext()) {
			node = it.next();

			index = node.asInt();
		}
		return index;
	}
	public void run() {
		try {
			sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(!Thread.interrupted()) {
			if (SctpClientHandler.crList.contains(checkcrnti)) {
				//call the rest to get the index
				index = getIndex();
				for (int i=index;i<SctpClient.allData.size()-1; i++){
					xrancPdu = RadioReportPerUE1.constructPacket(SctpClientHandler.ecgiList.get(i), checkcrnti, pciarfcn, SctpClientHandler.cqiList.get(i));
					try{
						sleep(1);
						ctx.writeAndFlush(getSctpMessage(xrancPdu));
					}
					catch(Exception e){
						System.out.println(e.getStackTrace());
					}
					if ( i >= index+9){
						break;
					}
				}

			}
			else{
				System.out.println("#### => Not sending 18= ");
			}

			System.out.println("Sleeping the thread for 1.1 sec");
			try{
				Thread.sleep(1000);
			}
			catch (InterruptedException e){
				System.out.println(e);
			}
		}
		}

	private SctpMessage getSctpMessage(XrancPdu pdu)  throws IOException {
		BerByteArrayOutputStream os = new BerByteArrayOutputStream(4096);
		pdu.encode(os);
		final ByteBuf buf = Unpooled.buffer(os.getArray().length);
		for (int i = 0; i < buf.capacity(); i++) {
			buf.writeByte(os.getArray()[i]);
		}
		return new SctpMessage(0, 0, buf);
	}
	}

package enodeb;

import com.opencsv.CSVReader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.sctp.SctpChannelOption;
import io.netty.channel.sctp.nio.NioSctpChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.List;

public class SctpClient {
	private EventLoopGroup workerGroup;
	static public List<String[]> allData;
	public static void main(String[] args) throws UnknownHostException {
		SctpClient client = new SctpClient();

		if (args.length == 4) {
			//File csvFile = new File(System.getProperty("user.home")+"/file_test13.csv");
			File csvFile = new File("/tmp/file_test13.csv");
			FileReader fReader = null;
			try {
				fReader = new FileReader(csvFile);
				CSVReader csvReader = new CSVReader(fReader);
				allData = csvReader.readAll();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			client.run(args[0], args[1], args[2], args[3]);
		}
	}

	private void run(String host_t, String port_t, String host_l, String port_l) throws UnknownHostException {
		try {
			Bootstrap bootstrap = createClientBootstrap();
			bootstrap.handler(new ChannelInitializer<SctpChannel>() {

				@Override
				protected void initChannel(SctpChannel ch) throws Exception {
					ch.pipeline().addLast(
							new SctpClientHandler(host_l)
							);
				}
			});

			SocketAddress addr_t = new InetSocketAddress(host_t, Integer.parseInt(port_t));
			SocketAddress addr_l = new InetSocketAddress(host_l, Integer.parseInt(port_l));

			ChannelFuture sync = bootstrap.connect(addr_t, addr_l).sync();

			sync.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
		}
	}

	private Bootstrap createClientBootstrap() {

		workerGroup = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(workerGroup)
			.channel(NioSctpChannel.class);
		return b;
	}

}

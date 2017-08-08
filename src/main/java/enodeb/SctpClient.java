package enodeb;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.SctpChannel;
import io.netty.channel.sctp.SctpChannelOption;
import io.netty.channel.sctp.nio.NioSctpChannel;

public class SctpClient {
    private EventLoopGroup workerGroup;

    public static void main(String[] args) {
        SctpClient client = new SctpClient();

        if (args.length == 2) {
            client.run(args[0], args[1]);
        } else {
            client.run("10.0.0.1","7891");
        }
    }

    private void run(String host, String port) {
        try {
            Bootstrap bootstrap = createClientBootstrap();
            bootstrap.handler(new ChannelInitializer<SctpChannel>() {

                @Override
                protected void initChannel(SctpChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new SctpClientHandler()
                    );
                }
            });

            ChannelFuture sync = bootstrap.connect(host, Integer.parseInt(port)).sync();

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
                .channel(NioSctpChannel.class)
                .option(SctpChannelOption.SCTP_NODELAY, true);
        return b;
    }

}

package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.stub.StreamObserver;


public class SkiConditionServer
{
    private String address = "127.0.0.5";
    private int port = 50051;
    private Server server;
    private SocketAddress socket;
    public void start() throws IOException{
        try { socket = new InetSocketAddress(InetAddress.getByName(address), port);	} catch(UnknownHostException e) {};
        SkiConditionsImpl skiConditions = new SkiConditionsImpl();
        server = NettyServerBuilder.forAddress(socket).executor(Executors.newFixedThreadPool(10))
                .addService(skiConditions)
                .build()
                .start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("Shutting down gRPC server");
                SkiConditionServer.this.stop();
                System.err.println("server shut down");
            }
        });
    }
    public void stop(){
        if(server!=null){
            server.shutdown();
        }
    }
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        final SkiConditionServer server = new SkiConditionServer();
        server.start();
        server.blockUntilShutdown();
    }


}

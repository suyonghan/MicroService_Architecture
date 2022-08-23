package ib.api.grpc.server;

import ib.api.grpc.server.service.ProductInfoImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class ProductInfoServer {

    public static void main(String[]args) throws IOException, InterruptedException {
        int port =50051;
        Server server = ServerBuilder.forPort(port)
                .addService(new ProductInfoImpl())
                .build()
                .start();


        log.info("Server Started, Listening on {}", port);

        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            log.error("Shutting down grpc server since JVM is shutting down");
            if(server != null){
                server.shutdown();
            }
            log.error("server shut down");
        }));

        server.awaitTermination();
    }
}

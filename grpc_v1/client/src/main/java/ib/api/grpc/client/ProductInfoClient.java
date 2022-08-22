package ib.api.grpc.client;

import ib.api.grpc.grpc_v1.Product;
import ib.api.grpc.grpc_v1.ProductID;
import ib.api.grpc.grpc_v1.ProductInfoGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductInfoClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        ProductInfoGrpc.ProductInfoBlockingStub stub = ProductInfoGrpc.newBlockingStub(channel);

        ProductID productID = stub.addProduct(Product.newBuilder()
                .setName("Apple IPhone 14")
                .setDescription("suyong want to get a new Iphone")
                .setPrice(1600.0f)
                .build());

        log.info(productID.getValue());

        Product product = stub.getProduct(productID);
        log.info(product.toString());
        channel.shutdown();
    }
}

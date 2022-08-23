package ib.api.grpc.client;

import com.google.protobuf.Any;
import ib.api.grpc.client.service.ProductService;
import ib.api.grpc.grpc_v1.*;
import ib.api.grpc.grpc_v1.Exception;
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

        ProductService productService = new ProductService(channel);
        productService.processProduct(Product.newBuilder()
                .setName("Apple IPhone 14")
                .setDescription("suyong want to get a new Iphone")
                .setPrice(1600.0f)
                .build());

        productService.processProductV4(ProductV4.newBuilder()
                .setName("any-type using-test")
                .setException(Any.pack(Exception.newBuilder()
                                .setTitle("none")
                                .setDetailMessage("none")
                                .setErrorCode("0000")
                        .build()))
                .build());
        channel.shutdown();
    }
}

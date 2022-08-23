package ib.api.grpc.server.service;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import ib.api.grpc.grpc_v1.Exception;
import ib.api.grpc.grpc_v1.Product;
import ib.api.grpc.grpc_v1.ProductID;
import ib.api.grpc.grpc_v1.ProductInfoGrpc;
import ib.api.grpc.grpc_v1.ProductV4;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class ProductInfoImpl extends ProductInfoGrpc.ProductInfoImplBase{

    private Map<String, Product> productMap = new HashMap<String, Product>();
    private Map<String, ProductV4> productMapV4 = new HashMap<String, ProductV4>();

    @Override
    public void addProduct(Product request, StreamObserver<ProductID> responseObserver) {
        UUID uuid = UUID.randomUUID();
        String randomIDString = uuid.toString();

        request = request.toBuilder()
                .setId(randomIDString)
                .build();
        productMap.put(randomIDString, request);
        ProductID id = ProductID.newBuilder().setValue(randomIDString).build();
        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    @Override
    public void getProduct(ProductID request, StreamObserver<Product> responseObserver) {
        String id = request.getValue();
        if(productMap.containsKey(id)){
            responseObserver.onNext(productMap.get(id));
            responseObserver.onCompleted();
        } else{
            responseObserver.onError(new StatusException(Status.NOT_FOUND));
        }
    }

    @Override
    public void addProductV4(ProductV4 request, StreamObserver<ProductID> responseObserver) {
        UUID uuid = UUID.randomUUID();
        String randomIDString = uuid.toString();
        Exception exception = null;
        log.info("request : {}", request.toString());
        try {
            log.info("request-exception : {}", request.getException().unpack(Exception.class));
        } catch (InvalidProtocolBufferException e) {
            exception = Exception.newBuilder()
                    .setTitle(String.valueOf(e.getCause()))
                    .setErrorCode("A999")
                    .setDetailMessage(Arrays.toString(e.getStackTrace()))
                    .build();
            //throw new RuntimeException(e);
        }
        request = request.toBuilder()
                .setId(randomIDString)
                .setException(Any.pack(exception))
                .build();


        productMapV4.put(randomIDString, request);
        ProductID id = ProductID.newBuilder().setValue(randomIDString).build();
        responseObserver.onNext(id);
        responseObserver.onCompleted();
    }

    @Override
    public void getProductV4(ProductID request, StreamObserver<ProductV4> responseObserver) {
        super.getProductV4(request, responseObserver);
    }
}

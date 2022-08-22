package ib.api.grpc.server;

import ib.api.grpc.grpc_v1.Product;
import ib.api.grpc.grpc_v1.ProductID;
import ib.api.grpc.grpc_v1.ProductInfoGrpc;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductInfoImpl extends ProductInfoGrpc.ProductInfoImplBase{

    private Map<String, Product> productMap = new HashMap<String, Product>();

    @Override
    public void addProduct(Product request, StreamObserver<ProductID> responseObserver) {
        UUID uuid = UUID.randomUUID();
        String randomIDString = uuid.toString();
        request = request.toBuilder().setId(randomIDString).build();
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
}

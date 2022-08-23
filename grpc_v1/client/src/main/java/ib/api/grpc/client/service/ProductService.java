package ib.api.grpc.client.service;

import ib.api.grpc.grpc_v1.*;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductService {

    private ProductInfoGrpc.ProductInfoBlockingStub stub;

    public ProductService(Channel channel){
         stub = ProductInfoGrpc.newBlockingStub(channel);
    }

    public Product processProduct(Product product){
        ProductID productID = stub.addProduct(product);
        log.info("productId : {}", productID.getValue());
        Product selectedProduct = stub.getProduct(productID);
        log.info("selectedProduct : {}", selectedProduct.toString());
        return selectedProduct;
    };



    public ProductV2 processProductV2(ProductV2 product){
        ProductID productID = stub.addProductV2(product);
        log.info("productV2Id : {}", productID.getValue());
        ProductV2 selectedProduct = stub.getProductV2(productID);
        log.info("selectedProductV2 : {}", selectedProduct.toString());
        return selectedProduct;
    };

    public ProductV3 processProductV3(ProductV3 product){
        ProductID productID = stub.addProductV3(product);
        log.info("productV3Id : {}", productID.getValue());
        ProductV3 selectedProduct = stub.getProductV3(productID);
        log.info("selectedProductV3 : {}", selectedProduct.toString());
        return selectedProduct;
    };

    public ProductV4 processProductV4(ProductV4 product){
        ProductID productID = stub.addProductV4(product);
        log.info("productV4Id : {}", productID.getValue());
        ProductV4 selectedProduct = stub.getProductV4(productID);
        log.info("selectedProductV4 : {}", selectedProduct.toString());
        return selectedProduct;
    };
}

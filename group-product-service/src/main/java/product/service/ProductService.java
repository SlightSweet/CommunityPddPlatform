package product.service;

import product.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    boolean launchProduct(Product product);
    boolean setPreSaleStock(Long productId, Integer stock);
    String seckill(Long productId, Long userId);
    String preloadSeckillStock(Long productId);
}

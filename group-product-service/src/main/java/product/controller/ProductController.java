package product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.pojo.Product;
import product.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @GetMapping("/list")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @PostMapping("/launch")
    public boolean launchProduct(@RequestBody Product product) {
        return productService.launchProduct(product);
    }
    @PostMapping("/setPreSaleStock")
    public boolean setPreSaleStock(@RequestParam Long productId, @RequestParam Integer stock) {
        return productService.setPreSaleStock(productId, stock);
    }
    @PostMapping("/seckill/{productId}")
    public String seckill(@PathVariable Long productId, @RequestParam Long userId) {
        return productService.seckill(productId, userId);
    }
    @PostMapping("/preloadSeckillStock/{productId}")
    public String preloadSeckillStock(@PathVariable Long productId) {
        return productService.preloadSeckillStock(productId);
    }
}

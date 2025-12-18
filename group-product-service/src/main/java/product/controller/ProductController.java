package product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import product.pojo.Product;
import product.service.ProductService;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
public class ProductController {

    private static final Logger logger = Logger.getLogger(ProductController.class.getName());

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
    @CrossOrigin(origins = "*")
    public ResponseEntity<Boolean> launchProduct(@RequestBody Product product) {
        try {
            logger.info("Received product for launch: " + product.getName());
            boolean result = productService.launchProduct(product);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            logger.severe("Error launching product: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
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

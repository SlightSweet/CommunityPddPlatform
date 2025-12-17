package product.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import product.pojo.Product;
import product.service.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*")
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
    public Map<String, Object> launchProduct(@RequestBody Product product) {
        Map<String, Object> res = new HashMap<>();
        try {
            boolean ok = productService.launchProduct(product);
            res.put("success", ok);
            if (ok) {
                res.put("message", "商品保存成功");
            } else {
                res.put("message", "商品保存失败");
            }
        } catch (Exception e) {
            res.put("success", false);
            res.put("message", "商品保存异常: " + e.getMessage());
        }
        return res;
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

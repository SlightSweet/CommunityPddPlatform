package product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import product.pojo.Product;
import product.service.ProductService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {

    // 模拟数据库存储
    private List<Product> products = new ArrayList<>();

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PostConstruct
    public void init() {
        // 初始化一些示例数据
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("iPhone 12");
        product1.setPrice(new java.math.BigDecimal("5999.00"));
        product1.setStock(100);
        product1.setStatus((byte) 1);

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("小米电视");
        product2.setPrice(new java.math.BigDecimal("2999.00"));
        product2.setStock(50);
        product2.setStatus((byte) 1);

        products.add(product1);
        products.add(product2);
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean launchProduct(Product product) {
        // 设置商品状态为上架
        product.setStatus((byte) 1);
        product.setCreateTime(new java.util.Date());
        product.setUpdateTime(new java.util.Date());

        if (product.getId() == null) {
            // 新商品分配ID
            long maxId = products.stream()
                    .mapToLong(Product::getId)
                    .max()
                    .orElse(0L);
            product.setId(maxId + 1);
            products.add(product);
        } else {
            // 更新已有商品
            products.removeIf(p -> p.getId().equals(product.getId()));
            products.add(product);
        }
        return true;
    }

    @Override
    public boolean setPreSaleStock(Long productId, Integer stock) {
        Product product = getProductById(productId);
        if (product != null) {
            product.setPreSaleStock(stock);
            product.setStatus((byte) 2); // 设置为预售状态
            product.setUpdateTime(new java.util.Date());
            return true;
        }
        return false;
    }

    @Override
    public String seckill(Long productId, Long userId) {
        // 从Redis检查库存
        String stockKey = "seckill:stock:" + productId;
        String userKey = "seckill:user:" + productId + ":" + userId;

        // 检查用户是否已经参与过秒杀
        Boolean userExists = redisTemplate.hasKey(userKey);
        if (Boolean.TRUE.equals(userExists)) {
            return "您已经参与过此商品的秒杀活动";
        }

        // 使用Redis的原子操作减少库存
        Long stock = redisTemplate.opsForValue().decrement(stockKey);
        if (stock != null && stock >= 0) {
            // 秒杀成功，记录用户信息
            redisTemplate.opsForValue().set(userKey, 1, 30, TimeUnit.MINUTES);
            return "秒杀成功";
        } else {
            // 恢复库存（因为decrement已经执行了）
            if (stock != null && stock < 0) {
                redisTemplate.opsForValue().increment(stockKey);
            }
            return "秒杀失败，商品已售完";
        }
    }

    @Override
    public String preloadSeckillStock(Long productId) {
        Product product = getProductById(productId);
        if (product == null) {
            return "商品不存在";
        }

        // 将库存预热到Redis
        String key = "seckill:stock:" + productId;
        redisTemplate.opsForValue().set(key, product.getStock(), 30, TimeUnit.MINUTES);
        return "秒杀库存预热成功，预热数量：" + product.getStock();
    }
}

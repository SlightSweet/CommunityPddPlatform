package product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import product.mapper.ProductMapper;
import product.pojo.Product;
import product.service.ProductService;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<Product> getAllProducts() {
        return productMapper.getAllProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productMapper.getProductById(id);
    }

    @Override
    public boolean launchProduct(Product product) throws Exception {
        try {
            if (product.getId() == null) {
                // 新商品
                product.setCreateTime(new Date());
                product.setStatus((byte) 1); // 默认上架
                productMapper.insertProduct(product);
            } else {
                // 更新已有商品
                productMapper.updateProduct(product);
            }
            return true;
        } catch (Exception e) {
            throw new Exception("商品添加/更新失败: " + e.getMessage());
        }
    }

    @Override
    public boolean setPreSaleStock(Long productId, Integer stock) {
        Product product = getProductById(productId);
        if (product != null) {
            product.setPreSaleStock(stock);
            product.setStatus((byte) 2); // 设置为预售状态
            productMapper.updateProduct(product);
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
            redisTemplate.opsForValue().set(userKey, 1, 30, TimeUnit.MINUTES);
            return "秒杀成功";
        } else {
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

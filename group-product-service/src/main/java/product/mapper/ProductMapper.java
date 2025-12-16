package product.mapper;

import org.apache.ibatis.annotations.Mapper;
import product.pojo.Product;
import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> getAllProducts();
    
    Product getProductById(Long id);
    
    void insertProduct(Product product);
    
    void updateProduct(Product product);
    
    void deleteProduct(Long id);
}

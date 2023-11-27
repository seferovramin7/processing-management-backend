package az.processing.msuser.service.factory;

import az.processing.msuser.dto.generic.ProductDto;
import az.processing.msuser.dto.request.ProductSaveDto;
import java.util.List;

public interface ProductFactory {
    ProductDto createProduct(ProductSaveDto productDto);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);

    ProductDto updateProduct(Long id, ProductDto updatedProductDto);

    void deleteProduct(Long id);
}


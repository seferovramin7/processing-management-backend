package az.processing.msuser.service.genericImpl;

import az.processing.msuser.dto.generic.ProductDto;
import az.processing.msuser.dto.request.ProductSaveDto;
import az.processing.msuser.entity.ProductEntity;
import az.processing.msuser.repository.ProductRepository;
import az.processing.msuser.service.factory.ProductFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService implements ProductFactory {

    private final ProductRepository productRepository;

    public ProductDto createProduct(ProductSaveDto productDto) {
        log.info("Creating product: {}", productDto.getName());

        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDto.getName());
        productEntity.setPrice(productDto.getPrice());
        productEntity.setStockCount(productDto.getCount());

        ProductEntity savedEntity = productRepository.save(productEntity);
        log.info("Product created: {}", productDto.getName());
        return convertToDto(savedEntity);
    }

    public List<ProductDto> getAllProducts() {
        log.info("Fetching all products");
        List<ProductEntity> productEntities = productRepository.findAll();
        log.info("Fetched all products");
        return convertToDtoList(productEntities);
    }

    public ProductDto getProductById(Long id) {
        log.info("Fetching product with ID: {}", id);

        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);

        if (optionalProductEntity.isPresent()) {
            log.info("Fetched product: {}", optionalProductEntity.get().getName());

            return convertToDto(optionalProductEntity.get());
        } else {
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }

    public ProductDto updateProduct(Long id, ProductDto updatedProductDto) {
        log.info("Updating product with ID: {}", id);
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);

        if (optionalProductEntity.isPresent()) {
            log.info("Updated product: {}", optionalProductEntity.get().getName());
            ProductEntity existingEntity = optionalProductEntity.get();

            existingEntity.setName(updatedProductDto.getName());
            existingEntity.setPrice(updatedProductDto.getPrice());
            existingEntity.setStockCount(updatedProductDto.getCount());

            ProductEntity updatedEntity = productRepository.save(existingEntity);
            return convertToDto(updatedEntity);
        } else {
            log.error("Product not found with ID: {}", id);
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
        log.info("Product deleted with ID: {}", id);
    }

    private ProductDto convertToDto(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto();
        productDto.setId(productEntity.getId());
        productDto.setName(productEntity.getName());
        productDto.setPrice(productEntity.getPrice());
        productDto.setCount(productEntity.getStockCount());
        return productDto;
    }

    private List<ProductDto> convertToDtoList(List<ProductEntity> productEntities) {
        return productEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
}

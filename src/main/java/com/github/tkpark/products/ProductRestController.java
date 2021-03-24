package com.github.tkpark.products;

import com.github.tkpark.errors.NotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.github.tkpark.utils.ApiUtils.ApiResult;
import static com.github.tkpark.utils.ApiUtils.success;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("api/products")
public class ProductRestController {

    private final ProductService productService;

    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "id")
    public ApiResult<ProductDto> findById(@Valid @RequestBody  ProductRequest request) {
        return success(
                productService.findById(request.getId())
                        .map(ProductDto::new)
                        .orElseThrow(() -> new NotFoundException("Could not found product for " + request.getId()))
        );
    }

    @PostMapping(path = "all")
    public ApiResult<List<ProductDto>> findAll() {
        return success(productService.findAll().stream()
                .map(ProductDto::new)
                .collect(toList())
        );
    }

}
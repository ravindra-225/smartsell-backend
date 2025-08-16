package com.example.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.models.Products;
import com.example.models.Users;
import com.example.models.category;
import com.example.repository.CategoryRepository;
import com.example.repository.UserRepository;
import com.example.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	private final ProductService productService;
	private static final String UPLOAD_DIR = "src/main/resources/static/images/";
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
   
    @PostMapping
    public ResponseEntity<?> createProduct(@RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("price") Double price,
            @RequestParam("location") String location,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("image") MultipartFile image) {
        try {
        	System.out.println("Received parameters: title=" + title + ", description=" + description + ", price=" + price +
                    ", location=" + location + ", categoryId=" + categoryId); // Debug log
        	// Validate input
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("Title is required");
            }
        	
        	String fileName = StringUtils.cleanPath(image.getOriginalFilename());
            String extension = fileName.substring(fileName.lastIndexOf("."));
            String uniqueFileName = System.currentTimeMillis() + extension;
            Path uploadPath = Paths.get(UPLOAD_DIR + uniqueFileName);
            Files.createDirectories(uploadPath.getParent());
            Files.write(uploadPath, image.getBytes());
            String imageUrl = "/images/" + uniqueFileName; // Relative URL
            System.out.printf("Image saved at %s%n", imageUrl);
            
         // Set product details
            Products product = new Products();
            product.setTitle(title);
            product.setDescription(description);
            product.setPrice(price);
            product.setLocation(location);
            product.setImageUrl(imageUrl);

            // Set seller from authenticated user
            Users seller = getAuthenticatedSeller();
            if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                String email = userDetails.getUsername();
                seller = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Seller not found for email: " + email));
            } else {
                throw new RuntimeException("Seller is required");
            }
            product.setSeller(seller);
            System.out.printf("Seller set: %s%n", seller.getEmail());
            
            // Map categoryId to category object
            category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found for ID: " + categoryId));
            product.setCategory(category);

            Products savedProduct = productService.addProduct(product);
            System.out.println("Returning product: " + savedProduct.getId() + ", imageUrl: " + savedProduct.getImageUrl());
            return new ResponseEntity<>(savedProduct, HttpStatus.OK);
        } catch (Exception e) {
        	return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);        }
    }
    
    private Long extractCategoryIdFromRequest(Products product) {
        return product.getCategory() != null && product.getCategory().getId() != null
                ? product.getCategory().getId()
                : null;
    }

    private Long extractSellerIdFromRequest(Products product) {
        return product.getSeller() != null && product.getSeller().getId() != null
                ? product.getSeller().getId()
                : null;
    }
    
    private Users getAuthenticatedSeller() {
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
            SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String email = userDetails.getUsername();
            return userRepository.findByEmail(email).orElse(null);
        }
        return null;
    }

    @GetMapping
    public List<Products> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping("/{id}")
    public Products updateProduct(@PathVariable Long id, @RequestBody Products product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
    
    @GetMapping("/{id}")
    public Optional<Products> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    
    @GetMapping("/search")
    public List<Products> searchProducts(@RequestParam String keyword) {
        return productService.searchByKeyword(keyword);
    }

    @GetMapping("/category/{categoryId}")
    public List<Products> getProductsByCategory(@PathVariable Long categoryId) {
        return productService.getByCategory(categoryId);
    }

    @GetMapping("/location")
    public List<Products> getProductsByLocation(@RequestParam String location) {
        return productService.getByLocation(location);
    }

    @GetMapping("/price-range")
    public List<Products> getProductsByPriceBetween(@RequestParam double min,
                                                 @RequestParam double max) {
        return productService.getByPriceRange(min, max);
    }
}


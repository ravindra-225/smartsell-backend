package com.example.models;

import java.util.Optional;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Products {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@NotBlank(message = "Title is required")
    private String title;

    @Column(length = 2000)
    @Size(max = 2000, message = "Description can be up to 2000 characters")
    private String description;
    
    @Positive(message = "Price must be greater than zero")
    private double price;
    
    @NotBlank(message = "Location is required")
    private String location;
    
    @NotBlank(message = "Image URL is required")
    private String imageUrl;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    @NotNull(message = "Category is required")
    private category category;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users seller;
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}

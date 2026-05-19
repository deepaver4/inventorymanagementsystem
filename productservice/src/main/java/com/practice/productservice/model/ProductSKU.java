package com.practice.productservice.model;

import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ProductSKU {

    @Indexed(unique=true)
    private String skuCode;

    private int quantity;

}

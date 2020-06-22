package org.webshop.product;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;

@ApplicationScoped
@Transactional(REQUIRED)
public class ProductService {

    @Transactional(SUPPORTS)
    public List<Product> findAllProducts() {
        return Product.listAll();
    }

    @Transactional(SUPPORTS)
    public Product findProductById(Long id) {
        return Product.findById(id);
    }

    @Transactional(SUPPORTS)
    public Product findRandomProduct() {
        Product randomProduct = null;
        while (randomProduct == null) {
            randomProduct = Product.findRandomProduct();
        }
        return randomProduct;
    }

    public Product persistProduct(@Valid Product product) {
        Product.persist(product);
        return product;
    }

    public Product updateProduct(long id, String vendor) {
        Product entity = Product.findById(id);
        entity.vendor = vendor;
        Product.persist(entity);

        return entity;
    }
    public Product updateProduct(@Valid Product product) {
        Product entity = Product.findById(product.id);
        entity.name = product.name;
        entity.description = product.description;
        entity.price = product.price;
        entity.picture = product.picture;
        entity.sauce = product.sauce;
        entity.vendor = product.vendor;
        return entity;
    }

    public void deleteProduct(Long id) {
        Product product = Product.findById(id);
        product.delete();
    }
}
package org.webshop.cart;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import org.webshop.cart.client.CustomerService;
import org.webshop.cart.client.Product;
import org.webshop.cart.client.ProductService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.webshop.cart.relations.CartProduct;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.math.BigDecimal;
import java.util.List;

import static javax.transaction.Transactional.TxType.REQUIRED;
import static javax.transaction.Transactional.TxType.SUPPORTS;


// todo
@ApplicationScoped
@Transactional(REQUIRED)
public class CartService {

    @Inject
    @RestClient
    ProductService productService;

    @Inject
    @RestClient
    CustomerService customerService;

    @Transactional(SUPPORTS)
    public Cart findCartById(Long id) {
        return Cart.findById(id);
    }

    public Cart persistCart(@Valid Cart cart) {
        Cart.persist(cart);
        return cart;
    }

    public boolean CartForCustomerExists(long customerId) {
        if (Cart.findByCustomerId(customerId) != null) {
            return true;
        }
        return false;
    }

    public CartProduct createCartProduct(long productId, Cart cart){
        Product product = productService.getProductById(productId);
        BigDecimal bdQuantity = new BigDecimal(5);
        CartProduct cartProduct = new CartProduct(cart, product.id, 5, bdQuantity.multiply(bdQuantity));
        cartProduct.persist();
        return cartProduct;
    }

    public Cart addProductToCart(long cartId, long productId) {
        Cart cart = findCartById(cartId);
        CartProduct cartProduct = createCartProduct(productId, cart);

        cart.cartProducts.add(cartProduct);
        cart.persist();

        return cart;
    }
}
    /*

    public Cart removeProductFromCart (Long cartId, long productId) {
        Cart cart = findCartById(cartId);
        cart.products.removeIf(cartProduct -> cart.id == productId);
        cart.persist();

        return cart;
    }*/


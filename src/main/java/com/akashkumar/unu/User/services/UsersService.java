package com.akashkumar.unu.User.services;

import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Courier.entity.Courier;
import com.akashkumar.unu.Courier.repository.CourierRepository;
import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Dealer.repository.DealerRepository;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserFound;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserNotFound;
import com.akashkumar.unu.Order.OrderMapper;
import com.akashkumar.unu.Order.OrderRepository;
import com.akashkumar.unu.Order.Orders;
import com.akashkumar.unu.Order.OrdersDto;
import com.akashkumar.unu.Product.Products.Product.Product;
import com.akashkumar.unu.Product.Products.ProductRepository;
import com.akashkumar.unu.User.dto.AddToCart;
import com.akashkumar.unu.User.dto.UsersDto;
import com.akashkumar.unu.User.entity.Users;
import com.akashkumar.unu.User.mapper.UsersMapper;
import com.akashkumar.unu.User.repository.UsersRepository;
import com.akashkumar.unu.Utilities.Enums.OrderStatus;
import com.akashkumar.unu.Utilities.Enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersService implements UsersServices{
    @Autowired UsersRepository usersRepository;
    @Autowired AdminRepository adminRepository;
    @Autowired DealerRepository dealerRepository;
    @Autowired ProductRepository productRepository;
    @Autowired CourierRepository courierRepository;
    @Autowired OrderRepository orderRepository;

    @Override
    public UsersDto createUser(UsersDto usersDto) {
        Optional<Admin> checkAdmin = adminRepository.findByRole(Role.ADMIN);
        Optional<Users> checkUser = usersRepository.findByMobile(usersDto.getMobile());
        if (checkAdmin.isEmpty()){
            throw new UserNotFound("Admin Not Found ");
        }
        Admin admin = checkAdmin.get();
        Users users = UsersMapper.toEntity(usersDto);
        if (checkUser.isEmpty()){
            if (usersDto.getBankDetail()==null){
                usersDto.setBankDetail(null);
            }
            if (usersDto.getAddress()==null){
                usersDto.setAddress(null);
            }
            usersRepository.save(users);
            admin.getUsers().add(users.getUserId());
            admin.getActiveUsers().add(users.getUserId());
        }else{
            throw new UserFound("User already exists ");
        }
        adminRepository.save(admin);
        return UsersMapper.toDto(users);
    }

    @Override
    public UsersDto addToCart(AddToCart addToCart) {
        Optional<Dealer> checkDealer = dealerRepository.findById(addToCart.getDealerId());
        Optional<Users> checkUser = usersRepository.findById(addToCart.getUserId());
        Optional<Product> checkProduct = productRepository.findById(addToCart.getProductId());
        if (checkDealer.isEmpty()){
            throw new UserNotFound("Dealer Not Found ");
        }
        if (checkUser.isEmpty()){
            throw new UserNotFound("User Not Found");
        }
        if (checkProduct.isEmpty()){
            throw new RuntimeException("Product Not Found");
        }

        //dealer ke bad check karo ki ye product exists karata hai dealer ke account mai
        Dealer dealer = checkDealer.get();
        if(!dealer.getAllProductsIds().contains(addToCart.getProductId())){
            throw new RuntimeException("Product Not Found");
        }

        Product product = checkProduct.get();
        if (product.getInBagQuantity()<=0){
            throw new RuntimeException("Product is not Available");
        }


        Users users = checkUser.get();
        if (users.getCarts().contains(addToCart.getProductId())){
            throw new RuntimeException("Product already add to cart");
        }
        users.getCarts().add(addToCart.getProductId());
        usersRepository.save(users);
        //add product in user cart list

        return UsersMapper.toDto(users);
    }

    @Override
    public List<String> getAllProducts(AddToCart addToCart) {
        Optional<Users> checkUser = usersRepository.findById(addToCart.getUserId());
        Users users = checkUser.get();
        return users.getCarts();
    }

    @Override
    public OrdersDto createOrder(OrdersDto ordersDto) {
        Optional<Users> checkUser = usersRepository.findById(ordersDto.getUserId());
        if (checkUser.isEmpty()){
            throw new RuntimeException("User Not Found ");
        }
        Users users = checkUser.get();
        List<String> productIds = users.getCarts();

        for (String productId : productIds) {
            Optional<Product> checkProduct = productRepository.findById(productId);
            if (checkProduct.isEmpty()) {
                throw new RuntimeException("Product not found: " + productId);
            }

            Product product = checkProduct.get();
            if (!product.isCodAvailable()) {
                throw new RuntimeException("COD not available for product: " + product.getProductId());
            }

            productRepository.save(product);
        }

        Orders orders = OrderMapper.toOrderEntity(ordersDto);
        orders.setOrderStatus(OrderStatus.SHIPPED);
        Orders savedOrder = orderRepository.save(orders);

        //order hone ke bad
        //store order id in user order
        //store order id in courier
        //store order id in dealer


        // clear user cart after successful order
        Users user = usersRepository.findById(ordersDto.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        user.getCarts().clear();
        user.getOrders().add(savedOrder.getOrderId());
        usersRepository.save(user);

        Optional<Dealer> checkDealer = dealerRepository.findById(ordersDto.getDealerId());
        if (checkDealer.isEmpty()){
            throw new RuntimeException("Dealer Not Found ");
        }
        Dealer dealer = checkDealer.get();

        //Assign Courier
        Optional<Courier> checkCourier = courierRepository.findByCourierCity(user.getAddress().getUserCity());
        System.out.println("--------------> "+checkCourier);
        //create list of courier if need i have to work only one courier in one city
        if (checkCourier.isEmpty()){
            throw new RuntimeException("Courier Not Found : Pending ");
        }

        Courier courier = checkCourier.get();
        courier.getOrdersList().add(savedOrder.getOrderId());
        courier.getDealers().add(dealer.getDealerId());
        courierRepository.save(courier);

        orders.setDealerId(dealer.getDealerId());
        orders.setCourierId(courier.getCourierId());
        orderRepository.save(orders);

        //set orders in dealer

        // Courier, Dealer, and rest of logic same...
        dealer.getShippedOrdersIds().add(orders.getOrderId());
        dealerRepository.save(dealer);
        return OrderMapper.toOrderDto(savedOrder);
    }
}
interface UsersServices{
    UsersDto createUser(UsersDto usersDto);
    UsersDto addToCart(AddToCart addToCart);
    List<String> getAllProducts(AddToCart addToCart);
    OrdersDto createOrder(OrdersDto ordersDto);
}

package com.akashkumar.unu.User.controller;

import com.akashkumar.unu.ExceptionHandling.ApiResponse;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserNotFound;
import com.akashkumar.unu.ForgotPassword.ForgotPasswordService;
import com.akashkumar.unu.Order.OrdersDto;
import com.akashkumar.unu.Product.Products.Dto.ProductDto;
import com.akashkumar.unu.Product.Products.Mapper.ProductMapper;
import com.akashkumar.unu.Product.Products.Product.Product;
import com.akashkumar.unu.Product.Products.ProductRepository;
import com.akashkumar.unu.User.dto.AddToCart;
import com.akashkumar.unu.User.dto.AddToCartResponse;
import com.akashkumar.unu.User.dto.Login.LoginRequest;
import com.akashkumar.unu.User.dto.Login.LoginResponse;
import com.akashkumar.unu.User.dto.UsersDto;
import com.akashkumar.unu.User.entity.Users;
import com.akashkumar.unu.User.repository.UsersRepository;
import com.akashkumar.unu.User.services.UsersService;
import com.akashkumar.unu.Utilities.Enums.Role;
import com.akashkumar.unu.Utilities.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Urls.Baseurl+"/user")
public class UsersController implements UsersServices{

    @Autowired
    UsersService usersService;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ForgotPasswordService service;


    @Override
    @PostMapping("/register")
    public ResponseEntity<?> createUser(UsersDto usersDto) {
        UsersDto usersDto1 = usersService.createUser(usersDto);
        ApiResponse<UsersDto> apiResponse = new ApiResponse<>("User Created Successfully ", usersDto1);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        Optional<Users> users1 = usersRepository.findByUserMobile(loginRequest.getUserMobile());
        if (users1.isEmpty()){
            throw new UserNotFound("User Not Found");
        }
        Users user = users1.get();
        if (user.isBlock()){ //true
            throw new RuntimeException("You're Block. Please contect with admin");
        }
        if (user.getUserMobile().equals(loginRequest.getUserMobile()) && user.getUserPassword().equals(loginRequest.getUserPassword())){
            LoginResponse loginResponse = getLoginResponse(user);
            ApiResponse<LoginResponse> loginResponseApiResponse = new ApiResponse<>("User Login Successfully ", loginResponse);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponseApiResponse);
        }else {
            throw new RuntimeException("Something went wrong");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam Role role, @RequestParam String email) {
        return ResponseEntity.ok(service.requestToSendRestLink(role, email));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam Role role,
            @RequestParam String otp,
            @RequestParam String newPassword
    ) {
        return ResponseEntity.ok(service.requestToResetPassword(role,newPassword, otp));
    }

    @Override
    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(AddToCart addToCart) {
        Optional<Product> getMyProduct = productRepository.findById(addToCart.getProductId());
        if (getMyProduct.isEmpty()){
            throw new RuntimeException("Product Not Found : UserController");
        }
        usersService.addToCart(addToCart);
        Product productData = getMyProduct.get();
        ApiResponse<Product> loginResponseApiResponse = new ApiResponse<Product>("Product added in Cart Successfully ",productData);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseApiResponse);
    }

    @Override
    @PostMapping("/removeToCart")
    public ResponseEntity<?> removeToCart(AddToCart addToCart) {
        Optional<Product> getMyProduct = productRepository.findById(addToCart.getProductId());
        Optional<Users> checkUser = usersRepository.findById(addToCart.getUserId());
        if (getMyProduct.isEmpty()){
            throw new RuntimeException("Product Not Found ");
        }
        if (checkUser.isEmpty()){
            throw new RuntimeException("User Not Found ");
        }
        Users users = checkUser.get();
        if (!users.getCarts().contains(addToCart.getProductId())){
            throw new RuntimeException("Cart Not Found ");
        }
        users.getCarts().remove(addToCart.getProductId());
        usersRepository.save(users);
        ApiResponse<?> apiResponse = new ApiResponse<>("Product Removed Successfully ",null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Override
    @PostMapping("/getAllCarts")
    public ResponseEntity<?> getAllCartsProducts(@RequestBody AddToCart addToCart) {
        List<String> getMyAllProducts = usersService.getAllProducts(addToCart);
        List<ProductDto> productDtos = new ArrayList<>();

        double totalPrice = 0.0;
        int totalProducts = getMyAllProducts.size();

        for (String productId : getMyAllProducts) {
            Optional<Product> checkProduct = productRepository.findById(productId);
            if (checkProduct.isPresent()) {
                ProductDto productDto = ProductMapper.toDto(checkProduct.get());
                totalPrice = totalPrice +  productDto.getPrice();
                productDtos.add(productDto);
            } else {
                System.out.println("Product not found for ID: " + productId);
            }
        }

        AddToCartResponse<?> productDtoAddToCartResponse = new AddToCartResponse<>(
                "All Carts", totalPrice, totalProducts, productDtos
        );

//        ApiResponse<?> apiResponse = new ApiResponse<>("All Users Carts", productDtos);
        return ResponseEntity.status(HttpStatus.OK).body(productDtoAddToCartResponse);
    }



    @Override
    @PostMapping("/order-product")
    public ResponseEntity<?> createOrder(OrdersDto ordersDto) {
        OrdersDto ordersDTO =  usersService.createOrder(ordersDto);
        ApiResponse<OrdersDto> loginResponseApiResponse = new ApiResponse<>("Ordered Successfully ",ordersDTO);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseApiResponse);
    }


    private static LoginResponse getLoginResponse(Users users) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(users.getUserId());

        loginResponse.setUserName(users.getUserName());
        loginResponse.setUserEmail(users.getUserEmail());
        loginResponse.setUserPassword(users.getUserPassword());
        loginResponse.setUserMobile(users.getUserMobile());
        loginResponse.setRole(users.getRole());
        loginResponse.setTotalSpend(users.getTotalSpend());
        loginResponse.setActive(users.isActive());
        loginResponse.setBlock(users.isBlock());
        loginResponse.setCheckBank(users.isCheckBank());
        loginResponse.setCheckAddress(users.isCheckAddress());
        return loginResponse;
    }
}
interface UsersServices{
    ResponseEntity<?> createUser(@RequestBody UsersDto usersDto);
    ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest);
    ResponseEntity<?> addToCart(@RequestBody AddToCart addToCart);
    ResponseEntity<?> removeToCart(@RequestBody AddToCart addToCart);
    ResponseEntity<?> getAllCartsProducts(@RequestBody AddToCart addToCart);
    ResponseEntity<?> createOrder(@RequestBody OrdersDto ordersDto);
}

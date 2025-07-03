package com.akashkumar.unu.Courier.controller;

import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Courier.dto.ChangeOrderStatus;
import com.akashkumar.unu.Courier.dto.CourierDto;
import com.akashkumar.unu.Courier.dto.Login.LoginRequest;
import com.akashkumar.unu.Courier.dto.Login.CourierLoginResponse;
import com.akashkumar.unu.Courier.entity.Courier;
import com.akashkumar.unu.Courier.repository.CourierRepository;
import com.akashkumar.unu.Courier.services.CourierServices;
import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Dealer.repository.DealerRepository;
import com.akashkumar.unu.ExceptionHandling.ApiResponse;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserNotFound;
import com.akashkumar.unu.ForgotPassword.ForgotPasswordService;
import com.akashkumar.unu.Order.OrderRepository;
import com.akashkumar.unu.Order.Orders;
import com.akashkumar.unu.Product.Products.Product.Product;
import com.akashkumar.unu.Product.Products.ProductRepository;
import com.akashkumar.unu.User.entity.Users;
import com.akashkumar.unu.User.repository.UsersRepository;
import com.akashkumar.unu.Utilities.Enums.OrderStatus;
import com.akashkumar.unu.Utilities.Enums.Role;
import com.akashkumar.unu.Utilities.Urls;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(Urls.Baseurl+"/courier")
public class CourierController implements CourierService{
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    CourierServices courierServices;

    @Autowired
    ForgotPasswordService service;

    @Autowired
    DealerRepository dealerRepository;


    @Autowired
    CourierRepository courierRepository;

    @Autowired
    UsersRepository usersRepository;


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;



    @Override
    @PostMapping("/register")
    public ResponseEntity<?> createCourier(CourierDto courierDto) {
//        CourierDto courierDto1 = courierServices.createCourier(courierDto);
        CourierDto courierDto1 = courierServices.createCourier(courierDto);
        ApiResponse<CourierDto> apiResponse = new ApiResponse<>("Courier Register Successfully ", courierDto1);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> loginCourier(LoginRequest loginRequest) {
        Optional<Courier> courier1 = courierRepository.findByMobile(loginRequest.getMobile());
        if (courier1.isEmpty()){
            throw new UserNotFound("Courier Not Found");
        }
        Courier courier = courier1.get();
        if (courier.isBlock()){
            throw new RuntimeException("You're Block. Please contect with admin");
        }
        if (courier.getMobile().equals(loginRequest.getMobile()) && courier.getPassword().equals(loginRequest.getPassword())){
            CourierLoginResponse courierLoginResponse = getLoginResponse(courier);
            ApiResponse<CourierLoginResponse> loginResponseApiResponse = new ApiResponse<>("Courier Login Successfully ", courierLoginResponse);
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
    @PostMapping("/changeOrderStatus")
    public ResponseEntity<?> changeStatus(ChangeOrderStatus changeOrderStatus) {
        Optional<Dealer> checkDealer = dealerRepository.findById(changeOrderStatus.getDealerId());
        Optional<Courier> checkCourier = courierRepository.findById(changeOrderStatus.getCourierId());
        Optional<Users> checkUser = usersRepository.findById(changeOrderStatus.getUserId());
        Optional<Orders> checkOrder = orderRepository.findByOrderId(changeOrderStatus.getOrderId());


        if (checkDealer.isEmpty()){
            throw new RuntimeException("Dealer Not Found ");
        }
        if (checkCourier.isEmpty()){
            throw new RuntimeException("Courier Not Found ");
        }
        if (checkUser.isEmpty()){
            throw new RuntimeException("User Not Found ");
        }
        if (checkOrder.isEmpty()){
            throw new RuntimeException("Order Not Found ");
        }
        Dealer dealer = checkDealer.get();
        Courier courier = checkCourier.get();
//        Users users = checkUser.get();
        Orders orders = checkOrder.get();
        if(changeOrderStatus.getStatus().equals(OrderStatus.CONFIRM)){
            orders.setOrderStatus(OrderStatus.CONFIRM);
            orderRepository.save(orders);

            courier.getOrdersList().remove(changeOrderStatus.getOrderId());
            courier.getConfirmOrdersIds().add(changeOrderStatus.getOrderId());
            courierRepository.save(courier);

            dealer.getShippedOrdersIds().remove(changeOrderStatus.getOrderId());
            dealer.getConfirmOrdersIds().add(changeOrderStatus.getOrderId());
            dealerRepository.save(dealer);
            ApiResponse<?> apiResponse = new ApiResponse<>("Order Confirm Successfully", null);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

        }else if(changeOrderStatus.getStatus().equals(OrderStatus.OUT_FOR_DELIVERY)){
            orders.setOrderStatus(OrderStatus.OUT_FOR_DELIVERY);
            orderRepository.save(orders);

            courier.getConfirmOrdersIds().remove(changeOrderStatus.getOrderId());
            courier.getOutOfDelivery().add(changeOrderStatus.getOrderId());
            courierRepository.save(courier);

            dealer.getConfirmOrdersIds().remove(changeOrderStatus.getOrderId());
            dealer.getOutOfDeliveryOrdersIds().add(changeOrderStatus.getOrderId());
            dealerRepository.save(dealer);
            ApiResponse<?> apiResponse = new ApiResponse<>("Order Out For Delivery ...", null);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }else{

            double courierPercent = 10.0;
            double dealerPercent = 50.0;
            double adminPercent = 40.0;
            double courierMoney = Math.round((orders.getTotalPrice() * courierPercent) / 100 * 100.0) / 100.0;
            double dealerMoney = Math.round((orders.getTotalPrice() * dealerPercent) / 100 * 100.0) / 100.0;
            double adminMoney = Math.round((orders.getTotalPrice() * adminPercent) / 100 * 100.0) / 100.0;



            //this is delivered
            orders.setOrderStatus(OrderStatus.DELIVERED);
            orderRepository.save(orders);

            courier.getOutOfDelivery().remove(changeOrderStatus.getOrderId());
            courier.getDelivered().add(changeOrderStatus.getOrderId());
            double totalCourierMoney = courier.getTotalEarning();
            courier.setTotalEarning(totalCourierMoney + courierMoney);
            courierRepository.save(courier);

            Users users = checkUser.get();
            //handle sale product
            Optional<Orders> myPerticularOrder = orderRepository.findByOrderId(changeOrderStatus.getOrderId());
            if (myPerticularOrder.isEmpty()){
                throw new RuntimeException("Something went wrong : Order Not Found ");
            }
            Orders myOrder = myPerticularOrder.get();
            List<String> myProduct = myOrder.getCarts();
            for (String ids : myProduct){
                Optional<Product> checkMyProduct = productRepository.findById(ids);
                if (checkMyProduct.isEmpty()){
                    throw new RuntimeException("Something went wrong : Product Not Found");
                }
                Product product = checkMyProduct.get();
                product.setInBagQuantity(product.getInBagQuantity()-1);
                if (product.getInBagQuantity()==0){
                    product.setSold(true);
                }
                productRepository.save(product);
            }
            //handle sale product

            users.getOrders().remove(orders.getOrderId());
            users.getOrderedHistory().add(orders.getOrderId());
            usersRepository.save(users);

            dealer.getOutOfDeliveryOrdersIds().remove(changeOrderStatus.getOrderId());
            dealer.getDelivered().add(changeOrderStatus.getOrderId());
            double totalDealerMoney = dealer.getTotalEarning();
            dealer.setTotalEarning(totalDealerMoney + dealerMoney);
            if (!dealer.getUsersIds().contains(users.getUserId())){
                dealer.getUsersIds().add(users.getUserId());
            }
            if (!dealer.getCouriersIds().contains(courier.getCourierId())){
                dealer.getCouriersIds().add(courier.getCourierId());
            }

            dealerRepository.save(dealer);

            Optional<Admin> checkAdmin = adminRepository.findByRole(Role.ADMIN);
            if (checkAdmin.isEmpty()){
                throw new RuntimeException("Admin Not Found : Courier Controller ");
            }
            Admin admin = checkAdmin.get();
            double totalAdminMoney = admin.getTotalEarning();
            admin.setTotalEarning(totalAdminMoney + adminMoney);
            adminRepository.save(admin);

            ApiResponse<?> apiResponse = new ApiResponse<>("Order Delivered Successfully ...", null);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }
    }

    private static CourierLoginResponse getLoginResponse(Courier courier) {
        CourierLoginResponse courierLoginResponse = new CourierLoginResponse();
        courierLoginResponse.setCourierId(courier.getCourierId());
        courierLoginResponse.setName(courier.getName());
        courierLoginResponse.setEmail(courier.getEmail());
        courierLoginResponse.setEmail(courier.getMobile());
        courierLoginResponse.setPassword(courier.getPassword());
//        courierLoginResponse.setDealerName(courier.getDealerName());
        courierLoginResponse.setRole(courier.getRole());
        courierLoginResponse.setTotalEarning(courier.getTotalEarning());
        courierLoginResponse.setActive(courier.isActive());
        courierLoginResponse.setBlock(courier.isBlock());
        courierLoginResponse.setCheckBank(courier.isCheckBank());
        courierLoginResponse.setCheckAddress(courier.isCheckAddress());
        return courierLoginResponse;
    }
}
interface CourierService{
    ResponseEntity<?> createCourier(@RequestBody CourierDto courierDto);
    ResponseEntity<?> loginCourier(@RequestBody LoginRequest loginRequest);
    ResponseEntity<?> changeStatus(@RequestBody ChangeOrderStatus changeOrderStatus);
}

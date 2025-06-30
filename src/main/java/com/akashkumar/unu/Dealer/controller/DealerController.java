package com.akashkumar.unu.Dealer.controller;

import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Dealer.dto.DealerDto;
import com.akashkumar.unu.Dealer.dto.Login.LoginRequest;
import com.akashkumar.unu.Dealer.dto.Login.LoginResponse;
import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Dealer.repository.DealerRepository;
import com.akashkumar.unu.Dealer.services.DealerService;
import com.akashkumar.unu.ExceptionHandling.ApiResponse;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserNotFound;
import com.akashkumar.unu.ForgotPassword.ForgotPasswordService;
import com.akashkumar.unu.Product.Category.CategoryType.CategoryType;
import com.akashkumar.unu.Product.Category.CategoryType.UpdateCategoryType;
import com.akashkumar.unu.Product.Category.SubCategory.*;
import com.akashkumar.unu.Product.Category.CategoryType.CategoryTypeRepository;
import com.akashkumar.unu.Product.Category.Entity.Category;
import com.akashkumar.unu.Product.Category.Entity.SubCategory;
import com.akashkumar.unu.Product.Category.dto.CategoryDto;
import com.akashkumar.unu.Product.Category.dto.RemoveCategory;
import com.akashkumar.unu.Product.Category.CategoryType.RemoveCategoryType;
import com.akashkumar.unu.Product.Category.dto.UpdateCategoryDto;
import com.akashkumar.unu.Product.Category.repository.CategoryRepository;
import com.akashkumar.unu.Product.Category.repository.SubCategoryRepository;
import com.akashkumar.unu.Product.Products.Dto.ProductDto;
import com.akashkumar.unu.Product.Products.Dto.RemoveProduct;
import com.akashkumar.unu.Product.Products.Dto.UpdateProductDto;
import com.akashkumar.unu.Product.Products.Product.Product;
import com.akashkumar.unu.Product.Products.ProductRepository;
import com.akashkumar.unu.User.entity.Users;
import com.akashkumar.unu.User.repository.UsersRepository;
import com.akashkumar.unu.Utilities.Enums.Role;
import com.akashkumar.unu.Utilities.Urls;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(Urls.Baseurl+"/dealer")
public class DealerController implements DealerControllers {
    /// /api/sedulous/unu-ecommerce/Dealer/register

    @Autowired
    DealerRepository dealerRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ForgotPasswordService service;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    DealerService dealerService;

    @Autowired
    DealerService productService;


    @Autowired
    CategoryTypeRepository categoryTypeRepository;
    @Autowired
    SubCategoryTypeRepository subCategoryTypeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SubCategoryRepository subCategoryRepository;


    @Override
    @PostMapping("/register")
    public ResponseEntity<?> registerDealerAccount(DealerDto dto) {
        DealerDto dealerDto = dealerService.registerDealerAccount(dto);
        ApiResponse<DealerDto> apiResponse = new ApiResponse<>("Dealer Registration Successfully ", dealerDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    @PostMapping("/login")
    public ResponseEntity<?> loginDealerAccount(LoginRequest loginRequest) {
        Optional<Dealer> dealer1 = dealerRepository.findByDealerMobile(loginRequest.getDealerMobile());
        if (dealer1.isEmpty()){
            throw new UserNotFound("Dealer Not Found");
        }
        Dealer dealer = dealer1.get();
        if (dealer.isBlock()){
            throw new RuntimeException("You'r blocked. Please contect with admin");
        }
        if (dealer.getDealerMobile().equals(loginRequest.getDealerMobile()) && dealer.getDealerPassword().equals(loginRequest.getDealerPassword())){
            LoginResponse loginResponse = getLoginResponse(dealer);
            ApiResponse<LoginResponse> loginResponseApiResponse = new ApiResponse<>("Dealer Login Successfully ", loginResponse);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponseApiResponse);
        }else {
            throw new RuntimeException("Something went wrong");
        }
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam Role role, @RequestParam String email) {
        String checkResponse = service.requestToSendRestLink(role, email);
        if (checkResponse.equals("user")){
            return ResponseEntity.status(HttpStatus.OK).body(checkResponse);
        }else if (checkResponse.equals("admin")){
            return ResponseEntity.status(HttpStatus.OK).body(checkResponse);
        }else if (checkResponse.equals("dealer")){
            return ResponseEntity.status(HttpStatus.OK).body(checkResponse);
        }else{

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");

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
    @PostMapping("/create-category")
    public ResponseEntity<?> createCategory(CategoryDto categoryDto) {
        CategoryDto dto = dealerService.createCategory(categoryDto);
        ApiResponse<CategoryDto> response = new ApiResponse<>("Category Created Successfully", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PostMapping("/remove-category")
    public ResponseEntity<?> removeCategory(RemoveCategory categoryDto) {
        Optional<Category> checkCategory = categoryRepository.findById(categoryDto.getCategoryId());
        Optional<Dealer> checkDealer = dealerRepository.findById(categoryDto.getDealerId());

        if (checkCategory.isEmpty()){
            throw new RuntimeException("Category Not Found ");
        }
        if (checkDealer.isEmpty()){
            throw new RuntimeException("Dealer Not Found ");
        }

        categoryRepository.deleteById(categoryDto.getCategoryId());
        Dealer dealer = checkDealer.get();
        dealer.getCategoriesIds().remove(categoryDto.getCategoryId());
        dealerRepository.save(dealer);

        ApiResponse<?> apiResponse = new ApiResponse<>("Category Deleted Successfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Override
    @PutMapping("/update-category")
    public ResponseEntity<?> updateCategory(UpdateCategoryDto categoryDto) {
        Optional<Category> checkCategory = categoryRepository.findById(categoryDto.getCategoryId());
        Optional<Dealer> checkDealer = dealerRepository.findById(categoryDto.getDealerId());

        if (checkCategory.isEmpty()){
            throw new RuntimeException("Category Not Found ");
        }
        if (checkDealer.isEmpty()){
            throw new RuntimeException("Dealer Not Found ");
        }

        Category category = checkCategory.get();
        category.setCategoryName(categoryDto.getCategoryName());
        categoryRepository.save(category);

        ApiResponse<?> apiResponse = new ApiResponse<>("Category Updated Successfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Override
    @PostMapping("/create-subCategory")
    public ResponseEntity<?> createSubCategory(SubCategoryDto subCategoryDto) {
        SubCategoryDto dto = dealerService.subCreateCategory(subCategoryDto);
        ApiResponse<SubCategoryDto> response = new ApiResponse<>("Sub Category Created Successfully", dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    @PostMapping("/remove-subCategory")
    public ResponseEntity<?> removeSubCategory(RemoveSubCategory categoryDto) {
        Optional<SubCategory> checkSubCategory = subCategoryRepository.findById(categoryDto.getCategoryId());
        Optional<Dealer> checkDealer = dealerRepository.findById(categoryDto.getDealerId());

        if (checkSubCategory.isEmpty()){
            throw new RuntimeException("SubCategory Not Found ");
        }
        if (checkDealer.isEmpty()){
            throw new RuntimeException("Dealer Not Found ");
        }

        subCategoryRepository.deleteById(categoryDto.getSubCategoryId());
        Dealer dealer = checkDealer.get();
        dealer.getSubCategoriesIds().remove(categoryDto.getSubCategoryId());
        dealerRepository.save(dealer);

        ApiResponse<?> apiResponse = new ApiResponse<>("SubCategory Deleted Successfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Override
    @PostMapping("/update-subCategory")
    public ResponseEntity<?> updateSubCategory(UpdateSubCategoryDto updateSubCategoryDto) {
        Optional<Category> checkCategory = categoryRepository.findById(updateSubCategoryDto.getCategoryId());
        Optional<SubCategory> checkSubCategory = subCategoryRepository.findById(updateSubCategoryDto.getSubCategoryId());
        Optional<Dealer> checkDealer = dealerRepository.findById(updateSubCategoryDto.getDealerId());

        if (checkSubCategory.isEmpty()){
            throw new RuntimeException("SubCategory Not Found ");
        }
        if (checkCategory.isEmpty()){
            throw new RuntimeException("Category Not Found ");
        }
        if (checkDealer.isEmpty()){
            throw new RuntimeException("Dealer Not Found ");
        }

        SubCategory subCategory = checkSubCategory.get();
        subCategory.setSubCategoryName(updateSubCategoryDto.getSubCategoryName());
        subCategory.setSubCategoryType(updateSubCategoryDto.getSubCategoryType());
        subCategoryRepository.save(subCategory);

        ApiResponse<?> apiResponse = new ApiResponse<>("SubCategory Updated Successfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

    @Override
    @PostMapping("/create-categoryType")
    public ResponseEntity<?> createCategoryType(CategoryType categoryType) {
        Optional<Dealer> checkDealer = dealerRepository.findByDealerId(categoryType.getDealerId());
        if (checkDealer.isEmpty()){
            throw new UserNotFound("Dealer not found ");
        }
        Dealer dealer = checkDealer.get();
        Optional<CategoryType> checkType = categoryTypeRepository.findByCategoryType(categoryType.getCategoryType());
        if (checkType.isPresent()){
            throw new RuntimeException("Category Type is already exists ");
        }
        CategoryType savedCategory =  categoryTypeRepository.save(categoryType);

        dealer.getAllCategoryTypes().add(savedCategory.getCategoryTypeId());
        dealerRepository.save(dealer);
        ApiResponse<CategoryType> apiResponse = new ApiResponse<>("Category Type created successfully ", savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);

    }

    @Override
    @PostMapping("/remove-categoryType")
    public ResponseEntity<?> removeCategoryType(RemoveCategoryType categoryType) {
        Optional<CategoryType> checkCategoryType = categoryTypeRepository.findById(categoryType.getCategoryTypeId());
        Optional<Dealer> checkDealer = dealerRepository.findByDealerId(categoryType.getDealerId());

        if (checkCategoryType.isEmpty()){
            throw new RuntimeException("Category Type Not Found ");
        }
        if (checkDealer.isEmpty()){
            throw new RuntimeException("Dealer Not Found ");
        }

        categoryTypeRepository.deleteById(categoryType.getCategoryTypeId());
        Dealer dealer = checkDealer.get();
        dealer.getAllCategoryTypes().remove(categoryType.getCategoryTypeId());
        dealerRepository.save(dealer);

        ApiResponse<CategoryType> apiResponse = new ApiResponse<>("Category Type Deleted successfully ", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    @PutMapping("/update-categoryType")
    public ResponseEntity<?> updateCategoryType(UpdateCategoryType categoryType) {
        Optional<CategoryType> checkCategoryType = categoryTypeRepository.findById(categoryType.getCategoryTypeId());
        Optional<Dealer> checkDealer = dealerRepository.findByDealerId(categoryType.getDealerId());

        if (checkCategoryType.isEmpty()){
            throw new RuntimeException("Category Type Not Found ");
        }
        if (checkDealer.isEmpty()){
            throw new RuntimeException("Dealer Not Found ");
        }
        CategoryType categoryTypeData = checkCategoryType.get();
        categoryTypeData.setCategoryType(categoryType.getCategoryType());
        categoryTypeRepository.save(categoryTypeData);

        ApiResponse<CategoryType> apiResponse = new ApiResponse<>("Category Type Updated successfully ", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    @PostMapping("/create-subCategoryType")
    public ResponseEntity<?> createSubCategoryType(SubCategoryType subCategoryType) {
        Optional<Dealer> checkDealer = dealerRepository.findByDealerId(subCategoryType.getDealerId());
        Optional<CategoryType> checkCategoryType = categoryTypeRepository.findById(subCategoryType.getCategoryTypeId());
        Optional<SubCategoryType> checkSubCategoryType = subCategoryTypeRepository.findBySubCategoryType(subCategoryType.getSubCategoryType());

        if (checkDealer.isEmpty()){
            throw new UserNotFound("Dealer not found ");
        }

        if (checkCategoryType.isEmpty()){
            throw new RuntimeException("Category Type Not found ");
        }

        if (checkSubCategoryType.isPresent()){
            throw new RuntimeException("Sub Category is already exits");
        }

        Dealer dealer = checkDealer.get();
        CategoryType category = checkCategoryType.get();

        SubCategoryType savedCategory = subCategoryTypeRepository.save(subCategoryType);
        category.getListSubCategory().add(savedCategory.getSubCategoryTypeId());
        categoryTypeRepository.save(category);
        dealer.getAllSubCategoryTypes().add(savedCategory.getSubCategoryTypeId());
        dealerRepository.save(dealer);

        ApiResponse<SubCategoryType> apiResponse = new ApiResponse<>("SubCategory Type created successfully ", savedCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    @PostMapping("/remove-subCategoryType")
    public ResponseEntity<?> removeSubCategoryType(SubCategoryType subCategoryType) {
        Optional<Dealer> checkDealer = dealerRepository.findByDealerId(subCategoryType.getDealerId());
        Optional<CategoryType> checkCategoryType = categoryTypeRepository.findById(subCategoryType.getCategoryTypeId());
        Optional<SubCategoryType> checkSubCategoryType = subCategoryTypeRepository.findBySubCategoryType(subCategoryType.getSubCategoryType());

        if (checkDealer.isEmpty()){
            throw new UserNotFound("Dealer not found ");
        }

        if (checkCategoryType.isEmpty()){
            throw new RuntimeException("Category Type Not found ");
        }

        if (checkSubCategoryType.isPresent()){
            throw new RuntimeException("Sub Category is already exits");
        }

        Dealer dealer = checkDealer.get();
        CategoryType category = checkCategoryType.get();
        subCategoryTypeRepository.deleteById(subCategoryType.getSubCategoryTypeId());

        category.getListSubCategory().remove(subCategoryType.getSubCategoryTypeId());
        categoryTypeRepository.save(category);
        dealer.getAllSubCategoryTypes().remove(subCategoryType.getSubCategoryTypeId());
        dealerRepository.save(dealer);

        ApiResponse<SubCategoryType> apiResponse = new ApiResponse<>("SubCategory Type deleted successfully ", null);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @Override
    @PutMapping("/update-subCategoryType")
    public ResponseEntity<?> updateSubCategoryType(UpdateSubCategoryType subCategoryType) {
        Optional<Dealer> checkDealer = dealerRepository.findByDealerId(subCategoryType.getDealerId());
        Optional<CategoryType> checkCategoryType = categoryTypeRepository.findById(subCategoryType.getCategoryTypeId());
        Optional<SubCategoryType> checkSubCategoryType = subCategoryTypeRepository.findBySubCategoryType(subCategoryType.getSubCategoryType());

        if (checkDealer.isEmpty()){
            throw new UserNotFound("Dealer not found ");
        }

        if (checkCategoryType.isEmpty()){
            throw new RuntimeException("Category Type Not found ");
        }

        if (checkSubCategoryType.isEmpty()){
            throw new RuntimeException("Sub Category Not found");
        }


        SubCategoryType subCategoryType1 = checkSubCategoryType.get();

        subCategoryType1.setSubCategoryTypeName(subCategoryType.getSubCategoryTypeName());
        subCategoryType1.setSubCategoryType(subCategoryType.getSubCategoryType());
        subCategoryTypeRepository.save(subCategoryType1);


        ApiResponse<SubCategoryType> apiResponse = new ApiResponse<>("SubCategory Type Updated successfully ", subCategoryType1);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    private static LoginResponse getLoginResponse(Dealer dealer) {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setDealerId(dealer.getDealerId());
        loginResponse.setDealerName(dealer.getDealerName());
        loginResponse.setDealerEmail(dealer.getDealerEmail());
        loginResponse.setDealerMobile(dealer.getDealerMobile());
        loginResponse.setDealerPassword(dealer.getDealerPassword());
        loginResponse.setRole(dealer.getRole());
        loginResponse.setTotalEarning(dealer.getTotalEarning());
        loginResponse.setActive(dealer.isActive());
        loginResponse.setBlock(dealer.isBlock());
        loginResponse.setCheckBank(dealer.isCheckBank());
        loginResponse.setCheckAddress(dealer.isCheckAddress());
        return loginResponse;
    }
    @Override
    @PostMapping(value = "/create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@RequestPart("product") String productJson, @RequestPart("image") MultipartFile multipartFile) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto = objectMapper.readValue(productJson, ProductDto.class);
        ProductDto createdProduct = productService.createProduct(productDto, multipartFile);

        //CategoryProduct -> subcategory.empty || subCategoryType.empty
        if (productDto.getSubCategoryId().isEmpty() && productDto.getSelectedSubCategoryType().isEmpty()){
            ApiResponse<ProductDto> apiResponse = new ApiResponse<>("Category Product created Successfully", createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }else{
            ApiResponse<ProductDto> apiResponse = new ApiResponse<>("SubCategory Product created Successfully", createdProduct);
            return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
        }
    }

    @Override
    @PostMapping("/remove-product")
    public ResponseEntity<?> removeProduct(RemoveProduct removeProduct) {
        Optional<Product> checkProduct = productRepository.findById(removeProduct.getProductId());
        Optional<Users> checkUsers = usersRepository.findById(removeProduct.getUserId());
        Optional<Dealer> checkDealer = dealerRepository.findByDealerId(removeProduct.getDealerId());
        if (checkProduct.isEmpty()){
            throw new RuntimeException("Product Not Found ");
        }
        if (checkUsers.isEmpty()){
            System.out.println("User Not Found ");
        }
        if (checkDealer.isEmpty()){
            throw new RuntimeException("Dealer Not Found ");
        }

        productRepository.deleteById(removeProduct.getProductId());

        Dealer dealer = checkDealer.get();
        dealer.getAllProductsIds().remove(removeProduct.getProductId());
        dealerRepository.save(dealer);

       if (checkUsers.isPresent()){
           Users users = checkUsers.get();
           users.getCarts().remove(removeProduct.getProductId());
           usersRepository.save(users);
       }

        return ResponseEntity.ok(Map.of("Status","Product Removed Successfully "));
    }

    @Override
    @PutMapping("/update-product")
    public ResponseEntity<?> updateProduct(UpdateProductDto updatedValues) {
        Optional<Product> checkProduct = productRepository.findById(updatedValues.getProductId());
        Optional<Dealer> checkDealer = dealerRepository.findByDealerId(updatedValues.getDealerId());
        if (checkProduct.isEmpty()){
            throw new UserNotFound("Product Not Found ");
        }
        if (checkDealer.isEmpty()){
            throw new UserNotFound("Dealer Not Found ");
        }

        Product product = checkProduct.get();
        product.setPrice(updatedValues.getPrice());
        product.setDescription(updatedValues.getDescription());
        product.setDiscount(updatedValues.getDiscount());
        product.setHowToUse(updatedValues.getHowToUse());
        product.setKeyIngredients(updatedValues.getKeyIngredients());
        product.setAbout(updatedValues.getAbout());
        product.setOverview(updatedValues.getOverview());
        product.setStockStatus(updatedValues.isStockStatus());
        product.setCodAvailable(updatedValues.isCodAvailable());
        product.setSold(updatedValues.isSold());
        productRepository.save(product);


        ApiResponse<?> apiResponse = new ApiResponse<>("Product Updated Successfully", null);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
interface DealerControllers{
    ResponseEntity<?> registerDealerAccount(@RequestBody DealerDto dto);
    ResponseEntity<?> loginDealerAccount(@RequestBody LoginRequest loginRequest);

    ResponseEntity<?> createCategoryType(@RequestBody CategoryType categoryType);
    ResponseEntity<?> removeCategoryType(@RequestBody RemoveCategoryType categoryType); // agar ye delte kar diya to isake related sab delte ho jayenge
    ResponseEntity<?> updateCategoryType(@RequestBody UpdateCategoryType categoryType); // agar ye delte kar diya to isake related sab delte ho jayenge


    ResponseEntity<?> createSubCategoryType(@RequestBody SubCategoryType subCategoryType);
    ResponseEntity<?> removeSubCategoryType(@RequestBody SubCategoryType subCategoryType);
    ResponseEntity<?> updateSubCategoryType(@RequestBody UpdateSubCategoryType subCategoryType);

    ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto);
    ResponseEntity<?> removeCategory(@RequestBody RemoveCategory categoryDto);
    ResponseEntity<?> updateCategory(@RequestBody UpdateCategoryDto categoryDto); //update only name

    ResponseEntity<?> createSubCategory(@RequestBody SubCategoryDto subCategoryDto);
    ResponseEntity<?> removeSubCategory(@RequestBody RemoveSubCategory subCategoryDto);
    ResponseEntity<?> updateSubCategory(@RequestBody UpdateSubCategoryDto subCategoryDto);

    ResponseEntity<?> createProduct(@RequestPart("product") String productJson, @RequestPart("image") MultipartFile multipartFile) throws IOException;
    ResponseEntity<?> removeProduct(@RequestBody RemoveProduct removeProduct);
    ResponseEntity<?> updateProduct(@RequestBody UpdateProductDto updateProductDto);

}
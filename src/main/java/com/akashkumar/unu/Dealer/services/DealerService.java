package com.akashkumar.unu.Dealer.services;

import com.akashkumar.unu.Admin.entity.Admin;
import com.akashkumar.unu.Admin.repository.AdminRepository;
import com.akashkumar.unu.Dealer.dto.DealerDto;
import com.akashkumar.unu.Dealer.entity.Dealer;
import com.akashkumar.unu.Dealer.mapper.DealerMapper;
import com.akashkumar.unu.Dealer.repository.DealerRepository;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserFound;
import com.akashkumar.unu.ExceptionHandling.AuthenticationException.UserNotFound;
import com.akashkumar.unu.Product.Category.CategoryType.CategoryType;
import com.akashkumar.unu.Product.Category.CategoryType.CategoryTypeRepository;
import com.akashkumar.unu.Product.Category.Entity.Category;
import com.akashkumar.unu.Product.Category.Entity.SubCategory;
import com.akashkumar.unu.Product.Category.dto.CategoryDto;
import com.akashkumar.unu.Product.Category.SubCategory.SubCategoryDto;
import com.akashkumar.unu.Product.Category.mapper.CategoryMapper;
import com.akashkumar.unu.Product.Category.mapper.SubCategoryMapper;
import com.akashkumar.unu.Product.Category.repository.CategoryRepository;
import com.akashkumar.unu.Product.Category.repository.SubCategoryRepository;
import com.akashkumar.unu.Product.Products.Dto.ProductDto;
import com.akashkumar.unu.Product.Products.ImagesRepository;
import com.akashkumar.unu.Product.Products.Mapper.ProductMapper;
import com.akashkumar.unu.Product.Products.Product.Images;
import com.akashkumar.unu.Product.Products.Product.Product;
import com.akashkumar.unu.Product.Products.ProductRepository;
import com.akashkumar.unu.Utilities.Enums.Role;
import com.akashkumar.unu.Utilities.Urls;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class DealerService implements DealerServices{

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    DealerRepository dealerRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImagesRepository imagesRepository;

    @Autowired
    CategoryRepository categoryRepository;

     @Autowired
     SubCategoryRepository subCategoryRepository;

    @Autowired
    CategoryTypeRepository categoryTypeRepository;


    @Autowired
    Cloudinary cloudinary;

    @Override
    public DealerDto registerDealerAccount(DealerDto dto) {
        Optional<Admin> checkAdmin = adminRepository.findByRole(Role.ADMIN);
        Optional<Dealer> checkDealer = dealerRepository.findByDealerMobile(dto.getDealerMobile());
        if (checkAdmin.isEmpty()){
            throw new UserNotFound("Admin Not Found ");
        }
        Admin admin = checkAdmin.get();
        Dealer dealer = DealerMapper.toEntity(dto);
        if (checkDealer.isEmpty()){
            if (dto.getBankDetail()==null){
                dto.setBankDetail(null);
            }
            if (dto.getMyAddress()==null){
                dto.setMyAddress(null);
            }
             dealerRepository.save(dealer);
             admin.getDealers().add(dealer.getDealerId());
             admin.getActiveDealers().add(dealer.getDealerId());
        }else{
            throw new UserFound("Dealer already exists ");
        }
        adminRepository.save(admin);
        return DealerMapper.toDto(dealer);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto, MultipartFile multipartFile) throws IOException {
        Optional<Dealer> checkDealer = dealerRepository.findByDealerId(productDto.getDealerId());
        if (checkDealer.isEmpty()){
            throw new UserNotFound("Dealer Not Found ");
        }


        Map options = ObjectUtils.asMap(
                Urls.FOLDER, Urls.BASEURL_Cloudinary,
                Urls.USE_FILE, true,
                Urls.UNIQUE_FILENAME, true
        );

        Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), options);
        String imageUrl = uploadResult.get("secure_url").toString();

        Product product = ProductMapper.toEntity(productDto);

        if((product.getSelectedCategoryType()==null ||
            product.getSelectedCategoryType().isEmpty()) && !product.getSelectedSubCategoryType().isEmpty()){
            throw new RuntimeException("you can't add subProduct without selecting category ");
        }

        if(!product.getSelectedSubCategoryType().isEmpty() && !product.getSubCategoryId().isEmpty()){
            Optional<SubCategory> checkCategory = subCategoryRepository.findById(product.getSubCategoryId());
            if (checkCategory.isEmpty()){
                throw new RuntimeException("SubCategory Not Found");
            }
            Images image = new Images();
            image.setImageUrl(imageUrl);
            image.setCategoryId(productDto.getCategoryId());
            image.setSubCategoryId(product.getSubCategoryId());
            image.setProductId(product.getProductId());
            imagesRepository.save(image);

            product.getImageUrls().add(imageUrl);
            Product saveProductsInDealer =  productRepository.save(product);

            Dealer dealer = checkDealer.get();
            dealer.getAllProductsIds().add(saveProductsInDealer.getProductId());
            dealerRepository.save(dealer);

        }else{
            //add in subcategory
            Optional<Category> checkCategory = categoryRepository.findById(product.getCategoryId());
            if (checkCategory.isEmpty()){
                throw new RuntimeException("Category Not Found");
            }
            Images image = new Images();
            image.setImageUrl(imageUrl);
            image.setCategoryId(productDto.getCategoryId());
            image.setSubCategoryId("");
            image.setProductId(product.getProductId());
            imagesRepository.save(image);
 ;
            Product saveProductsInDealer =  productRepository.save(product);

            Dealer dealer = checkDealer.get();
            dealer.getAllProductsIds().add(saveProductsInDealer.getProductId());
            dealerRepository.save(dealer);

        }
        return ProductMapper.toDto(product);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
         Optional<Dealer> checkDealer = dealerRepository.findByDealerId(categoryDto.getDealerId());
         if (checkDealer.isEmpty()){
             throw new UserNotFound("Dealer Not Found in Database ");
         }

         Optional<CategoryType> checkCategoryType = categoryTypeRepository.findByCategoryType(categoryDto.getSelectedCategoryType());
         if (checkCategoryType.isEmpty()){
             throw new RuntimeException("Category Type Not Found In Database");
         }

        Category category = CategoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryRepository.save(category);

        //Now save in admin
        Dealer dealer = checkDealer.get();
        dealer.getCategoriesIds().add(category.getCategoryId());
        dealerRepository.save(dealer);
        return CategoryMapper.toDto(savedCategory);
    }

    @Override
    public SubCategoryDto subCreateCategory(SubCategoryDto subCategoryDto) {
        Optional<Dealer> checkDealer = dealerRepository.findByDealerId(subCategoryDto.getDealerId());
        if (checkDealer.isEmpty()){
            throw new UserNotFound("Dealer Not Found in Database ");
        }
        Optional<Category> checkCategory = categoryRepository.findById(subCategoryDto.getCategoryId());
        if (checkCategory.isEmpty()){
            throw new RuntimeException("Category not found for Adding Sub Category");
        }

        SubCategory subCategory = SubCategoryMapper.toEntity(subCategoryDto);
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);

        Category category = checkCategory.get();
        category.getSubCategoryIds().add(savedSubCategory.getSubCategoryId());
        categoryRepository.save(category);

        //Now save in admin
        Dealer dealer = checkDealer.get();
        dealer.getSubCategoriesIds().add(subCategory.getSubCategoryId());
        dealerRepository.save(dealer);
        return SubCategoryMapper.toDto(savedSubCategory);
    }
}

interface DealerServices{
    DealerDto registerDealerAccount(DealerDto dto);
    ProductDto createProduct(ProductDto productDto, MultipartFile multipartFile) throws IOException;
    CategoryDto createCategory(CategoryDto categoryDto);
    SubCategoryDto subCreateCategory(SubCategoryDto subCategoryDto);
}

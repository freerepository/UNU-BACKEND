package com.akashkumar.unu.Bank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankDto {
    private String bankId;

    private String userId;
    private String adminId;
    private String dealerId;
    private String courierId;

    private String accountHolderName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String branchName;
    private String adharNumber;
    private String panNumber;
}

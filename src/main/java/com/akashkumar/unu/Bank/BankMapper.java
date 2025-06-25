package com.akashkumar.unu.Bank;

public class BankMapper {

    public static Bank toEntity(BankDto dto) {
        Bank bank = new Bank();
        bank.setBankId(dto.getBankId());

        bank.setAdminId(dto.getAdminId());
        bank.setUserId(dto.getUserId());
        bank.setDealerId(dto.getDealerId());
        bank.setCourierId(dto.getCourierId());

        bank.setAccountHolderName(dto.getAccountHolderName());
        bank.setBankName(dto.getBankName());
        bank.setAccountNumber(dto.getAccountNumber());
        bank.setIfscCode(dto.getIfscCode());
        bank.setBranchName(dto.getBranchName());
        bank.setAdharNumber(dto.getAdharNumber());
        bank.setPanNumber(dto.getPanNumber());
        return bank;
    }
    public static BankDto toDto(Bank bank) {
        BankDto dto = new BankDto();
        dto.setBankId(bank.getBankId());

        dto.setAdminId(bank.getAdminId());
        dto.setUserId(bank.getUserId());
        dto.setDealerId(bank.getDealerId());
        dto.setCourierId(bank.getCourierId());

        dto.setAccountHolderName(bank.getAccountHolderName());
        dto.setBankName(bank.getBankName());
        dto.setAccountNumber(bank.getAccountNumber());
        dto.setIfscCode(bank.getIfscCode());
        dto.setBranchName(bank.getBranchName());
        dto.setAdharNumber(bank.getAdharNumber());
        dto.setPanNumber(bank.getPanNumber());
        return dto;
    }
}

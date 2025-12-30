package com.labodc.enterprise_service.dto;

public class EnterpriseProfileResponse {

    private Long id;
    private String companyName;
    private String email;
    private String address;
    private String description;

    public Long getId() {
        return id;
    }
        
    public void setId(Long id) {
        this.id = id;
    }
        
    public String getCompanyName() {
        return companyName;
    }
        
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
        
    public String getEmail() {
        return email;
    }
        
    public void setEmail(String email) {
        this.email = email;
    }
        
    public String getAddress() {
        return address;
    }
        
    public void setAddress(String address) {
        this.address = address;
    }
        
    public String getDescription() {
        return description;
    }
        
    public void setDescription(String description) {
        this.description = description;
    }
}

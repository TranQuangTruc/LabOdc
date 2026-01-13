package com.labodc.userprofile.entity;


import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {


// Khoa chinh cua bang users
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


// Ten dang nhap, khong duoc trung
@Column(unique = true, nullable = false)
private String username;


// Mat khau da duoc ma hoa
@Column(nullable = false)
private String password;


// Vai tro nguoi dung
@Enumerated(EnumType.STRING)
private UserRole role;


public Long getId() {
return id;
}


public String getUsername() {
return username;
}


public void setUsername(String username) {
this.username = username;
}


public String getPassword() {
return password;
}


public void setPassword(String password) {
this.password = password;
}


public UserRole getRole() {
return role;
}


public void setRole(UserRole role) {
this.role = role;
}
}
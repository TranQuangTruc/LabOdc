package com.labodc.userprofile.security;


import com.labodc.userprofile.entity.User;
import com.labodc.userprofile.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class CustomUserDetailsService implements UserDetailsService {


private final UserRepository userRepository;


public CustomUserDetailsService(UserRepository userRepository) {
this.userRepository = userRepository;
}


@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
User user = userRepository.findByUsername(username)
.orElseThrow(() -> new UsernameNotFoundException("Khong tim thay user"));


return new org.springframework.security.core.userdetails.User(
user.getUsername(),
user.getPassword(),
List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
);
}
}
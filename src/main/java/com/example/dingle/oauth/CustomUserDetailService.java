package com.example.dingle.oauth;

import com.example.dingle.exception.*;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalMember = userRepository.findByEmail(email);
        User findUser = optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return new CustomUserDetails(findUser);
    }


    private static final class CustomUserDetails extends User implements UserDetails {
        CustomUserDetails(User user) {
            setId(user.getId());
            setToken(user.getToken());
            setImage(user.getImage());
            setEmail(user.getEmail());
            setKakakoId(user.getKakakoId());
            setStatus(user.getStatus());
            setCreatedAt(user.getCreatedAt());
            setModifiedAt(user.getModifiedAt());
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getUsername() {
            return getEmail();
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
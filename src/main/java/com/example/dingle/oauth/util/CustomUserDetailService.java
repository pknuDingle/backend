package com.example.dingle.oauth.util;

import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.user.entity.User;
import com.example.dingle.user.repository.UserRepository;
import java.util.Collection;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<User> optionalMember = userRepository.findById(Long.valueOf(id));
        User findUser = optionalMember.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.USER_NOT_FOUND));

        return new CustomUserDetails(findUser);
    }


    private static final class CustomUserDetails extends User implements UserDetails {

        CustomUserDetails(User user) {
            setId(user.getId());
            setName(user.getName());
            setImageUrl(user.getImageUrl());
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
            return String.valueOf(getId());
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
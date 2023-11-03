package com.example.dingle.userCategory.service;

import com.example.dingle.category.entity.Category;
import com.example.dingle.exception.BusinessLogicException;
import com.example.dingle.exception.ExceptionCode;
import com.example.dingle.user.entity.User;
import com.example.dingle.userCategory.entity.UserCategory;
import com.example.dingle.userCategory.repository.UserCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;

    // Create
    public UserCategory createUserCategory(User currentUser, Category category) {
        UserCategory userCategory = category.toUserCategory(currentUser);
        return userCategoryRepository.save(userCategory);
    }

    // Read
    public UserCategory findUserCategory(long userCategoryId) {
        return verifiedUserCategory(userCategoryId);
    }


    // Delete
    public void deleteUserCategory(long userCategoryId) {
        UserCategory userCategory = verifiedUserCategory(userCategoryId);
        userCategoryRepository.delete(userCategory);
    }

    // 증명
    public UserCategory verifiedUserCategory(long userCategoryId) {
        Optional<UserCategory> userCategory = userCategoryRepository.findById(userCategoryId);
        return userCategory.orElseThrow(() -> new BusinessLogicException(ExceptionCode.USERCATEGORY_NOT_FOUND));
    }
}

package com.service.user;

import com.model.jwt.AppUser;
import com.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends IGeneralService<AppUser>, UserDetailsService {
    UserDetails loadUserByUsername(String username);

    AppUser getUserByUsername(String username);

    AppUser findAppUserByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    AppUser findByUsername(String username);

    Iterable<AppUser> managerFindAll();
    Iterable<AppUser> adminFindAll();
    void openAccountById(Long id);
    void lockAccountById(Long id);

    void changeToManager(Long id);
    void changeToUser(Long id);

    String randomPassword();
}

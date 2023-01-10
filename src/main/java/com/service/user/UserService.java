package com.service.user;

import com.model.jwt.AppUser;
import com.repository.jwt.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Random;

@Service
@Transactional
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    public UserService() {
    }

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Iterable<AppUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<AppUser> findByUser(String name) {
        return Optional.ofNullable(userRepository.findByUsername(name));
    }

    @Override
    public AppUser save(AppUser user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username);
        return new User(user.getUsername(), user.getPassword(), user.getRoles());
    }

    @Override
    public AppUser getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public AppUser findAppUserByEmail(String email) {
        return userRepository.findAppUserByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Iterable<AppUser> managerFindAll() {
        return userRepository.managerFindAll();
    }

    public Iterable<AppUser> adminFindAll() {
        return userRepository.adminFindAll();
    }

    @Override
    public void lockAccountById(Long id){
        userRepository.lockAccountById(id);
    }

    @Override
    public void openAccountById(Long id){
        userRepository.openAccountById(id);
    }

    @Override
    public void changeManager(String name) {
        userRepository.changeManager(name);
    }

    @Override
    public void changeUser(String name) {
        userRepository.changeUser(name);
    }

    @Override
    public String randomPassword() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }
}

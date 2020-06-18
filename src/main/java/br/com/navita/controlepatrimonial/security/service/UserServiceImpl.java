package br.com.navita.controlepatrimonial.security.service;

import br.com.navita.controlepatrimonial.security.model.UserDetailsCustom;
import br.com.navita.controlepatrimonial.security.repository.UserRepository;
import br.com.navita.controlepatrimonial.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The type User service.
 */
@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        return UserDetailsCustom.build(user);
    }
}

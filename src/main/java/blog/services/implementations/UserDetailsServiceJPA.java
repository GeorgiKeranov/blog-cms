package blog.services.implementations;

import blog.models.User;
import blog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceJPA implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        if(s == null || s.equals("")) throw new UsernameNotFoundException("Invalid User");;

        User user = userRepo.findByUsername(s);

        if(user == null){
            throw new UsernameNotFoundException("Invalid User");
        }

        else {
            Set<GrantedAuthority> authorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRole()))
                    .collect(Collectors.toSet());

            return new org.springframework.security.core.userdetails
                    .User(user.getUsername(), user.getPassword(), authorities);
        }
    }
}

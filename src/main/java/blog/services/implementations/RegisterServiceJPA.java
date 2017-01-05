package blog.services.implementations;

import blog.models.User;
import blog.repositories.UserRepository;
import blog.services.interfaces.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceJPA implements RegisterService {

    @Autowired
    UserRepository userRepo;

    @Override
    public User register(User userForRegi) {

        User isUserWithThisUsername = userRepo.findByUsername(userForRegi.getUsername());

        if(isUserWithThisUsername != null) {
            return null;
        }

        User regUser = userRepo.save(userForRegi);
        return regUser;
    }
}

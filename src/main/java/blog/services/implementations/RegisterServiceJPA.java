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
    public String register(User userForRegi) {

        User isUserWithThisUsername = userRepo.findByUsername(userForRegi.getUsername());

        if(isUserWithThisUsername != null) {
            return "Username is already taken.";
        }

        User isUserWithThatEmail = userRepo.findByEmail(userForRegi.getEmail());

        if(isUserWithThatEmail != null){
            return "Email is already taken.";
        }

        User regUser = userRepo.save(userForRegi);
        return null;
    }

    @Override
    public String generateUserUrl(String fullname) {

        // Editing fullname alphabets to lowercase.
        fullname = fullname.toLowerCase();

        User user = userRepo.findByUserUrl(fullname);

        if(user == null) return fullname;
        else {
            int i = 0;
            while (user != null) {
                fullname = fullname + "." + i;
                user = userRepo.findByUserUrl(fullname);
                i++;
            }
        }

        return fullname;
    }


}

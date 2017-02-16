package blog.services.implementations;

import blog.models.Role;
import blog.models.User;
import blog.repositories.RoleRepository;
import blog.repositories.UserRepository;
import blog.services.interfaces.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceJPA implements RegisterService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public String register(User userForRegi) {

        if(userForRegi.getUsername().length() < 5) return "Username have to be more than 4 characters.";
        if(userForRegi.getUsername().length() > 40) return "Username have to be smaller than 40 characters.";

        User isUserWithThisUsername = userRepo.findByUsername(userForRegi.getUsername());

        if(isUserWithThisUsername != null) {
            return "Username is already taken.";
        }

        User isUserWithThatEmail = userRepo.findByEmail(userForRegi.getEmail());

        if(isUserWithThatEmail != null){
            return "Email is already taken.";
        }

        userForRegi.setUserUrl(
                generateUserUrl(
                        userForRegi.getFirstName() + "." +
                        userForRegi.getLastName())
        );

        User regUser = userRepo.save(userForRegi);

        Role user_role = new Role();
        user_role.setRole("ROLE_USER");
        user_role.setUser(regUser);
        roleRepository.save(user_role);

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

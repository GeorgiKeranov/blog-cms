package blog.services.interfaces;

import blog.models.User;

public interface RegisterService {

    String register(User userForRegi);
    String generateUserUrl(String fullname);
}

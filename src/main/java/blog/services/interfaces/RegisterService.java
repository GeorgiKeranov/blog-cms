package blog.services.interfaces;

import blog.models.User;

public interface RegisterService {

    public String register(User userForRegi);
    public String generateUserUrl(String fullname);
}

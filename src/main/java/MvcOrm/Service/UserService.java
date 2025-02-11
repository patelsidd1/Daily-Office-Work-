package MvcOrm.Service;

import MvcOrm.Dao.UserDao;
import MvcOrm.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    public int createUser(User user){

        return this.userDao.saveUser(user);

    }
}

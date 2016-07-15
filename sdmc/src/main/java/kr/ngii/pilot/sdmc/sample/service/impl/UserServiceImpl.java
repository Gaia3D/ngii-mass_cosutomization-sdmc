package kr.ngii.pilot.sdmc.sample.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ngii.pilot.sdmc.sample.model.User;
import kr.ngii.pilot.sdmc.sample.service.UserService;
import kr.ngii.pilot.sdmc.sample.service.dao.UserDao;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public List<User> selectAllUsers() {
        return userDao.selectAllUsers();
    }

    @Override
    public User selectUser(User user) {
        List<User> users = userDao.selectAllUsers(user);
        // if (true)  throw new BizException("Error Test 입니다.");
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int deleteUser(User user) {
        return userDao.deleteUser(user);
    }

}

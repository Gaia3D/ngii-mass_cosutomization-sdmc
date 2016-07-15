package kr.ngii.pilot.sdmc.sample.service.dao;

import java.util.List;

import kr.ngii.pilot.sdmc.core.annotation.DsApp;
import kr.ngii.pilot.sdmc.sample.model.User;

@DsApp
public interface UserDao {

    public List<User> selectAllUsers();
    public List<User> selectAllUsers(User user);

    public int insertUser(User user);
    public int updateUser(User user);
    public int deleteUser(User user);
}

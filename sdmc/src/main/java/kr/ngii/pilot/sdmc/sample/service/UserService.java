package kr.ngii.pilot.sdmc.sample.service;

import java.util.List;

import kr.ngii.pilot.sdmc.sample.model.User;

public interface UserService {

    public List<User> selectAllUsers();
    
    
    public User selectUser(User user);
    
    public int insertUser(User user);
    
    public int updateUser(User user);
    
    public int deleteUser(User user);
}

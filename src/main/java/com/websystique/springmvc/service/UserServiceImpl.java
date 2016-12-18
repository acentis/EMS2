package com.websystique.springmvc.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.UserDao;
import com.websystique.springmvc.model.User;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public User findById(int id) {
		return dao.findById(id);
	}

	public User findBySSO(String sso) {
		User user = dao.findBySSO(sso);
		return user;
	}

	public void saveUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setmJan(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmFeb(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmMar(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmApr(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmMay(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmJun(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmJul(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmAug(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmSep(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmOct(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmNov(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setmDec(user.getPerDayHours().multiply(new BigDecimal(22)));
		user.setYearName("2016");
		dao.save(user);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate update explicitly.
	 * Just fetch the entity from db and update it with proper values within transaction.
	 * It will be updated in db once transaction ends. 
	 */
	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if(entity!=null){
			entity.setSsoId(user.getSsoId());
			if(!user.getPassword().equals(entity.getPassword())){
				entity.setPassword(passwordEncoder.encode(user.getPassword()));
			}
			entity.setFirstName(user.getFirstName());
			entity.setLastName(user.getLastName());
			entity.setEmail(user.getEmail());
			entity.setPerHourCost(user.getPerHourCost());
			entity.setPerDayHours(user.getPerDayHours());
			entity.setUserProfiles(user.getUserProfiles());
		}
	}

	
	public void deleteUserBySSO(String sso) {
		dao.deleteBySSO(sso);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}
	
	public List<User> findAllUsersByType(String userProfileType) {
		return dao.findAllUsersByType(userProfileType);
	}

	public boolean isUserSSOUnique(Integer id, String sso) {
		User user = findBySSO(sso);
		return ( user == null || ((id != null) && (user.getId() == id)));
	}
	
}

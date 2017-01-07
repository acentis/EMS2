package com.websystique.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.websystique.springmvc.dao.WorkPackageUserAllocationDao;
import com.websystique.springmvc.model.User;
import com.websystique.springmvc.model.WorkPackage;
import com.websystique.springmvc.model.WorkPackageUserAllocation;

@Service("workPackageUserAllocationService")
@Transactional
public class WorkPackageUserAllocationServiceImpl implements
		WorkPackageUserAllocationService {

	@Autowired
	private WorkPackageUserAllocationDao dao;

	public WorkPackageUserAllocation findById(int id) {
		return dao.findById(id);
	}

	public void saveWorkPackageUserAllocation(
			WorkPackageUserAllocation workPackageUserAllocation) {
		dao.save(workPackageUserAllocation);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
	 */
	public void updateWorkPackageUserAllocation(
			WorkPackageUserAllocation workPackageUserAllocation) {
		WorkPackageUserAllocation entity = dao
				.findById(workPackageUserAllocation.getId());
		if (entity != null) {
			entity.setTotalPlannedDays(workPackageUserAllocation.getTotalPlannedDays());
			entity.setmJan(workPackageUserAllocation.getmJan());
			entity.setmFeb(workPackageUserAllocation.getmFeb());
			entity.setmMar(workPackageUserAllocation.getmMar());
			entity.setmApr(workPackageUserAllocation.getmApr());
			entity.setmMay(workPackageUserAllocation.getmMay());
			entity.setmJun(workPackageUserAllocation.getmJun());
			entity.setmJul(workPackageUserAllocation.getmJul());
			entity.setmAug(workPackageUserAllocation.getmAug());
			entity.setmSep(workPackageUserAllocation.getmSep());
			entity.setmOct(workPackageUserAllocation.getmOct());
			entity.setmNov(workPackageUserAllocation.getmNov());
			entity.setmDec(workPackageUserAllocation.getmDec());
			entity.setYearName(workPackageUserAllocation.getYearName());
			entity.setWorkPackage(workPackageUserAllocation.getWorkPackage());
			entity.setUser(workPackageUserAllocation.getUser());
		}
	}

	public void deleteWorkPackageUserAllocationById(int id) {
		dao.deleteById(id);
	}

	@Override
	public List<WorkPackageUserAllocation> findAllWorkPackageUserAllocations() {
		return dao.findAllWorkPackageUserAllocations();
	}

	@Override
	public List<WorkPackageUserAllocation> findAllWorkPackageUserAllocationsByWorkPackage(
			WorkPackage workPackage) {
		return dao.findAllWorkPackageUserAllocationsByWorkPackage(workPackage);
	}

	@Override
	public List<WorkPackageUserAllocation> findAllWorkPackageUserAllocationsByUser(
			User user) {
		return dao.findAllWorkPackageUserAllocationsByUser(user);
	}

	@Override
	public List<WorkPackageUserAllocation> findAllWorkPackageUserAllocationsByUserAndWorkPackage(
			WorkPackage workPackage, User user) {
		return dao.findAllWorkPackageUserAllocationsByUserAndWorkPackage(
				workPackage, user);
	}

	public List<WorkPackageUserAllocation> findAllWorkPackageUserAllocationsBySum() {
		return dao.findAllWorkPackageUserAllocationsBySum();
	}

	@Override
	public List<WorkPackageUserAllocation> findAllWorkPackageUserAllocationsBySumOfAllMonths() {
		return dao.findAllWorkPackageUserAllocationsBySumOfAllMonths();
	}

	@Override
	public List<WorkPackageUserAllocation> getWorkPackageHoursForAllUsers() {
		return dao.getWorkPackageHoursForAllUsers();
	}

	@Override
	public List<WorkPackageUserAllocation> getTotalWorkPackageHours() {
		return dao.getTotalWorkPackageHours();
	}

	@Override
	public List<WorkPackageUserAllocation> getWorkPackageHoursForAllUsers(
			String workPackageNumber) {
		return dao.getWorkPackageHoursForAllUsers(workPackageNumber);
	}

	@Override
	public List<WorkPackageUserAllocation> findByProjectID(int projectID) {		
		return dao.findByProjectID(projectID);
	}
	
	

}

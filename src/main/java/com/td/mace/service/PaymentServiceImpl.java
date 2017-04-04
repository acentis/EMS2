package com.td.mace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.td.mace.dao.PaymentDao;
import com.td.mace.model.Payment;
import com.td.mace.model.Project;

@Service("paymentService")
@Transactional
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDao paymentDao;

	public Payment findById(int id) {
		return paymentDao.findById(id);
	}
	
	public Payment findByPaymentName(String paymentName) {
		return paymentDao.findByPaymentName(paymentName);
	}
	
	public void savePayment(Payment payment) {
		paymentDao.save(payment);
	}

	/*
	 * Since the method is running with Transaction, No need to call hibernate
	 * update explicitly. Just fetch the entity from db and update it with
	 * proper values within transaction. It will be updated in db once
	 * transaction ends.
	 */
	public void updatePayment(Payment payment) {
		Payment entity = paymentDao.findById(payment.getId());
		if (entity != null) {
			entity.setPaymentName(payment.getPaymentName());
			entity.setAmount(payment.getAmount());
			entity.setBalance(payment.getBalance());
			entity.setWorkPackage((payment.getWorkPackage()));
			/*entity.setWorkPackages(payment.getWorkPackages());*/
		}
	}

	public void deletePaymentByWorkPackageId(String workPackageId) {
		paymentDao.deletePaymentByWorkPackageId(workPackageId);
	}
	
	public void deletePaymentByPaymentName(String paymentName) {
		paymentDao.deletePaymentByPaymentName(paymentName);
	}

	public List<Payment> findAllPayments() {
		return paymentDao.findAllPayments();
	}
		
}
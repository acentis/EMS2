package com.td.mace.controller;

import com.td.mace.model.Payment;
import com.td.mace.model.User;
import com.td.mace.model.WorkPackage;
import com.td.mace.service.PaymentService;
import com.td.mace.service.UserService;
import com.td.mace.service.WorkPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/Payment")
@SessionAttributes("workPackagesList")
@PropertySource(value = { "classpath:application.properties" })
public class PaymentController {

	@Autowired
	Environment environment;

	@Autowired
	PaymentService paymentService;

	@Autowired
	WorkPackageService workPackageService;

	@Autowired
	UserService userService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;

	@Autowired
	AuthenticationTrustResolver authenticationTrustResolver;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(BigDecimal.class, new BigDecimalEditor());
    }

	/**
	 * This method will list all existing payments.
	 */
	@RequestMapping(value = { "/paymentslist" }, method = RequestMethod.GET)
	public String listPayments(ModelMap model) {

		List<Payment> payments = paymentService.findAllPayments();
		if (payments != null) {
			for (Payment payment : payments) {
				payment.setPaymentPercentage(PaymentUtils.calculatePaymentPercentage(payment));
			}
		}
		model.addAttribute("payments", payments);
		model.addAttribute("defaultLanguage",
				environment.getProperty("default.language"));
		model.addAttribute("loggedinuser", getPrincipal());
		return "paymentslist";
	}

	@ModelAttribute("workPackagesList")
	public List<WorkPackage> initializeWorkPackagesList() {
		return workPackageService.findAllWorkPackages();
	}

	/**
	 * This method will provide the medium to add a new payment.
	 */
	@RequestMapping(value = { "/newpayment" }, method = RequestMethod.GET)
	public String newPayment(ModelMap model,HttpServletRequest request) {
		Payment payment = new Payment();
        // get first name of logged user
        User loggedUser = userService.findBySSO(getPrincipal());
        if(loggedUser != null){
            payment.setCreatedBy(loggedUser.getFirstName());
        }
		if (request.getParameter("workPackageId") != null) {
			int workPackageId = (Integer
					.parseInt(request.getParameter("workPackageId")));
			WorkPackage workPackage = workPackageService.findById(workPackageId);
			payment.setWorkPackage(workPackage);
            payment.setPaymentPercentage(PaymentUtils.calculatePaymentPercentage(payment));
			// check if wp exists in model list
            List<WorkPackage> currWPList = (List<WorkPackage>) model.get("workPackagesList");
            Boolean wpExists = false;
            for (WorkPackage workPackage1: currWPList) {
                   if(workPackage1.getId() == workPackageId){
                       wpExists = true;
                       break;
                   }
            }

            if(!wpExists){
                currWPList.add(workPackage);
            }
        }

		model.addAttribute("payment", payment);
		model.addAttribute("edit", false);
		/*
		 * model.addAttribute("yearNameStart",environment.getProperty(
		 * "year.name.start"));
		 * model.addAttribute("yearNameEnd",environment.getProperty
		 * ("year.name.end"));
		 */
		model.addAttribute("loggedinuser", getPrincipal());
		return "payment";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * saving payment in database. It also validates the payment input
	 */
	@RequestMapping(value = { "/newpayment" }, method = RequestMethod.POST)
	public String savePayment(@Valid Payment payment, BindingResult result,
			ModelMap model, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("yearNameStart",
					environment.getProperty("year.name.start"));
			model.addAttribute("yearNameEnd",
					environment.getProperty("year.name.end"));
			return "payment";
		}

		paymentService.savePayment(payment);
		workPackageService.updateCalculatedCost(payment.getWorkPackage());
		//paymentService.updatePayment(payment);

		// model.addAttribute("success", "Payment " + payment.getFirstName() +
		// " "+ payment.getLastName() + " registered successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		// return "success";
		request.getSession(false).setAttribute("paymentslist",
				paymentService.findAllPayments());
		return "redirect:/WorkPackage/edit-workPackage-"+payment.getWorkPackage().getId();
		//return "redirect:/Payment/paymentslist";
	}

	/**
	 * This method will provide the medium to update an existing payment.
	 */
	@RequestMapping(value = { "/edit-payment-{id}" }, method = RequestMethod.GET)
	public String editPayment(@PathVariable Integer id, ModelMap model) {
		Payment payment = paymentService.findById(id);
        payment.setPaymentPercentage(PaymentUtils.calculatePaymentPercentage(payment));
		model.addAttribute("payment", payment);
		model.addAttribute("yearNameStart",
				environment.getProperty("year.name.start"));
		model.addAttribute("yearNameEnd",
				environment.getProperty("year.name.end"));
		model.addAttribute("edit", true);
		model.addAttribute("loggedinuser", getPrincipal());
		return "payment";
	}

	/**
	 * This method will be called on form submission, handling POST request for
	 * updating payment in database. It also validates the payment input
	 */
	@RequestMapping(value = { "/edit-payment-{id}" }, method = RequestMethod.POST)
	public String updatePayment(@Valid Payment payment, BindingResult result,
			ModelMap model, @PathVariable String id, HttpServletRequest request) {

		if (result.hasErrors()) {
			model.addAttribute("yearNameStart",
					environment.getProperty("year.name.start"));
			model.addAttribute("yearNameEnd",
					environment.getProperty("year.name.end"));
			return "payment";
		}
		
		paymentService.updatePayment(payment);
		workPackageService.updateCalculatedCost(payment.getWorkPackage());

		// model.addAttribute("success", "Payment " + payment.getFirstName() +
		// " "+ payment.getLastName() + " updated successfully");
		model.addAttribute("loggedinuser", getPrincipal());
		request.getSession(false).setAttribute("paymentslist",
				paymentService.findAllPayments());
		return "redirect:/WorkPackage/edit-workPackage-"+payment.getWorkPackage().getId();
		//return "redirect:/Payment/paymentslist";
	}

	/**
	 * This method will delete an payment by it's SSOID value.
	 */
	@RequestMapping(value = { "/delete-payment-{id}" }, method = RequestMethod.GET)
	public String deletePayment(@PathVariable Integer id,
			HttpServletRequest request) {
		Payment payment = paymentService.findById(id);
		WorkPackage workPackage = new WorkPackage();
		workPackage = payment.getWorkPackage();
		paymentService.deletePaymentById(id);
		
		workPackageService.updateWorkPackageCalculatedCost(workPackage);
		request.getSession(false).setAttribute("paymentslist",
				paymentService.findAllPayments());
		return "redirect:/WorkPackage/edit-workPackage-"+payment.getWorkPackage().getId();
		//return "redirect:/Payment/paymentslist";
	}

	/**
	 * This method returns the principal[user-name] of logged-in user.
	 */
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}

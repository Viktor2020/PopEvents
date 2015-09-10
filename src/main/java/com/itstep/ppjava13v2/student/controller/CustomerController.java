package com.itstep.ppjava13v2.student.controller;

import com.itstep.ppjava13v2.student.controller.validators.CustomerFormValidator;
import com.itstep.ppjava13v2.student.controller.validators.MusicalStyleFormValidator;
import com.itstep.ppjava13v2.student.db.dao.CustomerDao;
import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Customer;
import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**

 */
@Controller
@RequestMapping("/customer")
public class CustomerController {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private MusicalStyleDao musicalStyleDao;
	@Autowired
	private CustomerFormValidator customerFormValidator;
	@Autowired
	private MusicalStyleFormValidator musicalStyleFormValidator;


	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping
	public ModelAndView getAllCustomers() throws DaoException {
		ModelAndView mav = new ModelAndView("customers/customers");
		List customers = new ArrayList(customerDao.findAll());
		mav.addObject("customers", customers);
		log.trace(customers.toString());
		return mav;
	}

	@RequestMapping("/searchCustomers")
	public ModelAndView searchContacts(@RequestParam(required = false, defaultValue = "") String customerFirstName) throws DaoException {
		ModelAndView mav = new ModelAndView("customers/customers");
//		List<Customer> customers = customerDao.findByEngagementFirstName(customerFirstName.trim());
//		mav.addObject("customers", customers);
		return mav;
	}

	@RequestMapping(value = "/saveCustomer", method = RequestMethod.GET)
	public ModelAndView newCustomerForm() {
		ModelAndView mav = new ModelAndView("customers/newCustomer");
		Customer customer = new Customer();
		mav.getModelMap().put("newCustomer", customer);
		return mav;
	}

	@RequestMapping(value = "/saveCustomer", method = RequestMethod.POST)
	public String create(@ModelAttribute("newCustomer") Customer customer, BindingResult result, SessionStatus status) throws DaoException {
		customerFormValidator.validate(customer, result);
		if (result.hasErrors()) {
			return "customers/newCustomer";
		}
		customerDao.save(customer);
		status.setComplete();
		return "redirect:/customer";
	}

	@RequestMapping(value = "/customerAddMusicalStyle", method = RequestMethod.POST)
	public ModelAndView customerAddMusicalStyle(@ModelAttribute("editCustomerAddMusicalStyle") MusicalStyle musicalStyle,
	                                            @ModelAttribute("editCustomer") Customer customer,
	                                            BindingResult result, SessionStatus status) throws DaoException {
		customer.addMusicalStyle(musicalStyle);
		customerDao.save(customer);
		status.setComplete();
		musicalStyleFormValidator.validate(musicalStyle, result);
		ModelAndView mav = new ModelAndView("customers/editCustomer");
		mav.addObject("editCustomer", customer);
		mav.addObject("customerMusicalStyles",customer.getCustomerMusicalStyleList());

		return mav;
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("customerId") long customerId) throws DaoException {
		ModelAndView mav = new ModelAndView("customers/editCustomer");
		Customer customer = customerDao.findById(customerId);
		mav.addObject("editCustomer", customer);
		mav.addObject("customerMusicalStyles",customer.getCustomerMusicalStyleList());
		return mav;
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
	public String update(@ModelAttribute("editCustomer") Customer customer, BindingResult result, SessionStatus status) throws DaoException {
		customerFormValidator.validate(customer, result);
		if (result.hasErrors()) {
			return "customers/editCustomer";
		}
		customerDao.update(customer);
		status.setComplete();
		return "redirect:/customer";
	}

	@RequestMapping(value = "/deleteCustomer", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("customerId") long customerId) throws DaoException {
		customerDao.remove(customerId);
		return "redirect:/customer";
	}

}


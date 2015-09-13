package com.itstep.ppjava13v2.student.controller;

import com.itstep.ppjava13v2.student.controller.validators.EngagementFormValidator;
import com.itstep.ppjava13v2.student.db.dao.EngagementDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Engagement;
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
@RequestMapping("/engagement")
public class EngagementController {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private EngagementDao engagementDao;
	@Autowired
	private EngagementFormValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping
	public ModelAndView getAllEngagements() throws DaoException {
		ModelAndView mav = new ModelAndView("engagements/engagements");
		List engagements = new ArrayList(engagementDao.findAll());
		mav.addObject("engagements", engagements);
		log.trace(engagements.toString());
		return mav;
	}

	@RequestMapping("/searchEngagements")
	public ModelAndView searchContacts(@RequestParam(required = false, defaultValue = "") long engagementPrice) throws DaoException {
		ModelAndView mav = new ModelAndView("engagements/engagements");
		List<Engagement> engagements = new ArrayList<>(engagementDao.findByEngagementPrice(engagementPrice));
		mav.addObject("engagements", engagements);
		return mav;
	}

	@RequestMapping(value = "/saveEngagement", method = RequestMethod.GET)
	public ModelAndView newEngagementForm() {
		ModelAndView mav = new ModelAndView("engagements/newEngagement");
		Engagement engagement = new Engagement();
		mav.getModelMap().put("newEngagement", engagement);
		return mav;
	}

	@RequestMapping(value = "/saveEngagement", method = RequestMethod.POST)
	public String create(@ModelAttribute("newEngagement") Engagement engagement, BindingResult result, SessionStatus status) throws DaoException {
		validator.validate(engagement, result);
		if (result.hasErrors()) {
			return "engagements/newEngagement";
		}
		engagementDao.save(engagement);
		status.setComplete();
		return "redirect:/engagement";
	}

	@RequestMapping(value = "/updateEngagement", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("engagementId") long engagementId) throws DaoException {
		ModelAndView mav = new ModelAndView("engagements/editEngagement");
		Engagement engagement = engagementDao.findById(engagementId);
		mav.addObject("editEngagement", engagement);
		return mav;
	}

	@RequestMapping(value = "/updateEngagement", method = RequestMethod.POST)
	public String update(@ModelAttribute("editEngagement") Engagement engagement, BindingResult result, SessionStatus status) throws DaoException {
		validator.validate(engagement, result);
		if (result.hasErrors()) {
			return "engagements/editEngagement";
		}
		engagementDao.update(engagement);
		status.setComplete();
		return "redirect:/engagement";
	}

	@RequestMapping(value = "/deleteEngagement", method = { RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("engagementId") long engagementId) throws DaoException {
		engagementDao.remove(engagementId);
		return  "redirect:/engagement";
	}

}
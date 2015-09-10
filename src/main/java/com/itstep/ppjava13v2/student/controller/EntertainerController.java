package com.itstep.ppjava13v2.student.controller;

import com.itstep.ppjava13v2.student.controller.validators.EntertainerFormValidator;
import com.itstep.ppjava13v2.student.db.dao.EntertainerDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Entertainer;
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
@RequestMapping("/entertainer")
public class EntertainerController {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private EntertainerDao entertainerDao;
	@Autowired
	EntertainerFormValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping
	public ModelAndView getAllEntertainers() throws DaoException {
		ModelAndView mav = new ModelAndView("entertainers/entertainers");
		List entertainers = new ArrayList(entertainerDao.findAll());
		mav.addObject("entertainers", entertainers);
		log.trace(entertainers.toString());
		return mav;
	}

	@RequestMapping("/searchEntertainers")
	public ModelAndView searchContacts(@RequestParam(required = false, defaultValue = "") String entertainerStageName) throws DaoException {
		ModelAndView mav = new ModelAndView("entertainers/entertainers");
		List<Entertainer> entertainers = new ArrayList<>(entertainerDao.findByEntertainerFirstName(entertainerStageName.trim()));
		mav.addObject("entertainers", entertainers);
		return mav;
	}

	@RequestMapping(value = "/saveEntertainer", method = RequestMethod.GET)
	public ModelAndView newEntertainerForm() {
		ModelAndView mav = new ModelAndView("entertainers/newEntertainer");
		Entertainer entertainer = new Entertainer();
		mav.getModelMap().put("newEntertainer", entertainer);
		return mav;
	}

	@RequestMapping(value = "/saveEntertainer", method = RequestMethod.POST)
	public String create(@ModelAttribute("newEntertainer") Entertainer entertainer, BindingResult result, SessionStatus status) throws DaoException {
		validator.validate(entertainer, result);
		if (result.hasErrors()) {
			return "entertainers/newEntertainer";
		}
		entertainerDao.save(entertainer);
		status.setComplete();
		return "redirect:/entertainer";
	}

	@RequestMapping(value = "/updateEntertainer", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("entertainerId") long entertainerId) throws DaoException {
		ModelAndView mav = new ModelAndView("entertainers/editEntertainer");
		Entertainer entertainer = entertainerDao.findById(entertainerId);
		mav.addObject("editEntertainer", entertainer);
		return mav;
	}

	@RequestMapping(value = "/updateEntertainer", method = RequestMethod.POST)
	public String update(@ModelAttribute("editEntertainer") Entertainer entertainer, BindingResult result, SessionStatus status) throws DaoException {
		validator.validate(entertainer, result);
		if (result.hasErrors()) {
			return "entertainers/editEntertainer";
		}
		entertainerDao.update(entertainer);
		status.setComplete();
		return "redirect:/entertainer";
	}

	@RequestMapping(value = "/deleteEntertainer", method = { RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("entertainerId") long entertainerId) throws DaoException {
		entertainerDao.remove(entertainerId);
		return  "redirect:/entertainer";
	}

}

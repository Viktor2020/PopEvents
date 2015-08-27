package com.itstep.ppjava13v2.student.controller;

import com.itstep.ppjava13v2.student.controller.validators.MusicalStyleFormValidator;
import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
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
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/musicalStyle")
public class MusicalStyleController {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private MusicalStyleDao musicalStyleDao;
	@Autowired
	private MusicalStyleFormValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping
	public ModelAndView getAllMusicalStyles() throws DaoException {
		ModelAndView mav = new ModelAndView("musicalStyles/musicalStyles");
		List musicalStyles = musicalStyleDao.findAll();
		mav.addObject("musicalStyles", musicalStyles);
		log.trace(musicalStyles.toString());
		return mav;
	}

	@RequestMapping("/searchMusicalStyles")
	public ModelAndView searchContacts(@RequestParam(required = false, defaultValue = "") String musicalStyleName) throws DaoException {
		ModelAndView mav = new ModelAndView("musicalStyles/musicalStyles");
		List<MusicalStyle> musicalStyles = musicalStyleDao.findByMusicalStyleName(musicalStyleName.trim());
		mav.addObject("musicalStyles", musicalStyles);
		return mav;
	}

	@RequestMapping(value = "/saveMusicalStyle", method = RequestMethod.GET)
	public ModelAndView newMusicalStyleForm() {
		ModelAndView mav = new ModelAndView("musicalStyles/newMusicalStyle");
		MusicalStyle musicalStyle = new MusicalStyle();
		mav.getModelMap().put("newMusicalStyle", musicalStyle);
		return mav;
	}

	@RequestMapping(value = "/saveMusicalStyle", method = RequestMethod.POST)
	public String create(@ModelAttribute("newMusicalStyle") MusicalStyle musicalStyle, BindingResult result, SessionStatus status) throws DaoException {
		validator.validate(musicalStyle, result);
		if (result.hasErrors()) {
			return "musicalStyles/newMusicalStyle";
		}
		musicalStyleDao.save(musicalStyle);
		status.setComplete();
		return "redirect:/musicalStyle";
	}

	@RequestMapping(value = "/updateMusicalStyle", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("musicalStyleId") long musicalStyleId) throws DaoException {
		ModelAndView mav = new ModelAndView("musicalStyles/editMusicalStyle");
		MusicalStyle musicalStyle = musicalStyleDao.findById(musicalStyleId);
		mav.addObject("editMusicalStyle", musicalStyle);
		return mav;
	}

	@RequestMapping(value = "/updateMusicalStyle", method = RequestMethod.POST)
	public String update(@ModelAttribute("editMusicalStyle") MusicalStyle musicalStyle, BindingResult result, SessionStatus status) throws DaoException {
		validator.validate(musicalStyle, result);
		if (result.hasErrors()) {
			return "musicalStyles/editMusicalStyle";
		}
		musicalStyleDao.update(musicalStyle);
		status.setComplete();
		return "redirect:/musicalStyle";
	}

	@RequestMapping(value = "/deleteMusicalStyle", method = { RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("musicalStyleId") long musicalStyleId) throws DaoException {
		musicalStyleDao.remove(musicalStyleId);
		return  "redirect:/musicalStyle";
	}

}

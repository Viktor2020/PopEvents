package com.itstep.ppjava13v2.student.controller;

import com.itstep.ppjava13v2.student.controller.validators.AgentFormValidator;
import com.itstep.ppjava13v2.student.db.dao.AgentDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Agent;
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

@Controller
@RequestMapping("/agent")
public class AgentController {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private AgentDao agentDao;
	@Autowired
	private AgentFormValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping
	public ModelAndView getAllAgents() throws DaoException {
		ModelAndView mav = new ModelAndView("agents/agents");
		List agents = new ArrayList(agentDao.findAll());
		mav.addObject("agents", agents);
		log.trace(agents.toString());
		return mav;
	}

	@RequestMapping("/searchAgents")
	public ModelAndView searchContacts(@RequestParam(required = false, defaultValue = "") String agentFirstName) throws DaoException {
		ModelAndView mav = new ModelAndView("agents/agents");
		List<Agent> agents = new ArrayList<>(agentDao.findByAgentFirstName(agentFirstName.trim()));
		mav.addObject("agents", agents);
		return mav;
	}

	@RequestMapping(value = "/saveAgent", method = RequestMethod.GET)
	public ModelAndView newAgentForm() {
		ModelAndView mav = new ModelAndView("agents/newAgent");
		Agent agent = new Agent();
		mav.getModelMap().put("newAgent", agent);
		return mav;
	}

	@RequestMapping(value = "/saveAgent", method = RequestMethod.POST)
	public String create(@ModelAttribute("newAgent") Agent agent, BindingResult result, SessionStatus status) throws DaoException {
		validator.validate(agent, result);
		if (result.hasErrors()) {
			return "agents/newAgent";
		}
		agentDao.save(agent);
		status.setComplete();
		return "redirect:/agent";
	}

	@RequestMapping(value = "/updateAgent", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("agentId") long agentId) throws DaoException {
		ModelAndView mav = new ModelAndView("agents/editAgent");
		Agent agent = agentDao.findById(agentId);
		mav.addObject("editAgent", agent);
		return mav;
	}

	@RequestMapping(value = "/updateAgent", method = RequestMethod.POST)
	public String update(@ModelAttribute("editAgent") Agent agent, BindingResult result, SessionStatus status) throws DaoException {
		validator.validate(agent, result);
		if (result.hasErrors()) {
			return "agents/editAgent";
		}
		agentDao.update(agent);
		status.setComplete();
		return "redirect:/agent";
	}

	@RequestMapping(value = "/deleteAgent", method = { RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("agentId") long agentId) throws DaoException {
		agentDao.remove(agentId);
		return  "redirect:/agent";
	}

}

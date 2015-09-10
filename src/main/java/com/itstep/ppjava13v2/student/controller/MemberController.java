package com.itstep.ppjava13v2.student.controller;

import com.itstep.ppjava13v2.student.controller.validators.MemberFormValidator;
import com.itstep.ppjava13v2.student.db.dao.MemberDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Member;
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
@RequestMapping("/member")
public class MemberController {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberFormValidator validator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

	@RequestMapping
	public ModelAndView getAllMembers() throws DaoException {
		ModelAndView mav = new ModelAndView("members/members");
		List members = new ArrayList(memberDao.findAll());
		mav.addObject("members", members);
		log.trace(members.toString());
		return mav;
	}

	@RequestMapping("/searchMembers")
	public ModelAndView searchContacts(@RequestParam(required = false, defaultValue = "") String memberFirstName) throws DaoException {
		ModelAndView mav = new ModelAndView("members/members");
		List<Member> members = new ArrayList<>(memberDao.findByMemberFirstName(memberFirstName.trim()));
		mav.addObject("members", members);
		return mav;
	}

	@RequestMapping(value = "/saveMember", method = RequestMethod.GET)
	public ModelAndView newMemberForm() {
		ModelAndView mav = new ModelAndView("members/newMember");
		Member member = new Member();
		mav.getModelMap().put("newMember", member);
		return mav;
	}

	@RequestMapping(value = "/saveMember", method = RequestMethod.POST)
	public String create(@ModelAttribute("newMember") Member member, BindingResult result, SessionStatus status) throws DaoException {
		validator.validate(member, result);
		if (result.hasErrors()) {
			return "members/newMember";
		}
		memberDao.save(member);
		status.setComplete();
		return "redirect:/member";
	}

	@RequestMapping(value = "/updateMember", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("memberId") long memberId) throws DaoException {
		ModelAndView mav = new ModelAndView("members/editMember");
		Member member = memberDao.findById(memberId);
		mav.addObject("editMember", member);
		return mav;
	}

	@RequestMapping(value = "/updateMember", method = RequestMethod.POST)
	public String update(@ModelAttribute("editMember") Member member, BindingResult result, SessionStatus status) throws DaoException {
		validator.validate(member, result);
		if (result.hasErrors()) {
			return "members/editMember";
		}
		memberDao.update(member);
		status.setComplete();
		return "redirect:/member";
	}

	@RequestMapping(value = "/deleteMember", method = { RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam("memberId") long memberId) throws DaoException {
		memberDao.remove(memberId);
		return  "redirect:/member";
	}

}

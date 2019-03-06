
package controllers;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.MemberService;
import utilities.Md5;
import domain.Brotherhood;
import domain.Enrol;
import domain.Member;
import forms.MemberForm;

@Controller
@RequestMapping("/member")
public class MemberController extends AbstractController {

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}

	// List ------------------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int brotherhoodId) {
		ModelAndView result;
		Collection<Enrol> enrols;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findOne(brotherhoodId);
			enrols = brotherhood.getEnrols();
			result = new ModelAndView("member/list");
			result.addObject("enrols", enrols);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}
	// Register ------------------------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MemberForm memberForm;

		try {
			//Se crea un memberform vacio
			memberForm = new MemberForm();
			result = new ModelAndView("member/create");
			result.addObject("memberForm", memberForm);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// Save the new member ----------------------------------------------------------------------
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(final MemberForm memberForm, final BindingResult binding) {
		ModelAndView result;
		String password;

		final Member member = this.memberService.reconstruct(memberForm, binding);

		if(!memberForm.isAccepted()){
			binding.rejectValue("accepted", "register.terms.error", "Service terms must be accepted");
		}if (binding.hasErrors()) {
			result = this.createEditModelAndView(memberForm);
		} else {
			try {
				password = Md5.encodeMd5(member.getUserAccount().getPassword());
				member.getUserAccount().setPassword(password);
				this.memberService.save(member);
				result = new ModelAndView("redirect:../security/login.do");

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(memberForm, "member.commit.error");
			}
		}
		return result;
	}

	// Edit ------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Member member;

		try {
			member = this.memberService.findByPrincipal();

			// Set relations to null to use as a prune object
			member.setFinder(null);
			member.setEnrols(null);
			member.setDropouts(null);
			member.setRequests(null);

			result = new ModelAndView("member/edit");
			result.addObject("member", member);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	// SAVE ------------------------------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(final Member prune, final BindingResult binding) {
		ModelAndView result;
		Member member;

		member = this.memberService.reconstruct(prune, binding);

		if (binding.hasErrors()) {
			final List<ObjectError> errors = binding.getAllErrors();
			for (final ObjectError e : errors) {
				System.out.println(e.toString());
			}

			result = new ModelAndView("brotherhood/edit");
			result.addObject("brotherhood", prune);
		} else {
			try {
				this.memberService.save(member);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				System.out.println();
				System.out.println(oops.getMessage());
				System.out.println(oops.getClass());
				System.out.println(oops.getCause());
				result = this.editModelAndView(member, "member.registration.error");
			}
		}
		return result;
	}
	


	// Ancillary methods -----------------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final MemberForm memberForm) {
		ModelAndView result;

		result = this.createEditModelAndView(memberForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MemberForm memberForm, final String messageCode) {
		final ModelAndView result;

		result = new ModelAndView("member/create");
		result.addObject("memberForm", memberForm);

		result.addObject("message", messageCode);

		return result;

	}

	protected ModelAndView editModelAndView(final Member member) {
		ModelAndView result;

		result = this.editModelAndView(member, null);

		return result;
	}

	protected ModelAndView editModelAndView(final Member member, final String message) {
		ModelAndView result;

		result = new ModelAndView("member/edit");
		result.addObject("member", member);
		result.addObject("message", message);

		return result;
	}

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}
}

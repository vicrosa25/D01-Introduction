
package controllers.brotherhood;

import java.util.Collection;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.BrotherhoodService;
import services.MemberService;
import controllers.AbstractController;
import domain.Brotherhood;
import domain.Enrol;

@Controller
@RequestMapping("/member/brotherhood")
public class MemberBrotherhoodController extends AbstractController {

	@Autowired
	private MemberService		memberService;

	@Autowired
	private BrotherhoodService	brotherhoodService;


	@ExceptionHandler(TypeMismatchException.class)
	public ModelAndView handleMismatchException(final TypeMismatchException oops) {
		return new ModelAndView("redirect:/");
	}
	/******** MAIN METHODS *********/
	// List ------------------------------------------------------------------------------------
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Enrol> enrols;
		Brotherhood brotherhood;

		try {
			brotherhood = this.brotherhoodService.findByPrincipal();
			enrols = brotherhood.getEnrols();
			result = new ModelAndView("member/list");
			result.addObject("enrols", enrols);
			result.addObject("bro", 1);
		} catch (final Throwable oops) {
			System.out.println(oops.getMessage());
			System.out.println(oops.getClass());
			System.out.println(oops.getCause());
			result = this.forbiddenOpperation();
		}

		return result;
	}

	/** ANCILLARY METHODS **/

	private ModelAndView forbiddenOpperation() {
		return new ModelAndView("redirect:/");
	}

	/************************************************************************************************/
}

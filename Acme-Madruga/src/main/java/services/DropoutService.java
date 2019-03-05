package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Dropout;
import domain.Enrol;
import domain.Member;
import domain.Position;
import repositories.DropoutRepository;

@Service
@Transactional
public class DropoutService {

	// Managed repository   -------------------------------------------------------------
	@Autowired
	private DropoutRepository dropoutRespository;

	// Supporting services  -------------------------------------------------------------
	@Autowired
	private ActorService 	  actorService;
	
	@Autowired
	private EnrolService	  enrolService;

	

	// CRUD methods ---------------------------------------------------------------------
	public Dropout create() {
		Dropout result = new Dropout();
		Calendar calendar = new GregorianCalendar();			
		result.setDate(calendar.getTime());
		
		return result;
	}
	
	
	
	public Dropout findOne(int dropoutId) {
		Dropout result = this.dropoutRespository.findOne(dropoutId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Dropout> findAll() {
		Collection<Dropout> result = this.dropoutRespository.findAll();
		Assert.notEmpty(result);
		Assert.notNull(result);

		return result;
	}

	public Dropout save(Dropout dropout) {
		Assert.notNull(dropout);
		Dropout result;
		Actor principal;
		
		// Make sure that the principal is a Member
		principal = this.actorService.findByPrincipal();
		Assert.isInstanceOf(Member.class, principal);
		
		Member member = (Member) principal;
		
		ArrayList<Position> positions = new ArrayList<Position>();
		Position position;
		
		
		// Delete the enroll
		for(Enrol enrol : dropout.getBrotherhood().getEnrols()) {
			if(enrol.getMember().equals(member)) {
				// Delete enrol from position
				positions.addAll(enrol.getPositions());
				position = positions.get(0);
				position.getEnrol().remove(enrol);
				
				this.enrolService.delete(enrol);
			}
		}
		
		result = this.dropoutRespository.save(dropout);

		return result;
	}

	public void delete(Dropout dropout) {
		Assert.notNull(dropout);

		this.dropoutRespository.delete(dropout);

	}

	// Other methods
	// -----------------------------------------------------------------

}

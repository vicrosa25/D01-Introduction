
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.EnrolRepository;
import domain.Enrol;
import domain.Position;

@Service
@Transactional
public class EnrolService {

	// Managed repository
	// -------------------------------------------------------------
	@Autowired
	private EnrolRepository	enrolRepository;


	// Supporting services
	// -------------------------------------------------------------

	// CRUD methods
	// ------------------------------------------------------------------
	public Enrol create() {
		final Enrol result = new Enrol();

		result.setMoment(new Date());
		result.setPositions(new ArrayList<Position>());

		return result;
	}

	public Enrol findOne(final int enrolId) {
		final Enrol result = this.enrolRepository.findOne(enrolId);
		Assert.notNull(result);
		return result;
	}

	public Collection<Enrol> findAll() {
		final Collection<Enrol> result = this.enrolRepository.findAll();
		Assert.notEmpty(result);
		Assert.notNull(result);

		return result;
	}

	public Enrol save(final Enrol enrol) {
		Assert.notNull(enrol);
		final Enrol result = this.enrolRepository.save(enrol);

		return result;
	}

	public void delete(final Enrol enrol) {
		Assert.notNull(enrol);

		this.enrolRepository.delete(enrol);

	}

	// Other methods
	// -----------------------------------------------------------------

}

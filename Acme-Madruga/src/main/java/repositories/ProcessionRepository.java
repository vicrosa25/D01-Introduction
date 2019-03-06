package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Procession;

public interface ProcessionRepository extends JpaRepository<Procession, Integer> {
	
	@Query("select p from Procession p where p.brotherhood.id = ?1 and p.draftMode = 'false'")
	Collection<Procession> findByBrotherhoodNotDraft(int brotherhoodId);

}

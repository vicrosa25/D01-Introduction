
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import domain.Request;

public interface RequestRepository extends JpaRepository<Request, Integer> {

	@Query("select r from Request r join r.procession p where p.brotherhood.id = ?1 order by r.status")
	Collection<Request> findRequestByBrotherhood(int brotherhoodId);

}

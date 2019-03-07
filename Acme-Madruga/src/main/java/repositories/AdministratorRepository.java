
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;
import domain.Brotherhood;
import domain.Procession;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	
	// Queries level C
	@Query("select admin from Administrator admin where admin.userAccount.id = ?1")
	Administrator findByUserAccountId(int id);

	@Query("select avg(b.enrols.size), min(b.enrols.size), max(b.enrols.size), stddev(b.enrols.size) from Brotherhood b")
	Object[] query1();

	@Query("select b1 from Brotherhood b1 where b1.enrols.size = (Select max(b2.enrols.size) from Brotherhood b2)")
	Collection<Brotherhood> query2();

	@Query("select b1 from Brotherhood b1 where b1.enrols.size = (Select min(b2.enrols.size) from Brotherhood b2)")
	Collection<Brotherhood> query3();

	@Query("select count(r1)*1.0 / (select count(r2)*1.0 from Request r2) from Request r1 group by r1.status")
	Collection<Double> query4();

	@Query("select p from Procession p where p.moment <= ?1")
	Collection<Procession> query5(Date date);
	
	@Query("select m.name, count(r), (select count(r2)*0.1 from Request r2) from Member m join m.requests r where r.status = 'APPROVED' group by m having count(r) >= (select count(r2)*0.1 from Request r2)")
	Collection<Object> query7();
	
	@Query("select p.englishName, p.enrol.size from Position p")
	Collection<Object> query8();
	
	
	// Queries level B
	@Query("select count(a)*1.0/(select count(b)*1.0 from Brotherhood b), count(a.brotherhoods.size), avg(a.brotherhoods.size), min(a.brotherhoods.size), max(a.brotherhoods.size), stddev(a.brotherhoods.size) from Area a")
	Object[] query9();
	
	@Query("select min(f.processions.size), max(f.processions.size), avg(f.processions.size), stddev(f.processions.size) from Finder f")
	Object[] query10();
	
	@Query("select count(f)*1.0 / (select count(f1)*1.0 from Finder f1 where f1.processions.size > 0) from Finder f where f.processions.size = 0")
	Double query11();
	
	
	
	// Chart queries
	@Query("select count(a) from Actor a where a.isSpammer = true")
	Integer getAllSpammers();

	@Query("select count(a) from Actor a where a.isSpammer = false")
	Integer getAllNotSpammers();
	
	@Query("select avg(a.score) from Actor a")
	Double getAveragePolarity();
}

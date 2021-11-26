package acme.features.officer.duty;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.roles.Officer;
import acme.entities.duties.Duty;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface OfficerDutyRepository extends AbstractRepository {

	@Query("select t from Duty t where t.officer.id = ?1 order by t.workload desc")
	Collection<Duty> findMany(int id);

	@Query("select t from Duty t where t.id = ?1")
	Duty findOneDutyById(int id);
	
	@Query("select t from Duty t where t.isPublic = true")
	Collection<Duty> findpublicDuty();

	@Query("select m from Officer m where m.id = ?1")
	Officer getOfficerById(int id);
}

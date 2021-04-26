package acme.features.anonymous.task;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.tasks.Task;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousTaskRepository extends AbstractRepository {
	
	@Query("select t from Task t where t.isPublic = true AND t.endPeriod > :current order by t.workload desc")
	Collection<Task> findMany(Date current);

	@Query("select t from Task t where t.id = ?1")
	Task findOneTaskById(int id);
}

package acme.features.administrator.spamWord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.spamWords.SpamWord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorSpamWordRepository extends AbstractRepository {


	@Query("select s.word from SpamWord s")
	Collection<String> findWords();

	@Query("select s from SpamWord s")
	Collection<SpamWord> findMany();
	
}

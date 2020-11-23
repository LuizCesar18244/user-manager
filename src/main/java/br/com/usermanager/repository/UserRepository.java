package br.com.usermanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.usermanager.model.Status;
import br.com.usermanager.model.User;

public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByName( String name);

	List<User> findByStationName(String station);

	List<User> findByProfilesName(String profile);

	List<User> findByStatus(Status status);
	
	@Query(value = "SELECT * FROM User u where u.cpf= :cpf AND u.status= :status", nativeQuery = true)
	Optional<User> findIfIsActive( @Param( "cpf" ) String cpf, @Param( "status" ) String status);
	
	@Query(value = "SELECT * FROM USER u WHERE u.gender = 'FEMALE' AND FLOOR(DATEDIFF(dd, u.BIRTHDATE, CURRENT_DATE)/365.25) >= 18", nativeQuery = true)
	List<User> findFemalesBiggerThenEighteen();

	@Query(value = "SELECT u.cpf FROM USER u WHERE SUBSTR(u.cpf, 1, 1) = '0'", nativeQuery = true)
	List<String> findCpfsThatStartsWithZero();
}



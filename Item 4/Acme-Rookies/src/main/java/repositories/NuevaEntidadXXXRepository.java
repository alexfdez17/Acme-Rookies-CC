
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.NuevaEntidadXXX;

@Repository
public interface NuevaEntidadXXXRepository extends JpaRepository<NuevaEntidadXXX, Integer> {

	@Query("select n from NuevaEntidadXXX n where n.audit.auditor.id=?1")
	Collection<NuevaEntidadXXX> findByAuditorId(int id);

	@Query("select n from NuevaEntidadXXX n where n.audit.position.company.id=?1")
	Collection<NuevaEntidadXXX> findByCompanyId(int id);

	@Query("select n from NuevaEntidadXXX n where n.audit.id=?1")
	Collection<NuevaEntidadXXX> findByAuditId(int id);

}

package home.repository;

import home.entity.ApplEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplRepository extends JpaRepository<ApplEntity, String> {
  boolean existsByApplId(String applId);

  @Query("select distinct a.apiMtdatApplId FROM ApplEntity a WHERE a.applId = ?1")
  List<String> findDistinctApiMtdatApplIds(String applId);
}

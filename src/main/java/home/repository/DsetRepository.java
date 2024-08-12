package home.repository;

import home.entity.DsetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DsetRepository extends JpaRepository<DsetEntity, String> {}

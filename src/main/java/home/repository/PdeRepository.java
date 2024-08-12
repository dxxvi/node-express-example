package home.repository;

import home.entity.PdeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdeRepository extends JpaRepository<PdeEntity, String> {}

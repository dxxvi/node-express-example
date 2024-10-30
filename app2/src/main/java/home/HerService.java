package home;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class HerService {
  @PersistenceContext private EntityManager em;

  private final HerLockRepository repository;

  public HerService(HerLockRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public void only1canAccess() throws Exception {
    String id = "id-1";
    Optional<HerLock> herLockOptional = repository.findById(id);
    System.out.printf(
        "In app2: the db should lock on the row %s: %s\n", id, herLockOptional.isPresent());
    for (int i = 0; i < 99; i++) {
      System.out.printf("%d ... ", i);
      Thread.sleep(999);
    }
    System.out.println("\nNow the db is not locked by app2 anymore");
  }

  @Transactional
  public void only1canAccessNonOracle() throws Exception {
    String id = "id-1";
    List<HerLock> herLocks =
        em.createQuery("select l from HerLock l where l.id = :id", HerLock.class)
            .setParameter("id", id)
            .setHint("jakarta.persistence.lock.timeout", 999)
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .getResultList();
    System.out.printf("In app2: the db should lock on %d row(s): %s\n", herLocks.size(), id);
    for (int i = 0; i < 99; i++) {
      System.out.printf("%d ... ", i);
      Thread.sleep(999);
    }
    System.out.println("\nNow the db is not locked by app2 anymore");
  }
}

package home;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MyService {
  @PersistenceContext private EntityManager em;

  private final MyLockRepository repository;

  public MyService(MyLockRepository repository) {
    this.repository = repository;
  }

  @Transactional
  public void only1canAccess() throws Exception {
    String id = "id-1";
    Optional<MyLock> myLockOptional = repository.findById(id);
    System.out.printf("The db should lock the row %s: %s\n", id, myLockOptional.isPresent());
    for (int i = 0; i < 100; i++) {
      System.out.printf("%d ... ", i);
      Thread.sleep(999);
    }
    System.out.println("\nNow the db is not locked anymore");
  }

  @Transactional
  public void only1canAccessNonOracle() throws Exception {
    String id = "id-1";
    /*
        List<MyLock> myLocks =
            em.createQuery("select l from MyLock l where l.id = :id", MyLock.class)
                .setParameter("id", id)
                .setHint("jakarta.persistence.lock.timeout", 999)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getResultList();
        System.out.printf("The db should lock %d row(s): %s\n", myLocks.size(), id);
    */
    var myLock =
        em.find(
            MyLock.class,
            id,
            LockModeType.PESSIMISTIC_WRITE,
            Map.of("jakarta.persistence.lock.timeout", 9));
    System.out.printf("The db should lock %s row", myLock == null ? 0 : 1);
    for (int i = 0; i < 100; i++) {
      System.out.printf("%d ... ", i);
      Thread.sleep(999);
    }
    System.out.println("\nNow the db is not locked anymore");
  }
}

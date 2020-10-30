package ua.antibyte.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ua.antibyte.dao.PersonageDao;
import ua.antibyte.model.Personage;

@Repository
public class PersonageDaoImpl implements PersonageDao {
    private final SessionFactory sessionFactory;

    public PersonageDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void addAll(List<Personage> personages) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession().getSession();
            transaction = session.beginTransaction();
            for (Personage personage : personages) {
                session.save(personage);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't add all personages " + e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public Personage findById(Long id) {
        try (Session session = sessionFactory.openSession().getSession()) {
            Query<Personage> findByIdQuery = session.createQuery("from Personage p "
                    + "where p.id = :id", Personage.class);
            findByIdQuery.setParameter("id", id);
            return findByIdQuery.getSingleResult();
        } catch (Exception e) {
            throw new RuntimeException("Can't find personage by id " + id, e);
        }
    }

    @Override
    public List<Personage> findByName(String name) {
        try (Session session = sessionFactory.openSession().getSession()) {
            Query<Personage> findByNameQuery = session.createQuery("from Personage p "
                    + "where p.name like :name", Personage.class);
            findByNameQuery.setParameter("name", "%" + name + "%");
            return findByNameQuery.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Can't find by name " + name, e);
        }
    }

    @Override
    public Long getCountOfPersonages() {
        try (Session session = sessionFactory.openSession().getSession()) {
            return (Long) session.createQuery("select count(*) from Personage p").uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Can't get count of personages" + e);
        }
    }
}

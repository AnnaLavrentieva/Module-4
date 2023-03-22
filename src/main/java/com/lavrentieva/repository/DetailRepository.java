package com.lavrentieva.repository;

import com.lavrentieva.config.HibernateUtil;
import com.lavrentieva.model.Detail;
import com.lavrentieva.model.StatsDTO;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

public class DetailRepository {
    private static DetailRepository instance;

    public static DetailRepository getInstance() {
        if (instance == null) {
            instance = new DetailRepository();
        }
        return instance;
    }

    public void save(final Detail detail) {
        Objects.requireNonNull(detail);
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(detail);
        entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public StatsDTO showStatistic() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<StatsDTO> query = criteriaBuilder.createQuery(StatsDTO.class);
        final Root<Detail> root = query.from(Detail.class);
        query.select(criteriaBuilder.construct(
                StatsDTO.class,
                criteriaBuilder.count(root.get("id")),
                criteriaBuilder.sum(root.get("brokenMicrochips")),
                criteriaBuilder.sum(
                        criteriaBuilder.sum(root.get("fuel")),
                        criteriaBuilder.sum(root.get("spentFuel")))));
        return entityManager.createQuery(query).getSingleResult();
    }

    public Detail getDetailById(final String id) {
        Objects.requireNonNull(id);
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        return Objects.requireNonNull(entityManager.find(Detail.class, id));
    }

    public List<String> getAllId() {
        final EntityManager entityManager = HibernateUtil.getEntityManager();
        final String sql = "select id from details";
        return entityManager.createNativeQuery(sql).getResultList();
    }
}

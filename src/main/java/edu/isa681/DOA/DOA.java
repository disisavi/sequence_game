package edu.isa681.DOA;

import com.google.api.services.plus.Plus;
import edu.isa681.DOA.entity.Player;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DOA {
    private static DOA doa;
    private Session session;
    private Transaction transaction;

    private DOA() {
        this.session = HibernateUtil.getSessionFactory().openSession();
        transaction = this.session.beginTransaction();
    }

    public static DOA getDoa() {
        if (doa == null) {
            synchronized (DOA.class) {
                if (doa == null) {
                    doa = new DOA();
                }
            }
        }
        return doa;
    }

    void getNewSession() {
        this.session = HibernateUtil.getSessionFactory().openSession();
        transaction = this.session.beginTransaction();
    }

    void startTrasaction() {
        transaction.begin();
    }

    public Object getByKey(Class className, String key) {
        if (!this.transaction.isActive()) {
            this.startTrasaction();
        }
        Object object = this.session.get(className, key);
        this.commit();
        return object;
    }


    public void updateObject(Object object) {
        if (!this.transaction.isActive()) {
            this.startTrasaction();
        }
        this.session.update(object);
        commit();
    }

    public synchronized void persistNewObject(Object object) {
        if (!this.transaction.isActive()) {
            this.startTrasaction();
        }
        this.session.persist(object);
        commit();
    }

    public synchronized void removeObject(Object object) {
        if (!this.transaction.isActive()) {
            this.startTrasaction();
        }
        this.session.delete(object);
        commit();
    }

    public Set<Object> getAll(Class classname) {
        if (!this.transaction.isActive()) {
            this.startTrasaction();
        }
        List objects = this.session.createCriteria(classname).list();
        Set<Object> retrunObjectSet = new HashSet<>(objects);

        this.commit();
        return retrunObjectSet;
    }

    public Player getPlayerByEmail(byte[] encryptedEmail) {
        if (!this.transaction.isActive()) {
            this.startTrasaction();
        }

        Player player = (Player) this.session.createCriteria(Player.class).add(Restrictions.eq("emailID", encryptedEmail)).uniqueResult();
        return player;
    }

    private void commit() {
        try {
            this.session.getTransaction().commit();
            this.getNewSession();
        } catch (Exception e) {
            this.session.getTransaction().rollback();
            throw e;
        }
    }
}

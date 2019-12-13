package edu.isa681.DOA;

import edu.isa681.DOA.entity.Player;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;


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

    public void getNewSession() {
        try {
            this.session = HibernateUtil.getSessionFactory().getCurrentSession();
        } catch (Exception ex) {
            this.session = HibernateUtil.getSessionFactory().openSession();
        }

        transaction = this.session.beginTransaction();
    }

    void startTrasaction() {
        transaction.begin();
    }


    public void updateObject(Object object) {
        if (!this.transaction.isActive()) {
            this.startTrasaction();
        }
        this.session.saveOrUpdate(object);
    }


//    public Set<Object> getAll(Class classname) {
//        if (!this.transaction.isActive()) {
//            this.startTrasaction();
//        }
//        List objects = this.session.createCriteria(classname).list();
//        Set<Object> retrunObjectSet = new HashSet<>(objects);
//
//        return retrunObjectSet;
//    }

    public Player getPlayerByEmail(String encryptedEmail) {
        getNewSession();
        if (!this.transaction.isActive()) {
            this.startTrasaction();
        }
        Player player = (Player) this.session.createCriteria(Player.class).add(Restrictions.eq("playerSub", encryptedEmail)).uniqueResult();
        commit();
        return player;
    }

    public void commit() {
        try {
            this.transaction.commit();
        } catch (Exception e) {
            this.transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}

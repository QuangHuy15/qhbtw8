package vniot.star.dao.impl;

import java.util.List;

import jakarta.persistence.*;
import jakarta.persistence.Query;
import vniot.star.configs.JPAConfig;
import vniot.star.dao.IVideoDao;
import vniot.star.entity.Video;

public class VideoDao implements IVideoDao {

	@Override
	public int count() {
        EntityManager em = JPAConfig.getEntityManager();
        String jpql = "SELECT count(v) FROM Video v"; // Đếm số lượng video
        Query query = em.createQuery(jpql);
        return ((Long) query.getSingleResult()).intValue();
	}

	@Override
	public List<Video> findAll(int page, int pageSize) {
        EntityManager em = JPAConfig.getEntityManager();
        TypedQuery<Video> query = em.createNamedQuery("Video.findAll", Video.class);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize); 
        return query.getResultList();
	}

	@Override
	public List<Video> findByTitle(String title) {
        EntityManager em = JPAConfig.getEntityManager();
        String jpql = "SELECT v FROM Video v WHERE v.title LIKE :title"; 
        TypedQuery<Video> query = em.createQuery(jpql, Video.class);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
	}

	@Override
	public List<Video> findAll() {
        EntityManager em = JPAConfig.getEntityManager();
        TypedQuery<Video> query = em.createNamedQuery("Video.findAll", Video.class); 
        return query.getResultList();
	}

	@Override
	public Video findById(String videoid) {
        EntityManager em = JPAConfig.getEntityManager();
        return em.find(Video.class, videoid); 
	}

	@Override
	public void delete(String videoid) throws Exception {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Video video = em.find(Video.class, videoid); 
            if (video != null) {
                em.remove(video);
            } else {
                throw new Exception("Không tìm thấy video");
            }
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback(); 
            throw e;
        } finally {
            em.close();
        }
		
	}

	@Override
	public void update(Video video) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(video); 
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback();
            throw e;
        } finally {
            em.close();
        }
	}

	@Override
	public void insert(Video video) {
        EntityManager em = JPAConfig.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(video); 
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            trans.rollback(); 
            throw e;
        } finally {
            em.close();
        }
	}

}

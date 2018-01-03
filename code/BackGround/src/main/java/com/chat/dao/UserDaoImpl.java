package com.chat.dao;

import com.chat.entity.User;
import com.chat.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao{
    @Transactional
    public void add(User user) {
        Session session=HibernateUtil.getCurrentSession();
        session.save(user);
    }

    public void update(User user) {
        Session session=HibernateUtil.getCurrentSession();
        session.update(user);
    }

    public User getUser(String id) {
        Session session=HibernateUtil.getCurrentSession();
        return session.get(User.class,new Short(id));
    }

    public User getUserByEmail(String email) {
        Session session=HibernateUtil.getCurrentSession();
        Query<User> query=session.createQuery(
                "from User where email=?",User.class);
        query.setParameter(0,email);

        List<User> list=query.list();

        if(list.size()==0){
            return null;
        }

        return list.get(0);
    }

    public User getBriefUserByEmail(String email) {
        User user=getUserByEmail(email);

        HibernateUtil.getCurrentSession().evict(user);
        user.setPwd(null);
        user.setCertificate(null);
        user.setTags(null);
        user.setFriends(null);
        user.setMessages(null);
        user.setFriendApplies(null);

        return user;
    }
}

package com.example.CryptoApp.Repository;


import com.example.CryptoApp.Entity.Coin;
import com.example.CryptoApp.dto.CoinTransationDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@EnableAutoConfiguration
public class CoinRepository {
    @Autowired
    private EntityManager entityManager;



    @Transactional
    public Coin insert(Coin coin) {

        entityManager.persist(coin);
        return coin;
    }

    @Transactional
    public Coin update(Coin coin) {
        entityManager.merge(coin);
        return coin;
    }

    public List<CoinTransationDTO> getAll() {
        String jpql = "select new com.example.CryptoApp.dto.CoinTransationDTO(c.name,sum(c.quantity) from coin c group by c.name";
        TypedQuery<CoinTransationDTO> query = entityManager.createQuery(jpql, CoinTransationDTO.class);
        return query.getResultList();
    }

    public List<Coin> getByName(String name) {
        String jpql = "select c from Coin c where c.name like :name";
            TypedQuery<Coin> query = entityManager.createQuery(jpql, Coin.class);
            query.setParameter("name", "%"+ name + "%");
            return query.getResultList();
    }

    @Transactional
    public boolean remove(int id) {
       Coin coin = entityManager.find(Coin.class, id);

       if (coin ==null)
           throw new RuntimeException();
       entityManager.remove(coin);
       return true;
    }
}

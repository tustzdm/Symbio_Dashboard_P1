package com.symbio.dashboard.data.repository;

import com.symbio.dashboard.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRep extends JpaRepository<Menu, Integer> {

    Menu getById(Integer id);

    @Query(value = "SELECT * FROM menu WHERE validation = 1 ORDER BY idx", nativeQuery = true)
    List<Menu> getList();

}

package com.lcdd.backend.dbrepositories;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.lcdd.backend.pojo.Event;
import com.lcdd.backend.pojo.Game;
import com.lcdd.backend.pojo.User;

public interface EventRepository extends JpaRepository<Event, Long> {
	
	@Query("SELECT g.acronym, COUNT(e) FROM Event e JOIN e.game g GROUP BY e.game")
	List<Object[]> countGamesEvent();
	
	Page<Event> findAll(Pageable page);
	Page<Event> findByGame(Game game, Pageable pageable);
	
	Event findByName(String name);
	Event findById(long id);
	
}

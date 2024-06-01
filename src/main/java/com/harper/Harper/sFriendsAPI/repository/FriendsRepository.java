package com.harper.Harper.sFriendsAPI.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.harper.Harper.sFriendsAPI.model.Friend;

public interface FriendsRepository extends CrudRepository<Friend, Long> {
    Optional<Friend> findByName(String name);
    List<Friend> findBySize(String size);
    List<Friend> findByColor(String color);
    List<Friend> findByIsSoft(Boolean isSoft);
    Optional<Friend> findByRank(Integer rank);
    List<Friend> findByDoesSqueak(Boolean doesSqueak);

    List<Friend> findByRankGreaterThanOrderByRank(Integer rank);
}

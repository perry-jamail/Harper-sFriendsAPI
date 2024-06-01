package com.harper.Harper.sFriendsAPI.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.harper.Harper.sFriendsAPI.model.Friend;
import com.harper.Harper.sFriendsAPI.repository.FriendsRepository;

/*REST Controller which performs the necessary CRUD operations for the FriendsRepository */

@RestController
@RequestMapping("/friends")
public class FriendsController {
    private final FriendsRepository friendsRepo;

    public FriendsController(FriendsRepository friendsRepository) {
        this.friendsRepo = friendsRepository;
    }

    /*READ */
    @GetMapping
    public Iterable<Friend> getAllFriends() {
        return this.friendsRepo.findAll();
    }

    private void validateNewFriend(Friend friend) {
        if (ObjectUtils.isEmpty(friend.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Friend> friendToValidateOptional = this.friendsRepo.findById(friend.getId());
        if (friendToValidateOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }

    /*CREATE */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Friend addFriend(@RequestBody Friend friend) {
        validateNewFriend(friend);
        return this.friendsRepo.save(friend);
    }

    /*DELETE */
    @DeleteMapping("/{id}")
    public Friend deleteFriend(@PathVariable Long id) {
        Optional<Friend> friendToDeleteOptional = this.friendsRepo.findById(id);
        if (!friendToDeleteOptional.isPresent()) {
            return null;
        }

        Friend friendToDelete = friendToDeleteOptional.get();
        this.friendsRepo.delete(friendToDelete);
        return friendToDelete;
    }

    @GetMapping("/{id}")
    public Friend findById(@PathVariable Long id) {
        Optional<Friend> foundFriendOptional = this.friendsRepo.findById(id);
        if (!foundFriendOptional.isPresent()) {
            return null;
        }

        Friend foundFriend = foundFriendOptional.get();
        return foundFriend;
    }

    /*UPDATE */
    @PutMapping("/{id}")
    public Friend updateFriendInfo(@PathVariable Long id, @RequestBody Friend friend) {
        Optional<Friend> friendToUpdateOptional = this.friendsRepo.findById(id);
        if (friendToUpdateOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Friend friendToUpdate = friendToUpdateOptional.get();
        copyFriendInfoFrom(friend, friendToUpdate);
        this.friendsRepo.save(friendToUpdate);
        return friendToUpdate;
    }

    private void copyFriendInfoFrom(Friend updatedFriend, Friend existingFriend) {
        if (ObjectUtils.isEmpty(updatedFriend.getName())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if (!ObjectUtils.isEmpty(updatedFriend.getSize())) {
            existingFriend.setSize(updatedFriend.getSize());
        }

        if (!ObjectUtils.isEmpty(updatedFriend.getColor())) {
            existingFriend.setColor(updatedFriend.getColor());
        }

        if (!ObjectUtils.isEmpty(updatedFriend.getIsSoft())) {
            existingFriend.setIsSoft(updatedFriend.getIsSoft());
        }

        if (!ObjectUtils.isEmpty(updatedFriend.getRank())) {
            existingFriend.setRank(updatedFriend.getRank());
        }

        if (!ObjectUtils.isEmpty(updatedFriend.getDoesSqueak())) {
            existingFriend.setDoesSqueak(updatedFriend.getDoesSqueak());
        }
    }

    /*Advanced search operations */
    @GetMapping("/sortByRank")
    public List<Friend> sortByRank() {
        List<Friend> returnList = this.friendsRepo.findByRankGreaterThanOrderByRank(0);
        return returnList;
    }

    @GetMapping("/findByName/{name}")
    public Friend findByName(@PathVariable String name) {
        Optional<Friend> foundFriendOptional = this.friendsRepo.findByName(name);
        if (!foundFriendOptional.isPresent()) {
            return null;
        }

        Friend foundFriend = foundFriendOptional.get();
        return foundFriend;
    }

    @GetMapping("/findBySize/{size}")
    public List<Friend> findBySize(@PathVariable String size) {
        List<Friend> listBySize = this.friendsRepo.findBySize(size);
        return listBySize;
    }

    @GetMapping("/findByColor/{color}")
    public List<Friend> findByColor(@PathVariable String color) {
        List<Friend> listByColor = this.friendsRepo.findByColor(color);
        return listByColor;
    }

    @GetMapping("/findByIsSoft/{isSoft}")
    public List<Friend> findByIsSoft(@PathVariable Boolean isSoft) {
        List<Friend> listByIsSoft = this.friendsRepo.findByIsSoft(isSoft);
        return listByIsSoft;
    }

    @GetMapping("/findByRank/{rank}")
    public Friend findByRank(@PathVariable Integer rank) {
        Optional<Friend> foundByRankOptional = this.friendsRepo.findByRank(rank);
        if (!foundByRankOptional.isPresent()) {
            return null;
        }

        Friend foundByRank = foundByRankOptional.get();
        return foundByRank;
    }

    @GetMapping("/findByDoesSqueak/{doesSqueak}")
    public List<Friend> findByDoesSqueak(@PathVariable Boolean doesSqueak) {
        List<Friend> listByDoesSqueak = this.friendsRepo.findByDoesSqueak(doesSqueak);
        return listByDoesSqueak;
    }
}

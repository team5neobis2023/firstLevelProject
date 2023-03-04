package com.neobis.g4g.girls_for_girls.controller;

import com.neobis.g4g.girls_for_girls.data.entity.UserGroup;
import com.neobis.g4g.girls_for_girls.repository.UserGroupRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/role")
public class UserGroupController {

    private final UserGroupRepository userGroupRepository;

    @Autowired
    public UserGroupController(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    @Operation(summary = "Получить роль по его ID", tags = "Роли")
    @GetMapping("/{id}")
    public UserGroup getUserGroup(@PathVariable int id){
        return userGroupRepository.findById(id);
    }

    @Operation(summary = "Получить все роли", tags = "Роли")
    @GetMapping
    public List<UserGroup> getUserGroups(){
        return userGroupRepository.findAll();
    }

    @Operation(summary = "Добавить новую роль", tags = "Роли")
    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public UserGroup addNewGroup(@RequestBody UserGroup userGroup){
        return userGroupRepository.save(userGroup);
    }

    @Operation(summary = "Изменить роль", tags = "Роли")
    @PutMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public UserGroup saveGroup(@RequestBody UserGroup userGroup){
        return userGroupRepository.save(userGroup);
    }

    @Operation(summary = "Удалить роль по его ID", tags = "Роли")
    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteUserGroup(@PathVariable int id){
        userGroupRepository.deleteById(id);
    }
}

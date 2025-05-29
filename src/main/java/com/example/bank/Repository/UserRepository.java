package com.example.bank.Repository;

import com.example.bank.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // việc dùng Optional giúp xử lý tình huống không có user trong DB
    // 1 cách rõ ràng và an toàn hơn trả về null
    Optional<User> findByUsername(String username);



    // Tìm user kể cả đã bị xóa
    @Query("SELECT u FROM User u WHERE u.id = :id")
    Optional<User> findByIdIncludeDeleted(@Param("id") Long id);

    // Lấy toàn bộ user đã bị xóa
    @Query("SELECT u FROM User u WHERE u.deleted = true")
    List<User> findDeletedUsers();
}

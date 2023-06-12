package com.A2P.dev.mapper;

import com.A2P.dev.domain.entity.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper {
    @Insert("INSERT INTO admin (admin_id, admin_password, admin_name, admin_birth, admin_phone_number, admin_point, admin_position, admin_registration_date) " +
            "VALUES (#{adminId}, #{adminPassword}, #{adminName}, #{adminBirth}, #{adminPhoneNumber}, #{adminPoint}, #{adminPosition.name()}, #{adminRegistrationDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertAdmin(Admin admin);

    @Select("SELECT * FROM admin WHERE admin_id = #{adminId} AND admin_password = #{adminPassword}")
    Admin loginAdmin(@Param("adminId") String adminId, @Param("adminPassword") String adminPassword);
}

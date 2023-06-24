package com.A2P.dev.mapper;

import com.A2P.dev.domain.entity.Admin;
import com.A2P.dev.domain.request.FindIdRequest;
import com.A2P.dev.domain.request.ManagerSignUpRequest;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;

@Mapper
public interface AdminMapper {

    // salt 조회
    @Select("SELECT salt FROM admin WHERE admin_id = #{adminId}")
    String searchingSalt(@Param("adminId") String adminId);

    // 로그인
    @Select("SELECT * FROM admin WHERE admin_id = #{adminId} AND admin_password = #{adminPassword}")
    Admin login(@Param("adminId") String adminId, @Param("adminPassword") String adminPassword);

    // 이메일 중복 체크
    @Select("SELECT * FROM admin WHERE admin_id = #{adminId}")
    Admin idCheck(@Param("adminId") String adminId);

    // 매니저 회원 등록
    @Insert("INSERT INTO admin (admin_id, admin_password, salt, admin_name, admin_birth, admin_phone_number, admin_point, admin_position, admin_registration_date) " +
            "VALUES (#{adminId}, #{adminPassword}, #{salt}, #{adminName}, #{adminBirth}, #{adminPhoneNumber}, #{adminPoint}, #{adminPosition}, #{adminRegistrationDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int managerSignUp(Admin admin);

    // 아이디 찾기
    @Select("SELECT admin_id FROM admin WHERE admin_name = #{adminName} AND admin_birth = #{adminBirth} AND admin_phone_number = #{adminPhoneNumber}")
    String findId(@Param("adminName") String adminName, @Param("adminBirth") LocalDate adminBirth, @Param("adminPhoneNumber") String adminPhoneNumber);

    // 비밀번호 변경할 계정 찾기
    @Select("SELECT id FROM admin WHERE admin_id = #{adminId} AND admin_name = #{adminName} AND admin_birth = #{adminBirth}")
    Long findPw(@Param("adminId") String adminId, @Param("adminName") String adminName, @Param("adminBirth") LocalDate adminBirth);

    // id로 변경할 비밀번호 salt 조회
    @Select("SELECT salt FROM admin WHERE id = #{id}")
    String findSaltById(@Param("id") Long id);

    // 비밀번호 변경
    @Update("UPDATE admin SET admin_password = #{adminPassword} WHERE id = #{id}")
    int changePw(@Param("adminPassword") String adminPassword, @Param("id") Long id);
}

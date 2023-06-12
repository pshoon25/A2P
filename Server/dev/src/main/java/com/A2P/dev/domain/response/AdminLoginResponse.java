package com.A2P.dev.domain.response;

import com.A2P.dev.domain.entity.Admin;
import com.A2P.dev.domain.enums.AdminStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminLoginResponse {
    private Long id;
    private String adminName;
    private String adminPoint;
    private AdminStatus adminPosition;

    public AdminLoginResponse(Admin admin){
        this.id = admin.getId();
        this.adminName = admin.getAdminName();
        this.adminPoint = admin.getAdminPoint();
        this.adminPosition = admin.getAdminPosition();
    }
}

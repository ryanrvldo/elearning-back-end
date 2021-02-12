package com.lawencon.elearning.service;

import com.lawencon.elearning.dto.admin.DashboardResponseDto;

/**
 * @author Rian Rivaldo
 */
public interface AdminService {

  DashboardResponseDto getDashboard() throws Exception;

}

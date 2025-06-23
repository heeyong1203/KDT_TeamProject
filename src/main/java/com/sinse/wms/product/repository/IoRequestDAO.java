package com.sinse.wms.product.repository;


import java.sql.*;
import java.util.*;

import com.sinse.wms.common.Config;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.*;

public class IoRequestDAO {
    DBManager dbManager = DBManager.getInstance();
 
    // 전체 입출고 요청 조회
    public List<IoRequest> selectByFilter(String io_request_type, String status_name, List<String> filters) { 
           
        List<IoRequest> list = new ArrayList<>();
        Connection con = dbManager.getConnetion(); 
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	StringBuffer sql = new StringBuffer();
        	sql.append("SELECT ir.*,");
        	sql.append(" p.product_name, p.product_code,");
        	sql.append(" co.company_name,");
        	sql.append(" m.member_name,");
        	sql.append(" d.dept_name,");
        	sql.append(" rs.status_name");
        	sql.append(" FROM io_request ir");
        	sql.append(" RIGHT JOIN product p ON ir.product_id = p.product_id");
        	sql.append(" RIGHT JOIN company co ON p.company_id = co.company_id");
        	sql.append(" RIGHT JOIN member m ON ir.request_member_id = m.member_id");
        	sql.append(" RIGHT JOIN dept d ON m.dept_id = d.dept_id");
        	sql.append(" RIGHT JOIN request_status rs ON ir.status_id = rs.status_id");
        	sql.append(" WHERE 1=1");
        	if(io_request_type != null && !io_request_type.isEmpty()) {
        		sql.append(" AND ir.io_request_type = ?");	// 입출고명 : 입고 혹은 출고
        	}
        	
        	if(status_name != null && !"현황".equals(status_name) && !status_name.isEmpty()) {
        		sql.append(" AND rs.status_name = ?"); // 상태 : 요청, 검수요청, 승인, 반려        		
        	}
        	
        	if(filters != null) {
	            if (filters.get(0) != null && !filters.get(0).isEmpty()) {
	                sql.append(" AND co.company_name = ?");
	            }
	            if (filters.get(1) != null && !filters.get(1).isEmpty()) {
	                sql.append(" AND d.dept_name = ?");
	            }
	            if (filters.get(2) != null && !filters.get(2).isEmpty()) {
	                sql.append(" AND m.member_name = ?");
	            }
	            if (filters.get(3) != null && !filters.get(3).isEmpty()) {
	                sql.append(" AND p.product_code = ?");
	            }
	            if (filters.get(4) != null && !filters.get(4).isEmpty()) {
	                sql.append(" AND p.product_name = ?");
	            }
	            if (filters.get(5) != null && !filters.get(5).isEmpty()) {
	                sql.append(" AND rs.status_name = ?");
	            }
        	}
            
            pstmt = con.prepareStatement(sql.toString());
            int index = 1;
            if(io_request_type != null && !io_request_type.isEmpty()) {
            	pstmt.setString(index, io_request_type); // 매개변수로 받은 상태명
            }
            
            if (status_name != null && !"현황".equals(status_name) && !status_name.isEmpty()) {
            	pstmt.setString(++index, status_name);            	
            }
            
            if(filters != null) {
            	for(int i = 0; i < filters.size(); i++) {
            		String filter = filters.get(i);
            		if(filter != null && !filter.isEmpty()) {
            			pstmt.setString(++index, filter);
            		}
            	}
            }
            
            
            rs = pstmt.executeQuery();

            while (rs.next()) {
                IoRequest io = new IoRequest();
                io.setIoRequest_id(rs.getInt("io_request_id"));
                io.setIoRequest_type(rs.getString("io_request_type"));
                io.setQuantity(rs.getInt("quantity"));
                io.setRequest_reason(rs.getString("request_reason"));
                io.setRequest_at(rs.getDate("request_at"));
                io.setExpected_date(rs.getDate("expected_date"));
                io.setApproved_at(rs.getDate("approved_at"));
                io.setRemark(rs.getString("remark"));

                Company company = new Company();
                company.setCompany_name(rs.getString("company_name"));
                Product product = new Product();
                product.setProduct_id(rs.getInt("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setProduct_code(rs.getString("product_code"));
                product.setCompany(company);
                io.setProduct(product);
                
                Dept dept = new Dept();
                dept.setDept_name(rs.getString("dept_name"));
                Member member = new Member();
                member.setMember_id(rs.getInt("approve_member_id"));
                member.setMember_name(rs.getString("member_name"));
                member.setDept(dept);
                io.setRequest_member_id(member);
                io.setMember(member);
                
                Location location = new Location();
                location.setLocation_id(rs.getInt("location_id"));
                io.setLocation(location);
                
                RequestStatus status = new RequestStatus();
                status.setStatus_id(rs.getInt("status_id"));
                status.setStatus_name(rs.getString("status_name"));
                io.setStatus(status);
                
                list.add(io);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.release(pstmt, rs);
        }
        return list;
    }

    // 입출고 요청 등록
    public void insert(IoRequest io) {
        Connection con = dbManager.getConnetion();
        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO io_request (iorequest_type, product_id, quantity, location_id, request_member_id, request_reason, status_id, request_at, expected_date, approve_member_id, approve_at, remark) "
                       + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, io.getIoRequest_type());
            pstmt.setInt(2, io.getProduct().getProduct_id());
            pstmt.setInt(3, io.getQuantity());
            pstmt.setInt(4, io.getLocation().getLocation_id());
            pstmt.setInt(5, io.getRequest_member_id().getMember_id()); // Member 객체에서 id 추출
            pstmt.setString(6, io.getRequest_reason());
            pstmt.setInt(7, io.getStatus().getStatus_id());
            pstmt.setDate(8, io.getRequest_at());
            pstmt.setDate(9, io.getExpected_date());
            pstmt.setInt(10, io.getMember().getMember_id());
            pstmt.setDate(11, io.getApproved_at());
            pstmt.setString(12, io.getRemark());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.release(pstmt);
        }
    }

    // 입출고 요청 수정
    public void update(IoRequest io) {
        Connection con = dbManager.getConnetion();
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE io_request SET iorequest_type=?, product_id=?, quantity=?, location_id=?, request_member_id=?, request_reason=?, status_id=?, request_at=?, expected_date=?, approve_member_id=?, approve_at=?, remark=? "
                       + "WHERE iorequest_id=?";

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, io.getIoRequest_type());
            pstmt.setInt(2, io.getProduct().getProduct_id());
            pstmt.setInt(3, io.getQuantity());
            pstmt.setInt(4, io.getLocation().getLocation_id());
            pstmt.setInt(5, io.getRequest_member_id().getMember_id());
            pstmt.setString(6, io.getRequest_reason());
            pstmt.setInt(7, io.getStatus().getStatus_id());
            pstmt.setDate(8, io.getRequest_at());
            pstmt.setDate(9, io.getExpected_date());
            pstmt.setInt(10, io.getMember().getMember_id());
            pstmt.setDate(11, io.getApproved_at());
            pstmt.setString(12, io.getRemark());
            pstmt.setInt(13, io.getIoRequest_id());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.release(pstmt);
        }
    }

    // 입출고 요청 삭제
    public void delete(int iorequestId) {
        Connection con = dbManager.getConnetion();
        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM io_request WHERE iorequest_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, iorequestId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.release(pstmt);
        }
    }
}


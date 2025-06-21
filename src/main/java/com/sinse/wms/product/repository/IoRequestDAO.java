package com.sinse.wms.product.repository;


import java.sql.*;
import java.util.*;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.*;

public class IoRequestDAO {
    DBManager dbManager = DBManager.getInstance();

    // 전체 입출고 요청 조회
    public List<IoRequest> selectAll() {
        List<IoRequest> list = new ArrayList<>();
        Connection con = dbManager.getConnetion(); // 오타 수정

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT * FROM io_request";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                IoRequest io = new IoRequest();
                io.setIoRequest_id(rs.getInt("iorequest_id"));
                io.setIoRequest_type(rs.getInt("iorequest_type"));
                io.setQuantity(rs.getInt("quantity"));
                io.setRequest_reason(rs.getString("request_reason"));
                io.setRequest_at(rs.getDate("request_at"));
                io.setExpected_date(rs.getDate("expected_date"));
                io.setApprove_at(rs.getDate("approve_at"));
                io.setRemark(rs.getString("remark"));

                Product product = new Product();
                product.setProduct_id(rs.getInt("product_id"));
                io.setProduct(product);

                Location location = new Location();
                location.setLocation_id(rs.getInt("location_id"));
                io.setLocation(location);

                RequestStatus status = new RequestStatus();
                status.setStatus_id(rs.getInt("status_id"));
                io.setStatus(status);

                Member member = new Member();
                member.setMember_id(rs.getInt("approve_member_id"));
                io.setMember(member);

                list.add(io);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.release(pstmt, rs);
            dbManager.release(con); // 연결도 해제
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
            pstmt.setInt(1, io.getIoRequest_type());
            pstmt.setInt(2, io.getProduct().getProduct_id());
            pstmt.setInt(3, io.getQuantity());
            pstmt.setInt(4, io.getLocation().getLocation_id());
            pstmt.setInt(5, io.getRequest_member_id().getMember_id()); // Member 객체에서 id 추출
            pstmt.setString(6, io.getRequest_reason());
            pstmt.setInt(7, io.getStatus().getStatus_id());
            pstmt.setDate(8, io.getRequest_at());
            pstmt.setDate(9, io.getExpected_date());
            pstmt.setInt(10, io.getMember().getMember_id());
            pstmt.setDate(11, io.getApprove_at());
            pstmt.setString(12, io.getRemark());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.release(pstmt);
            dbManager.release(con);
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
            pstmt.setInt(1, io.getIoRequest_type());
            pstmt.setInt(2, io.getProduct().getProduct_id());
            pstmt.setInt(3, io.getQuantity());
            pstmt.setInt(4, io.getLocation().getLocation_id());
            pstmt.setInt(5, io.getRequest_member_id().getMember_id());
            pstmt.setString(6, io.getRequest_reason());
            pstmt.setInt(7, io.getStatus().getStatus_id());
            pstmt.setDate(8, io.getRequest_at());
            pstmt.setDate(9, io.getExpected_date());
            pstmt.setInt(10, io.getMember().getMember_id());
            pstmt.setDate(11, io.getApprove_at());
            pstmt.setString(12, io.getRemark());
            pstmt.setInt(13, io.getIoRequest_id());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.release(pstmt);
            dbManager.release(con);
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
            dbManager.release(con);
        }
    }
}


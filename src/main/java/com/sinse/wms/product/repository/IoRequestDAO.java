package com.sinse.wms.product.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sinse.wms.common.util.ChangeFormToDate;
import com.sinse.wms.common.util.ChangeFormToDate;
import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.product.model.Company;
import com.sinse.wms.product.model.Dept;
import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.model.Location;
import com.sinse.wms.product.model.Member;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.model.ProductUnit;
import com.sinse.wms.product.model.RequestStatus;


public class IoRequestDAO {
    DBManager dbManager = DBManager.getInstance();
	
	// 전체 입출고 요청 조회
	public List<IoRequest> selectAll() {
		List<IoRequest> list = new ArrayList<>();
		Connection con = dbManager.getConnetion(); // 오타 수정

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
		}
		return list;
	}

	// 일별 입출고량
	public int selectDailyIobound(String ioType) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ifnull(sum(quantity), 0) as total" + " from io_request where io_request_type=?"
					+ " and date(expected_date)=current_date");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, ioType);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return result;
	}

	// 주별 입출고량
	public int selectWeekIobound(String ioType) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ifnull(sum(quantity), 0) as total" + " from io_request where io_request_type=?"
					+ " and date(expected_date)"
					+ " between date_sub(current_date, interval(dayofweek(current_date)-2) day)" + " and current_date");

			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, ioType);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return result;
	}

	// 월별 입출고량
	public int selectMonthIobound(String ioType) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int result = 0;

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select ifnull(sum(quantity), 0) as total" + " from io_request where io_request_type=?"
					+ " and year(expected_date) = year(current_date)"
					+ " and month(expected_date) = month(current_date)");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, ioType);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt("total");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}
		return result;
	}

	// 입출고량 top 5 항목 가져오기
	public List<IoRequest> selectInOutTop5(String ioType) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<IoRequest> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select p.product_name, sum(i.quantity) from"
					+ " io_request i inner join product p on i.product_id = p.product_id"
					+ " where i.io_request_type = ?" + " group by product_name order by sum(quantity) desc limit 5");
			pstmt = con.prepareStatement(sql.toString());
			pstmt.setString(1, ioType);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				IoRequest io = new IoRequest();
				io.setQuantity(rs.getInt("sum(i.quantity)"));

				Product product = new Product();
				product.setProduct_name(rs.getString("p.product_name"));

				io.setProduct(product);

				list.add(io);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}

		return list;
	}

	// 카테고리 별 출고 비율(백분율) 가져오기
	public List<Map<String, Double>> selectCategoryQuantityPercent() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Map<String, Double>> list = new ArrayList<>();

		con = dbManager.getConnetion();

		try {
			StringBuffer sql = new StringBuffer();
			sql.append("select c.category_name, sum(i.quantity) as total_quantity,"
					+ " round(sum(i.quantity)/sum(sum(i.quantity)) over()*100, 2) as quantity_ratio"
					+ " from io_request i join product p on i.product_id = p.product_id"
					+ " join category c on p.category_id = c.category_id" + " where i.io_request_type = '출고'"
					+ " group by c.category_name" + " order by quantity_ratio desc");
			pstmt = con.prepareStatement(sql.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Map<String, Double> map = new HashMap<>();
				map.put(rs.getString("c.category_name"), rs.getDouble("quantity_ratio"));
				list.add(map);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			dbManager.release(pstmt, rs);
		}

    	return list;
    }

    // 입출고 요청 등록
    public void insert(IoRequest io, Connection con) {
        PreparedStatement pstmt = null;

        try {
            String sql = "INSERT INTO io_request (io_request_type, product_id, quantity, location_id, request_member_id, request_reason, status_id, request_at, expected_date, approve_member_id, approved_at, remark) "
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

    // 입출고 요청 전체 데이터 수정
    public void update(IoRequest io) {
        Connection con = dbManager.getConnetion();
        PreparedStatement pstmt = null;

        try {
            StringBuffer sql = null;
            sql.append("UPDATE io_request SET io_request_type=?, product_id=?, quantity=?, location_id=?, request_member_id=?, request_reason=?, status_id=?, request_at=?, expected_date=?, approve_member_id=?, approved_at=?, remark=? ");
            sql.append("WHERE io_request_id=?");

            pstmt = con.prepareStatement(sql.toString());
            pstmt = con.prepareStatement(sql.toString());
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
    
    // 입출고 요청 업데이트
    public void update(IoRequest io, Connection con, boolean isApproved) { // true=바뀐 상태가 승인(최종상태)인지?
        PreparedStatement pstmt = null;

        try {
            StringBuffer sql = new StringBuffer();
            sql.append("UPDATE io_request SET ");
            sql.append("status_id=?, ");
            sql.append("request_at=?, ");
            sql.append("approved_at=? ");
            sql.append("WHERE io_request_id=?");

            pstmt = con.prepareStatement(sql.toString());
            pstmt.setInt(1, io.getStatus().getStatus_id());		// 1. 상태 ID
            pstmt.setDate(2, ChangeFormToDate.nowDate()); 		// 2. 요청일은 항상 현재일자로 업데이트
            if (isApproved) { 									// 3. 승인일은 진행상태가 '승인'일 때만, 아니면 null
            	pstmt.setDate(3, ChangeFormToDate.nowDate());
            } else {
            	pstmt.setDate(3, null);
            }								
            pstmt.setInt(4, io.getIoRequest_id()); 				// 4. 조건 where절
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.release(pstmt);
        }
    }

    // 입출고 요청 삭제
    public void delete(int io_request_id, Connection con) {
        PreparedStatement pstmt = null;

        try {
            String sql = "DELETE FROM io_request WHERE io_request_id = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, io_request_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbManager.release(pstmt);
        }
    }

 // 필터 요청 조회
    public List<IoRequest> selectByFilter(String io_request_type, String status_name, List<String> filters) { 
           
        List<IoRequest> list = new ArrayList<>();
        Connection con = dbManager.getConnetion(); 
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
        	StringBuffer sql = new StringBuffer();
        	sql.append("SELECT ir.*,");
        	sql.append(" p.product_name, p.product_code, p.product_price, p.product_stock,");
        	sql.append(" u.unit_name,");
        	sql.append(" co.company_name,");
        	sql.append(" m.member_name,");
        	sql.append(" d.dept_name,");
        	sql.append(" rs.status_name");
        	sql.append(" FROM io_request ir");
        	sql.append(" LEFT JOIN product p ON ir.product_id = p.product_id");
        	sql.append(" LEFT JOIN product_unit u ON p.unit_id = u.unit_id");
        	sql.append(" LEFT JOIN company co ON p.company_id = co.company_id");
        	sql.append(" LEFT JOIN member m ON ir.request_member_id = m.member_id");
        	sql.append(" LEFT JOIN dept d ON m.dept_id = d.dept_id");
        	sql.append(" LEFT JOIN request_status rs ON ir.status_id = rs.status_id");
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
//	            if (filters.get(5) != null && !filters.get(5).isEmpty()) {
//	                sql.append(" AND rs.status_name = ?");
//	            }
        	}
            
            pstmt = con.prepareStatement(sql.toString());
            int index = 1;
            if(io_request_type != null && !io_request_type.isEmpty()) {
            	pstmt.setString(index++, io_request_type); // 매개변수로 받은 상태명
            }
            
            if (status_name != null && !"현황".equals(status_name) && !status_name.isEmpty()) {
            	pstmt.setString(index++, status_name);            	
            }
            
            if(filters != null) {
            	for(int i = 0; i < filters.size(); i++) {
            		String filter = filters.get(i);
            		if(filter != null && !filter.isEmpty()) {
            			pstmt.setString(index++, filter);
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
                ProductUnit unit = new ProductUnit();
                unit.setUnit_name(rs.getString("unit_name"));
                Product product = new Product();
                product.setProduct_id(rs.getInt("product_id"));
                product.setProduct_name(rs.getString("product_name"));
                product.setProduct_code(rs.getString("product_code"));
                product.setProduct_price(rs.getInt("product_price"));
                product.setProduct_stock(rs.getInt("product_stock"));
                product.setUnit(unit);
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
}

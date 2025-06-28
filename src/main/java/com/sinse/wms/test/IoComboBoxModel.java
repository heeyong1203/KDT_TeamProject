package com.sinse.wms.test;

import java.util.ArrayList;
import java.util.List;

import com.sinse.wms.product.model.Company;
import com.sinse.wms.product.model.Dept;
import com.sinse.wms.product.model.Member;
import com.sinse.wms.product.model.Product;
import com.sinse.wms.product.model.RequestStatus;
import com.sinse.wms.product.repository.CompanyDAO;
import com.sinse.wms.product.repository.DeptDAO;
import com.sinse.wms.product.repository.MemberDAO;
import com.sinse.wms.product.repository.ProductDAO;
import com.sinse.wms.product.repository.RequestStatusDAO;

public class IoComboBoxModel {	
	private List<String> companyNames = new ArrayList<>();
	private List<String> deptNames = new ArrayList<>();
	private List<String> memberNames = new ArrayList<>();
	private List<String> productCodes = new ArrayList<>();
	private List<String> productNames = new ArrayList<>();
	private List<String> statusNames = new ArrayList<>();
	
	public void setTableCombo() {
		CompanyDAO companyDAO = new CompanyDAO();
		List<Company> companies = companyDAO.selectAll();
		companyNames.add("거래처를 선택하세요.");  // 콤보박스 기본값
		for (Company c : companies) {
			companyNames.add(c.getCompany_name());
		}

		DeptDAO deptDAO = new DeptDAO();
		List<Dept> depts = deptDAO.selectAll();
		deptNames.add("부서명을 선택하세요.");
		for (Dept d : depts) {
			deptNames.add(d.getDept_name());
		}
		
		MemberDAO memberDAO = new MemberDAO(); 
		List<Member> members = memberDAO.selectAll();
		memberNames.add("사원명을 선택하세요.");  
		for (Member m : members) {
			memberNames.add(m.getMember_name());
		}
		
		ProductDAO productDAO = new ProductDAO();
		List<Product> products = productDAO.selectAll();  
		productCodes.add("품목코드를 선택하세요.");
		productNames.add("품목명을 선택하세요.");
		for (Product p : products) {
			productCodes.add(p.getProduct_code());
			productNames.add(p.getProduct_name());
		}
		
		RequestStatusDAO statusDAO = new RequestStatusDAO();
		List<RequestStatus> statuses = statusDAO.selectAll();
		statusNames.add("진행상태를 선택하세요.");
		for (RequestStatus s : statuses) {
			statusNames.add(s.getStatus_name());
		}
	}

	public List<String> getCompanyNames() {
		return companyNames;
	}

	public void setCompanyNames(List<String> companyNames) {
		this.companyNames = companyNames;
	}

	public List<String> getDeptNames() {
		return deptNames;
	}

	public void setDeptNames(List<String> deptNames) {
		this.deptNames = deptNames;
	}

	public List<String> getMemberNames() {
		return memberNames;
	}

	public void setMemberNames(List<String> memberNames) {
		this.memberNames = memberNames;
	}

	public List<String> getProductCodes() {
		return productCodes;
	}

	public void setProductCodes(List<String> productCodes) {
		this.productCodes = productCodes;
	}

	public List<String> getProductNames() {
		return productNames;
	}

	public void setProductNames(List<String> productNames) {
		this.productNames = productNames;
	}

	public List<String> getStatusNames() {
		return statusNames;
	}

	public void setStatusNames(List<String> statusNames) {
		this.statusNames = statusNames;
	}
}

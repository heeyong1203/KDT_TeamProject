package com.sinse.wms.test;

import java.util.List;

import com.sinse.wms.product.model.ProductUnit;
import com.sinse.wms.product.repository.ProductUnitDAO;

public class ProductUnitTest {
	public static void main(String[] args) {
		ProductUnitDAO dao = new ProductUnitDAO();

		// 1. INSERT 테스트
		ProductUnit unit = new ProductUnit();
		unit.setUnit_name("박스");
		dao.insert(unit);
		System.out.println("✅ 단위 추가 완료");

		// 2. SELECT 테스트
		List<ProductUnit> list = dao.selectAll();
		for (ProductUnit u : list) {
			System.out.println("📦 " + u.getUnit_id() + " / " + u.getUnit_name());
		}
	}
}


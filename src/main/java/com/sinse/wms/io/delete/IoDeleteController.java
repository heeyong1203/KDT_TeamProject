package com.sinse.wms.io.delete;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import com.sinse.wms.common.util.DBManager;
import com.sinse.wms.io.view.IoFilterPanel;
import com.sinse.wms.io.view.IoTableModel;
import com.sinse.wms.product.model.IoRequest;
import com.sinse.wms.product.model.ProductSnapshot;
import com.sinse.wms.product.repository.IoRequestDAO;
import com.sinse.wms.product.repository.ProductSnapshotDAO;

public class IoDeleteController {
	private IoFilterPanel filterPanel;
	private IoRequestDAO dao = new IoRequestDAO();
	private ProductSnapshotDAO snapDAO = new ProductSnapshotDAO();
	
	public IoDeleteController(IoFilterPanel filterPanel) {
		this.filterPanel = filterPanel;
		
	}
	
	/*-------------------------------------------------------------
     * 선택된 행(레코드)들을 삭제합니다.
     ------------------------------------------------------------*/
	public void delete() {
		DBManager dbManager = DBManager.getInstance();
		Connection con = null;
		
		try {
			con = dbManager.getConnetion();
			con.setAutoCommit(false);
			
			// IoTableModel model = filterPanel.getIoTableModel(); 최신정보 반영이 안되어 있을 수 있음
			IoTableModel model = (IoTableModel) filterPanel.getTableLayout().getTable().getModel();
			List<IoRequest> selectedList = model.getSelectedRows(); // 체크박스 선택된 레코드
						
			if (selectedList.isEmpty()) {
				JOptionPane.showMessageDialog(null, "체크된 삭제 항목이 없습니다.");
				return;
			}
			
			for (IoRequest io : selectedList) {
				int selectedPk = io.getIoRequest_id(); // 체크한 레코드의 pk값 얻어오기
					dao.delete(selectedPk, con); // 선택한 레코드의 pk값을 이용하여 삭제 실행
					snapDAO.delete(selectedPk, con); // io_request_id == product_snapshot_id
			}
			con.commit();
			model.clearSelections(); // 체크박스 원상복구
			JOptionPane.showMessageDialog(null, "선택하신 항목이 삭제되었습니다.");
			
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "삭제 처리에 실패하였습니다. : " + e.getMessage());
		} finally {
			try {
				if (con != null) 
					con.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "오류 발생");
			}
		}
	}
}

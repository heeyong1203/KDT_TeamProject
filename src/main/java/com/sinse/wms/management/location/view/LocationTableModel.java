package com.sinse.wms.management.location.view;

import com.sinse.wms.product.model.Location;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class LocationTableModel extends AbstractTableModel {
	private List<Location> locations = new ArrayList<>();

	public void setLocations(List<Location> locations) {
		this.locations.clear();
		this.locations.addAll(locations);
	}

	@Override
	public int getRowCount() {
		return this.locations.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public String getColumnName(int column) {
		return "이름";
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Location location = this.locations.get(rowIndex);
		return location.getLocation_name();
	}

}

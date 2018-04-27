package com.titan.Transformerslab.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.titan.Transformerslab.domain.ShiftOrderDetails;
import com.titan.Transformerslab.utils.DateUtils;

@Component
public class EOMRepositoryImpl implements EOMRepository {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public Map<String, ShiftOrderDetails> getShiftOrders(String storeNumber, String shiftNumber) throws SQLException {
		Map<String, ShiftOrderDetails> shiftOrdersMap = new HashMap<String, ShiftOrderDetails>();

		String currentDate = DateUtils.getCurrentDateIn_YYYYMMDD();
		
		String query = "SELECT " + 
				"    po.tc_purchase_orders_id AS orderkey, " + 
				"    '' AS senderid, " + 
				"    ROW_NUMBER() OVER( " + 
				"        ORDER BY po.tc_purchase_orders_id " + 
				"    ) AS scheduleid, " + 
				"    '0' AS stopnumber, " + 
				"    '' AS stopid, " + 
				"    '' AS plannedbegundate, " + 
				"    '' AS plannedcompleteddate, " + 
				"    '' AS resourcekey, " + 
				"    '' AS planneddeparteddate, " + 
				"    'UR' AS routeid, " + 
				"    '' AS stoporderline, " + 
				"    '' AS vandeparttime, " + 
				"    '' AS vanarrivaltime " + 
				"FROM " + 
				"    osflca.purchase_orders po, " + 
				"    osflca.po_ref_fields porf " + 
				"WHERE " + 
				"        po.purchase_orders_id = porf.purchase_orders_id " + 
				"    AND " + 
				"        porf.ref_num5 = :shiftNumber  " + 
				"    AND " + 
				"        po.purchase_orders_status = 400 " + 
				"    AND " + 
				"        po.purchase_orders_id IN ( " + 
				"            SELECT DISTINCT " + 
				"                poli.purchase_orders_id " + 
				"            FROM " + 
				"                osflca.purchase_orders_line_item poli " + 
				"            WHERE " + 
				"                    poli.dsg_ship_via = 'A1' " + 
				"                AND " + 
				"                    trunc(poli.req_dlvr_dttm) = trunc(TO_DATE( :shift_date,'yyyymmdd','NLS_DATE_LANGUAGE = American') ) " + 
				"                AND " + 
				"                    poli.o_facility_alias_id = :facility_name  " + 
				"                AND " + 
				"                    poli.is_deleted = 0 " + 
				"        ) " + 
				"UNION " + 
				"SELECT " + 
				"    '' AS orderkey, " + 
				"    '' AS senderid, " + 
				"    1 AS scheduleid, " + 
				"    '0' AS stopnumber, " + 
				"    '' AS stopid, " + 
				"    '' AS plannedbegundate, " + 
				"    '' AS plannedcompleteddate, " + 
				"    '' AS resourcekey, " + 
				"    '' AS planneddeparteddate, " + 
				"    'UR' AS routeid, " + 
				"    '' AS stoporderline, " + 
				"    '' AS vandeparttime, " + 
				"    '' AS vanarrivaltime " + 
				"FROM " + 
				"    dual";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("shiftNumber", shiftNumber);
		parameters.addValue("shift_date", currentDate);
		parameters.addValue("facility_name", storeNumber);

		List<ShiftOrderDetails> shiftOrdersList = (List<ShiftOrderDetails>) namedParameterJdbcTemplate.query(query,
				parameters, new RowMapper<ShiftOrderDetails>() {
					@Override
					public ShiftOrderDetails mapRow(ResultSet resultSet, int i) throws SQLException {
						return mapRows(resultSet);
					}
				});

		shiftOrdersList.forEach(orderDetails -> shiftOrdersMap.put(orderDetails.getOrderKey(), orderDetails));

		return shiftOrdersMap;
	}

	

	public ShiftOrderDetails mapRows(ResultSet rs) throws SQLException {

		ShiftOrderDetails shiftOrder = null;
		if (rs.getString("ORDERKEY") != null)
			shiftOrder = new ShiftOrderDetails(rs.getString("ORDERKEY"), rs.getString("ROUTEID"),
					rs.getString("STOPNUMBER"), rs.getString("STOPID"));

		return shiftOrder;
	}

}

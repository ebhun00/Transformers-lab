--You need to add <SHIFT> and <STORE_NUM> and <DATE>


SELECT
PO.TC_PURCHASE_ORDERS_ID as OrderKey,
'' as senderID,
ROW_NUMBER() OVER (ORDER BY PO.TC_PURCHASE_ORDERS_ID) as ScheduleID, '0' as StopNumber, '' as StopID, '' as PlannedBegunDate, '' as PlannedCompletedDate, '' as ResourceKey, '' as PlannedDepartedDate, 'UR' as RouteID, '' as StopOrderLine, '' as VanDepartTime, '' as VanArrivalTime FROM osflca.PURCHASE_ORDERS PO,osflca.PO_REF_FIELDS PORF  WHERE PO.PURCHASE_ORDERS_ID=PORF.PURCHASE_ORDERS_ID AND PORF.REF_NUM5='<SHIFT>' AND PO.PURCHASE_ORDERS_STATUS=400 AND 
PO.PURCHASE_ORDERS_ID in (SELECT DISTINCT POLI.PURCHASE_ORDERS_ID FROM osflca.PURCHASE_ORDERS_LINE_ITEM POLI WHERE POLI.DSG_SHIP_VIA='A1' AND TRUNC(POLI.REQ_DLVR_DTTM) = TRUNC(TO_DATE('<DATE>','yyyymmdd','NLS_DATE_LANGUAGE = American')) 
AND POLI.O_FACILITY_ALIAS_ID='<STORE_NUM>' 
AND POLI.IS_DELETED=0
) union SELECT '' as OrderKey, '' as senderID,
1 as ScheduleID,
'0' as StopNumber,
'' as StopID,
'' as PlannedBegunDate,
'' as PlannedCompletedDate,
'' as ResourceKey,
'' as PlannedDepartedDate,
'UR' as RouteID,
'' as StopOrderLine,
'' as VanDepartTime,
'' as VanArrivalTime from Dual;


UPDATE osflca.po_ref_fields
    SET
        ref_field1 = 'UR1<SHIFT>',
        ref_field2 = '<VAN_DEPARTURE_DTTM>',
        ref_field3 = '1111111',
        ref_field4 = '<ESTIMATED_ARRIVAL_TIME>',
        ref_field5 = '<STOP_NUM>',
        ref_field6 = '<VAN_DEPARTURE_DTTM>',
        ref_field7 = '<ESTIMATED_ARRIVAL_TIME>'
WHERE
    purchase_orders_id = (
        SELECT
            purchase_orders_id
        FROM
            osflca.purchase_orders
        WHERE
            tc_purchase_orders_id = '<ORDER_NUM>'
    );
    
    
    select * from osflca.po_ref_fields where  purchase_orders_id = (
        SELECT
            purchase_orders_id
        FROM
            osflca.purchase_orders
        WHERE
            tc_purchase_orders_id = '1094499'
    );
CREATE TYPE type_itemSearch AS ("name" VARCHAR, "barcode" VARCHAR, "serial_number" VARCHAR, "price" float4, "item_id" INT);

CREATE OR REPLACE FUNCTION bh_searchItems(branch_id_in INT, item_name_in VARCHAR, barcode_in VARCHAR)
  RETURNS SETOF type_itemSearch AS
$BODY$
   SELECT "item_ids"."name", "item_ids"."barcode", "serials"."serial_number", "serials"."price", "item_ids"."item_id" FROM
	(SELECT "items"."name", "barcodes"."barcode", "barcodes"."item_id" FROM (SELECT "id", "name" FROM pos_item_names WHERE upper("name") LIKE upper(concat(item_name_in, '%'))) AS items
	INNER JOIN
	(SELECT "item_id", "barcode" FROM pos_barcodes WHERE upper("barcode") LIKE upper(concat(barcode_in,'%'))) AS barcodes
	ON "items"."id" = "barcodes"."item_id") AS item_ids
INNER JOIN
	(SELECT "serial_details"."serial_number", "branches"."price", "branches"."item_id" FROM (SELECT "serial_id", "price", "item_id" FROM pos_branch_prices WHERE "branch_id" = branch_id_in) AS branches
	INNER JOIN
	(SELECT "serial_number", "id" FROM pos_item_serials) AS serial_details
	ON "branches"."serial_id" = "serial_details"."id") AS serials
ON "item_ids"."item_id" = "serials"."item_id" ORDER BY "item_ids"."name";
$BODY$
  LANGUAGE 'sql';
CREATE OR REPLACE FUNCTION "public"."bh_getbarcodes"(branch_id_in int4)
  RETURNS SETOF "pg_catalog"."varchar" AS $BODY$
	DECLARE barcode_out CHARACTER VARYING;
BEGIN
    FOR barcode_out IN
												(SELECT barcodes."barcode" FROM pos_barcodes AS barcodes
												INNER JOIN
													(SELECT "item_id" FROM pos_branch_prices WHERE "branch_id" = branch_id_in GROUP BY "item_id") AS item_ids
												ON barcodes."item_id" = item_ids."item_id"
												ORDER BY barcodes."barcode")
			LOOP
					RETURN NEXT barcode_out;
    END LOOP;
    RETURN;
END
	$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
 ROWS 1000
;

ALTER FUNCTION "public"."bh_getbarcodes"(branch_id_in int4) OWNER TO "postgres";
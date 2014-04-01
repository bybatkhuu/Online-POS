CREATE OR REPLACE FUNCTION "public"."bh_checkbarcode"(barcode_in varchar, branch_id_in int4)
  RETURNS "pg_catalog"."bool" AS $BODY$
	DECLARE item_id_use INT;
	DECLARE has_barcode_out BOOLEAN;
BEGIN
		IF (SELECT count(*) FROM "pos_barcodes" WHERE "pos_barcodes"."barcode" = barcode_in) > 0 THEN
			item_id_use = (SELECT "item_id" FROM pos_barcodes WHERE "barcode" = barcode_in);
			IF (SELECT count(*) FROM pos_branch_prices WHERE "branch_id" = branch_id_in AND "item_id" = item_id_use) > 0 THEN
				has_barcode_out = TRUE;
			ELSE
				has_barcode_out = FALSE;
			END IF;
		ELSE
			has_barcode_out = FALSE;
		END IF;
		RETURN has_barcode_out;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE COST 100
;

ALTER FUNCTION "public"."bh_checkbarcode"(barcode_in varchar, branch_id_in int4) OWNER TO "postgres";
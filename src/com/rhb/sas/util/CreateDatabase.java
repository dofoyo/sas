package com.rhb.sas.util;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;


/*
 * Ҫ��hibernate.cfg.xml�������srcĿ¼��
 * 
 *
 */

public class CreateDatabase {
	public static void main(String[] args) {
		Configuration cfg = new Configuration().configure();
		SchemaExport export = new SchemaExport(cfg);

		export.create(true, true);
		
	}
}

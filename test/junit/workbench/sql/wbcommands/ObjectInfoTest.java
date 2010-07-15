/*
 * ObjectInfoTest.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2010, Thomas Kellerer
 * No part of this code maybe reused without the permission of the author
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.sql.wbcommands;

import junit.framework.TestCase;
import workbench.TestUtil;
import workbench.db.WbConnection;
import workbench.sql.StatementRunnerResult;
import workbench.storage.DataStore;

/**
 *
 * @author Thomas Kellerer
 */
public class ObjectInfoTest
	extends TestCase
{
	private WbConnection db;
	
	public ObjectInfoTest(String testName)
	{
		super(testName);
	}

	@Override
	protected void setUp()
		throws Exception
	{
		super.setUp();
		TestUtil util = new TestUtil(getName());
		db = util.getConnection(getName());
		TestUtil.executeScript(db,
				"CREATE TABLE person (nr integer primary key, person_name varchar(100)); \n" +
				"CREATE TABLE person_group (person_nr integer, group_nr integer); \n" +
				"ALTER TABLE person_group ADD CONSTRAINT fk_pg_p FOREIGN KEY (person_nr) REFERENCES person (nr); \n" +
				"CREATE VIEW v_person (pnr, pname) AS SELECT nr, person_name FROM PERSON; \n" +
				"create sequence seq_id; \n" +
				"commit;"
		);
	}

	@Override
	protected void tearDown()
		throws Exception
	{
		super.tearDown();
	}

	public void testGetObjectInfo()
		throws Exception
	{
		String objectName = "person";
		ObjectInfo info = new ObjectInfo();
		StatementRunnerResult tableInfo = info.getObjectInfo(db, objectName, false);
		assertTrue(tableInfo.hasDataStores());
		DataStore ds = tableInfo.getDataStores().get(0);
		assertEquals(2, ds.getRowCount());
		assertEquals("NR", ds.getValueAsString(0, 0));
		assertEquals("PERSON_NAME", ds.getValueAsString(1, 0));

		tableInfo = info.getObjectInfo(db, objectName, true);
		assertTrue(tableInfo.hasDataStores());
		assertEquals(3, tableInfo.getDataStores().size());

		DataStore indexes = tableInfo.getDataStores().get(1);
		assertEquals("PERSON - Indexes", indexes.getResultName());
		assertEquals(1, indexes.getRowCount());

		DataStore fk = tableInfo.getDataStores().get(2);
		assertEquals("PERSON - Referenced by", fk.getResultName());
		assertEquals(1, fk.getRowCount());

		tableInfo = info.getObjectInfo(db, "PERSON_GROUP", true);
		assertTrue(tableInfo.hasDataStores());
		assertEquals(3, tableInfo.getDataStores().size());

		indexes = tableInfo.getDataStores().get(1);
		assertEquals("PERSON_GROUP - Indexes", indexes.getResultName());
		assertEquals(1, indexes.getRowCount());

		fk = tableInfo.getDataStores().get(2);
		assertEquals("PERSON_GROUP - References", fk.getResultName());
		assertEquals(1, fk.getRowCount());
		
		StatementRunnerResult viewInfo = info.getObjectInfo(db, "v_person", false);
//		System.out.println(viewInfo.getSourceCommand());
		assertTrue(viewInfo.getSourceCommand().startsWith("CREATE FORCE VIEW"));
		assertTrue(viewInfo.hasDataStores());
		
		DataStore viewDs = viewInfo.getDataStores().get(0);
		assertEquals(2, viewDs.getRowCount());
		assertEquals("PNR", viewDs.getValueAsString(0, 0));
		assertEquals("PNAME", viewDs.getValueAsString(1, 0));

		StatementRunnerResult seqInfo = info.getObjectInfo(db, "seq_id", false);
//		System.out.println(seqInfo.getSourceCommand());
		assertTrue(seqInfo.hasDataStores());
		assertEquals(1, seqInfo.getDataStores().get(0).getRowCount());
	}
}

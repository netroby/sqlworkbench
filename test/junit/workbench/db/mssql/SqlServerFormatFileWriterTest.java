/*
 * SqlServerFormatFileWriterTest.java
 *
 *  This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 *  Copyright 2002-2012, Thomas Kellerer
 *  No part of this code may be reused without the permission of the author
 *
 *  To contact the author please send an email to: support@sql-workbench.net
 */
package workbench.db.mssql;

import java.sql.Types;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import workbench.TestUtil;
import workbench.WbTestCase;
import workbench.db.ColumnIdentifier;
import workbench.db.TableIdentifier;
import workbench.db.exporter.DataExporter;
import workbench.db.exporter.RowDataConverter;
import workbench.storage.ResultInfo;
import workbench.storage.RowData;
import workbench.util.StrBuffer;
import workbench.util.WbFile;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas Kellerer
 */
public class SqlServerFormatFileWriterTest
	extends WbTestCase
{

	public SqlServerFormatFileWriterTest()
	{
		super("SqlServerFormatFileWriterTest");
	}

	@BeforeClass
	public static void setUpClass()
		throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass()
		throws Exception
	{
	}

	@Before
	public void setUp()
	{
	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testWriteFormatFile()
		throws Exception
	{
		TestUtil util = getTestUtil();
		final WbFile export = new WbFile(util.getBaseDir(), "export.txt");
		DataExporter exporter = new DataExporter(null)
		{

			@Override
			public String getFullOutputFilename()
			{
				return export.getFullPath();
			}

			@Override
			public boolean getExportHeaders()
			{
				return true;
			}

			@Override
			public String getTextDelimiter()
			{
				return "\t";
			}

			@Override
			public String getTextQuoteChar()
			{
				return "\"";
			}
		};

		ColumnIdentifier id = new ColumnIdentifier("id" ,Types.INTEGER, true);
		ColumnIdentifier firstname = new ColumnIdentifier("firstname", Types.VARCHAR);
		ColumnIdentifier lastname = new ColumnIdentifier("lastname", Types.VARCHAR);
		final TableIdentifier table = new TableIdentifier("person");

		final ResultInfo info = new ResultInfo(new ColumnIdentifier[] { id, firstname, lastname } );
		info.setUpdateTable(table);

		RowDataConverter converter = new RowDataConverter()
		{

			@Override
			public ResultInfo getResultInfo()
			{
				return info;
			}

			@Override
			public StrBuffer convertRowData(RowData row, long rowIndex)
			{
				return new StrBuffer();
			}

			@Override
			public StrBuffer getStart()
			{
				return null;
			}

			@Override
			public StrBuffer getEnd(long totalRows)
			{
				return null;
			}
		};

		try
		{
			SqlServerFormatFileWriter writer = new SqlServerFormatFileWriter();
			writer.writeFormatFile(exporter, converter);
			WbFile formatFile = new WbFile(util.getBaseDir(), "export.fmt");
			assertTrue(formatFile.exists());

			List<String> contents = TestUtil.readLines(formatFile);
			assertEquals("7.0", contents.get(0));
			assertEquals("3", contents.get(1));
			assertEquals("1    SQLCHAR 0  0 \"\\t\"   1    id", contents.get(2).trim());
			assertEquals("2    SQLCHAR 0  0 \"\\t\"   2    firstname", contents.get(3).trim());
			assertEquals("3    SQLCHAR 0  0 \"\\n\"   3    lastname", contents.get(4).trim());
		}
		finally
		{
			util.emptyBaseDirectory();
		}

	}
}
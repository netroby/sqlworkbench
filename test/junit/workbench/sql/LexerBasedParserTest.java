/*
 * LexerBasedParserTest
 * 
 *  This file is part of SQL Workbench/J, http://www.sql-workbench.net
 * 
 *  Copyright 2002-2009, Thomas Kellerer
 *  No part of this code maybe reused without the permission of the author
 * 
 *  To contact the author please send an email to: support@sql-workbench.net
 */
package workbench.sql;

import junit.framework.TestCase;
import workbench.sql.LexerBasedParser;

/**
 *
 * @author Thomas Kellerer
 */
public class LexerBasedParserTest
	extends TestCase
{

	public LexerBasedParserTest(String testName)
	{
		super(testName);
	}

	@Override
	protected void setUp()
		throws Exception
	{
		super.setUp();
	}

	@Override
	protected void tearDown()
		throws Exception
	{
		super.tearDown();
	}

	public void testGetNextCommand()
		throws Exception
	{
		String sql = "select * from test;\n" + "select * from person;";
		LexerBasedParser parser = new LexerBasedParser(sql);
		assertTrue(parser.hasMoreCommands());
		ScriptCommandDefinition cmd = null;
		while ((cmd = parser.getNextCommand()) != null)
		{
			int index = cmd.getIndexInScript();
			if (index == 0)
			{
				assertEquals("select * from test", cmd.getSQL());
			}
			else if (index == 1)
			{
				assertEquals("select * from person", cmd.getSQL());
			}
			else
			{
				fail("Wrong command index: " + index);
			}
		}

	}
	public void testStoreIndexOnly()
		throws Exception
	{
		String sql = "select * from test;\n" + "select * from person;";
		LexerBasedParser parser = new LexerBasedParser(sql);
		parser.setStoreStatementText(false);
		ScriptCommandDefinition cmd = null;
		while ((cmd = parser.getNextCommand()) != null)
		{
			int index = cmd.getIndexInScript();
			if (index == 0)
			{
				String cmdSql = sql.substring(cmd.getStartPositionInScript(), cmd.getEndPositionInScript());
				assertEquals("select * from test", cmdSql.trim());
			}
			else if (index == 1)
			{
				String cmdSql = sql.substring(cmd.getStartPositionInScript(), cmd.getEndPositionInScript());
				assertEquals("select * from person", cmdSql.trim());
			}
			else
			{
				fail("Wrong command index: " + index);
			}
		}
	}

	public void testTrimLeadingWhiteSpace()
		throws Exception
	{
		String sql = "select * from test;\nselect * from person;\n";
		LexerBasedParser parser = new LexerBasedParser(sql);
		parser.setReturnLeadingWhitespace(false);
		ScriptCommandDefinition cmd = null;
		while ((cmd = parser.getNextCommand()) != null)
		{
			int index = cmd.getIndexInScript();
			if (index == 0)
			{
				assertEquals("select * from test", cmd.getSQL());
			}
			else if (index == 1)
			{
				assertEquals("select * from person", cmd.getSQL());
			}
			else
			{
				fail("Wrong command index: " + index);
			}
		}

		sql = "COMMENT ON COLUMN COMMENT_TEST.ID IS 'Primary key column';\r\nCOMMENT ON COLUMN COMMENT_TEST.FIRST_NAME IS 'Firstname';\r\n";
		parser = new LexerBasedParser(sql);
		parser.setReturnLeadingWhitespace(false);
		parser.setStoreStatementText(false);
		while ((cmd = parser.getNextCommand()) != null)
		{
			int index = cmd.getIndexInScript();
			if (index == 0)
			{
				int start = cmd.getStartPositionInScript();
				int end = cmd.getEndPositionInScript();
				String cmdSql = sql.substring(start, end);
				assertEquals("COMMENT ON COLUMN COMMENT_TEST.ID IS 'Primary key column'", cmdSql);
			}
			else if (index == 1)
			{
				int start = cmd.getStartPositionInScript();
				int end = cmd.getEndPositionInScript();
				String cmdSql = sql.substring(start, end);
				assertEquals("COMMENT ON COLUMN COMMENT_TEST.FIRST_NAME IS 'Firstname'", cmdSql);
			}
			else
			{
				fail("Wrong command index: " + index);
			}
		}
	}

	public void testReturnLeadingWhiteSpace()
		throws Exception
	{
		String sql = " select * from test;\n" + " select * from person;";
		LexerBasedParser parser = new LexerBasedParser(sql);
		parser.setReturnLeadingWhitespace(true);
		ScriptCommandDefinition cmd = null;
		while ((cmd = parser.getNextCommand()) != null)
		{
			int index = cmd.getIndexInScript();
			if (index == 0)
			{
				assertEquals(" select * from test", cmd.getSQL());
			}
			else if (index == 1)
			{
				assertEquals("\n select * from person", cmd.getSQL());
			}
			else
			{
				fail("Wrong command index: " + index);
			}
		}
	}

	public void testEmptyLineDelimiter()
		throws Exception
	{
		String sql = "select * from test\n\n" + "select * from person\n";
		LexerBasedParser parser = new LexerBasedParser(sql);
		parser.setEmptyLineIsDelimiter(true);
		ScriptCommandDefinition cmd = null;
		while ((cmd = parser.getNextCommand()) != null)
		{
			int index = cmd.getIndexInScript();
			if (index == 0)
			{
				assertEquals("select * from test", cmd.getSQL().trim());
			}
			else if (index == 1)
			{
				assertEquals("select * from person", cmd.getSQL().trim());
			}
			else
			{
				fail("Wrong command index: " + index);
			}
		}
	}
	
	public void testAlternateDelimiter()
		throws Exception
	{
		String sql = "select * from test\n./\n" + "select * from person\n./\n";
		LexerBasedParser parser = new LexerBasedParser(sql);
		parser.setDelimiter(new DelimiterDefinition("./", false));
		ScriptCommandDefinition cmd = null;
		while ((cmd = parser.getNextCommand()) != null)
		{
			int index = cmd.getIndexInScript();
			if (index == 0)
			{
				assertEquals("select * from test", cmd.getSQL().trim());
			}
			else if (index == 1)
			{
				assertEquals("select * from person", cmd.getSQL().trim());
			}
			else
			{
				fail("Wrong command index: " + index);
			}
		}

		sql = "select * from test./\n./\n" + "select * from person\n./\n";
		parser = new LexerBasedParser(sql);
		parser.setDelimiter(new DelimiterDefinition("./", true));
		while ((cmd = parser.getNextCommand()) != null)
		{
			//System.out.println(cmd.getSQL() + "\n******************\n");
			int index = cmd.getIndexInScript();
			if (index == 0)
			{
				assertEquals("select * from test./", cmd.getSQL().trim());
			}
			else if (index == 1)
			{
				assertEquals("select * from person", cmd.getSQL().trim());
			}
			else
			{
				fail("Wrong command index: " + index);
			}
		}
	}

	public void testOraInclude()
		throws Exception
	{
		String sql = "select \n@i = 1,id from test;\n" + "select * from person;delete from test2;commit;";
		LexerBasedParser parser = new LexerBasedParser(sql);
		parser.setSupportOracleInclude(false);
		ScriptCommandDefinition cmd = null;
		while ((cmd = parser.getNextCommand()) != null)
		{
//			System.out.println(cmd.getSQL() + "\n**************************");
			int index = cmd.getIndexInScript();
			if (index == 0)
			{
				assertEquals("select \n@i = 1,id from test", cmd.getSQL().trim());
			}
			else if (index == 1)
			{
				assertEquals("select * from person", cmd.getSQL().trim());
			}
			else if (index == 2)
			{
				assertEquals("delete from test2", cmd.getSQL().trim());
			}
			else if (index == 3)
			{
				assertEquals("commit", cmd.getSQL().trim());
			}
			else
			{
				fail("Wrong command index: " + index);
			}
		}

		sql = "delete from person;\n  @insert_person.sql\ncommit;";
		parser = new LexerBasedParser(sql);
		parser.setSupportOracleInclude(true);
		while ((cmd = parser.getNextCommand()) != null)
		{
//			System.out.println(cmd.getSQL() + "\n**************************");
			int index = cmd.getIndexInScript();
			if (index == 0)
			{
				assertEquals("delete from person", cmd.getSQL());
			}
			else if (index == 1)
			{
				assertEquals("@insert_person.sql", cmd.getSQL());
			}
			else if (index == 2)
			{
				assertEquals("commit", cmd.getSQL());
			}
			else
			{
				fail("Wrong command index: " + index);
			}
		}

	}
}

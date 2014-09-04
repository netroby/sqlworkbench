/*
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2014 Thomas Kellerer.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * To contact the author please send an email to: support@sql-workbench.net
 */

package workbench.gui.bookmarks;

import java.util.List;

import workbench.WbTestCase;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Thomas Kellerer
 */
public class ProcedureBookmarksTest
	extends WbTestCase
{

	public ProcedureBookmarksTest()
	{
		super("ProcedureBookmarksTest");
	}

	@Test
	public void testOracle()
	{
		String script =
			"-- this is a test proc\n" +
			"create or replace function foo(p_foo integer, p_bar varchar(20)) return boolean as begin return 42; end;\n" +
			"\n" +
			"-- another proc\n" +
			"drop procedure bar;\n" +
			"create procedure bar as begin null; end;\n";
		ProcedureBookmarks parser = new ProcedureBookmarks();
		parser.setIncludeParameterNames(false);
		parser.parseScript(script);
		List<NamedScriptLocation> bookmarks = parser.getBookmarks();
//		System.out.println(bookmarks);
		assertEquals(2, bookmarks.size());
		assertEquals("foo(integer,varchar(20))", bookmarks.get(0).getName());
		assertEquals("bar", bookmarks.get(1).getName());


		script =
			"-- this is a test proc\n" +
			"create or replace function foo(p_one integer, p_two varchar(20) default 'foo') return boolean as begin return 42; end;\n";
		parser = new ProcedureBookmarks();
		parser.setIncludeParameterNames(true);
		parser.parseScript(script);
		bookmarks = parser.getBookmarks();
//		System.out.println(bookmarks);
		assertEquals(1, bookmarks.size());
		assertEquals("foo(p_one integer, p_two varchar(20))", bookmarks.get(0).getName());
	}

	@Test
	public void testMicrosoft()
	{
		String script =
			"create procedure foo(@p_foo int, @p_bar varchar(20) = 'bar') \n" +
			"as\n" +
			"begin" +
			"   do_something;\n" +
			"end;\n";
		ProcedureBookmarks parser = new ProcedureBookmarks();
		parser.setIncludeParameterNames(false);
		parser.parseScript(script);

		List<NamedScriptLocation> bookmarks = parser.getBookmarks();
//		System.out.println(bookmarks);
		assertEquals(1, bookmarks.size());
		assertEquals("foo(int,varchar(20))", bookmarks.get(0).getName());
		parser.setIncludeParameterNames(true);
		parser.parseScript(script);
		bookmarks = parser.getBookmarks();
		assertEquals(1, bookmarks.size());
		assertEquals("foo(@p_foo int, @p_bar varchar(20))", bookmarks.get(0).getName());
//		System.out.println(bookmarks);
	}

	@Test
	public void testPostgres()
	{
		String script =
			"create function get_answer(p_foo integer) returns boolean \n" +
			"as $$\n" +
			"begin" +
			"   select 42;\n" +
			"end;\n" +
			"$$" +
			"language sql;";

		ProcedureBookmarks parser = new ProcedureBookmarks();
		parser.setIncludeParameterNames(false);
		parser.parseScript(script);

		List<NamedScriptLocation> bookmarks = parser.getBookmarks();
//		System.out.println(bookmarks);
		assertEquals(1, bookmarks.size());
		assertEquals("get_answer(integer)", bookmarks.get(0).getName());

		script =
			"create function get_answer(p_foo integer, p_bar integer default = 0) returns boolean \n" +
			"as $$\n" +
			"begin" +
			"   select 42;\n" +
			"end;\n" +
			"$$" +
			"language sql;";

		parser.setIncludeParameterNames(true);
		parser.parseScript(script);
		bookmarks = parser.getBookmarks();
//		System.out.println(bookmarks);
		assertEquals(1, bookmarks.size());
		assertEquals("get_answer(p_foo integer, p_bar integer)", bookmarks.get(0).getName());
	}


}

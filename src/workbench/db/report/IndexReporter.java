/*
 * IndexReporter.java
 *
 * This file is part of SQL Workbench/J, http://www.sql-workbench.net
 *
 * Copyright 2002-2013, Thomas Kellerer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at.
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * To contact the author please send an email to: support@sql-workbench.net
 *
 */
package workbench.db.report;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import workbench.log.LogMgr;

import workbench.db.IndexColumn;
import workbench.db.IndexDefinition;
import workbench.db.IndexReader;
import workbench.db.TableIdentifier;
import workbench.db.WbConnection;
import workbench.db.mssql.SqlServerUtil;
import workbench.db.oracle.OracleIndexPartition;

import workbench.util.CollectionUtil;
import workbench.util.SqlUtil;
import workbench.util.StrBuffer;
import workbench.util.StringUtil;

import static workbench.db.report.ReportTable.TAG_TABLESPACE;

/**
 * Class to retrieve all index definitions for a table and
 * generate an XML string from that.
 *
 * @author  Thomas Kellerer
 */
public class IndexReporter
{
	public static final String TAG_INDEX = "index-def";

	public static final String TAG_INDEX_NAME = "name";
	public static final String TAG_INDEX_UNIQUE = "unique";
	public static final String TAG_INDEX_PK = "primary-key";
	public static final String TAG_INDEX_TYPE = "type";
	public static final String TAG_INDEX_EXPR = "index-expression";
	public static final String TAG_INDEX_COLUMN_LIST = "column-list";
	public static final String TAG_INDEX_COLUMN_NAME = "column";
	public static final String TAG_INDEX_OPTION = "index-option";

	private Collection<IndexDefinition> indexList;
	private TagWriter tagWriter = new TagWriter();
	private String mainTagToUse;
	private Map<IndexDefinition, List<ObjectOption>> indexOptions;

	public IndexReporter(TableIdentifier tbl, WbConnection conn)
	{
		this(tbl, conn, true);
	}

	public IndexReporter(TableIdentifier tbl, WbConnection conn, boolean includeOptions)
	{
		indexList  = conn.getMetadata().getIndexReader().getTableIndexList(tbl);
		if (includeOptions)
		{
			retrieveOracleOptions(conn);
		}
		retrieveSqlServerOptions(tbl, conn);
	}

	public IndexReporter(IndexDefinition index)
	{
		indexList  = new LinkedList<IndexDefinition>();
		indexList.add(index);
	}

	public void setMainTagToUse(String tag)
	{
		mainTagToUse = tag;
	}

	public void appendXml(StrBuffer result, StrBuffer indent)
	{
		int numIndex = this.indexList.size();
		if (numIndex == 0) return;
		StrBuffer defIndent = new StrBuffer(indent);
		defIndent.append("  ");

		for (IndexDefinition index : indexList)
		{
			if (index == null) continue;
			tagWriter.appendOpenTag(result, indent, mainTagToUse == null ? TAG_INDEX : mainTagToUse);
			result.append('\n');
			tagWriter.appendTag(result, defIndent, TAG_INDEX_NAME, index.getName());
			tagWriter.appendTag(result, defIndent, TAG_INDEX_EXPR, index.getExpression());
			tagWriter.appendTag(result, defIndent, TAG_INDEX_UNIQUE, index.isUnique());
			if (index.isUniqueConstraint())
			{
				tagWriter.appendTag(result, defIndent, ForeignKeyDefinition.TAG_CONSTRAINT_NAME, index.getUniqueConstraintName());
			}
			tagWriter.appendTag(result, defIndent, TAG_INDEX_PK, index.isPrimaryKeyIndex());
			tagWriter.appendTag(result, defIndent, TAG_INDEX_TYPE, index.getIndexType());
			List<IndexColumn> columns = index.getColumns();
			if (columns.size() > 0)
			{
				StrBuffer colIndent = new StrBuffer(defIndent);
				colIndent.append("  ");
				tagWriter.appendOpenTag(result, defIndent, TAG_INDEX_COLUMN_LIST);
				result.append('\n');
				for (IndexColumn col : columns)
				{

					List<TagAttribute> attrs = new ArrayList<TagAttribute>(2);
					attrs.add(new TagAttribute("name", SqlUtil.removeObjectQuotes(col.getColumn())));

					if (col.getDirection() != null)
					{
						attrs.add(new TagAttribute("direction", col.getDirection()));
					}
					tagWriter.appendOpenTag(result, colIndent, TAG_INDEX_COLUMN_NAME, attrs, false);
					result.append("/>\n");
				}
				tagWriter.appendCloseTag(result, defIndent, TAG_INDEX_COLUMN_LIST);
			}
			if (StringUtil.isNonBlank(index.getTablespace()))
			{
				tagWriter.appendTag(result, defIndent, TAG_TABLESPACE, index.getTablespace(), false);
			}
			writeDbmsOptions(result, defIndent, index);
			tagWriter.appendCloseTag(result, indent, mainTagToUse == null ? TAG_INDEX : mainTagToUse);
		}
	}

	private void writeDbmsOptions(StrBuffer output, StrBuffer indent, IndexDefinition index)
	{
		if (indexOptions == null) return;

		List<ObjectOption> options = indexOptions.get(index);
		if (CollectionUtil.isEmpty(options)) return;

		StrBuffer myindent = new StrBuffer(indent);
		myindent.append("  ");
		output.append(indent);
		output.append("<index-options>\n");
		for (ObjectOption option : options)
		{
			StrBuffer result = option.getXml(myindent);
			output.append(result);
		}
		output.append(indent);
		output.append("</index-options>\n");
	}

	private void retrieveSqlServerOptions(TableIdentifier table, WbConnection conn)
	{
		if (!conn.getMetadata().isSqlServer()) return;
		if (!SqlServerUtil.isSqlServer2005(conn)) return;
		IndexReader reader = conn.getMetadata().getIndexReader();

		for (IndexDefinition index : indexList)
		{
			String included = reader.getIndexOptions(table, index);
			if (StringUtil.isNonEmpty(included))
			{
				String cols = included.replaceAll("(?i)\\s+INCLUDE\\s+", "");
				ObjectOption option = new ObjectOption("included-columns", cols);
				option.setWriteFlaxXML(true);
				addOption(index, option);
			}
		}
	}

	private void retrieveOracleOptions(WbConnection conn)
	{
		if (!conn.getMetadata().isOracle()) return;

		try
		{
			for (IndexDefinition index : indexList)
			{
				OracleIndexPartition reader = new OracleIndexPartition(conn);
				reader.retrieve(index, conn);
				if (reader.isPartitioned())
				{
					ObjectOption option = new ObjectOption("partition", reader.getSourceForIndexDefinition());
					addOption(index, option);
				}
			}
		}
		catch (SQLException sql)
		{
			LogMgr.logWarning("IndexReporter.retrieveOracleOptions()", "Could not retrieve index options", sql);
		}
	}

	private void addOption(IndexDefinition index, ObjectOption option)
	{
		if (indexOptions == null)
		{
			indexOptions = new HashMap<IndexDefinition, List<ObjectOption>>();
		}
		List<ObjectOption> options = indexOptions.get(index);
		if (options == null)
		{
			options = new ArrayList<ObjectOption>();
			indexOptions.put(index, options);
		}
		options.add(option);
	}

	public Collection<IndexDefinition> getIndexList()
	{
		return this.indexList;
	}

	public void done()
	{
	}
}

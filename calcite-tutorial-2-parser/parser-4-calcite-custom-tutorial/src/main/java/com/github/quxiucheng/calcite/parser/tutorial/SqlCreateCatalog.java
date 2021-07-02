package com.github.quxiucheng.calcite.parser.tutorial;

import org.apache.calcite.sql.*;
import org.apache.calcite.sql.parser.SqlParserPos;

import java.util.List;

public class SqlCreateCatalog extends SqlCall {

	private SqlNode catalogName;
	private boolean isTemporary;
	private SqlNodeList properties;

	public SqlCreateCatalog(SqlParserPos pos,
							 SqlNode catalogName, boolean isTemporary,
							 SqlNodeList properties) {

		super(pos);
		this.catalogName = catalogName;
		this.isTemporary = isTemporary;
		this.properties = properties;
	}


	public SqlNode getCatalogName() {
		return catalogName;
	}

	public Boolean getTemporary() {
		return isTemporary;
	}

	public SqlNodeList getProperties() {
		return properties;
	}

	@Override
	public SqlOperator getOperator() {
		return null;
	}

	@Override
	public List<SqlNode> getOperandList() {
		return null;
	}

	@Override
	public SqlKind getKind() {
		return SqlKind.OTHER_DDL;
	}

	@Override
	public void unparse(SqlWriter writer, int leftPrec, int rightPrec) {
		writer.keyword("CREATE");
		if (isTemporary) writer.keyword("TEMPORARY");
		writer.keyword("CATALOG");
		catalogName.unparse(writer, leftPrec, rightPrec);
		if (properties != null) {
			writer.newlineAndIndent();
			writer.keyword("WITH");
			SqlWriter.Frame propertyFrame = writer.startList("(", ")");
			for (SqlNode property : properties) {
				writer.sep(",", false);
				writer.newlineAndIndent();
				writer.print("  ");
				property.unparse(writer, leftPrec, rightPrec);
			}
			writer.newlineAndIndent();
			writer.endList(propertyFrame);
		}
	}
}

package org.hibernate.mapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.MappingException;
import org.hibernate.dialect.Dialect;

import com.tianee.webframe.util.global.TeeSysProps;

public class ForeignKey extends Constraint {
	private Table referencedTable;
	private String referencedEntityName;
	private boolean cascadeDeleteEnabled;
	private List referencedColumns = new ArrayList();

	public String sqlConstraintString(Dialect dialect, String constraintName,
			String defaultCatalog, String defaultSchema) {
		String[] cols = new String[getColumnSpan()];
		String[] refcols = new String[getColumnSpan()];
		int i = 0;
		Iterator refiter = null;
		if (isReferenceToPrimaryKey()) {
			refiter = this.referencedTable.getPrimaryKey().getColumnIterator();
		} else {
			refiter = this.referencedColumns.iterator();
		}

		Iterator iter = getColumnIterator();
		while (iter.hasNext()) {
			cols[i] = ((Column) iter.next()).getQuotedName(dialect);
			refcols[i] = ((Column) refiter.next()).getQuotedName(dialect);
			i++;
		}
		String result = dialect.getAddForeignKeyConstraintString(
				constraintName, cols, this.referencedTable.getQualifiedName(
						dialect, defaultCatalog, defaultSchema), refcols,
				isReferenceToPrimaryKey());
		int foreignType = TeeSysProps.getInt("FOREIGN_TYPE");
		if (foreignType == 1) {

		} else if (foreignType == 2) {
			result = result + " on delete cascade";
		} else if (foreignType == 3) {
			if(TeeSysProps.getString("dialect").equals("oracle") || TeeSysProps.getString("dialect").equals("sqlserver")){
				result = "";
			}else{
				result = result.split(",")[0];
			}
		}
//		System.out.println(result);
		return null;
	}

	public Table getReferencedTable() {
		return this.referencedTable;
	}

	private void appendColumns(StringBuilder buf, Iterator columns) {
		while (columns.hasNext()) {
			Column column = (Column) columns.next();
			buf.append(column.getName());
			if (columns.hasNext())
				buf.append(",");
		}
	}

	public void setReferencedTable(Table referencedTable)
			throws MappingException {
		this.referencedTable = referencedTable;
	}

	public void alignColumns() {
		if (isReferenceToPrimaryKey())
			alignColumns(this.referencedTable);
	}

	private void alignColumns(Table referencedTable) {
		if (referencedTable.getPrimaryKey().getColumnSpan() != getColumnSpan()) {
			StringBuilder sb = new StringBuilder();
			sb.append("Foreign key (").append(getName() + ":")
					.append(getTable().getName()).append(" [");

			appendColumns(sb, getColumnIterator());
			sb.append("])")
					.append(") must have same number of columns as the referenced primary key (")
					.append(referencedTable.getName()).append(" [");

			appendColumns(sb, referencedTable.getPrimaryKey()
					.getColumnIterator());
			sb.append("])");
			throw new MappingException(sb.toString());
		}

		Iterator fkCols = getColumnIterator();
		Iterator pkCols = referencedTable.getPrimaryKey().getColumnIterator();
		while (pkCols.hasNext())
			((Column) fkCols.next()).setLength(((Column) pkCols.next())
					.getLength());
	}

	public String getReferencedEntityName() {
		return this.referencedEntityName;
	}

	public void setReferencedEntityName(String referencedEntityName) {
		this.referencedEntityName = referencedEntityName;
	}

	public String sqlDropString(Dialect dialect, String defaultCatalog,
			String defaultSchema) {
		return "alter table "
				+ getTable().getQualifiedName(dialect, defaultCatalog,
						defaultSchema) + dialect.getDropForeignKeyString()
				+ getName();
	}

	public boolean isCascadeDeleteEnabled() {
		return this.cascadeDeleteEnabled;
	}

	public void setCascadeDeleteEnabled(boolean cascadeDeleteEnabled) {
		this.cascadeDeleteEnabled = cascadeDeleteEnabled;
	}

	public boolean isPhysicalConstraint() {
		return (this.referencedTable.isPhysicalTable())
				&& (getTable().isPhysicalTable())
				&& (!this.referencedTable.hasDenormalizedTables());
	}

	public List getReferencedColumns() {
		return this.referencedColumns;
	}

	public boolean isReferenceToPrimaryKey() {
		return this.referencedColumns.isEmpty();
	}

	public void addReferencedColumns(Iterator referencedColumnsIterator) {
		while (referencedColumnsIterator.hasNext()) {
			Selectable col = (Selectable) referencedColumnsIterator.next();
			if (!col.isFormula())
				addReferencedColumn((Column) col);
		}
	}

	private void addReferencedColumn(Column column) {
		if (!this.referencedColumns.contains(column))
			this.referencedColumns.add(column);
	}

	public String toString() {
		if (!isReferenceToPrimaryKey()) {
			StringBuilder result = new StringBuilder(getClass().getName() + '('
					+ getTable().getName() + getColumns());
			result.append(" ref-columns:(" + getReferencedColumns());
			result.append(") as " + getName());
			return result.toString();
		}

		return super.toString();
	}
}

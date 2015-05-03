package edu.ilstu.cast.it.util;

import java.awt.Component;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 * @author at_gsharma
 *	This class particularly formats the cell padding of the table
 */
public class ColumnTableCellRenderer extends JLabel implements TableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			final int row, int column) {

		JLabel cellSpacingLabel = (JLabel) (this);

		if (hasFocus) {
			setBorder(UIManager.getBorder("Table.focusCellHighlightBorder"));
			cellSpacingLabel = null;
		} else {
			setBackground(table.getBackground());
			setBorder(null);
		}
		if (isSelected) {
			setBackground(table.getSelectionBackground());
			setBorder(null);
		} else {
			setBackground(table.getBackground());
			setBorder(null);
		}
		if (cellSpacingLabel != null) {
			cellSpacingLabel.setBorder(new CompoundBorder(new EmptyBorder(new Insets(1, 4, 1, 4)), cellSpacingLabel
					.getBorder()));
		}
		this.setOpaque(true);
		setText((String) value);
		return this;

	}

}
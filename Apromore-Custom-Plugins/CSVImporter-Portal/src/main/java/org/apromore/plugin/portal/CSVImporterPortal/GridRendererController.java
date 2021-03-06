/*
 * This file is part of "Apromore".
 *
 * Copyright (C) 2019 - 2020 The University of Melbourne.
 *
 * "Apromore" is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of the
 * License, or (at your option) any later version.
 *
 * "Apromore" is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program.
 * If not, see <http://www.gnu.org/licenses/lgpl-3.0.html>.
 */

package org.apromore.plugin.portal.CSVImporterPortal;

import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;


/**
 * The Class GridRendererController.
 */
public class GridRendererController implements RowRenderer<String[]> {

	private Integer AttribWidth;
	/*
	 * @see org.zkoss.zul.RowRenderer#render(org.zkoss.zul.Row, java.lang.Object, int)
	 * Append rows to the grid
	 */
	public void render(Row row, String[] data, int index) throws Exception  {
		for (int i = 0; i < data.length; i++) {
			Label lbl = new Label();
			lbl.setValue(data[i]);
			lbl.setWidth(this.AttribWidth + "px");
			lbl.setMultiline(false);
//			lbl.setMaxlength(25);
			lbl.setTooltiptext(data[i]);
			row.appendChild(lbl);

//			row.setStyle("height: 10px;");
		}
	}

	public void setAttribWidth(Integer attribWidth) {
		AttribWidth = attribWidth;
	}
}

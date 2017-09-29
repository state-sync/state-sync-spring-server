package org.statesync.spring.model.grid;

import org.statesync.model.AnnotatedList;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GridModel<Item> extends AnnotatedList<Item> {
	public GridQuery query = new GridQuery();
	public GridQuery shadowQuery = new GridQuery();
	public GridPagination pagination = new GridPagination();
}

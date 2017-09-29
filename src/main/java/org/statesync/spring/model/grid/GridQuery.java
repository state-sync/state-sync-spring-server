package org.statesync.spring.model.grid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import lombok.Data;

@Data
public class GridQuery implements Cloneable {
	public String search;
	public int page = 0;
	public int size = 10;
	public String sortBy;

	public Pageable toPageable() {
		return new PageRequest(this.page, this.size);
	}
}

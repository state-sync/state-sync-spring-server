package org.statesync.spring.model.grid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GridReducerContext<T, DT> {

	Page<DT> search(Pageable pageable);

}

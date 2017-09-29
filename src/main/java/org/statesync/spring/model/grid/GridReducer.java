package org.statesync.spring.model.grid;

import org.statesync.model.AnnotatedList;
import org.statesync.model.JReducer;

public abstract class GridReducer<M extends GridModel<T>, C extends GridReducerContext<T, DT>, T, DT> extends JReducer {

	public abstract T produce(DT db, GridReducerContext<T, DT> ctx);

	public M reduce(final M state, final C context) {
		final boolean refresh = state.data == null || state.query.equals(state.shadowQuery);
		if (refresh) {
			state.data = select(state.query, context);
			state.shadowQuery = state.query;
		}

		return state;
	}

	private AnnotatedList<T> select(final GridQuery query, final C context) {
		// TODO Auto-generated method stub
		return null;
	}

	// public List<T> reduce(final List<T> state, final GridReducerContext<DT>
	// ctx) {
	// return list(ctx //
	// .search(this.query.toPageable()) //
	// .map(db -> this.produce(db, ctx)) // java rx
	//
	// );
	// }
	//
	// public Store<T> reduce(final Store<T> state, final GridReducerContext<DT>
	// ctx) {
	// this.query = state.query;
	// return new Store<T>(state) {
	// {
	// this.items = reduce(state.items, ctx);
	// }
	// };
	// }
}

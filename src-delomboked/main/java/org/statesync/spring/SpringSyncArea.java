package org.statesync.spring;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.statesync.InMemoryStateStorage;
import org.statesync.SignalHandler;
import org.statesync.StateStorage;
import org.statesync.SyncArea;
import org.statesync.SyncAreaApi;
import org.statesync.config.SyncAreaConfig;

import com.fasterxml.jackson.databind.node.ObjectNode;

public class SpringSyncArea<Model> {

	private SyncArea<Model> area;
	private SyncAreaConfig<Model> config;

	@Autowired
	protected SpringSyncService service;

	private Map<String, SignalHandler<Model>> signalHandlers = new ConcurrentHashMap<>();

	public SyncArea<Model> getArea() {
		return this.area;
	}

	@SuppressWarnings("unchecked")
	protected SyncAreaConfig<Model> getConfig() {
		if (this.config == null) {
			this.config = new SyncAreaConfig<>();
			final SyncAreaService ann = AnnotationUtils.findAnnotation(getClass(), SyncAreaService.class);
			this.config.setId(ann.clientLocalPrefix());
			this.config.setClientPush(ann.clientPush());
			this.config.setId(ann.id());
			this.config.setModel((Class<Model>) ann.model());
			this.config.setServerLocalPrefix(ann.serverLocalPrefix());
			this.config.setServerPush(ann.serverPush());
			this.config.setTimeout(ann.timeout());
		}
		return this.config;
	}

	protected StateStorage getStateStorage() {
		return new InMemoryStateStorage();
	}

	@PostConstruct
	public void init() {
		this.area = newSyncArea();
		this.service.register(this);
	}

	protected Model newModel() {
		try {
			return getConfig().getModel().newInstance();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private SyncArea<Model> newSyncArea() {
		return new SyncArea<Model>(getConfig(), getStateStorage(), this::process, this::signal);
	}

	protected Model process(final Model model, final SyncAreaApi<Model> user) {
		return model;
	}

	public void registerSignalHandler(final String signal, final SignalHandler<Model> handler) {
		this.signalHandlers.put(signal, handler);
	}

	public Model signal(final Model model, final SyncAreaApi<Model> user, final String signal,
			final ObjectNode parameters) {
		final SignalHandler<Model> signalHandler = this.signalHandlers.get(signal);
		if (signalHandler == null)
			throw new RuntimeException("Unknown signal '" + signal + "' for area " + this.area.getAreaId());
		return signalHandler.handle(model, user, signal, parameters);
	}
}

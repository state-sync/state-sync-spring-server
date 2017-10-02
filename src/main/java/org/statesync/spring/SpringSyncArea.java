package org.statesync.spring;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.statesync.InMemoryStateStorage;
import org.statesync.StateStorage;
import org.statesync.SyncArea;
import org.statesync.SyncAreaUser;
import org.statesync.config.SyncAreaConfig;

public class SpringSyncArea<Model> {

	private SyncArea<Model> area;
	private SyncAreaConfig<Model> config;

	@Autowired
	protected SpringSyncService service;

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
		}
		return this.config;
	}

	protected StateStorage<Model> getStateStorage() {
		return new InMemoryStateStorage<>(this::newModel);
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
		return new SyncArea<Model>(getConfig(), getStateStorage(), this::process);
	}

	protected Model process(final Model model, final SyncAreaUser user) {
		return model;
	}
}

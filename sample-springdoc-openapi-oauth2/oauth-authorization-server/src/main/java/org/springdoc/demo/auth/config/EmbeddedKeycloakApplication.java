package org.springdoc.demo.auth.config;

import java.util.NoSuchElementException;

import org.keycloak.Config;
import org.keycloak.models.KeycloakSession;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.services.managers.ApplianceBootstrap;
import org.keycloak.services.managers.RealmManager;
import org.keycloak.services.resources.KeycloakApplication;
import org.keycloak.services.util.JsonConfigProviderFactory;
import org.keycloak.util.JsonSerialization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.demo.auth.config.KeycloakServerProperties.AdminUser;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class EmbeddedKeycloakApplication extends KeycloakApplication {

	private static final Logger LOG = LoggerFactory.getLogger(EmbeddedKeycloakApplication.class);

	static KeycloakServerProperties keycloakServerProperties;

	protected void loadConfig() {
		JsonConfigProviderFactory factory = new RegularJsonConfigProviderFactory();
		Config.init(factory.create()
				.orElseThrow(() -> new NoSuchElementException("No value present")));
	}

	public EmbeddedKeycloakApplication() {

		super();

		createMasterRealmAdminUser();

		createBaeldungRealm();
	}

	private void createMasterRealmAdminUser() {

		KeycloakSession session = getSessionFactory().create();

		ApplianceBootstrap applianceBootstrap = new ApplianceBootstrap(session);

		AdminUser admin = keycloakServerProperties.getAdminUser();

		try {
			session.getTransactionManager().begin();
			applianceBootstrap.createMasterRealmUser(admin.getUsername(), admin.getPassword());
			session.getTransactionManager().commit();
		} catch (Exception ex) {
			LOG.warn("Couldn't create keycloak master admin user: {}", ex.getMessage());
			session.getTransactionManager().rollback();
		}

		session.close();
	}

	private void createBaeldungRealm() {
		KeycloakSession session = getSessionFactory().create();

		try {
			session.getTransactionManager().begin();

			RealmManager manager = new RealmManager(session);
			Resource lessonRealmImportFile = new ClassPathResource(keycloakServerProperties.getRealmImportFile());

			manager.importRealm(
					JsonSerialization.readValue(lessonRealmImportFile.getInputStream(), RealmRepresentation.class));

			session.getTransactionManager().commit();
		} catch (Exception ex) {
			LOG.warn("Failed to import Realm json file: {}", ex.getMessage());
			session.getTransactionManager().rollback();
		}

		session.close();
	}
}
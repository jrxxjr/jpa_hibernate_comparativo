package br.com.tidicas.dao;

import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import br.com.tidicas.tipos.BancoDeDados;

/**
 * Classe que controla as instâncias da conexão com o banco de dados 
 * 
 * @author Evaldo Junior
 */
public class JpaUtil {
		
	private static final Logger LOGGER = Logger.getLogger(JpaUtil.class.getName());
	
	public static EntityManager getEmMySql(){
		EntityManager result =null;
		try {
			final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(BancoDeDados.MYSQL.getValor());
			result=entityManagerFactory.createEntityManager();
		}catch (Exception ex) {
			LOGGER.severe(ex.getMessage());
		}
		return result;
	}
	
	public static EntityManager getEmPostgres(){
		EntityManager result =null;
		try {
			final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(BancoDeDados.POSTGRES.getValor());
			result=entityManagerFactory.createEntityManager();
		}catch (Exception ex) {
			LOGGER.severe(ex.getMessage());
		}
		return result;
	}
}
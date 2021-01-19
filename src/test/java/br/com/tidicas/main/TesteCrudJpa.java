package br.com.tidicas.main;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

import org.junit.Test;

import br.com.tidicas.dao.BlogDao;
import br.com.tidicas.dao.CategoriaDao;
import br.com.tidicas.model.Blog;
import br.com.tidicas.model.Categoria;
import br.com.tidicas.tipos.BancoDeDados;

/**
 * Classe para geração das tabelas e teste com operacoes crud
 * Performance Hibernate
 * 
 * @author Evaldo Junior
 *
 */
public class TesteCrudJpa {

	private static final Logger LOGGER = Logger.getLogger(TesteCrudJpa.class.getName());
    
	@Test 
	public void execute() throws Exception {
		  
		LOGGER.info("inicio - banco de dados mysql"); 
		process(BancoDeDados.MYSQL);
		LOGGER.info("fim - banco de dados mysql");
		  
		LOGGER.info("inicio - banco de dados postgres");
		process(BancoDeDados.POSTGRES);
		LOGGER.info("fim - banco de dados postgres");
		  
	}
	
	private void process(BancoDeDados bancoDeDados) { 
		CategoriaDao categoriaDao = new CategoriaDao(bancoDeDados); 
		BlogDao blogDao = new BlogDao(bancoDeDados);
		
		// 1 Entidade Categoria
		Categoria categoria1 = new Categoria();
		categoria1.setDescricao("categoria 1");

		Categoria categoria2 = new Categoria();
		categoria2.setDescricao("categoria 2");

		categoriaDao.adiciona(categoria1);

		categoriaDao.adiciona(categoria2);

		categoria1 = categoriaDao.buscaPorDescricao(categoria1.getDescricao()).get(0);
		LOGGER.info("retorno :" + categoria1.getDescricao());

		categoria2 = categoriaDao.buscaPorDescricao(categoria2.getDescricao()).get(0);
		LOGGER.info("retorno :" + categoria2.getDescricao());

		categoria1.setDescricao("categoria1 update");
		categoria1 = categoriaDao.atualiza(categoria1);

		categoria2.setDescricao("categoria2 update");
		categoria2 = categoriaDao.atualiza(categoria2);

		// 2 inclusao entidades Blog
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss:SSS");
		LocalDateTime inicioInclusao = LocalDateTime.now();

		LOGGER.info("inicio processamento - inclusao:" + dtf.format(inicioInclusao));
		
		blogDao.processa(categoria2);

		LocalDateTime fimInclusao = LocalDateTime.now();
		LOGGER.info("fim processamento - inclusao:" + dtf.format(fimInclusao));

		Duration duracaoInclusao = Duration.between(inicioInclusao, fimInclusao);

		String duracaoIncFormatada = String.format("%02d:%02d:%02d:%03d", duracaoInclusao.toHours() % 24, duracaoInclusao.toMinutes() % 60,
				duracaoInclusao.getSeconds() % 60, duracaoInclusao.toMillis() % 1000);
		LOGGER.info("tempo decorrido total - inclusao:" + duracaoIncFormatada);

		// 3 consulta entidades Blog
		LocalDateTime inicioConsulta = LocalDateTime.now();

		LOGGER.info("inicio processamento - consulta:" + dtf.format(inicioConsulta));
		List<Blog> result = blogDao.lista();

		LocalDateTime fimConsulta = LocalDateTime.now();
		if (result!=null) {
			LOGGER.info("quantidade registros retornados consulta:" + result.size());
		}
		LOGGER.info("fim processamento - consulta:" + dtf.format(fimConsulta));

		Duration duracaoConsulta = Duration.between(inicioConsulta, fimConsulta);

		String duracaoConsFormatada = String.format("%02d:%02d:%02d:%03d", duracaoConsulta.toHours() % 24, duracaoConsulta.toMinutes() % 60,
				duracaoConsulta.getSeconds() % 60, duracaoConsulta.toMillis() % 1000);
		LOGGER.info("tempo decorrido total - consulta:" + duracaoConsFormatada);
		
		// 4 remover todas entidades
		blogDao.removeAll();
		categoriaDao.removeAll();
		
		LOGGER.info("removidos todos registros");

	}
}
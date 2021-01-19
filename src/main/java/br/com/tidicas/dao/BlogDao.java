package br.com.tidicas.dao;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import br.com.tidicas.model.Blog;
import br.com.tidicas.model.Categoria;
import br.com.tidicas.tipos.BancoDeDados;

/**
 * Classe para manipular as operacoes de banco de dados da tabela blog
 * 
 * @author Evaldo Junior
 */
public class BlogDao {
	private final Dao<Blog> dao;
	private EntityManager em;
	private static final Logger LOGGER = Logger.getLogger(JpaUtil.class.getName());

	public BlogDao(BancoDeDados bancoDeDados) {
		if (bancoDeDados == BancoDeDados.MYSQL) {
			this.em = JpaUtil.getEmMySql();

		} else if (bancoDeDados == BancoDeDados.POSTGRES) {
			this.em = JpaUtil.getEmMySql();
		}

		this.dao = new Dao<Blog>(em, Blog.class);
	}

	public void adiciona(Blog blog) {
		this.dao.adiciona(blog);
	}

	public void remove(Blog blog) {
		this.dao.remove(blog);
	}

	public Blog atualiza(Blog blog) {
		blog = this.dao.atualiza(blog);
		return blog;
	}

	public List<Blog> lista() {
		return this.dao.lista();
	}

	public Blog busca(Integer id) {
		return dao.busca(id);
	}

	public List<Blog> buscaPorTitulo(String titulo) {
		List<Blog> result = null;

		try {

			Query query = em.createQuery("select x from Blog x where x.titulo like :parametro ");
			query.setParameter("parametro", "%" + titulo + "%");
			result = query.getResultList();
			
		} catch (Exception ex) {
			LOGGER.severe(ex.getMessage());
		}
		return result;
	}
	
	public void processa(Categoria categoria) {
		int tamanhoBatch=100;
		this.em.getTransaction().begin();
		
		for (int i = 0; i < tamanhoBatch; i++) {
			
			if (i> 0 && i % tamanhoBatch == 0) {
				limpa();
			}
			
			Blog blog = new Blog();
			blog.setCategoria(categoria);
			blog.setConteudo("conteúdo teste");
			blog.setDtevento(new Date());
			blog.setPublicar(0);
			blog.setTitulo("titulo");

			this.em.persist(blog);
			
		}
		
		limpa();
		
		this.em.getTransaction().commit();
	}
	
	private void limpa() {
		this.em.flush();
		this.em.clear();
	}
	
	public void removeAll() {
		this.em.getTransaction().begin();
		this.em.createQuery("delete from Blog").executeUpdate();
		this.em.flush();
		this.em.getTransaction().commit();
	}
	
}
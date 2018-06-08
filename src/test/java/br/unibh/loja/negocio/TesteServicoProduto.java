package br.unibh.loja.negocio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import br.unibh.loja.entidades.Categoria;
import br.unibh.loja.entidades.Produto;
import br.unibh.loja.util.Resources;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteServicoProduto {
	@Deployment
	public static Archive<?> createTestArchive() {
		// Cria o pacote que vai ser instalado no Wildfly para realizacao dos testes
		return ShrinkWrap.create(WebArchive.class, "testeloja.war")
				.addClasses(Categoria.class, Produto.class, Resources.class, DAO.class,
					ServicoProduto.class, ServicoCategoria.class)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	// Realiza as injecoes com CDI
	@Inject
	private Logger log;
	@Inject
	private ServicoCategoria sc;
	@Inject
	private ServicoProduto sp;
	
	@Test
	public void teste00_inserirCategoriaSemErro() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Categoria categoria = new Categoria(1L, "Blá blá blá");
		sc.insert(categoria);
		Categoria aux = (Categoria) sc.findByName("Blá blá blá").get(0);
		assertNotNull(aux);
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	@Test
	public void teste01_inserirSemErro() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Categoria cat = (Categoria) sc.findByName("Blá blá blá").get(0);
		Produto produto = new Produto(
			1L,
			"Produto 1",
			"Descrição do produto 1",
			cat,
			new BigDecimal(22.00),
			"Apple"
		);
		sp.insert(produto);
		Produto aux = (Produto) sp.findByName("Produto 1").get(0);
		assertNotNull(aux);
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	@Test
	public void teste02_inserirComErro() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		try {
			Categoria cat = (Categoria) sc.findByName("Blá blá blá").get(0);
			Produto produto = new Produto(
				1L,
				"Produto 1@",
				"Descrição do produto 1",
				cat,
				new BigDecimal(22.00),
				"Apple"
			);
			sp.insert(produto);
		} catch (Exception e) {
			assertTrue(checkString(e, "Caracteres permitidos: letras, espaços, ponto e aspas simples"));
		}
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	@Test
	public void teste03_atualizar() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Produto produto = (Produto) sp.findByName("Produto 1").get(0);
		produto.setNome("Produto 101");
		sp.update(produto);
		Produto aux = (Produto) sp.findByName("Produto 101").get(0);
		assertNotNull(aux);
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	@Test
	public void teste04_excluir() throws Exception {
		log.info("============> Iniciando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
		Produto produto = (Produto) sp.findByName("Produto 101").get(0);
		sp.delete(produto);
		assertEquals(0, sp.findByName("Produto 101").size());
		log.info("============> Finalizando o teste " + Thread.currentThread().getStackTrace()[1].getMethodName());
	}

	private boolean checkString(Throwable e, String str) {
		if (e.getMessage().contains(str)) {
			return true;
		} else if (e.getCause() != null) {
			return checkString(e.getCause(), str);
		}
		return false;
	}
}
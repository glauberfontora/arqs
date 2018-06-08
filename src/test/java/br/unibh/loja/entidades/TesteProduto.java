package br.unibh.loja.entidades;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.junit.Assert;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteProduto {
	
	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		System.out.println("Inicializando validador...");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void testCreateObject() {
		Produto produto = new Produto(
			1L,
			"Produto 1",
			"Descrição do produto 1",
			new Categoria(1L, "Blá blá blá"),
			new BigDecimal(22.00),
			"Apple"
		);
		assertEquals(produto.getId(), new Long(1));
		assertEquals(produto.getNome(), "Produto 1");
		assertEquals(produto.getDescricao(), "Descrição do produto 1");
		assertEquals(produto.getCategoria(), new Categoria(1L, "Blá blá blá"));
		assertEquals(produto.getPreco(), new BigDecimal(22.00));
		assertEquals(produto.getFabricante(), "Apple");
	}
	
	@Test
	public void testCompareObjects() {
		Produto p1 = new Produto(
			1L,
			"Produto 1",
			"Descrição do produto 1",
			new Categoria(1L, "Blá blá blá"),
			new BigDecimal(22.00),
			"Apple"
		);
		Produto p2 = new Produto(
			1L,
			"Produto 1",
			"Descrição do produto 1",
			new Categoria(1L, "Blá blá blá"),
			new BigDecimal(22.00),
			"Apple"
		);
		assertEquals(p1, p2);
	}
	
	@Test
	public void testPrintObject() {
		Produto produto = new Produto(
			1L,
			"Produto 1",
			"Descrição do produto 1",
			new Categoria(1L, "Blá blá blá"),
			new BigDecimal(22.00),
			"Apple"
		);
		assertEquals(produto.toString(), "Produto [id=1, nome=Produto 1, descricao=Descrição do produto 1, categoria=Categoria [id=1, descricao=Blá blá blá], preco=22, fabricante=Apple]");
	}
	
	@Test
	public void testValidacaoNomeProduto() {
		Produto produto = new Produto(
				1L,
				"Produto",
				"Descrição do produto",
				new Categoria(1L, "Blá blá blá"),
				new BigDecimal(22.00),
				"Apple"
			);
		
		Set<ConstraintViolation<Produto>> constraintViolations = validator.validate(produto);
		
		for (ConstraintViolation<Produto> p: constraintViolations) {
			System.out.println(" Erro de Validacao: "+p.getMessage());
		}
		
		Assert.assertEquals(0, constraintViolations.size() );
	}
	
	@Test
	public void testValidacaoNomeProdutoErrado() {
		Produto produto = new Produto(
				1L,
				"Produto @",
				"Descrição do produto",
				new Categoria(1L, "Blá blá blá"),
				new BigDecimal(22.00),
				"Apple"
			);
		
		Set<ConstraintViolation<Produto>> constraintViolations = validator.validate(produto);
		
		for (ConstraintViolation<Produto> p: constraintViolations) {
			System.out.println(" Erro de Validacao: "+p.getMessage());
		}
		
		Assert.assertEquals(1, constraintViolations.size() );
	}
}

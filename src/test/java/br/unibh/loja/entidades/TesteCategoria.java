package br.unibh.loja.entidades;

import static org.junit.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TesteCategoria {
	
	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		System.out.println("Inicializando validador...");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void testCreateObject() {
		Categoria categoria = new Categoria(1L, "Blá blá blá");
		assertEquals(categoria.getId(), new Long(1));
		assertEquals(categoria.getDescricao(), "Blá blá blá");
	}
	
	@Test
	public void testCompareObjects() {
		Categoria c1 = new Categoria(1L, "Blá blá blá");
		Categoria c2 = new Categoria(1L, "Blá blá blá");
		assertEquals(c1, c2);
	}
	
	@Test
	public void testPrintObject() {
		Categoria categoria = new Categoria(1L, "Blá blá blá");
		assertEquals(categoria.toString(), "Categoria [id=1, descricao=Blá blá blá]");
	}
	
	@Test
	public void testValidacaoNomeCategoria() {
		Categoria categoria = new Categoria(1L, "Blá blá blá");
		
		Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate(categoria);
		
		for (ConstraintViolation<Categoria> c: constraintViolations) {
			System.out.println(" Erro de Validacao: "+c.getMessage());
		}
		
		Assert.assertEquals(0, constraintViolations.size() );
	}
	
	@Test
	public void testValidacaoNomeCategoriaErrado() {
		Categoria categoria = new Categoria(1L, "Blá blá blá%");
		
		Set<ConstraintViolation<Categoria>> constraintViolations = validator.validate(categoria);
		
		for (ConstraintViolation<Categoria> c: constraintViolations) {
			System.out.println(" Erro de Validacao: "+c.getMessage());
		}
		
		Assert.assertEquals(1, constraintViolations.size() );
	}
}

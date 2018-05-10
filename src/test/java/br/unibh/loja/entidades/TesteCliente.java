package br.unibh.loja.entidades;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
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
public class TesteCliente {
	
	private static Validator validator;
	
	@BeforeClass
	public static void setUp() {
		System.out.println("Inicializando validador...");
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}
	
	@Test
	public void testCreateObject() {
		Cliente cliente = new Cliente(
			1L,
			"Glauber Fontora",
			"glauber",
			"123456",
			"Administrador",
			"04793335640",
			"31984842331",
			"glauber@q3m.com.br",
			new Date(),
			new Date()
		);
		
		assertEquals(cliente.getId(),new Long(1));
		assertEquals(cliente.getNome(), "Glauber Fontora");
		assertEquals(cliente.getLogin(), "glauber");
		assertEquals(cliente.getSenha(), "123456");
		assertEquals(cliente.getPerfil(), "Administrador");
		assertEquals(cliente.getCpf(), "04793335640");
		assertEquals(cliente.getTelefone(), "31984842331");
		assertEquals(cliente.getEmail(), "glauber@q3m.com.br");
		assertEquals(cliente.getDataCadastro(), new Date());
		assertEquals(cliente.getDataNascimento(), new Date());
	}
	
	@Test
	public void testCompareObjects() {
		Cliente c1 = new Cliente(
			1L,
			"Glauber Fontora",
			"glauber",
			"123456",
			"Administrador",
			"04793335640",
			"31984842331",
			"glauber@q3m.com.br",
			new Date(),
			new Date()
		);
		
		Cliente c2 = new Cliente(
			1L,
			"Glauber Fontora",
			"glauber",
			"123456",
			"Administrador",
			"04793335640",
			"31984842331",
			"glauber@q3m.com.br",
			new Date(),
			new Date()
		);
		
		assertTrue(c1.equals(c2));
	}
	
	@Test
	public void testPrintObject() {
		Cliente c1 = new Cliente(
			1L,
			"Glauber Fontora",
			"glauber",
			"123456",
			"Administrador",
			"04793335640",
			"31984842331",
			"glauber@q3m.com.br",
			new Date(),
			new Date()
		);
		
		assertEquals(c1.toString(), "Cliente [id=1, nome=Glauber Fontora, login=glauber, senha=123456, perfil=Administrador, cpf=04793335640, telefone=31984842331, email=glauber@q3m.com.br, dataNascimento=" + new Date() + ", dataCadastro=" + new Date() + "]");
	}
	
	@Test
	public void testValidacaoNome() {
		Cliente c1 = new Cliente(
			1L,
			"Glauber Fontora",
			"glauberfontora",
			"123456",
			"Administrador",
			"04793335640",
			"(31)98484-2331",
			"glauber@q3m.com.br",
			new Date(),
			new Date()
		);
		
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(c1);
		
		for (ConstraintViolation<Cliente> c: constraintViolations) {
			System.out.println(" Erro de Validacao: "+c.getMessage());
		}
		
		Assert.assertEquals(0, constraintViolations.size() );
	}
	
	@Test
	public void testValidacaoNomeErrado() {
		Cliente c1 = new Cliente(
			1L,
			"Glauber Fontora@",
			"glauberfontora",
			"123456",
			"Administrador",
			"04793335640",
			"(31)98484-2331",
			"glauber@q3m.com.br",
			new Date(),
			new Date()
		);
		
		Set<ConstraintViolation<Cliente>> constraintViolations = validator.validate(c1);
		
		for (ConstraintViolation<Cliente> c: constraintViolations) {
			System.out.println(" Erro de Validacao: "+c.getMessage());
		}
		
		Assert.assertEquals(1, constraintViolations.size() );
	}
	
}

package br.unibh.loja.entidades;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class TesteCliente {
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
}

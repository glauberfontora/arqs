package br.unibh.loja.entidades;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TesteCategoria {
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
}

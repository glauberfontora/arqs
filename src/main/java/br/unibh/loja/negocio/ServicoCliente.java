package br.unibh.loja.negocio;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.unibh.loja.entidades.Cliente;

@Stateless
@LocalBean
public class ServicoCliente implements DAO<Cliente, Long> {

	@Inject
	EntityManager em;
	@Inject
	private Logger log;

	@Override
	public Cliente insert(Cliente t) throws Exception {
		t.setPerfil("Standard");
		log.info("Persistindo " + t);
		em.persist(t);
		return t;
	}

	@Override
	public Cliente update(Cliente t) throws Exception {
		Calendar premiumCalendar = Calendar.getInstance();
		Calendar goldCalendar = Calendar.getInstance();
		
		premiumCalendar.add(Calendar.YEAR, -1);
		goldCalendar.add(Calendar.YEAR, -5);

		Date premium = premiumCalendar.getTime();
		Date gold = goldCalendar.getTime();

		switch (t.getPerfil()) {
			case "Gold":
				if (t.getDataCadastro().before(gold)) {
					log.info("Atualizando para Gold" + t);
					return em.merge(t);
				} else {
					throw new Exception("A data não é permitida para o perfil Gold");
				}
			case "Premium":
				if (t.getDataCadastro().before(premium)) {
					log.info("Atualizando para Premium" + t);
					return em.merge(t);
				} else {
					throw new Exception("A data não é permitida para o perfil Premium");
				}
			case "Standard":
				log.info("Atualizando para Standard" + t);
				return em.merge(t);
	
			default:
				throw new Exception("Não foi possível identificar o perfil");
		}
	}

	@Override
	public void delete(Cliente t) throws Exception {
		log.info("Removendo " + t);
		Object c = em.merge(t);
		em.remove(c);
	}

	@Override
	public Cliente find(Long k) throws Exception {
		log.info("Encontrando pela chave " + k);
		return em.find(Cliente.class, k);
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findAll() throws Exception {
		log.info("Encontrando todos os objetos");
		return em.createQuery("from Cliente").getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> findByName(String name) throws Exception {
		log.info("Encontrando o " + name);
		return em.createNamedQuery("Cliente.findByName").setParameter("nome", "%" + name + "%").getResultList();
	}

}

package org.dcoo.dcoomvc;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;

import jdbm.PrimaryTreeMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;

public class Contato implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2150547352117475455L;
	public Integer codigo;
	public String nome;
	public String codigoArea;
	public String fone;
	public String cidade;

	static {
		configBD();
	}

	public Contato(Integer codigo, String nome, String codigoArea, String fone,
			String cidade) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.codigoArea = codigoArea;
		this.fone = fone;
		this.cidade = cidade;
	}

	public Contato(Integer codigo, String nome) {
		super();
		this.codigo = codigo;
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contato other = (Contato) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Contato [codigo=" + codigo + ", nome=" + nome + ", codigoArea="
				+ codigoArea + ", fone=" + fone + ", cidade=" + cidade + "]";
	}

	// ***************** Metodos ActiveRedord **************

	public static Collection<Contato> findAll() {
		return contatoPorCodigo.values();
	}

	public static Contato carregarContato(Integer codigo) {
		return contatoPorCodigo.get(codigo);
	}

	public static void atualizar(Contato contato) {
		contatoPorCodigo.put(contato.codigo, contato);
	}

	public static Contato excluir(Integer codigo) {
		Contato old = contatoPorCodigo.remove(codigo);
		return old;
	}

	public static Contato criarContato(String nome) {

		Integer codigo = 
			java.util.Collections.max(
				contatoPorCodigo.keySet());

		codigo = codigo + 1;

		Contato novo = new Contato(codigo, nome);

		return novo;

	}

	// ***************** FIM - Metodos ActiveRedord **************

	// ***************** ACESSO AO BANCO **************
	private static String caminhoBD = System.getProperty("user.dir")
			+ "/db/contatos.dat";

	private static RecordManager recman;
	private static PrimaryTreeMap<Integer, Contato> contatoPorCodigo;

	private static void configBD() {
		try {
			recman = RecordManagerFactory.createRecordManager(caminhoBD);
			contatoPorCodigo = recman.treeMap("contatoPorCodigo");

			Contato maria = new Contato(1, "Maria", "51", "6666-6666", "POA");
			Contato jose = new Contato(2, "José", "54", "5555-5555", "PF");
			Contato mario = new Contato(3, "Mario", "51", "3333-3333", "Canoas");
			Contato marta = new Contato(4, "Marta", "51", "3333-3333", "Canoas");

			contatoPorCodigo.put(maria.codigo, maria);
			contatoPorCodigo.put(jose.codigo, jose);
			contatoPorCodigo.put(mario.codigo, mario);
			contatoPorCodigo.put(marta.codigo, marta);

		} catch (IOException e) {
			System.out.println("Erro IO: " + e);
			System.exit(0);
		}
	}

	// ***************** FIM ACESSO AO BANCO *********

}

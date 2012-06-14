package org.dcoo.dcoomvc;

import java.io.IOException;

import jdbm.PrimaryTreeMap;
import jdbm.RecordManager;
import jdbm.RecordManagerFactory;
import jdbm.SecondaryKeyExtractor;
import jdbm.SecondaryTreeMap;

public class PessoaTest {
	
	static class Pessoa implements java.io.Serializable{
		String codigo;
		
		String nome;
		
		String nomeDoPai;
		
		Endereco endereco;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((codigo == null) ? 0 : codigo.hashCode());
			result = prime * result + ((nome == null) ? 0 : nome.hashCode());
			result = prime * result
					+ ((nomeDoPai == null) ? 0 : nomeDoPai.hashCode());
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
			Pessoa other = (Pessoa) obj;
			if (codigo == null) {
				if (other.codigo != null)
					return false;
			} else if (!codigo.equals(other.codigo))
				return false;
			if (nome == null) {
				if (other.nome != null)
					return false;
			} else if (!nome.equals(other.nome))
				return false;
			if (nomeDoPai == null) {
				if (other.nomeDoPai != null)
					return false;
			} else if (!nomeDoPai.equals(other.nomeDoPai))
				return false;
			return true;
		}

		public Pessoa(String codigo, String nome, String nomeDoPai, Endereco endereco) {
			super();
			this.codigo = codigo;
			this.nome = nome;
			this.nomeDoPai = nomeDoPai;
			this.endereco = endereco;
		}

		@Override
		public String toString() {
			return "Pessoa [codigo=" + codigo + ", nome=" + nome
					+ ", nomeDoPai=" + nomeDoPai + "]";
		}
		
	}
	
	static class Endereco implements java.io.Serializable{
		String rua;
		String numero;
		String cidade;
		public Endereco(String rua, String numero, String cidade) {
			super();
			this.rua = rua;
			this.numero = numero;
			this.cidade = cidade;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((cidade == null) ? 0 : cidade.hashCode());
			result = prime * result
					+ ((numero == null) ? 0 : numero.hashCode());
			result = prime * result + ((rua == null) ? 0 : rua.hashCode());
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
			Endereco other = (Endereco) obj;
			if (cidade == null) {
				if (other.cidade != null)
					return false;
			} else if (!cidade.equals(other.cidade))
				return false;
			if (numero == null) {
				if (other.numero != null)
					return false;
			} else if (!numero.equals(other.numero))
				return false;
			if (rua == null) {
				if (other.rua != null)
					return false;
			} else if (!rua.equals(other.rua))
				return false;
			return true;
		}
		@Override
		public String toString() {
			return "Endereco [rua=" + rua + ", numero=" + numero + ", cidade="
					+ cidade + "]";
		}
		
	}
	

	public static void main(String[] args) throws IOException {
		RecordManager recman = RecordManagerFactory.createRecordManager("c:\\pessoas");
		
		PrimaryTreeMap<String, Pessoa> pessoasPorCodigo =  recman.treeMap("");
		
		SecondaryTreeMap<String,String, Pessoa> pessoaPorCidade = 
				pessoasPorCodigo.secondaryTreeMap("pessoaPorCidade", 
						new SecondaryKeyExtractor<String, String, PessoaTest.Pessoa>() {

							@Override
							public String extractSecondaryKey(String arg0,
									Pessoa pessoa) {
								// TODO Auto-generated method stub
								return pessoa.endereco.cidade;
							}
					
				});
				
		
		
		Pessoa ze = new Pessoa("1", "Ze", "Pai do Ze", new Endereco("Ipiranga", "333", "POA"));
		
		pessoasPorCodigo.put(ze.codigo, ze);
		
		
		Pessoa maria = new Pessoa("2", "Maria", "Pai da Maria", new Endereco("Ceara", "666", "POA"));
		
		
		pessoasPorCodigo.put(maria.codigo, maria);
		
		
		Pessoa  pesquisa = pessoasPorCodigo.get("1");
		
		
		System.out.println(" resp: "  + pesquisa);
		
		
		System.out.println("Pessoal de POA: " + pessoaPorCidade.get("POA"));
		
	}
}

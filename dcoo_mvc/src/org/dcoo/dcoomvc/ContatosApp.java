package org.dcoo.dcoomvc;

import static spark.Spark.get;
import static spark.Spark.post;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;

import org.dcoo.dcoomvc.infra.MustacheRouteTemplate;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.utils.IOUtils;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;

public class ContatosApp {

	private static String caminhoTemplates = System.getProperty("user.dir")
			+ "/templates/";

	private static String caminhoImgs = System.getProperty("user.dir")
			+ "/imgs/";

	// private static String caminhoJS = System.getProperty("user.dir") +
	// "/js/";

	private static DefaultMustacheFactory mufa;

	static {
		mufa = new DefaultMustacheFactory(new java.io.File(caminhoTemplates));
	}

	public static void main(String[] args) {
		get(new Route("/hello") {
			@Override
			public Object handle(Request request, Response response) {
				return "Hello, world!!";
			}
		});

		get(new MustacheRouteTemplate("/contatos/novo",mufa) {
			@Override
			protected String mustacheFile() {
				return "contatos_novo.mustache";
			}
			@Override
			protected Object prepareData() {
				return null;
			}
		});

		post(new Route("/contatos/inserir") {

			@Override
			public Object handle(Request request, Response response) {
				String nome = request.params("nome");

				Contato novoContato = Contato.criarContato(nome);

				novoContato.codigoArea = request.params("codigoArea");
				novoContato.fone = request.params("fone");
				novoContato.cidade = request.params("cidade");

				Contato.atualizar(novoContato);

				return "contato inserido!";
			}
		});

		
		get (new MustacheRouteTemplate("/contatos/list", mufa) {
			
			@Override
			protected Object prepareData() {
				return Contato.findAll();
			}
			
			@Override
			protected String mustacheFile() {
				return "contatos_list.mustache";
			}
		});
		
		get(new Route("/contatos/old/list") {

			@Override
			public Object handle(Request request, Response response) {
				StringWriter sw = new StringWriter();
				try {
					Mustache mustache = mufa
							//.compile("contatos_lista_errado.mustache");
							.compile("contatos_list.mustache");

					try {
						mustache.execute(sw, Contato.findAll()).flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (Exception e) {
					halt(404, "Erro no servidor! " +
							"Informe o texto a seguir ao administrador:<br>" +
							e.getMessage());
				}
				return sw.toString();
			}
		});

		get(new Route("/contatos/old_list_nao_mvc") {
			@Override
			public Object handle(Request request, Response response) {
				StringBuffer buffy = new StringBuffer();
				buffy.append("<html>")
						.append("<head>")
						.append("<title>Listagem Contatos</title>")
						.append("</head>")
						.append("<body>")
						.append("<h1>Listagem de Contatos</h1>")
						.append("<table>")
						.append("<th>Código</th> <th>Nome</th> <th>Cidade</th>");

				for (Contato contato : Contato.findAll()) {
					/*
					 * Codigo Original for (Contato contato :
					 * contatoPorCodigo.values()) {
					 */
					buffy.append("<tr>").append("<td>").append(contato.codigo)
							.append("</td>").append("<td>")
							.append(contato.nome).append("</td>")
							.append("<td>").append(contato.cidade)
							.append("</td>").append("</tr>");
				}
				buffy.append("</table>").append("</body>").append("</html>");
				return buffy.toString();
			}
		});

		get(new Route("/contatos/:codigo") {
			@Override
			public Object handle(Request request, Response response) {
				Integer codigo = new Integer(request.params(":codigo"));
				Contato resultado = Contato.carregarContato(codigo);// contatoPorCodigo.get(codigo);
				if (resultado == null) {
					return "ERRO! contato nao encontrado!";
				}
				return resultado;
			}
		});

		get(new Route("/teste/:param") {
			@Override
			public Object handle(Request request, Response response) {
				String valorParam = request.params(":param");

				return "teste:" + valorParam;
			}
		});

		get(new Route("/static/:folder/:filename") {

			@Override
			public Object handle(Request request, Response response) {

				return String.format("Pasta: %s arquivo: %s",
						request.params(":folder"), request.params(":filename"));
			}
		});

		get(new Route("/imgs/:img_file") {
			@Override
			public Object handle(Request request, Response response) {
				File file = new File(caminhoImgs, request.params(":img_file"));
				try {
					response.raw().setContentType("image/jpeg;charset=utf-8");
					response.raw().getOutputStream();

					java.io.InputStream in = new FileInputStream(file);
					BufferedOutputStream out = new BufferedOutputStream(
							response.raw().getOutputStream());

					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}
		});

		get(new Route("/files/:file") {
			@Override
			public Object handle(Request request, Response response) {
				File file = new File(
						System.getProperty("user.dir") + "/files/",
						request.params(":file"));
				try {
					response.raw().setContentType("application/octet-stream");
					response.raw().getOutputStream();

					java.io.InputStream in = new FileInputStream(file);
					BufferedOutputStream out = new BufferedOutputStream(
							response.raw().getOutputStream());

					byte[] buf = new byte[1024];
					int len;
					while ((len = in.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
					in.close();
					out.close();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}
		});

	}
}

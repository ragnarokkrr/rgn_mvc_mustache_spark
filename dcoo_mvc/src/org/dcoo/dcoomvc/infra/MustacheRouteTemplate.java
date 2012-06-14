package org.dcoo.dcoomvc.infra;

import java.io.StringWriter;

import org.dcoo.dcoomvc.Contato;

import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import spark.Request;
import spark.Response;
import spark.Route;

public abstract class MustacheRouteTemplate extends Route {
	private MustacheFactory mufa;

	public MustacheRouteTemplate(String path, MustacheFactory mufa) {
		super(path);
		this.mufa = mufa;
	}

	// definir template
	protected abstract String mustacheFile();

	protected abstract Object prepareData();

	@Override
	public Object handle(Request request, Response response) {
		StringWriter sw = new StringWriter();

		try {
			Mustache mustache = mufa.compile(mustacheFile());

			mustache.execute(sw, prepareData()).flush();
		} catch (Throwable t) {
			halt(404,
					"Erro no servidor! "
							+ "Informe o texto a seguir ao administrador:<br>"
							+ t.getMessage());
		}

		return sw.toString();
	}

}

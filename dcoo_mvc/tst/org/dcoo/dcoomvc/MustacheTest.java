package org.dcoo.dcoomvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

public class MustacheTest {

	List<Item> items() {
		return Arrays.asList(
				new Item("Item 1", "$19.99", Arrays.asList(new Feature("New!"),
						new Feature("Awesome"))),
				new Item("Item 2", "$29.99", Arrays.asList(new Feature("Old!"),
						new Feature("Ugly"))));
	}

	static class Item {
		String name, price;
		List<Feature> features;

		public Item(String name, String price, List<Feature> features) {
			this.name = name;
			this.price = price;
			this.features = features;
		}
	}

	static class Feature {
		String description;

		public Feature(String description) {
			this.description = description;
		}
	}

	public static void main(String[] args) throws IOException {
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile("org/dcoo/dcoomvc/template.mustache");
		mustache.execute(new PrintWriter(System.out), new MustacheTest())
				.flush();
	}
}

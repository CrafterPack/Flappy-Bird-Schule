module flappybird {
	exports controller;
	exports model;
	exports view;

	requires transitive java.desktop;
	requires javafx.media;
	requires javafx.swing;
	requires json.simple;
	
	opens controller;
	opens view;
	opens model;
}
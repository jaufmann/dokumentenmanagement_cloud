package jena_test;

import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;

public class Szenario2 {
	
	public static void main(String[] args) {
		String fileName = "data/A-Box_Cloud_Dokumente.owl";
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		try {
			File file = new File(fileName);
			FileReader reader = new FileReader(file);
			model.read(reader,null);
			
			String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
			+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
			+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
			+ " SELECT ?Speicherort "
			+ " WHERE  { ?Dokument foaf:Dokument_hat_Verfasser ?Mitarbeiter ."
			+ "			 ?Dokument foaf:Speicherort ?Speicherort . "
			+ " 		 ?Dokument foaf:Schlagwort 'Kostenverteilung' ."
			+ "			 ?Mitarbeiter foaf:Name ?Name ."
			+ "			 ?x rdf:type mebase:Besprechungsprotokoll ."
			+ "			 ?Projekt foaf:Name 'Highnet' ."
			+ "			 FILTER regex(?Name,'Murakami')}";

			Query query = QueryFactory.create(sparQuery);
			QueryExecution qe = QueryExecutionFactory.create(query, model);
			ResultSet results = qe.execSelect();
			List var = results.getResultVars();
			
			while (results.hasNext()){
				QuerySolution qs = results.nextSolution();
				for(int i=0; i<var.size();i++){
					String va = var.get(i).toString();
					RDFNode node = qs.get(va);
					System.out.println(node.toString());
					getName(node.toString());
				}
			}
			MyOutputStream myOutput = new MyOutputStream();
			ResultSetFormatter.out(myOutput, results, query);
			String sparqlResults = myOutput.getString();
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void getName(String speicherort){
		String fileName = "data/A-Box_Cloud_Dokumente.owl";
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		try {
			File file = new File(fileName);
			FileReader reader = new FileReader(file);
			model.read(reader,null);
			
		String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
				+ " SELECT ?Name "
				+ " WHERE  { ?Dokument foaf:Speicherort '"+speicherort+"' ."
						+ "	 ?Dokument foaf:Name ?Name}";
				
				
				Query query = QueryFactory.create(sparQuery);
				QueryExecution qe = QueryExecutionFactory.create(query, model);
				ResultSet results = qe.execSelect();
				List var = results.getResultVars();
				
				while (results.hasNext()){
					QuerySolution qs = results.nextSolution();
					for(int i=0; i<var.size();i++){
						String va = var.get(i).toString();
						RDFNode node = qs.get(va);
						System.out.println(node.toString());
					}
				}
				MyOutputStream myOutput = new MyOutputStream();
				ResultSetFormatter.out(myOutput, results, query);
				String sparqlResults = myOutput.getString();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}

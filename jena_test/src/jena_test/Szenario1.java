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

public class Szenario1 {
	
	public static void main(String[] args) {
		String fileName = "data/A-Box_Cloud_Dokumente.owl";
		OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM);
		try {
			File file = new File(fileName);
			FileReader reader = new FileReader(file);
			model.read(reader,null);
			
			/*Aulesen von Speicherort aller Dateien zu einem bestimmtem Datum*/
			String sparQuery = "PREFIX foaf: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
					+ "	PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
					+ " PREFIX mebase: <http://www.semanticweb.org/alinasiebert/ontologies/2016/0/Cloud_Dokumente#>"
					+ " SELECT ?Speicherort ?Name "
					+ " WHERE  { ?x foaf:Erstellungsdatum '15-01-2016' ."
					+ "			 ?x foaf:Dokumenttyp 'Textdokument' ."
					+ "			 ?x foaf:Speicherort ?Speicherort ."
					+ "			 ?Dokument foaf:Dokument_gehoert_zu_Projekt ?Projekt ."
					+ "			 ?Projekt foaf:Name 'Highnet' ."
					+ "			 ?Dokument foaf:Name ?Name ."
					+ "			 FILTER regex(?Name,'Besprechungsprotokoll')}";
			
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
			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}

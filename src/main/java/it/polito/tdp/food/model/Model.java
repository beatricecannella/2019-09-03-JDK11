package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	private Graph<Portion, DefaultWeightedEdge> grafo;
	Map <Integer, Portion> idMap;
	FoodDao dao;
	List<Portion> soluzioneMigliore;
	int pesoTot;
	
	
	public Model() {
		idMap = new HashMap<>();
		
		dao = new FoodDao();
		dao.listAllPortions(idMap);
		
	}
	
	public void creaGrafo(int c) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getVertex(idMap, c));
		List<Adiacenza> archi = dao.getAdiacenze(idMap);
		
		for(Adiacenza a : archi) {
			
			if(grafo.vertexSet().contains(a.getP1()) && grafo.vertexSet().contains(a.getP2())) {
			Graphs.addEdge(grafo, a.getP1(), a.getP2(), a.getPeso());
		}
			}
		

	}

	
	
	public List<PortionConnesse> portionConnesse(Portion partenza){
		
		
			//ConnectivityInspector<Portion, DefaultWeightedEdge> ci = new ConnectivityInspector<Portion, DefaultWeightedEdge>(grafo);
			
		
			List<PortionConnesse> result = new ArrayList<>(); 
		
			for(DefaultWeightedEdge e: this.grafo.edgesOf(partenza)) {
				
				double peso = this.grafo.getEdgeWeight(e);
				
				Portion p = Graphs.getOppositeVertex(this.grafo, e, partenza);
				//genee.setPesoAdiacente(peso);
				result.add(new PortionConnesse(p, peso));
				}

			
			
			return result;
		}

	
	public int vertici() {
		return this.grafo.vertexSet().size();
	}
	public int archi() {
		return this.grafo.edgeSet().size();
	}
	
	public Graph<Portion, DefaultWeightedEdge> getGrafo(){
		return this.grafo;
	}

	public List<Portion> listaVertici(){
		List<Portion> lista = new ArrayList<>();
		
		for(Portion p: grafo.vertexSet()) {
			lista.add(p);
		}
		return lista;
	}
	
	
	public List<Portion> getPortion(){
		List<Portion> lista = new ArrayList<>();
		
		for(Portion p : this.listaVertici()) {
			if(!lista.contains(p))
			lista.add(p);
		}
		
		
		
		return lista;
	}
	
	public List<Portion> percorsoMigliore(Portion partenza, int n) {
		this.soluzioneMigliore = null ;
		this.pesoTot = 0;
		List<Portion> parziale = new ArrayList<>() ;
		parziale.add(partenza) ;
		
		cerca(parziale,partenza, n) ;
		
		return this.soluzioneMigliore ;
	}
	
	private void cerca(List<Portion> parziale, Portion partenza ,int n) {
		//caso terminale
		
		if(parziale.size() == n) {
			if(pesoTot<this.sommaPesi(parziale))
			this.soluzioneMigliore = new ArrayList<>(parziale) ;
			pesoTot = (int) this.sommaPesi(parziale);
			
			return ;
		}
		else {
			List<Portion> vicini = Graphs.neighborListOf(grafo, partenza);
			
			
			for(Portion p: vicini) {
	//			double peso = grafo.getEdgeWeight(grafo.getEdge(p, partenza));
				
					parziale.add(p);
					cerca(parziale, p, n);			
				parziale.remove(p);
				

		}
	
		}
	
	}
		public double sommaPesi(List<Portion> parziale) {
			double tot = 0;
				for(int i = 0; i<parziale.size(); i++) {
					tot += this.grafo.getEdgeWeight(this.grafo.addEdge(parziale.get(i),parziale.get(i+1)));
				}
			return tot;
			
	}
	
	public int pesoFinale() {
		return pesoTot;
	}
	
	
	
	
	
	
	
	
	
	
}

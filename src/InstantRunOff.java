import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class InstantRunOff {
	public ArrayList<ArrayList<String>> allVotes;
	private Set<String> allCandidates;
	private ArrayList<String> ranked;
	private int threshold;
	private int min = threshold;
	private int max = 0;
	private String winner;
	private String loser;
	
	public InstantRunOff(){
		super();
		allVotes = new ArrayList<ArrayList<String>>();
		allCandidates = new HashSet<String>();
		ranked = new ArrayList<String>();
	}
	
	public void loadIndividualVotes(String filename) throws FileNotFoundException{
		ArrayList<String> rank = new ArrayList<String>();
		String line;
		FileReader reader = new FileReader(filename);
		Scanner fileIn = new Scanner(reader);
		
		while (fileIn.hasNextLine()){
			line = fileIn.nextLine();
			rank.add(line);
			allCandidates.add(line);
		}
		allVotes.add(rank);
	}
	
	public void setThreshold(){
		threshold = allVotes.size()/2+1;
	}
	public void printAllVotes(){
		for(ArrayList individuals: allVotes){
			for(Object vote: individuals){
				System.out.print(vote+" ");
			}
			System.out.println();
		}
	}
	
	public void findWinner(ArrayList<ArrayList<String>> votingList){
		ArrayList<ArrayList<Object>> sumVotes = countVotes(votingList);
		setThreshold();
		for(ArrayList vote: sumVotes){
			if(ranked.size() == allCandidates.size()) break;
			else if (max >= threshold){
				ranked.add(winner);
				removeWinner(winner);
				findWinner(allVotes);
			}
			else {
				findWinner(adjustVotes(loser));
			}
		} 
	}
	
	public ArrayList<ArrayList<String>> adjustVotes(String loser){
		ArrayList<ArrayList<String>> copyAllVotes = new ArrayList<ArrayList<String>>();
		copyAllVotes = allVotes;
		for (ArrayList votes: copyAllVotes){
			if (votes.get(0).equals(loser)){
				votes.remove(0);
			}
		}
		return copyAllVotes;
	}
	public void removeWinner(String winner){
		for(ArrayList votes: allVotes){
			votes.remove(winner);
		}
	}
	
	public ArrayList<ArrayList<Object>> countVotes(ArrayList<ArrayList<String>> votingList){
		ArrayList<ArrayList<Object>> countedVotes = new ArrayList<ArrayList<Object>>();
		for(String candidate: allCandidates){
			int i = 0;
			ArrayList<Object> counts = new ArrayList<Object>();
			for(ArrayList votes: votingList){
				String votingFor = (String) votes.get(0);
				if(candidate.equals(votingFor)){
				++i;
				}
			else continue;
			}
			if (i<min){
				min = i;
				loser = candidate;
			}
			if (i>max){
				max = i;
				winner = candidate;
			}
			counts.add(candidate);
			counts.add(i);
			countedVotes.add(counts);
		}
		return countedVotes;
	}
	
/*	
	public int countVotes(String candidate, int position){
		int i = 0;
		if(ranked.contains(candidate)) return 0;
		else if (!allCandidates.contains(candidate)){
			++position;
			countVotes(candidate,position);
		}
		else{
			for(ArrayList votes: allVotes){
				String votingFor = (String) votes.get(position);
				if(candidate.equals(votingFor)){
					++i;
				}
				else continue;
		}
	}
		return i;
	}
	
	
	public void rank(){
		int threshold = allVotes.size()/2+1;
		int min = threshold;
		String loser = null;
		while(!allCandidates.isEmpty())
		for(String candidate: allCandidates){
			int count = countVotes(candidate, 0);
			ranking.put(candidate, count);
			if(count >= threshold){
				ranked.add(candidate);
			}
			if (count < min){
				min = count;
				loser = candidate;
			}
		allCandidates.remove(loser);
		rank();
		}
	}
	
	*/
	
	public void printCandidates(){
		for(String candidate: allCandidates){
			System.out.print(candidate+" ");
		}
	}
	
	public void printRank(){
		for (String candidate: ranked){
			System.out.println(candidate);
		}
	}
	public static void main(String[] args) throws FileNotFoundException{
		InstantRunOff attempt = new InstantRunOff();
		attempt.loadIndividualVotes("A");
		attempt.loadIndividualVotes("B");
		attempt.loadIndividualVotes("C");
		attempt.loadIndividualVotes("D");
		attempt.loadIndividualVotes("E");
		attempt.findWinner(attempt.allVotes);
		
	}
}

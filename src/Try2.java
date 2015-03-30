import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Try2 {
	public ArrayList<ArrayList<String>> allVotes;
	private Set<String> allCandidates;
	private int candidateSize;
	private ArrayList<String> ranked;
	private int threshold;
	private int min;
	private int max = 0;
	private String winner;
	private String loser = null;
	private Set<String> loserList;
	private ArrayList<ArrayList<String>> copyAllVotes;

	public Try2() {
		super();
		allVotes = new ArrayList<ArrayList<String>>();
		allCandidates = new HashSet<String>();
		ranked = new ArrayList<String>();
		loserList = new HashSet<String>();
	}

	public void loadIndividualVotes(String filename)
			throws IOException {
		ArrayList<String> rank = new ArrayList<String>();
		String line;
		FileReader reader = new FileReader(filename);
		Scanner fileIn = new Scanner(reader);

		while (fileIn.hasNextLine()) {
			line = fileIn.nextLine();
			rank.add(line);
			allCandidates.add(line);
		}
		setCandidateSize();
		allVotes.add(rank);
		fileIn.close();
		reader.close();
	}

	public void setThreshold() {
		threshold = allVotes.size() / 2 + 1;
	}
	
	public void setCandidateSize (){
		candidateSize = allCandidates.size();
	}

	public int getThreshold() {
		return threshold;
	}

	public void printAllVotes() {
		for (ArrayList individuals : allVotes) {
			for (Object vote : individuals) {
				System.out.print(vote + " ");
			}
			System.out.println();
		}
	}

	public void printVotingList(ArrayList<ArrayList<String>> votingList) {
		for (ArrayList individuals : votingList) {
			for (Object vote : individuals) {
				System.out.print(vote + " ");
			}
			System.out.println();
		}
	}

	public void printCandidates() {
		for (String candidate : allCandidates) {
			System.out.print(candidate + " ");
		}
	}

	public void countVotes(ArrayList<ArrayList<String>> votingList) {
		min = threshold;
		max = 0;
		for (String candidate : allCandidates) {
			int i = 0;
			ArrayList<Object> counts = new ArrayList<Object>();
			for (ArrayList<String> votes : votingList) {
				String votingFor = votes.get(0);
				if (candidate.equals(votingFor)) {
					++i;
				}
			}
			if (i < min) {
				min = i;
				loser = candidate;
			}
			if (i > max) {
				max = i;
				winner = candidate;
			}
		}
	}

	public void removeWinner(String winner) {
		for (ArrayList votes : allVotes) {
			votes.remove(winner);
		}
	}

	public ArrayList<ArrayList<String>> adjustVotes(ArrayList<ArrayList<String>> previousList, String loser) {
		copyAllVotes = new ArrayList<ArrayList<String>>();
		for (ArrayList<String> votes: allVotes){
			ArrayList<String> temp = new ArrayList();
			for (String candidate: votes){
				temp.add(candidate);
			}
			copyAllVotes.add(temp);
		}
		for (ArrayList votes : copyAllVotes) {
			votes.remove(loser);
			allCandidates.remove(loser);
		}
		//printVotingList(copyAllVotes);
		return copyAllVotes;
	}
	
	

	public void findRank(ArrayList<ArrayList<String>> votingList) {
		setThreshold();
		countVotes(votingList);
	while (ranked.size()< candidateSize){
		//System.out.print(winner);
		//System.out.println(max);
		if (max >= threshold) {
			ranked.add(winner);
			removeWinner(winner);
			allCandidates.remove(winner);
			allCandidates.addAll(loserList);
			loserList.clear();
			findRank(allVotes);
		} else {
			ArrayList<ArrayList<String>> adjustedList = adjustVotes(votingList, loser);
			//printVotingList(adjustedList);
			//System.out.println(loser);
			loserList.add(loser);
			findRank(adjustedList);
			
		}
	}

	}

	public void printRank() {
		for (String candidate : ranked) {
			System.out.println(candidate);
		}
	}

	public static void main(String[] args) throws IOException {
		Try2 attempt = new Try2();
		attempt.loadIndividualVotes("James");
		attempt.loadIndividualVotes("Kyle");
		attempt.loadIndividualVotes("Mollie");
		attempt.loadIndividualVotes("Lucy");
		attempt.loadIndividualVotes("Caleb");
		// attempt.printAllVotes();
		// attempt.printCandidates();
		// attempt.setThreshold();
		// System.out.println(attempt.getThreshold());
		// attempt.removeWinner("A");
		// attempt.countVotes(attempt.adjustVotes("D"));
		// attempt.printAllVotes();
		attempt.findRank(attempt.allVotes);
		attempt.printRank();

	}
}

package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.run();
    }

    record Round(String opponent, String you) {

        Long overallScore() {
            return matchScore() + yourScore();
        }

        Long yourScore() {
            return switch (you) {
                case "X" -> 1L;
                case "Y" -> 2L;
                case "Z" -> 3L;
                default -> throw new UnsupportedOperationException();
            };
        }

        Long opponentScore() {
            return switch (opponent) {
                case "A" -> 1L;
                case "B" -> 2L;
                case "C" -> 3L;
                default -> throw new UnsupportedOperationException();
            };
        }

        Long matchScore() {
            Long yourScore = this.yourScore();
            Long opponentScore = this.opponentScore();

            if ((yourScore - opponentScore) == 0L) {
                return 3L;
            }

            // 1 Rock, 2 Paper, 3 Scissor
            if ((yourScore == 1L && opponentScore == 2L)
                    || (yourScore == 2L && opponentScore == 3L)
                    || (yourScore == 3L && opponentScore == 1L)) {
                return 0L;
            }

            return 6L;
        }
    }

    final String path = "/Users/kevinwallis/projects/AdventOfCode-02/AdventOfCode-02/src/main/resources/";

    List<Round> rounds = new ArrayList<>();

    void run() throws IOException {
        this.readRounds();
        this.printOverallScore();
    }

    void readRounds() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(this.path + "input.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String[] values = line.split(" ");
                final String opponent = values[0];
                final String you = values[1];

                Round round = new Round(opponent, you);
                this.rounds.add(round);
            }
        }
    }

    void printOverallScore() {
        System.out.println(this.rounds.stream().map(Round::overallScore).reduce(Long::sum).get());
    }
}
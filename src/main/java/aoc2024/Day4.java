package aoc2024;

public class Day4 implements Day {

    @Override
    public int executePart1(String input) {
        Matrix matrix = Matrix.create(input);
        return matrix.calculateXMAS();
    }

    @Override
    public int executePart2(String input) {
        Matrix matrix = Matrix.create(input);
        return matrix.calculateX_MAS();
    }

    record Matrix(char[][] matrix) {

        static Matrix create(String input) {
            String[] lines = input.split("\n");
            char[][] matrix = new char[lines.length][];
            for (int i = 0; i < lines.length; i++) {
                matrix[i] = lines[i].replace("\r", "").toCharArray();
            }
            return new Matrix(matrix);
        }

        public int calculateXMAS() {
            String word = "XMAS";
            int count = 0;
            for (int i=0; i< matrix.length; i++) {
                for (int j=0; j< matrix[i].length; j++) {
                    if (matrix[i][j] == word.charAt(0)) {
                        count += countWords(i, j, word);
                    }
                }
            }
            return count;
        }

        public int calculateX_MAS() {
            int countX_MAS = 0;
            for (int i=0; i< matrix.length; i++) {
                for (int j=0; j< matrix[i].length; j++) {
                    if (matrix[i][j] == 'A') {
                        if ((i-1 >= 0 && j-1 >= 0) && (i+1 < matrix.length && j+1 < matrix[i].length)) {
                            boolean diagonalOne = ((matrix[i-1][j-1] == 'M' && matrix[i+1][j+1] == 'S') || (matrix[i-1][j-1] == 'S' && matrix[i+1][j+1] == 'M'));
                            boolean diagonalTwo = ((matrix[i-1][j+1] == 'M' && matrix[i+1][j-1] == 'S') || (matrix[i-1][j+1] == 'S' && matrix[i+1][j-1] == 'M'));
                            if (diagonalOne && diagonalTwo) {
                                countX_MAS++;
                            }
                        }
                    }
                }
            }
            return countX_MAS;
        }

        private int countWords(int i, int j, String word) {
            int count = 0;
            //LEFT
            boolean exists = true;
            for (int k=0; k<word.length() && exists; k++) {
                if (j-k < 0) exists = false;
                else if (matrix[i][j-k] != word.charAt(k)) exists = false;
            }
            if (exists) count++;
            //RIGHT
            exists = true;
            for (int k=0; k<word.length() && exists; k++) {
                if (j+k >= matrix[i].length) exists = false;
                else if (matrix[i][j+k] != word.charAt(k)) exists = false;
            }
            if (exists) count++;
            //UP
            exists = true;
            for (int k=0; k<word.length() && exists; k++) {
                if (i-k < 0) exists = false;
                else if (matrix[i-k][j] != word.charAt(k)) exists = false;
            }
            if (exists) count++;
            //DOWN
            exists = true;
            for (int k=0; k<word.length() && exists; k++) {
                if (i+k >= matrix.length) exists = false;
                else if (matrix[i+k][j] != word.charAt(k)) exists = false;
            }
            if (exists) count++;
            //UP-LEFT
            exists = true;
            for (int k=0; k<word.length() && exists; k++) {
                if (i-k < 0 || j-k < 0) exists = false;
                else if (matrix[i-k][j-k] != word.charAt(k)) exists = false;
            }
            if (exists) count++;
            //UP-RIGHT
            exists = true;
            for (int k=0; k<word.length() && exists; k++) {
                if (i-k < 0 || j+k >= matrix[i].length) exists = false;
                else if (matrix[i-k][j+k] != word.charAt(k)) exists = false;
            }
            if (exists) count++;
            //DOWN-LEFT
            exists = true;
            for (int k=0; k<word.length() && exists; k++) {
                if (i+k >= matrix.length || j-k < 0) exists = false;
                else if (matrix[i+k][j-k] != word.charAt(k)) exists = false;
            }
            if (exists) count++;
            //DOWN-RIGHT
            exists = true;
            for (int k=0; k<word.length() && exists; k++) {
                if (i+k >= matrix.length || j+k >= matrix[i].length) exists = false;
                else if (matrix[i+k][j+k] != word.charAt(k)) exists = false;
            }
            if (exists) count++;
            return count;
        }
    }

}
